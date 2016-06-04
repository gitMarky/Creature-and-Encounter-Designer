package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.ApplicationLogger;

@SuppressWarnings("serial")
public class ChoicePanel extends JPanel implements IView
{
	protected IView _updateView = null;


	@Override
	public void updateView()
	{
		if (_updateView != null)
		{
			ApplicationLogger.getLogger().info("Update view");
			_updateView.updateView();
		}
	}


	public void setUpdateView(final IView view)
	{
		_updateView = view;
	}
}
