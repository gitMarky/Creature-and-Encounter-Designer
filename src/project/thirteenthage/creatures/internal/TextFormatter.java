package project.thirteenthage.creatures.internal;

import java.util.List;

import project.thirteenthage.creatures.internal.conversions.Conversions;

public class TextFormatter
{
	public static final String PLACEHOLDER_START = "\\$";
	public static final String PLACEHOLDER_NAME = "name";
	public static final String PLACEHOLDER_DAMAGE = "damage";

	public static final String PLACEHOLDER_SCALABLE_DOUBLE = "(\\[x[\\d*\\.*\\d*]*\\])";


	public static String parse(final String source, final String placeholder, final String text)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("Parameter 'source' must not be null.");
		}
		if (placeholder == null)
		{
			throw new IllegalArgumentException("Parameter 'placeholder' must not be null.");
		}
		if (text == null)
		{
			throw new IllegalArgumentException("Parameter 'text' must not be null.");
		}

		return source.replaceAll(PLACEHOLDER_START + placeholder, text);
	}


	public static final String parse(final String source, final String placeholder, final double value)
	{
		return parse(source, placeholder, value, false);
	}


	public static final String parse(final String source, final String placeholder, final double value, final boolean inPercent)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("Parameter 'source' must not be null.");
		}
		if (placeholder == null)
		{
			throw new IllegalArgumentException("Parameter 'placeholder' must not be null.");
		}
		// replace for example $damage[x1.2] with 1.2*value

		final String expression = PLACEHOLDER_START + placeholder + PLACEHOLDER_SCALABLE_DOUBLE + "+";

		final String suffix = inPercent ? "%" : "";
		String text = parseDoubleScaled(source, expression, value, suffix);
		text = parseDoubleScaled(text, PLACEHOLDER_START + placeholder, value, suffix);

		return text;
	}


	private static final String parseDoubleScaled(final String source, final String expression, final double value, final String suffix)
	{
		if (source == null)
		{
			throw new IllegalArgumentException("Parameter 'source' must not be null.");
		}
		if (expression == null)
		{
			throw new IllegalArgumentException("Parameter 'placeholder' must not be null.");
		}

		String text = source;
		final List<String> matches = RegexMatcher.getAllMatches(source, expression);

		for (final String match : matches)
		{
			double scaledValue = value;

			final List<String> scales = RegexMatcher.getAllMatches(match, PLACEHOLDER_SCALABLE_DOUBLE);
			if (scales.size() == 1)
			{
				final String scale = scales.get(0).replace("[x", "").replace("]", "");

				scaledValue *= Double.parseDouble(scale);
			}

			text = text.replace(match, Integer.toString(Conversions.round(scaledValue)) + suffix);
		}

		return text;
	}
}
