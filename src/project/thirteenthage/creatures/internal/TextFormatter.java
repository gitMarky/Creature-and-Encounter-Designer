package project.thirteenthage.creatures.internal;

import java.util.List;

public class TextFormatter
{
	public static final String PLACEHOLDER_START = "\\$";
	public static final String PLACEHOLDER_NAME = "name";
	public static final String PLACEHOLDER_DAMAGE = "damage";
	
	public static final String PLACEHOLDER_SCALABLE_DOUBLE = "(\\[x[\\d*\\.*\\d*]*\\])";
	
	public static String parse(final String source, final String placeholder, final String text)
	{
		return source.replaceAll(PLACEHOLDER_START + placeholder, text);
	}
	
	public static final String parse(final String source, final String placeholder, final double value)
	{
		// replace for example $damage[x1.2] with 1.2*value
		
		String expression = PLACEHOLDER_START + placeholder + PLACEHOLDER_SCALABLE_DOUBLE + "*";

		List<String> matches = RegexMatcher.getAllMatches(source, expression);
		
		String text = source;
		for (final String match : matches)
		{
			System.out.println("Parsing doubles: ");
			System.out.println("- " +  match);
			
			double scaledValue = value;
			
			List<String> scales = RegexMatcher.getAllMatches(match, PLACEHOLDER_SCALABLE_DOUBLE);
			//for (String scale : scales)
			if (scales.size() == 1)
			{
				String scale = scales.get(0).replace("[x", "").replace("]", "");
				System.out.println("- found scale : " + scale);
				
				scaledValue *= Double.parseDouble(scale);
			}
			
			
			
			text = text.replace(match, Double.toString(scaledValue));
		}
		
		return text;
	}
}
