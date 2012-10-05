/*
 * Created on Mar 23, 2005
 */
package zz.snipsnap.client.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.jibx.runtime.JiBXException;

import zz.snipsnap.utils.jibx.Binder;
import zz.snipsnap.utils.jibx.JiBXListWrapper;
import zz.utils.Empty;
import zz.utils.ProgressModel;
import zz.utils.properties.IRWProperty;

public class AttachmentsManager extends AbstractManager
{
	public AttachmentsManager(SnipSnapSpace aSpace)
	{
		super(aSpace);
	}
	
	/**
	 * Returns the descriptions of all the files attached to a given snip.
	 */
	public Iterable<AttachmentDescriptor> getAttachments (String aSnipName)
	{
		try
		{
			String theXML = (String) getSpace().executeRPC("snipSnap.getAttachments", aSnipName);
			JiBXListWrapper theWrapper = (JiBXListWrapper) Binder.getInstance().unmarshall(theXML);
			return theWrapper.getList();
		}
		catch (JiBXException e)
		{
			e.printStackTrace();
			return Empty.ITERABLE;
		}
	}
	
	public InputStream getAttachmentInputStream(String aSnipName, String aFileName) throws IOException
	{
		URL theUrl = getSpace().resolve("space/"+aSnipName+"/"+aFileName).toURL();
		URLConnection theConnection = theUrl.openConnection();
		return theConnection.getInputStream();
	}
	
	public AttachmentDescriptor attach (
			String aSnipName, 
			String aAttachmentName, 
			String aMimeType, 
			PartSource aPartSource) throws IOException
	{
		URI theUri = getSpace().resolve("exec/upload?name="+aSnipName+"&upload=true");
		PostMethod thePost = getSpace().createHttpPost(theUri);
		
		thePost.addParameter("Accept", "*/*");
		thePost.addParameter("Cache-Control", "no-cache");
		thePost.addRequestHeader("Referer", "");
		
		Part[] theParts = {
				new FilePart(
						"file", 
						aPartSource,
						aMimeType,
						""),
		};
		
		thePost.setRequestEntity(new MultipartRequestEntity(theParts, thePost.getParams()));

		int theStatus = getSpace().getHttpClient().executeMethod(thePost);
		
		if (theStatus != HttpStatus.SC_OK) 
			throw new IOException("Upload failed for "+aAttachmentName+", status: "+theStatus);
		
		return new AttachmentDescriptor(aAttachmentName, aMimeType, aPartSource.getLength(), null, null); 
	}
	
	public AttachmentDescriptor attach (
			String aSnipName, 
			String aAttachmentName, 
			String aMimeType, 
			long aSize,
			InputStream aInputStream,
			ProgressModel<Long> aProgressModel) throws IOException
	{
		return attach (
				aSnipName, 
				aAttachmentName, 
				aMimeType, 
				new MyStreamPartSource(aAttachmentName, aSize, aInputStream, aProgressModel));
	}
	
	public AttachmentDescriptor attach (
			String aSnipName, 
			String aAttachmentName,
			String aMimeType, 
			File aFile,
			ProgressModel<Long> aProgressModel) throws IOException
	{
		if (! aFile.exists()) throw new RuntimeException("File does not exist");

		return attach(
				aSnipName, 
				aAttachmentName, 
				aMimeType, 
				new MyFilePartSource(aFile, aProgressModel));
	}
	
	/**
	 * Removes an attachment from a snip
	 */
	public void detach(String aSnipName, AttachmentDescriptor aAttachment)
	{
		detach(aSnipName, aAttachment.getName());
	}

	/**
	 * Removes an attachment from a snip
	 */
	public void detach(String aSnipName, String aFileName)
	{
		boolean theResult = (Boolean) getSpace().executeRPC(
				"snipSnap.removeAttachment", 
				aSnipName,
				aFileName);

		if (! theResult) throw new RuntimeException("Could not detach");
	}
	
	private static class MyFilePartSource implements PartSource
	{
		private File itsFile;
		private ProgressModel<Long> itsProgressModel;

		public MyFilePartSource(File aFile, ProgressModel<Long> aProgressModel)
		{
			itsFile = aFile;
			itsProgressModel = aProgressModel;
		}

		public long getLength()
		{
			return itsFile.length();
		}

		public String getFileName()
		{
			return itsFile.getName();
		}

		public InputStream createInputStream() throws IOException
		{
			itsProgressModel.pTotal.set(getLength());
			return new ProgressInputStream(new FileInputStream(itsFile), itsProgressModel.pCurrent);
		}
	}
	
	private static class MyStreamPartSource implements PartSource
	{
		private String itsFileName;
		private long itsLength;
		private InputStream itsStream; 

		public MyStreamPartSource(String aFileName, long aLength, InputStream aStream, ProgressModel<Long> aProgressModel)
		{
			itsFileName = aFileName;
			itsLength = aLength;
			aProgressModel.pTotal.set(aLength);
			itsStream = new ProgressInputStream(aStream, aProgressModel.pCurrent);
		}

		public long getLength()
		{
			return itsLength;
		}

		public String getFileName()
		{
			return itsFileName;
		}

		public InputStream createInputStream() throws IOException
		{
			return itsStream;
		}
	}
	
	/**
	 * A filter that keeps track of reading progress
	 * @author gpothier
	 */
	private static class ProgressInputStream extends FilterInputStream
	{
		private IRWProperty<Long> itsProgressProperty;
		private long itsProgress = 0;
		private long itsMark;

		public ProgressInputStream(InputStream aIn, IRWProperty<Long> aProgressProperty)
		{
			super(aIn);
			
			itsProgressProperty = aProgressProperty;
		}
		
		public synchronized void mark(int aReadlimit)
		{
			itsMark = itsProgress;
			super.mark(aReadlimit);
		}

		public synchronized void reset() throws IOException
		{
			itsProgress = itsMark;
			super.reset();
		}

		private void progress (int aAmount)
		{
			itsProgressProperty.set(itsProgress += aAmount);
		}
		
		public int read() throws IOException
		{
			int theResult = in.read();
			progress (1);
			return theResult;
		}

		public int read(byte[] aB, int aOff, int aLen) throws IOException
		{
			int theResult = in.read(aB, aOff, aLen);
			if (theResult > 0) progress(theResult);
			return theResult;
		}

		public int read(byte[] aB) throws IOException
		{
			int theResult = in.read(aB);
			if (theResult > 0) progress(theResult);
			return theResult;
		}
		
		
	}

}
