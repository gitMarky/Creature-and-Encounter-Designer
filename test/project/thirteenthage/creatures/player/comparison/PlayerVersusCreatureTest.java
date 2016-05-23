package project.thirteenthage.creatures.player.comparison;

import org.junit.Test;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import project.thirteenthage.creatures.player.AveragePlayerCharacter;
import project.thirteenthage.creatures.player.PlayerCharacter;

public class PlayerVersusCreatureTest
{

	@Test
	public void test()
	{
		final PlayerCharacter player = new AveragePlayerCharacter();
		
		CreatureTemplateLoader.getInstance();
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());

//		final ICreature monster = CreatureLoader.getInstance().getCreatures().get("creature_demontouched_human_ranger");
		final ICreatureTemplate monsterTemplate = CreatureTemplateLoader.getInstance().getTemplates().get("creature_great_fang_cadre");
		monsterTemplate.getLabels().remove("Mook");
		final ICreature monster = monsterTemplate.toCreature();
		
		final PlayerVersusCreature battle = new PlayerVersusCreature(player, monster);
		
		for (int i = 1; i < 11; ++i)
		{
			player.setLevel(i);
			double expectedSurvivalTime = battle.getExpectedSurvivalTime();
			double expectedKillingTime = battle.getExpectedKillingTime();
			System.out.println(String.format("Rounds until player dead: %.2f - rounds until monster dead %.2f", expectedSurvivalTime, expectedKillingTime));
		}
	}

}
