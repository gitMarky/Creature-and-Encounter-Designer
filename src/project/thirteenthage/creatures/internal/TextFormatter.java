package project.thirteenthage.creatures.internal;

import java.util.List;

public class TextFormatter
{
	public static final String PLACEHOLDER_START = "\\$";
	public static final String PLACEHOLDER_NAME = "name";
	public static final String PLACEHOLDER_DAMAGE = "damage";
	
	public static String parse(final String source, final String placeholder, final String text)
	{
		return source.replaceAll(PLACEHOLDER_START + placeholder, text);
	}
	
	public static final String parse(final String source, final String placeholder, final double value)
	{
		// replace for example $damage[x1.2] with 1.2*value
		
		List<String> matches = RegexMatcher.getAllMatches(source, PLACEHOLDER_START + placeholder);
		
		String text = source;
		for (final String match : matches)
		{
			text = text.replace(match, Double.toString(value));
		}
		
		return text;
	}
}
