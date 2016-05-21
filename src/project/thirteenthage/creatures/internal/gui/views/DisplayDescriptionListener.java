package project.thirteenthage.creatures.internal.gui.views;

import java.awt.Component;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import project.thirteenthage.creatures.internal.ApplicationLogger;

@SuppressWarnings("hiding")
class DisplayDescriptionListener<T, C extends Component> implements ListSelectionListener
{
	private final JPanel innerPanel;
	private final Class<C> descriptionComponentType;
	private final Class<T> listSelectionType;
	private final JFrame _listFrame;


	DisplayDescriptionListener(final JFrame listFrame, final JPanel panel, final Class<T> listSelectionType, final Class<C> descriptionComponentType)
	{
		this._listFrame = listFrame;
		this.innerPanel = panel;
		this.descriptionComponentType = descriptionComponentType;
		this.listSelectionType = listSelectionType;
	}


	@Override
	public void valueChanged(final ListSelectionEvent selection)
	{
		innerPanel.removeAll();

		@SuppressWarnings("unchecked")
		final JList<T> source = (JList<T>) selection.getSource();
		final int index = source.getSelectedIndex();
		if (index > -1)
		{
			final T attack = source.getModel().getElementAt(index);
			final C label = createNewLabel(descriptionComponentType, listSelectionType, attack);

			innerPanel.add(label);
		}

		_listFrame.pack();
		_listFrame.setVisible(true);
	}


	@SuppressWarnings("unchecked")
	private <C extends Component, T> C createNewLabel(final Class<C> descriptionComponentType, final Class<T> listSelectionType, final T listSelectionItem)
	{
		if (descriptionComponentType == null)
		{
			throw new IllegalArgumentException("Parameter 'descriptionComponentType' must not be null.");
		}
		if (listSelectionType == null)
		{
			throw new IllegalArgumentException("Parameter 'listSelectionType' must not be null.");
		}
		if (listSelectionItem == null)
		{
			throw new IllegalArgumentException("Parameter 'listSelectionItem' must not be null.");
		}

		C label = null;
		Constructor<?> constructor;
		Exception ex = null;
		try
		{
			constructor = descriptionComponentType.getDeclaredConstructor(listSelectionType);
			label = (C) constructor.newInstance(listSelectionItem);
		}
		catch (final NoSuchMethodException e)
		{
			ex = e;
		}
		catch (final SecurityException e)
		{
			ex = e;
		}
		catch (final InstantiationException e)
		{
			ex = e;
		}
		catch (final IllegalAccessException e)
		{
			ex = e;
		}
		catch (final IllegalArgumentException e)
		{
			ex = e;
		}
		catch (final InvocationTargetException e)
		{
			ex = e;
		}

		if (ex != null)
		{
			for (final StackTraceElement element : ex.getStackTrace())
			{
				ApplicationLogger.getLogger().warning(element.toString());
			}
		}

		if (label == null)
		{
			ApplicationLogger.getLogger().info("could not create label");
		}

		return label;
	}
}
