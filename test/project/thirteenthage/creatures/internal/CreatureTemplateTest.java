package project.thirteenthage.creatures.internal;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class CreatureTemplateTest
{

	@Test
	public void testDireRat()
	{
		final double delta = 1e-8;
		final File xml = new File("resources/creatures/creature_dire_rat.xml");
		System.out.println(xml.getAbsolutePath());
		
		final CreatureTemplate rat = new CreatureTemplate(xml);
		
		
		assertEquals("Dire Rat", rat.getName());
		//assertEquals("Normal", rat.getSize());
		assertEquals(1, rat.getLevel());
		
		assertEquals(2, rat.getLabels().size());
		assertEquals("Mook", rat.getLabels().get(0));
		assertEquals("Beast", rat.getLabels().get(1));
		
		assertEquals(1, rat.getInitiative());
		assertEquals(-2.0, rat.getAC(), delta);
		assertEquals(0, rat.getPD(), delta);
		assertEquals(-1, rat.getMD(), delta);
		assertEquals(0.857, rat.getHP(), delta);
		//assertEquals();
		/*
	<name>Dire Rat</name>
	<size>Normal</size>
	<level>1</level>
	<labels>
		<label>Mook</label>
		<label>Beast</label>
	</labels>
	<modifiers>
		<ini>1</ini>
		<ac>-2</ac>
		<pd>0</pd>
		<md>-1</md>
		<hp>0.857</hp>
	</modifiers>
	<attacks>
		<attack id="attack_infected_bite" />
	</attacks>
	<specials>
		<special id="" />
	</specials>
	<nastier>
		<special id="special_squealing_pack_attack" />
	</nastier>
		 */
	}

}
