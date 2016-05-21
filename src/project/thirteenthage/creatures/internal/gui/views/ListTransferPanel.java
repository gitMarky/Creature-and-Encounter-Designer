package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * A panel that contains two lists and two transfer buttons.
 */
@SuppressWarnings("serial")
public class ListTransferPanel<T> extends ChoicePanel implements ActionListener, ListSelectionListener
{
	private final List<T> _listA;
	private final List<T> _listB;
	final JButton _transferToListBbutton = new JButton(">>>");
	final JButton _transferToListAbutton = new JButton("<<<");
	final JButton _confirmButton = new JButton("Confirm");
	private final DefaultListModel<T> _listModelA = new DefaultListModel<T>();
	private final DefaultListModel<T> _listModelB = new DefaultListModel<T>();
	private final JList<T> _listDisplayA;
	private final JList<T> _listDisplayB;

	private boolean _listAlocked = false;
	private boolean _listBlocked = false;

	private boolean _listAunique = false;
	private boolean _listBunique = false;


	public ListTransferPanel(final List<T> listA, final List<T> listB)
	{
		super();
		_listA = listA;
		_listB = listB;

		final JPanel listApanel = new JPanel();
		final JPanel listBpanel = new JPanel();
		final JPanel buttonPanel = new JPanel();

		_listDisplayA = new JList<T>(_listModelA);
		_listDisplayB = new JList<T>(_listModelB);
		_listDisplayA.setLayoutOrientation(JList.VERTICAL);
		_listDisplayB.setLayoutOrientation(JList.VERTICAL);

		final JScrollPane listAscroller = new JScrollPane(_listDisplayA);
		listAscroller.setWheelScrollingEnabled(false);
		listAscroller.setEnabled(true);
		listApanel.add(listAscroller);
		listAscroller.setVisible(true);

		final JScrollPane listBscroller = new JScrollPane(_listDisplayB);
		listBscroller.setWheelScrollingEnabled(false);
		listBscroller.setEnabled(true);
		listBpanel.add(listBscroller);

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		_transferToListBbutton.addActionListener(this);
		_transferToListAbutton.addActionListener(this);

		listApanel.add(_listDisplayA);
		listBpanel.add(_listDisplayB);
		_listDisplayA.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_listDisplayB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_listDisplayA.addListSelectionListener(this);
		_listDisplayB.addListSelectionListener(this);

		buttonPanel.add(_transferToListBbutton);
		buttonPanel.add(_transferToListAbutton);
		buttonPanel.add(_confirmButton);

		updateListsModels();

		this.setLayout(new GridLayout(1, 3));
		this.add(listApanel);
		this.add(buttonPanel);
		this.add(listBpanel);

	}


	public void setLeftListLocked(final boolean locked)
	{
		_listAlocked = locked;
	}


	public void setRightListLocked(final boolean locked)
	{
		_listBlocked = locked;
	}


	public void setLeftListUnique(final boolean unique)
	{
		_listAunique = unique;
	}


	public void setRightListUnique(final boolean unique)
	{
		_listBunique = unique;
	}


	@Override
	public void actionPerformed(final ActionEvent action)
	{
		if (action.getSource() == _transferToListBbutton)
		{
			transferFromOneListToTheOther(_listA, _listB, _listDisplayA, _listAlocked, _listBunique);
		}

		if (action.getSource() == _transferToListAbutton)
		{
			transferFromOneListToTheOther(_listB, _listA, _listDisplayB, _listBlocked, _listAunique);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(final ListSelectionEvent selection)
	{
		final JButton transferButton;
		if (selection.getSource() == _listDisplayA)
			transferButton = _transferToListBbutton;
		else
			transferButton = _transferToListAbutton;

		if (((JList<T>) selection.getSource()).getSelectedIndex() == -1)
		{
			transferButton.setEnabled(false);
		}
		else
		{
			transferButton.setEnabled(true);
		}
	}


	private void transferFromOneListToTheOther(final List<T> listA, final List<T> listB, final JList<T> selection, final boolean locked, final boolean unique)
	{
		final List<T> transferSelection = new ArrayList<T>();

		System.out.println("Transferring to list B:");
		for (final int index : selection.getSelectedIndices())
		{
			final T elementAt = listA.get(index);// ;= (T)
			// _listDisplayA.getModel().getElementAt(index);

			transferSelection.add(elementAt);
		}

		for (final T element : transferSelection)
		{
			if (!unique || unique && !listB.contains(element)) listB.add(element);
			if (!locked) listA.remove(element);

			System.out.println("- " + element);
		}

		updateListsModels();

		_listDisplayA.setModel(_listModelA);
		_listDisplayB.setModel(_listModelB);
	}


	private void updateListsModels()
	{
		_listModelA.clear();
		_listModelB.clear();

		for (final T element : _listA)
		{
			_listModelA.addElement(element);
		}
		for (final T element : _listB)
		{
			_listModelB.addElement(element);
		}

		_transferToListAbutton.setEnabled(!_listB.isEmpty() && _listDisplayB.getSelectedIndex() != -1);
		_transferToListBbutton.setEnabled(!_listA.isEmpty() && _listDisplayA.getSelectedIndex() != -1);
	}


	public JButton getConfirmButton()
	{
		return _confirmButton;
	}


	public JList<T> getLeftList()
	{
		return _listDisplayA;
	}


	public JList<T> getRightList()
	{
		return _listDisplayB;
	}
}
