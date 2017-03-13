package legacy.project.thirteenthage.creatures.module.analysis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.loaders.CreatureLoader;
import legacy.project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import legacy.project.thirteenthage.creatures.module.analysis.AnalysisMode;
import legacy.project.thirteenthage.creatures.module.analysis.Combat;
import legacy.project.thirteenthage.creatures.module.analysis.CombatMonster;
import legacy.project.thirteenthage.creatures.module.analysis.CombatPlayer;
import legacy.project.thirteenthage.creatures.module.analysis.ICombattant;
import legacy.project.thirteenthage.creatures.player.AveragePlayerCharacter;

public class CombatTest
{

	@Test
	public void test()
	{
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());

		final List<ICombattant> players = new ArrayList<ICombattant>();
		final List<ICombattant> monsters = new ArrayList<ICombattant>();

		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));

		final ICreature human_thug = CreatureLoader.getInstance().getCreatures().get("creature_human_thug");
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));

		final Combat combat = new Combat(players, monsters);
		combat.resolve();

		final Combat player_survival = new Combat(players, monsters);
		player_survival.setMode(AnalysisMode.PLAYER_SURVIVAL);
		player_survival.resolve();

		final Combat monster_survival = new Combat(players, monsters);
		monster_survival.setMode(AnalysisMode.MONSTER_SURVIVAL);
		monster_survival.resolve();

		System.out.println("Combat was over in round:" + combat.getLastRound());
		System.out.println("Players would survive rounds:" + player_survival.getLastRound());
		System.out.println("Monsters would survive rounds:" + monster_survival.getLastRound());

		final StringBuilder hpPlayers = new StringBuilder("Players are left with HP:");
		for (final ICombattant player : combat.getPlayers())
		{
			hpPlayers.append("\n* " + player.getName() + ": " + player.getHP() + " HP");
		}

		final StringBuilder hpMonsters = new StringBuilder("Monsters are left with HP:");
		for (final ICombattant monster : combat.getMonsters())
		{
			hpMonsters.append("\n* " + monster.getName() + ": " + monster.getHP() + " HP");
		}

		System.out.println(hpPlayers.toString());
		System.out.println(hpMonsters.toString());
	}

}
