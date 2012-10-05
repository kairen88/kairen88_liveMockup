/**
 * Created by IntelliJ IDEA.
 * User: Guillaume
 * Date: 5 nov. 2002
 * Time: 22:46:21
 */
package zz.csg;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import zz.utils.ui.ResourceUtils;
import zz.utils.ui.text.XFont;



public class Resources
{
	public static final ImageIcon ICON_MISSING = loadIcon("RedCross.png");
	public static final ImageIcon ICON_TRUE = loadIcon("GreenTick.png");
	public static final ImageIcon ICON_FALSE = loadIcon ("RedCross.png");

	public static final ImageIcon ICON_DOWN_ARROW = loadIcon ("DownArrow.png");
	public static final ImageIcon ICON_RIGHT_ARROW = loadIcon ("RightArrow.png");

	public static final ImageIcon ICON_WHITE_SELECTION_HANDLE = loadIcon ("WhiteSelectionHandle.png");
	public static final ImageIcon ICON_BLACK_SELECTION_HANDLE = loadIcon ("BlackSelectionHandle.png");

    public static final ImageIcon ICON_GRAPHICEDITOR_RECTANGLE = loadIcon ("graphiceditor/rectangle.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_ELLIPSE = loadIcon ("graphiceditor/ellipse.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_TEXT = loadIcon ("graphiceditor/text.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_LINE = loadIcon ("graphiceditor/line.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_FLOWTEXT = loadIcon ("graphiceditor/flowText.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_BALLOON = loadIcon ("graphiceditor/balloon.png");
    public static final ImageIcon ICON_GRAPHICEDITOR_IMAGE = loadIcon ("graphiceditor/image.png");
    public static final ImageIcon ICON_GRAHICEDITOR_SELECT = loadIcon ("graphiceditor/select.png");
    public static final BufferedImage IMAGE_GRAHICEDITOR_BACKGROUND = loadImage ("graphiceditor/editorBackground.png");
    
	
	public static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();
	public static final Cursor LINK_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	public static final Cursor BUSY_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
	public static final Cursor CROSS_CURSOR = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	public static final Cursor TEXT_CURSOR = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
	public static final Cursor MOVE_CURSOR = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	public static final Cursor SW_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
	public static final Cursor SE_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
	public static final Cursor NW_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
	public static final Cursor NE_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
	public static final Cursor N_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
	public static final Cursor S_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
	public static final Cursor E_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
	public static final Cursor W_RESIZE_CURSOR = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);

	private static ImageIcon loadIcon (String aName)
	{
		return ResourceUtils.loadIconResource(Resources.class, aName);
	}
	
	private static BufferedImage loadImage (String aName)
	{
		return ResourceUtils.loadImageResource(Resources.class, aName);
	}
}
