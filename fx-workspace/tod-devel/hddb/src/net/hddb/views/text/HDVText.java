/*
 * Created on Feb 25, 2004
 */
package net.hddb.views.text;

import net.hddb.adapters.HDAMessage;
import net.hddb.adapters.HDAdapter;
import net.basekit.exceptions.UnexpectedMessageException;
import net.basekit.layoutdelegates.AxisLayoutDelegate;
import net.basekit.widgets.label.LabelWidget;
import net.basekit.Message;
import net.hddb.models.HDModel;
import net.hddb.models.text.HDAMChanged;
import net.hddb.models.text.HDMText;
import net.hddb.views.HDView;
import net.hddb.views.HDViewClass;

/**
 * View for a text. Simply displays the text as a label.
 * @author gpothier
 */
public class HDVText extends HDView
{
	private LabelWidget itsLabelWidget;
	
	public HDVText(HDModel aModel) 
	{
		super(aModel);
		createUI ();
		updateText();
	}

	public HDViewClass getViewClass ()
	{
		return HDVCText.getInstance ();
	}

	private HDMText getHDMText()	
	{
		return (HDMText) getModel();
	}
	
	private void createUI()
	{
		setLayoutDelegate(new AxisLayoutDelegate (AxisLayoutDelegate.X_POSITIVE));

		itsLabelWidget = new LabelWidget ("toto");
		addWidget(itsLabelWidget);
	}

	public void processMessage(Message aMessage)
	{
		if (aMessage instanceof HDAMChanged)
		{
			updateText();
		}
	}
	
	private void updateText ()
	{
		String theText = getHDMText ().getText ();
		itsLabelWidget.setTitle(theText);
	}

}
