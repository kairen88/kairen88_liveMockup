/*/*
 * Created on 16-abr-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package zz.snipsnap.utils.jibx;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

import zz.snipsnap.client.core.AttachmentDescriptor;
import zz.utils.Pool;


/**
 * This class gives access to our JiBX bindings.
 */
public class Binder
{
	private IBindingFactory itsBindingFactory;	
	private static Binder INSTANCE;
	
	public static Binder getInstance()
	{
		if (INSTANCE == null) INSTANCE = new Binder(JiBXListWrapper.class);
		return INSTANCE;
	}

	/**
	 * A pool of marshalling contexts.
	 */
	private Pool<IMarshallingContext> MARSHALLING_CONTEXT_POOL = 
		new Pool<IMarshallingContext> ()
	{
		protected IMarshallingContext create()
		{
			try
			{
				IMarshallingContext theContext = itsBindingFactory.createMarshallingContext();
				theContext.setIndent(1, "\n", '\t');
				return theContext;
			}
			catch (JiBXException e)
			{
				throw new RuntimeException("Error creating context", e);
			}
		}
	};
	
	private Pool<IUnmarshallingContext> UNMARSHALLING_CONTEXT_POOL = 
		new Pool<IUnmarshallingContext> ()
	{
		protected IUnmarshallingContext create()
		{
			try
			{
				return itsBindingFactory.createUnmarshallingContext();
			}
			catch (JiBXException e)
			{
				throw new RuntimeException("Error creating context", e);
			}
		}
	};
	

	

	private Binder(Class aBindingClass)
	{
		try
		{
			itsBindingFactory = BindingDirectory.getFactory(aBindingClass);
		} 
		catch (JiBXException e)
		{
			throw new RuntimeException("Error initializing binder", e);
		}
	}
	
	/**
	 * Reads the provided xml stream and tries to unmarshall its content.
	 */
	public Object unmarshall (Reader aReader) throws JiBXException
	{
		Object theResult;
		IUnmarshallingContext theContext = UNMARSHALLING_CONTEXT_POOL.request();
		try
		{			
			theResult = theContext.unmarshalDocument (aReader, null);
		}
		finally
		{
			UNMARSHALLING_CONTEXT_POOL.release(theContext);
		}
		
		return theResult;
	}

	/**
	 * Reads the provided xml string and tries to unmarshall its content.
	 * If a reader is available, it is better to use 
	 * {@link #unmarshall(Reader)}, which has less overhead.
	 */
	public Object unmarshall (String aXMLString) throws JiBXException
	{
		StringReader theReader = new StringReader(aXMLString);
		return unmarshall(theReader);
	}

	/**
	 * Creates an XML represention of the provided object and
	 * outputs it into the specified writer
	 */
	public void marshall(Object aObject, Writer aWriter) throws JiBXException
	{
		IMarshallingContext theContext = MARSHALLING_CONTEXT_POOL.request();
		try
		{
			theContext.marshalDocument(aObject, null, null, aWriter);
		}
		finally
		{
			MARSHALLING_CONTEXT_POOL.release(theContext);
		}
	}
	/**
	 * Creates an XML string representing the provided object.
	 */
	public String marshall(Object aObject) throws JiBXException
	{
		StringWriter theWriter = new StringWriter ();
		marshall(aObject, theWriter);
		return theWriter.toString();
	}
}


