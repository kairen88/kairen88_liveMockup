/**
 * Created by IntelliJ IDEA.
 * User: g1
 * Date: 11 mars 2004
 * Time: 22:54:20
 * To change this template use File | Settings | File Templates.
 */
package net.basekit.models.label;

import java.awt.Font;

import zz.utils.Utils;
import zz.utils.ui.Fonts;

/**
 * Default implementation of {@link ActionWidgetModel}.
 * @author gpothier
 */
public class DefaultLabelWidgetModel extends AbstractLabelWidgetModel
{
	private String itsTitle;
	private String itsDescription;
	private boolean itsEnabled = true;
	private Font itsTitleFont = Fonts.DEFAULT_FONT_PLAIN;

	public DefaultLabelWidgetModel ()
	{
	}
	
	public DefaultLabelWidgetModel (String aTitle)
	{
		itsTitle = aTitle;
	}

	public String getTitle ()
	{
		return itsTitle;
	}

	public String getDescription ()
	{
		return itsDescription;
	}

	public boolean isEnabled ()
	{
		return itsEnabled;
	}

	public void setTitle (String aTitle)
	{
		boolean theChanged = Utils.different (itsTitle, aTitle);
		itsTitle = aTitle;
		if (theChanged) fireLabelDataChanged();
	}


	public void setDescription (String aDescription)
	{
		boolean theChanged = Utils.different (itsDescription, aDescription);
		itsDescription = aDescription;
		if (theChanged) fireLabelDataChanged();
	}

	public void setEnabled (boolean aEnabled)
	{
		boolean theChanged = itsEnabled != aEnabled;
		itsEnabled = aEnabled;
		if (theChanged) fireLabelDataChanged();
	}
	
	public Font getTitleFont ()
	{
		return itsTitleFont;
	}
	
	public void setTitleFont (Font aTitleFont)
	{
		itsTitleFont = aTitleFont;
	}
}
