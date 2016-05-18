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
		_confirmButton.addActionListener(this);

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


	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (arg0.getSource() == _transferToListBbutton)
		{
			transferFromOneListToTheOther(_listA, _listB, _listDisplayA);
		}

		if (arg0.getSource() == _transferToListAbutton)
		{
			transferFromOneListToTheOther(_listB, _listA, _listDisplayB);
		}
		

		if (arg0.getSource() == _confirmButton)
		{
			updateView();
		}
	}


	@Override
	public void valueChanged(ListSelectionEvent arg0)
	{
		final JButton transferButton;
		if (arg0.getSource() == _listDisplayA)
			transferButton = _transferToListBbutton;
		else
			transferButton = _transferToListAbutton;
		
		if (((JList) arg0.getSource()).getSelectedIndex() == -1)
		{
			transferButton.setEnabled(false);
		}
		else
		{
			transferButton.setEnabled(true);
		}
	}


	private void transferFromOneListToTheOther(final List<T> listA, final List<T> listB, final JList<T> selection)
	{
		final List<T> transferSelection = new ArrayList<T>();
		
		System.out.println("Transferring to list B:");
		for (int index : selection.getSelectedIndices())
		{
			T elementAt = listA.get(index);// ;= (T)
											// _listDisplayA.getModel().getElementAt(index);

			transferSelection.add(elementAt);
		}
		
		for (final T element : transferSelection)
		{
			listB.add(element);
			listA.remove(element);

			System.out.println("- " + element);
		}

		updateListsModels();

		_listDisplayA.setModel(_listModelA);
		_listDisplayB.setModel(_listModelB);
	}
}
