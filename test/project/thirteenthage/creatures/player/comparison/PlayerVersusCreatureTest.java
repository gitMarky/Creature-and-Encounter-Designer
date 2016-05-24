package project.thirteenthage.creatures.player.comparison;

import org.junit.Test;

import project.thirteenthage.creatures.creature.EditableCreatureTemplate;
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

		final ICreatureTemplate lvl1template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_human_thug");
		final ICreatureTemplate lvl3template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_gnoll_savage");
		final ICreatureTemplate lvl6template = null; //CreatureTemplateLoader.getInstance().getTemplates().get("creature_");

		final ICreatureTemplate lvl10template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_great_fang_cadre");
		lvl10template.getLabels().remove("Mook");

		for (int i = 1; i < 11; ++i)
		{
			printBattle(player, lvl1template, i);
			printBattle(player, lvl3template, i);
//			printBattle(player, lvl6template, i);
			printBattle(player, lvl10template, i);
		}
	}

	private BattleInfo printBattle(PlayerCharacter player, ICreatureTemplate lvlTemplate, int level)
	{
		player.setLevel(level);
		
		EditableCreatureTemplate template = new EditableCreatureTemplate(lvlTemplate);
		template.setLevel(level);
		
		ICreature monster = template.toCreature();
		
		final PlayerVersusCreature battle = new PlayerVersusCreature(player, monster);
		double expectedSurvivalTime = battle.getExpectedSurvivalTime();
		double expectedKillingTime = battle.getExpectedKillingTime();
		System.out.println(String.format("Rounds until player dead: %.2f - rounds until monster dead %.2f (in player lvl " + level + " vs. " + monster.getName() + " lvl " + level + ")", expectedSurvivalTime, expectedKillingTime));
		
		return new BattleInfo(expectedSurvivalTime, expectedKillingTime);
	}

	
	private class BattleInfo
	{
		private final double _survivalTime;
		private final double _killingTime;
		
		private BattleInfo(final double survivalTime, final double killingTime)
		{
			_survivalTime = survivalTime;
			_killingTime = killingTime;
		}
		
		private double getSurvivalTime()
		{
			return _survivalTime;
		}
		
		private double getKillingTime()
		{
			return _killingTime;
		}
	}
}
