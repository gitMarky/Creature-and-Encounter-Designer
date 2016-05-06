package project.thirteenthage.creatures.internal;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreatureSizeTest
{

	@Test
	public void test()
	{
		assertEquals(CreatureSize.NORMAL, CreatureSize.fromString("normal"));
		assertEquals(CreatureSize.LARGE, CreatureSize.fromString("large"));
		assertEquals(CreatureSize.HUGE, CreatureSize.fromString("huge"));
	}

}
