/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.upload;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import zz.snipsnap.storysnipper.model.MediaCollection;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.ImageListComponent;
import zz.snipsnap.storysnipper.ui.widgets.imagelist.ImageListView;
import zz.utils.ProgressModel;
import zz.utils.notification.IEvent;
import zz.utils.notification.IEventListener;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.WaltzFrame;
import zz.waltz.api.action.DefaultActionModel;
import foxtrot.Job;
import foxtrot.Worker;

/**
 * Permits to browse the filesystem and upload files to a story.
 * @author gpothier
 */
public class UploadComponent extends WaltzComponent 
{
	/**
	 * The collection to which to upload the files.
	 */
	private MediaCollection itsMediaCollection;
	
	private FolderListModel itsModel;
	private ImageListComponent<File> itsImageListComponent;
	private JLabel itsFolderLabel;
	
	private IEventListener<File> itsSelectedListener = new IEventListener<File>()
	{
		public void fired(IEvent aEvent, File aData)
		{
			if (aData instanceof File)
			{
				File theFile = (File) aData;
				if (theFile.isDirectory()) setFolder(theFile);
				else upload(Collections.singletonList(theFile)); 
			}
		}
	};

	public UploadComponent(MediaCollection aMediaCollection)
	{
		itsMediaCollection = aMediaCollection;
		itsModel = new FolderListModel();
		
		itsImageListComponent = new ImageListComponent<File>(itsModel.getEntries(), FSThumbnailsCache.getInstance());
		
		itsImageListComponent.eObjectSelected().addListener(itsSelectedListener);
		itsFolderLabel = new JLabel();
		
		setFolder(FileSystemView.getFileSystemView().getHomeDirectory());
	}
	
	protected void setFolder (File aFolder)
	{
		itsImageListComponent.pHighlightedObjects().clear();
		itsModel.pFolder.set(aFolder);
		itsFolderLabel.setText(aFolder.getPath());
	}
	
	protected void upload (File aFile, ProgressModel<Long> aProgressModel) throws IOException
	{
		int theAttempt = 1;
		
		while (true)
		{
			try
			{
				System.out.println("Trying to upload: "+aFile+" attempt "+theAttempt);
				itsMediaCollection.addMedia(aFile, aProgressModel);
				System.out.println("ok");
				return;
			}
			catch (IOException e)
			{
				if (theAttempt++ < 3) e.printStackTrace();
				else throw e;
			}
		}
	}
	
	protected void upload (final List<File> aFiles)
	{
		Worker.post (new Job()
		{
			public Object run()
			{
				int theFilesCount = aFiles.size();
				System.out.println("Uploading "+theFilesCount+" files");
				
				ProgressModel<Integer> theFilesProgressModel = new ProgressModel<Integer>(0, theFilesCount);
				ProgressModel<Long> theFileProgressModel = new ProgressModel<Long>(0l, 0l);
				
				ProgressComponent theProgressComponent = 
					new ProgressComponent(theFileProgressModel, theFilesProgressModel);

				WaltzFrame theFrame = WaltzFrame.show(theProgressComponent, WaltzFrame.DO_NOTHING_ON_CLOSE);
				
				List<File> theFailedFiles = new ArrayList<File>();
				
				int theProgress = 1;
				for (File theFile : aFiles)
				{
					theProgressComponent.setMessage(theFile.getName());
					
					try
					{
						upload(theFile, theFileProgressModel);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						theFailedFiles.add(theFile);
					}
					
					theFilesProgressModel.pCurrent.set(theProgress++);
					if (theProgressComponent.isCanceled()) break;
				}
				
				theFrame.dispose();
				
				if (theFailedFiles.size() > 0)
				{
					
					StringBuilder theBuilder = new StringBuilder("The following files failed to upload:\r\n");
					
					for (File theFile : theFailedFiles)
					{
						theBuilder.append(theFile.getName());
						theBuilder.append("\r\n");
					}
					
					JTextArea theTextArea = new JTextArea(theBuilder.toString());
					theTextArea.setEditable(false);
					
					JOptionPane.showMessageDialog(
							theFrame, 
							theTextArea, 
							"Upload errors", 
							JOptionPane.ERROR_MESSAGE);
				}
				return null;
			}
	
		});
	}

	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		aCanvas.add(itsImageListComponent, BorderLayout.CENTER);
		renderToolbar(aCanvas.createCanvas(BorderLayout.NORTH));
	}
	
	private void renderToolbar (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout(FlowLayout.LEFT));
		aCanvas.addAction(BUTTON_ACTION, new ParentFolder(), null);
		aCanvas.addAction(BUTTON_ACTION, new UploadFolder(), null);
		aCanvas.addAction(BUTTON_ACTION, new UploadHighlighted(), null);
		aCanvas.add(itsFolderLabel, null);
	}
	
	
	private class ParentFolder extends DefaultActionModel
	{
		public ParentFolder()
		{
			super ("Up");
		}

		public void performed(JComponent aComponent)
		{
			File theCurrentFolder = itsModel.pFolder.get();
			File theParentFolder = theCurrentFolder.getParentFile();
			
			if (theParentFolder != null) setFolder(theParentFolder);
		}
		
		
	}
	
	private class UploadFolder extends DefaultActionModel
	{
		public UploadFolder()
		{
			super ("Upload all in folder");
		}

		public void performed(JComponent aComponent)
		{
			File theFolder = itsModel.pFolder.get();
			File[] theFiles = theFolder.listFiles();
			List<File> theFilesList = new ArrayList<File>(theFiles.length);
			for (File theFile : theFiles) 
			{
				if (! theFile.isDirectory()) theFilesList.add(theFile);
			}
			
			upload(theFilesList);
		}
		
	}
	
	private class UploadHighlighted extends DefaultActionModel
	{
		public UploadHighlighted()
		{
			super ("Upload highlighted items");
		}

		public void performed(JComponent aComponent)
		{
			List<File> theFilesList = new ArrayList<File>();

			for (Object theItem : itsImageListComponent.pHighlightedObjects())
			{
				File theFile = (File) theItem;
				if (! theFile.isDirectory()) theFilesList.add(theFile);
			}
			
			upload(theFilesList);
		}
		
	}
	
}
