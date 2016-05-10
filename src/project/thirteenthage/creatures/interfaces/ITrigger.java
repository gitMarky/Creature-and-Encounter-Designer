package project.thirteenthage.creatures.interfaces;

import project.thirteenthage.creatures.internal.interfaces.IDisplayableInGui;
import project.thirteenthage.creatures.internal.interfaces.IDisplayableInHtml;
import project.thirteenthage.creatures.internal.interfaces.INamedItem;

public interface ITrigger extends INamedItem, IDisplayableInGui, IDisplayableInHtml
{
	String getDescription();
}
