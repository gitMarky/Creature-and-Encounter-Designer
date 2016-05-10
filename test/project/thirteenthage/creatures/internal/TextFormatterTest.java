package project.thirteenthage.creatures.internal;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextFormatterTest
{

	@Test
	public void testFormatStringSingle()
	{
		assertEquals("This is a hamster", TextFormatter.parse("This is a $text", "text", "hamster"));
	}
	
	@Test
	public void testFormatStringMultiple()
	{
		assertEquals("This angry bird is an angry bird", TextFormatter.parse("This $name is an $name", "name", "angry bird"));
	}

	@Test
	public void testFormatDoubleSingle()
	{
		assertEquals("My height is 6.0\"", TextFormatter.parse("My height is $height\"", "height", 6));
	}
	
	@Test
	public void testFormatDoubleSingleScaled()
	{
		assertEquals("Half my height is 3.0\"", TextFormatter.parse("Half my height is $height[x0.5]\"", "height", 6));
	}
	
	@Test
	public void testFormatDoubleMultipleScales()
	{
		assertEquals("1.0 * 2 = 2.0; * 3.4 = 3.4", TextFormatter.parse("$value * 2 = $value[x2.0]; * 3.4 = $value[x3.4]", "value", 1.0));
	}
}
