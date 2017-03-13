package legacy.project.thirteenthage.creatures.internal;

import java.util.List;

import org.junit.Test;

public class RegexMatcherTest
{
	/**
	 * For playing around.
	 */
	@Test
	public void sandbox()
	{
	}


	@Test
	public void testFormatterExpression()
	{
		// String expression = "\\$value([\\[x\\d*\\.*\\d*\\]]*)";
		// String expression = "\\$value(\\[[x\\d*\\.*\\d*]*\\])*";
		final String expression = "\\$value(\\[x[\\d*\\.*\\d*]*\\])*";

		print("Simple");
		print(RegexMatcher.getAllMatches("abs $value", expression));
		print(RegexMatcher.getAllMatches("abs $value[", expression));

		print("With scale");
		print(RegexMatcher.getAllMatches("abs $value[x]", expression));
		print(RegexMatcher.getAllMatches("abs $value[x.5]", expression));
		print(RegexMatcher.getAllMatches("abs $value[x1.0]", expression));

		print("With scale and fake scale");
		print(RegexMatcher.getAllMatches("abc $value[x1.0]huefhue[x2.0]", expression));
		print(RegexMatcher.getAllMatches("abc $value[x1.x2.0]", expression));
	}


	private void print(final List<String> allMatches)
	{
		for (final String message : allMatches)
		{
			print(message);
		}
	}


	private void print(final String message)
	{
		System.out.println(message);
	}
}
