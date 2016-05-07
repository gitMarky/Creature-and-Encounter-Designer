package project.thirteenthage.creatures.creature;

import project.thirteenthage.creatures.internal.interfaces.IDisplayableInGui;

public enum CreatureSize implements IDisplayableInGui
{
	NORMAL("Normal"),
	LARGE("Large"),
	HUGE("Huge");
	
	private String _guiText;
	
	private CreatureSize(final String guiText)
	{
		_guiText = guiText;
	}

	public static CreatureSize fromString(final String name)
	{
		for (final CreatureSize size : CreatureSize.values())
		{
			if (size.name().equalsIgnoreCase(name))
			{
				return size;
			}
		}

		throw new IllegalArgumentException("No enum for " + name);
	}

	@Override
	public String toGuiText()
	{
		return _guiText;
	}
}
