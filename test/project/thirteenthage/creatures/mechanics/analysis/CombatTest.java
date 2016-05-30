package project.thirteenthage.creatures.mechanics.analysis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import project.thirteenthage.creatures.player.AveragePlayerCharacter;

public class CombatTest
{

	@Test
	public void test()
	{
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());
		
		List<ICombattant> players = new ArrayList<ICombattant>();
		List<ICombattant> monsters = new ArrayList<ICombattant>();
		
		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));
		players.add(new CombatPlayer(new AveragePlayerCharacter()));

		ICreature human_thug = CreatureLoader.getInstance().getCreatures().get("creature_human_thug");
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		monsters.add(new CombatMonster(human_thug));
		
		Combat combat = new Combat(players, monsters);
		combat.resolve();
		System.out.println("Combat was over in round:" + combat.getLastRound());
	}

}
