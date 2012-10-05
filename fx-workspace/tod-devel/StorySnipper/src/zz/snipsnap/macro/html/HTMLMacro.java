/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.macro.html;

import java.io.IOException;
import java.io.Writer;

import org.radeox.util.Encoder;
import org.snipsnap.render.macro.SnipMacro;
import org.snipsnap.render.macro.parameter.SnipMacroParameter;

public class HTMLMacro extends SnipMacro
{

	public String getName()
	{
		return "html";
	}

	public void execute(Writer writer, SnipMacroParameter params) throws IllegalArgumentException, IOException
	{
		if (params.getLength() == 0)
		{
			String theContent = params.getContent();
			String theUnescaped = Encoder.unescape(theContent);
			String theUnescaped2= Encoder.unescape(theUnescaped);
			writer.write(theUnescaped2);
			return;
		}
		else
		{
			throw new IllegalArgumentException("Number of arguments does not match");
		}
	}

}