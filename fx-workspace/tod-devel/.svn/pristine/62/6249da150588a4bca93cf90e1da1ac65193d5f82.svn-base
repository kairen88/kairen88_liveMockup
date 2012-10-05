/*
 * Created on Apr 2, 2005
 */
package zz.snipsnap.storysnipper.ui.upload;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import zz.utils.ProgressModel;
import zz.utils.properties.IProperty;
import zz.utils.properties.IPropertyListener;
import zz.utils.ui.GridStackLayout;
import zz.waltz.api.IWaltzCanvas;
import zz.waltz.api.WaltzComponent;
import zz.waltz.api.action.DefaultActionModel;

/**
 * This component displays two progress bars: one for 
 * overall progress, and one for current file progress.
 * @author gpothier
 */
public class ProgressComponent extends WaltzComponent 
implements IPropertyListener<Number>
{
	private ProgressModel<? extends Number> itsFileProgress;
	private ProgressModel<? extends Number> itsFilesProgress;

	private JProgressBar itsFileProgressBar;
	private JProgressBar itsFilesProgressBar;
	private JLabel itsMessageLabel;
	
	private boolean itsCanceled = false;
	
	public ProgressComponent(ProgressModel<? extends Number> aFileProgress, ProgressModel<? extends Number> aFilesProgress)
	{
		itsFileProgress = aFileProgress;
		itsFilesProgress = aFilesProgress;

		itsFileProgress.pCurrent.addListener(this);
		itsFileProgress.pTotal.addListener(this);
		itsFilesProgress.pCurrent.addListener(this);
		itsFilesProgress.pTotal.addListener(this);
		
		itsFileProgressBar = new JProgressBar();
		itsFilesProgressBar = new JProgressBar();
		itsMessageLabel = new JLabel();
	}
	
	private void update()
	{
		itsFileProgressBar.setMinimum(0);
		itsFileProgressBar.setMaximum(itsFileProgress.pTotal.get().intValue());
		itsFileProgressBar.setValue(itsFileProgress.pCurrent.get().intValue());

		itsFilesProgressBar.setMinimum(0);
		itsFilesProgressBar.setMaximum(itsFilesProgress.pTotal.get().intValue());
		itsFilesProgressBar.setValue(itsFilesProgress.pCurrent.get().intValue());
	}

	public void propertyChanged(IProperty<Number> aProperty, Number aOldValue, Number aNewValue)
	{
		update();
	}

	public void propertyValueChanged(IProperty<Number> aProperty)
	{
	}
	
	public void setMessage(String aString)
	{
		itsMessageLabel.setText(aString);
	}

	protected void render(IWaltzCanvas aCanvas)
	{
		aCanvas.setLayout(new GridStackLayout(1));
	
		aCanvas.label("Overall progress", null);
		aCanvas.add(itsFilesProgressBar, null);
		
		aCanvas.label("Current file progress", null);
		aCanvas.add(itsMessageLabel, null);
		aCanvas.add(itsFileProgressBar, null);
		
		aCanvas.addAction(BUTTON_ACTION, new CancelAction(), null);
	}
	
	/**
	 * Returns true if the user pressed the cancel button.
	 */
	public boolean isCanceled()
	{
		return itsCanceled;
	}
	
	
	private class CancelAction extends DefaultActionModel
	{
		public CancelAction()
		{
			super ("Cancel");
		}
		
		public void performed(JComponent aComponent)
		{
			itsCanceled = true;
			pEnabled.set(false);
		}
		
	}

}
