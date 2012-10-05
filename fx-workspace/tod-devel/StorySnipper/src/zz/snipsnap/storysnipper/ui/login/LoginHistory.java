/*
 * Created on Mar 30, 2005
 */
package zz.snipsnap.storysnipper.ui.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class LoginHistory
{
	private static LoginHistory INSTANCE = new LoginHistory();

	public static LoginHistory getInstance()
	{
		return INSTANCE;
	}

	private LoginHistory()
	{
	}
	
	private static final String FILENAME = "login.history";
	private static final int  MAX_ENTIES = 10;
	
	private List<Entry> itsEntries = new ArrayList<Entry>();
	
	/**
	 * Loads the entries from the file.
	 */
	public List<Entry> load()
	{
		itsEntries.clear();
		
		try
		{
			BufferedReader theReader = new BufferedReader(new FileReader(FILENAME));
			
			while (true)
			{
				String theSpaceUrl = theReader.readLine();
				if (theSpaceUrl == null) break;
				
				String theStoryName = theReader.readLine();
				String theUserName = theReader.readLine();
				String thePassword = theReader.readLine();
				String theBlank = theReader.readLine();
				
				if (theBlank.length() > 0) throw new RuntimeException("Expected blank line, found: "+theBlank);
				
				Entry theEntry = new Entry(theSpaceUrl, theStoryName, theUserName, thePassword);
				itsEntries.add (theEntry);
			}
			
			return itsEntries;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public void save()
	{
		try
		{
			File theFile = new File(FILENAME);
			theFile.delete();
			PrintWriter theWriter = new PrintWriter(theFile);
			int theLength = Math.min (itsEntries.size(), MAX_ENTIES);
			for (Iterator theIterator = itsEntries.listIterator(itsEntries.size() - theLength); theIterator.hasNext();)
			{
				Entry theEntry = (Entry) theIterator.next();
				
				theWriter.println(theEntry.getSpaceUrl());
				theWriter.println(theEntry.getStoryName());
				theWriter.println(theEntry.getUserName());
				theWriter.println(theEntry.getPassword());
				theWriter.println();
			}
			
			theWriter.flush();
			theWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addEntry (String aSpaceUrl, String aStoryName, String aUserName, String aPassword)
	{
		Entry theEntry = new Entry(aSpaceUrl, aStoryName, aUserName, aPassword);
		itsEntries.add (theEntry);
	}
	
	public static class Entry
	{
		private String itsSpaceUrl;
		private String itsStoryName;
		private String itsUserName;
		private String itsPassword;
		
		public Entry(String aSpaceUrl, String aStoryName, String aUserName, String aPassword)
		{
			itsSpaceUrl = aSpaceUrl;
			itsStoryName = aStoryName;
			itsUserName = aUserName;
			itsPassword = aPassword;
		}

		public String getPassword()
		{
			return itsPassword;
		}

		public String getSpaceUrl()
		{
			return itsSpaceUrl;
		}

		public String getStoryName()
		{
			return itsStoryName;
		}

		public String getUserName()
		{
			return itsUserName;
		}
	}
}
