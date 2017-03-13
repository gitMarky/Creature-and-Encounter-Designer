package legacy.project.thirteenthage.creatures.lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to a global list of known labels.
 */
public class Lists
{
	private final List<String> _labels = new ArrayList<String>();

	private static Lists _instance = null;


	public static List<String> labels()
	{
		if (_instance == null)
		{
			_instance = new Lists();
		}

		return _instance._labels;
	}
}
