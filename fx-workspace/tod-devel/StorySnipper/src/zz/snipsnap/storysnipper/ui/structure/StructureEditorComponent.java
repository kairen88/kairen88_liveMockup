/*
 * Created on Mar 25, 2005
 */
package zz.snipsnap.storysnipper.ui.structure;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import zz.snipsnap.storysnipper.model.Story;
import zz.snipsnap.storysnipper.model.StoryPage;
import zz.utils.properties.IListProperty;
import zz.utils.tree.SimpleTreeNode;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.action.DefaultActionModel;

/**
 * This component permits to edit the structure of the story,
 * ie. view page structure, create, remove pages.
 * @author gpothier
 */
public class StructureEditorComponent extends WaltzComponent
{
	public final IListProperty<StoryPage> pSelectedPages;
	
	private Story itsStory;
	
	private JTextField itsPageNameField;
	private StructureViewComponent itsStructureViewComponent;
	
	public StructureEditorComponent(Story aStory)
	{
		itsStory = aStory;
		
		itsStructureViewComponent = new StructureViewComponent(itsStory);
		pSelectedPages = itsStructureViewComponent.pSelectedPages;
		
		itsPageNameField = new JTextField(10);
	} 
	
	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new BorderLayout());
		
		renderToolbar(aCanvas.createCanvas(BorderLayout.NORTH));
		aCanvas.add (itsStructureViewComponent, BorderLayout.CENTER);
	}
	
	private void renderToolbar (IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new FlowLayout());
		
		aCanvas.add(itsPageNameField, null);
		aCanvas.addAction(BUTTON_ACTION, new NewPageAction(), null);
		aCanvas.addAction(BUTTON_ACTION, new DeletePageAction(), null);
	}
	
	private class NewPageAction extends DefaultActionModel
	{
		public NewPageAction()
		{
			super("New");
		}
		
		public void performed(JComponent aComponent)
		{
			SimpleTreeNode<StoryPage> theParentNode = itsStructureViewComponent.getSelectedNode();
			if (theParentNode == null) theParentNode = itsStory.getPages().getRoot();
			
			String theName = itsPageNameField.getText();
			if (theName.length() > 0) 
			{
				itsStory.addPage(theParentNode, theName);
			}
		}
	}
	
	private class DeletePageAction extends DefaultActionModel
	{
		public DeletePageAction()
		{
			super("Delete");
		}
		
		public void performed(JComponent aComponent)
		{
			SimpleTreeNode<StoryPage> theNode = itsStructureViewComponent.getSelectedNode();
			SimpleTreeNode<StoryPage> theRootNode = itsStory.getPages().getRoot();
			if (theNode != null && theNode != theRootNode) 
			{	
				int theOption = JOptionPane.showConfirmDialog(
						aComponent, 
						"Deletion", 
						"Sure you want to delete this page?", 
						JOptionPane.YES_NO_OPTION);
				
				if (theOption == JOptionPane.YES_OPTION) 
				{
					itsStory.getPages().removeNode(theNode);
				}
			}
		}
	}
}
