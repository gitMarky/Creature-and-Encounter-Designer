package legacy.project.thirteenthage.creatures.interfaces;

import legacy.project.thirteenthage.creatures.internal.interfaces.IDisplayableInGui;
import legacy.project.thirteenthage.creatures.internal.interfaces.IDisplayableInHtml;
import legacy.project.thirteenthage.creatures.internal.interfaces.INamedItem;

public interface ITrigger extends INamedItem, IDisplayableInGui, IDisplayableInHtml
{
	String getDescription();
}
