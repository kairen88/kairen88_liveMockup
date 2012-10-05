/*
 * Created on Mar 23, 2005
 */
package zz.snipsnap.client.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Vector;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.xmlrpc.XmlRpcClient;

/**
 * Instances of this class represents connections to a SnipSnap instance (or
 * space)
 * 
 * @author gpothier
 */
public class SnipSnapSpace
{
	private static final String COOKIE_NAME = "SnipSnapUser";
	
	private XmlRpcClient itsClient;
	
	private AttachmentsManager itsAttachmentsManager = new AttachmentsManager(this);
	private SnipsManager itsSnipsManager = new SnipsManager(this);

	/**
	 * A cookie returned by the server when logging in.
	 */
	private String itsSessionCookie;
	
	private HttpClient itsHttpClient;
	
	private final URI itsUri;
	private final String itsUsername;
	private final String itsPassword;
	

	/**
	 * Creates a SnipSnapInstance
	 * @param aUrl Root URL of the instance. Usually http://host:8668/[prefix/]
	 */
	public SnipSnapSpace(String aUrl, String aUsername, String aPassword) throws MalformedURLException
	{
		itsUri = URI.create(aUrl);
		itsUsername = aUsername;
		itsPassword = aPassword;
	}
	
	/**
	 * Returns an XmlRpc client object connected to the snipsnap instance on the server.
	 */
	protected XmlRpcClient getXmlRpcClient() throws MalformedURLException
	{
		if (itsClient == null)
		{
			itsClient = new XmlRpcClient(resolve("RPC2").toURL());
			itsClient.setBasicAuthentication(itsUsername, itsPassword);			
		}
		
		return itsClient;
	}
	
	protected HttpClient getHttpClient()
	{
		if (itsHttpClient == null) itsHttpClient = new HttpClient();
		return itsHttpClient;
	}
	
	/**
	 * Returns the base Uri of this space.
	 */
	public URI getUri()
	{
		return itsUri;
	}
	
	/**
	 * Returns an URL that represents the given query for this space.
	 */
	public URI resolve (String aQuery) 
	{
		return itsUri.resolve(aQuery);
	}
	
	/**
	 * Returns the relative URI corresponding to the specified absolute URI.
	 */
	public URI getRelativeUri(URI aUri)
	{
		return itsUri.relativize(aUri);
	}
	
	/**
	 * Executes a RPC call to this space.
	 * @param aMethodName The name of the method to invoke
	 * @param aParameters The parameters to pass to the method
	 * @return The result of the call
	 */
	public Object executeRPC (String aMethodName, Object... aParameters) 
	{
		try
		{
			Vector<Object> theParams = new Vector<Object>();
			if (aParameters != null) for (Object theParam : aParameters) theParams.add(theParam);
			Object theResult = getXmlRpcClient().execute(aMethodName, theParams);
			return theResult;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error executing XML-RPC", e);
		}
	}

	/**
	 * Returns an object that permits to manage attachments.
	 */
	public AttachmentsManager getAttachmentsManager()
	{
		return itsAttachmentsManager;
	}

	/**
	 * Returns an object that permits to manage snips.
	 */
	public SnipsManager getSnipsManager()
	{
		return itsSnipsManager;
	}
	
	/**
	 * Creates an authenticated http connection
	 */
	public PostMethod createHttpPost(URI aUri) throws IOException
	{
		if (itsSessionCookie == null)
		{
			itsSessionCookie = httpLogin();
			if (itsSessionCookie == null) throw new IOException("Cannot login");
		}
		
		PostMethod thePost = new PostMethod(aUri.toString());

		thePost.addParameter("Cookie", itsSessionCookie);

		return thePost;
	}
	
	/**
	 * Tries to login using http protocol
	 * @return A (set of) cookie(s) that identifies the session on the server
	 * @throws IOException 
	 */
	private String httpLogin() throws IOException
	{
		URI theUri = resolve(String.format(
				"exec/authenticate?login=%s&password=%s&referer=",
				itsUsername,
				itsPassword));
		
		PostMethod thePost = new PostMethod(theUri.toString());
		thePost.setFollowRedirects(false);
		getHttpClient().executeMethod(thePost);
		Header[] theResponseHeaders = thePost.getResponseHeaders("Set-Cookie");
		
		
		
		StringBuilder theCookies = new StringBuilder();
		
		for (Header theHeader : theResponseHeaders)
		{
			String theCookie = theHeader.getValue();
			int theIndex = theCookie.indexOf(';');
			if (theIndex != -1) theCookie = theCookie.substring(0, theIndex);
			theCookies.append(theCookie);
			theCookies.append(';');
		}
		
		return theCookies.toString();
	}
	
}
