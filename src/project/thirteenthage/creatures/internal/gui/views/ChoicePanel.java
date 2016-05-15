package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;

@SuppressWarnings("serial")
public class ChoicePanel extends JPanel implements IView
{
	protected IView _updateView = null;


	@Override
	public void updateView()
	{
		if (_updateView != null)
		{
			_updateView.updateView();
		}
	}


	public void setUpdateView(final IView view)
	{
		_updateView = view;
	}
}
