package project.thirteenthage.creatures.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher
{
	public static List<String> getAllMatches(final String source,  final String expression)
	{
		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile(expression).matcher(source);
		while (m.find())
		{
			allMatches.add(m.group());
		}
	 
		return allMatches;
	}
}
