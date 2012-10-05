import java.io.*;
import java.io.File;
import java.io.FileWriter;

/*
 * Created on Apr 23, 2004
 */
/**
 * 
 * @author gpothier
 */
public class LicenseUpdater
{
	public static void main (String[] args) throws IOException
	{
		String theLicenseFilename = null;
		String theTarget = null;

		for (int i = 0; i < args.length;)
		{
			String theString = args[i++];
			if ("-l".equals(theString)) theLicenseFilename = args[i++];
			else if ("-f".equals(theString)) theTarget = args[i++];
		}
		
		if (theLicenseFilename == null || theTarget == null)
		{
			System.err.println("Usage: LicenseUpdater -l <license file> -f <target file/dir>");
			System.exit(1);
		}
		
		FileReader theLicenseReader = new FileReader (theLicenseFilename);
		StringBuffer theBuffer = new StringBuffer ();
		int c;
		while ((c = theLicenseReader.read()) != -1) theBuffer.append((char)c);
		String theLicense = theBuffer.toString();
		
		File theRoot = new File (theTarget);
		
		processFile(theRoot, theLicense);
	}
	
	private static final int STATE_BEGIN = 0;
	private static final int STATE_SLASH1 = 1;
	private static final int STATE_STAR1 = 2;
	private static final int STATE_STAR2 = 3;
	
	private static void processFile (File aFile, String aLicenseText) throws IOException
	{
		if (aFile.isDirectory())
		{
			System.out.println("Processing directory: "+aFile.getName());
			File[] theFiles = aFile.listFiles();
			for (int i = 0; i < theFiles.length; i++)
			{
				File theFile = theFiles[i];
				processFile(theFile, aLicenseText);
			}
		}
		else if (aFile.isFile() && aFile.getName().endsWith(".java"))
		{
			System.out.println("Processing Java file: "+aFile.getName());			
			processJavaFile(aFile, aLicenseText);
		}
	}

	private static void processJavaFile (File aFile, String aLicenseText) throws IOException, FileNotFoundException
	{
		File theDraft = new File (aFile.getAbsolutePath()+".draft");
		FileWriter theWriter = new FileWriter (theDraft);
		FileReader theReader = new FileReader (aFile);
		
		int state = STATE_BEGIN;
		int c;
		while ((c = theReader.read()) != -1)
		{
			switch (state)
			{
				case STATE_BEGIN:
					theWriter.write(c);
					if (c == '/') state = STATE_SLASH1;
					break;
					
				case STATE_SLASH1:
					theWriter.write(c);
					if (c == '*')
					{
						theWriter.write('\n');
						theWriter.write(aLicenseText);
						state = STATE_STAR1;
					}
					
					break;
					
				case STATE_STAR1:
					if (c == '*') state = STATE_STAR2;
					break;
					
				case STATE_STAR2:
					if (c == '/') 
					{
						theWriter.write("\n*/\n");
						while ((c = theReader.read()) != -1) theWriter.write(c);
					}
					else state = STATE_STAR1;
					break;
					
			}
		}
		
		theWriter.flush();
		
		aFile.delete();
		theDraft.renameTo(aFile);
	}
}
