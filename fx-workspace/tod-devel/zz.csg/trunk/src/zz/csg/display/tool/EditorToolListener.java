/**
 * Created by IntelliJ IDEA.
 * User: gpothier
 * Date: Apr 21, 2003
 * Time: 1:40:33 PMM
 */
package zz.csg.display.tool;

public interface EditorToolListener
{
	public void operationPerformed (EditorTool aTool);
	public void operationCancelled (EditorTool aTool);
}
