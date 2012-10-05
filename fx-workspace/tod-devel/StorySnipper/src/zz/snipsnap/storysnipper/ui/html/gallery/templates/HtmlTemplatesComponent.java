/*
 * Created on Mar 24, 2005
 */
package zz.snipsnap.storysnipper.ui.html.gallery.templates;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.text.html.HTML;

import zz.snipsnap.storysnipper.ui.html.HtmlTemplate;
import zz.snipsnap.storysnipper.ui.html.gallery.AbstractGalleryComponent;
import zz.utils.ui.GridStackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.action.DefaultActionModel;

/**
 * This component permits to add predefined templates to
 * the edited layout.
 * @author gpothier
 */
public class HtmlTemplatesComponent extends AbstractGalleryComponent
{
	private TemplateAction[] itsActions = 
	{
			new TemplateAction("float left", new HtmlTemplate (HTML.Tag.DIV, 
					"<div>"
						+"<p style='float: left'>Here goes the image</p>"
						+"<p>Here goes the text</p>"
					+"</div>")),
					
			new TemplateAction("float right", new HtmlTemplate (HTML.Tag.DIV, 
					"<div>"
						+"<p style='float: right'>Here goes the image</p>"
						+"<p>Here goes the text</p>"
					+"</div>")),
					
			new TemplateAction("table", new HtmlTemplate (HTML.Tag.TABLE, 
					"<table border='0' width='90%'>"
						+"<tr>"
							+"<td>"
								+"<p align='left'>Edit me!</p>"
							+"</td>"
							+"<td>"
								+"<p align='left'>Edit me!</p>"
							+"</td>"
						+"</tr>"
					+"</table>")),
					
			new TemplateAction("image, legend at bottom, w=400", new HtmlTemplate (HTML.Tag.TABLE,
					"<table border='1' cellpadding='0' width='410' cellspacing='0'>"
						+"<tr>"
							+"<td>"
								+"Image"
							+"</td>"
						+"</tr><tr>"
							+"<td>"
								+"<p align='center'>Legend</p>"
							+"</td>"
						+"</tr>"
					+"</table>")),
					
			new TemplateAction("image, legend at top, w=400", new HtmlTemplate (HTML.Tag.TABLE,
					"<table border='1' cellpadding='0' width='410' cellspacing='0'>"
						+"<tr>"
							+"<td>"
								+"<p align='center'>Legend</p>"
							+"</td>"
						+"</tr><tr>"
							+"<td>"
								+"Image"
							+"</td>"
						+"</tr>"
					+"</table>")),
					

	};
	
	public HtmlTemplatesComponent()
	{
		super ("Layout");
	}

	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new GridStackLayout(1, 5, 5, false, false));
		aCanvas.label("Templates: ", null);
		
		for (TemplateAction theAction : itsActions)
		{
			aCanvas.addAction(BUTTON_ACTION, theAction, null);
		}
	}
	
	private class TemplateAction extends DefaultActionModel
	{
		private HtmlTemplate itsHtmlTemplate;

		public TemplateAction(String aName, HtmlTemplate aHtmlTemplate)
		{
			super(aName);
			itsHtmlTemplate = aHtmlTemplate;
		}
		
		public void performed(JComponent aComponent)
		{
			eSelected.fire(itsHtmlTemplate);
		}
	}
}
