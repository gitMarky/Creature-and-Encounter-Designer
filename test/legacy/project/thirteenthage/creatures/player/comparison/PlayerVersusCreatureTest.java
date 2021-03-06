package legacy.project.thirteenthage.creatures.player.comparison;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import project.thirteenthage.creatures.model.creature.template.EditableCreatureTemplate;
import legacy.project.thirteenthage.creatures.creature.CreatureSize;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import legacy.project.thirteenthage.creatures.player.AveragePlayerCharacter;
import legacy.project.thirteenthage.creatures.player.PlayerCharacter;

public class PlayerVersusCreatureTest
{

	@Test
	public void testNormalCreature()
	{
		testBattles(CreatureSize.NORMAL);
	}


	@Test
	public void testLargeCreature()
	{
		testBattles(CreatureSize.LARGE);
	}


	@Test
	public void testHugeCreature()
	{
		testBattles(CreatureSize.HUGE);
	}


	private void testBattles(final CreatureSize size)
	{
		final PlayerCharacter player = new AveragePlayerCharacter();

		CreatureTemplateLoader.getInstance();
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());

		final ICreatureTemplate lvl1template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_human_thug");
		final ICreatureTemplate lvl3template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_gnoll_savage");
		final ICreatureTemplate lvl6template = null; // CreatureTemplateLoader.getInstance().getTemplates().get("creature_");

		final ICreatureTemplate lvl10template = CreatureTemplateLoader.getInstance().getTemplates().get("creature_great_fang_cadre");
		lvl10template.getLabels().remove("Mook");

		final List<BattleInfo> playerVSsameLevel = new ArrayList<BattleInfo>();
		final List<BattleInfo> playerVSsuggestedLevel = new ArrayList<BattleInfo>();

		System.out.println("*** Player versus same level monster");
		for (int i = 1; i < 11; ++i)
		{
			playerVSsameLevel.add(printBattle(player, lvl1template, i, i, size));
			playerVSsameLevel.add(printBattle(player, lvl3template, i, i, size));
			// playerVSsameLevel.add(printBattle(player, lvl6template, i, i,
			// size));
			playerVSsameLevel.add(printBattle(player, lvl10template, i, i, size));
		}

		System.out.println("*** Player versus correct level monster");
		for (int i = 1; i < 11; ++i)
		{
			int monsterLevel = 0;
			if (i >= 5) monsterLevel += 1;
			if (i >= 8) monsterLevel += 1;

			playerVSsuggestedLevel.add(printBattle(player, lvl1template, i, i + monsterLevel, size));
			playerVSsuggestedLevel.add(printBattle(player, lvl3template, i, i + monsterLevel, size));
			// playerVSsuggestedLevel.add(printBattle(player, lvl6template, i, i
			// + monsterLevel, size));
			playerVSsuggestedLevel.add(printBattle(player, lvl10template, i, i + monsterLevel, size));
		}

		printAverages(playerVSsameLevel, "Player versus same level mosnter: ");
		printAverages(playerVSsuggestedLevel, "Player versus 'correct' level monster: ");

		System.out.println("*** Player versus different monster levels");
		for (int p = 1; p < 11; ++p)
		{
			for (int monsterLevel = 0; monsterLevel < 14; ++monsterLevel)
			{
				final List<BattleInfo> playerVSmonster = new ArrayList<BattleInfo>();

				playerVSmonster.add(printBattle(player, lvl1template, p, monsterLevel, size));
				playerVSmonster.add(printBattle(player, lvl3template, p, monsterLevel, size));
				// playerVSmonster.add(printBattle(player, lvl6template, p,
				// monsterLevel, size));
				playerVSmonster.add(printBattle(player, lvl10template, p, monsterLevel, size));
				printAverages(playerVSmonster, String.format("*** Player lvl %02d vs. monster lvl %02d ", p, monsterLevel));
			}
		}

		System.out.println("*** Player versus different monster levels");
		for (int monsterLevel = 0; monsterLevel < 11; ++monsterLevel)
		{
			final List<BattleInfo> playerVSmonster = new ArrayList<BattleInfo>();
			for (int p = 1; p < 11; ++p)
			{
				playerVSmonster.add(printBattle(player, lvl1template, p, p + monsterLevel, size));
				playerVSmonster.add(printBattle(player, lvl3template, p, p + monsterLevel, size));
				// playerVSmonster.add(printBattle(player, lvl6template, p, p +
				// monsterLevel, size));
				playerVSmonster.add(printBattle(player, lvl10template, p, p + monsterLevel, size));
			}
			printAverages(playerVSmonster, String.format("*** Player lvl vs. monster lvl + %02d ", monsterLevel));
		}
	}


	private void printAverages(final List<BattleInfo> data, final String message)
	{
		double totalSurvivalTime = 0.0;
		double totalKillingTime = 0.0;

		for (final BattleInfo info : data)
		{
			totalSurvivalTime += info.getSurvivalTime();
			totalKillingTime += info.getKillingTime();
		}

		final double averageSurvivalTime = totalSurvivalTime / data.size();
		final double averageKillingTime = totalKillingTime / data.size();

		System.out.println((message == null ? "" : message) + String.format("Average survival time: %6.2f / average killing time: %6.2f", averageSurvivalTime, averageKillingTime));
	}


	private BattleInfo printBattle(final PlayerCharacter player, final ICreatureTemplate lvlTemplate, final int playerLevel, final int monsterLevel, final CreatureSize size)
	{
		player.setLevel(playerLevel);

		final EditableCreatureTemplate template = new EditableCreatureTemplate(lvlTemplate);
		template.setLevel(monsterLevel);
		template.setSize(size);

		final ICreature monster = template.toCreature();

		final PlayerVersusCreature battle = new PlayerVersusCreature(player, monster);
		final double expectedSurvivalTime = battle.getExpectedSurvivalTime();
		final double expectedKillingTime = battle.getExpectedKillingTime();
		// System.out.println(String.format("Rounds until player dead: %.2f - rounds until monster dead %.2f (in player lvl "
		// + playerLevel + " vs. " + monster.getName() + " lvl " + monsterLevel
		// + ")", expectedSurvivalTime, expectedKillingTime));

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
