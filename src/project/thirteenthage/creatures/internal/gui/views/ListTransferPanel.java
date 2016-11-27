package project.thirteenthage.creatures.internal.gui.views;

import java.awt.Dimension;
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

import project.library.marky.logger.ApplicationLogger;

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

		if (listA == null)
		{
			throw new IllegalArgumentException("Parameter 'listA' must not be null.");
		}
		if (listB == null)
		{
			throw new IllegalArgumentException("Parameter 'listB' must not be null.");
		}

		final JPanel listContainerPanel = new JPanel();

		_listA = listA;
		_listB = listB;

		final JPanel buttonPanel = new JPanel();

		_listDisplayA = new JList<T>(_listModelA);
		_listDisplayB = new JList<T>(_listModelB);
		_listDisplayA.setLayoutOrientation(JList.VERTICAL);
		_listDisplayB.setLayoutOrientation(JList.VERTICAL);

		final JScrollPane listAscroller = new JScrollPane(_listDisplayA);
		listAscroller.setWheelScrollingEnabled(false);
		listAscroller.setEnabled(true);
		listAscroller.setVisible(true);

		final JScrollPane listBscroller = new JScrollPane(_listDisplayB);
		listBscroller.setWheelScrollingEnabled(false);
		listBscroller.setEnabled(true);

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		_transferToListBbutton.addActionListener(this);
		_transferToListAbutton.addActionListener(this);

		_listDisplayA.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_listDisplayB.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		_listDisplayA.addListSelectionListener(this);
		_listDisplayB.addListSelectionListener(this);

		buttonPanel.add(_transferToListBbutton);
		buttonPanel.add(_transferToListAbutton);
		buttonPanel.add(_confirmButton);

		updateListsModels();

		listContainerPanel.setLayout(new BoxLayout(listContainerPanel, BoxLayout.X_AXIS));
		listContainerPanel.add(listAscroller);
		listContainerPanel.add(buttonPanel);
		listContainerPanel.add(listBscroller);
		listContainerPanel.setPreferredSize(new Dimension(700, 200));
		listAscroller.setPreferredSize(new Dimension(300, 200));
		listBscroller.setPreferredSize(new Dimension(300, 200));
		buttonPanel.setPreferredSize(new Dimension(100, 200));

		this.add(listContainerPanel);
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


	private void transferFromOneListToTheOther(final List<T> sourceList, final List<T> targetList, final JList<T> selection, final boolean locked, final boolean unique)
	{
		if (sourceList == null)
		{
			throw new IllegalArgumentException("Parameter 'sourceList' must not be null.");
		}
		if (targetList == null)
		{
			throw new IllegalArgumentException("Parameter 'targetList' must not be null.");
		}
		if (selection == null)
		{
			throw new IllegalArgumentException("Parameter 'selection' must not be null.");
		}

		final List<T> transferSelection = new ArrayList<T>();

		ApplicationLogger.getLogger().fine("Transferring to list B:");
		for (final int index : selection.getSelectedIndices())
		{
			final T elementAt = sourceList.get(index);// ;= (T)

			transferSelection.add(elementAt);
		}

		for (final T element : transferSelection)
		{
			if (!unique || unique && !targetList.contains(element)) targetList.add(element);
			if (!locked) sourceList.remove(element);

			ApplicationLogger.getLogger().fine("- " + element);
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
