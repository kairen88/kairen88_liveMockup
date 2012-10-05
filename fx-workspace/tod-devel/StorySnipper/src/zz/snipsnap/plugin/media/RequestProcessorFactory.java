/*
 * Created on Mar 28, 2005
 */
package zz.snipsnap.plugin.media;

import java.util.HashMap;
import java.util.Map;

/**
 * This singleton permits to obtain instances of {@link zz.snipsnap.plugin.media.AbstractRequestProcessor}
 * given a request name.
 * It is also the place where processors are registered.
 * @author gpothier
 */
public class RequestProcessorFactory
{	
	private static RequestProcessorFactory INSTANCE = new RequestProcessorFactory();

	public static RequestProcessorFactory getInstance()
	{
		return INSTANCE;
	}

	private RequestProcessorFactory()
	{
		registerProcessor(GetMediaInfoProcessor.REQUEST_NAME, GetMediaInfoProcessor.class);
		registerProcessor(GetThumbnailProcessor.REQUEST_NAME, GetThumbnailProcessor.class);
		registerProcessor(ListMediaProcessor.REQUEST_NAME, ListMediaProcessor.class);
		registerProcessor(ShowMediaProcessor.REQUEST_NAME, ShowMediaProcessor.class);
		registerProcessor(GetResourceProcessor.REQUEST_NAME, GetResourceProcessor.class);
	}

	/**
	 * Map of registered processor classes.
	 */
	private Map<String, Class> itsProcessors = new HashMap<String, Class>();
	
	public void registerProcessor (String aQueryName, Class aProcessorClass)
	{
		itsProcessors.put(aQueryName, aProcessorClass);
	}
	
	/**
	 * Creates a new {@link AbstractRequestProcessor} given a request name.
	 */
	public AbstractRequestProcessor createProcessor (String aQueryName) 
	throws MediaPluginException
	{
		Class theClass = itsProcessors.get(aQueryName);
		if (theClass == null) throw new MediaPluginException("Unknown query name: "+aQueryName);
		
		try
		{
			AbstractRequestProcessor theProcessor = 
				(AbstractRequestProcessor) theClass.newInstance();
			
			return theProcessor;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		
	}

}
