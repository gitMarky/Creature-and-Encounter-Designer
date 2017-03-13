package legacy.project.thirteenthage.creatures.internal.conversions;

import project.library.marky.html.Html;
import legacy.project.thirteenthage.creatures.interfaces.ITrigger;
import legacy.project.thirteenthage.creatures.internal.TextFormatter;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

/**
 * Descriptions for various interfaces.
 */
public class HtmlDescriptions
{
	public static String getAttackDescription(final IAttack attack, final String creatureName, final double creatureDamage, final double creatureLevel, final boolean inPercent)
	{
		if (attack == null)
		{
			throw new IllegalArgumentException("Parameter 'attack' must not be null.");
		}
		if (creatureName == null)
		{
			throw new IllegalArgumentException("Parameter 'creatureName' must not be null.");
		}

		final StringBuilder htmlText = new StringBuilder();
		final int damageFactor = Conversions.round(attack.getDamageFactor() * (inPercent ? 100.0 : 1.0));

		String description = replaceName(attack.getDescription(), creatureName);
		description = replaceDamage(description, creatureDamage, inPercent);
		description = replaceLevel(description, creatureLevel, inPercent);

		htmlText.append(Html.BEGIN_BOLD + attack.getName() + Html.END_BOLD);
		htmlText.append(" ");
		htmlText.append(String.format("%+d vs. %s%s", attack.getAttackBonus(), attack.getDefense(), attack.getInfo()));
		htmlText.append(String.format(" - %d%s %s", damageFactor, inPercent ? "%" : "", description));

		for (final ITrigger trigger : attack.getTriggers())
		{
			htmlText.append(Html.LINE_BREAK);
			htmlText.append(trigger.toHtmlText());
		}

		return htmlText.toString();
	}


	public static String getTriggerDescription(final ITrigger trigger, final String creatureName, final double creatureDamage, final double creatureLevel, final boolean inPercent)
	{
		if (trigger == null)
		{
			throw new IllegalArgumentException("Parameter 'trigger' must not be null.");
		}
		if (creatureName == null)
		{
			throw new IllegalArgumentException("Parameter 'creatureName' must not be null.");
		}

		final StringBuilder htmlText = new StringBuilder();

		htmlText.append(Html.BEGIN_ITALIC + trigger.getName() + Html.END_ITALIC);
		htmlText.append(": ");

		String description = replaceName(trigger.getDescription(), creatureName);
		description = replaceDamage(description, creatureDamage, inPercent);
		description = replaceLevel(description, creatureLevel, inPercent);
		htmlText.append(description);

		return htmlText.toString();
	}


	public static String getSpecialDescription(final ISpecial special, final String creatureName)
	{
		if (special == null)
		{
			throw new IllegalArgumentException("Parameter 'special' must not be null.");
		}
		if (creatureName == null)
		{
			throw new IllegalArgumentException("Parameter 'creatureName' must not be null.");
		}

		final StringBuilder htmlText = new StringBuilder();

		final String description = TextFormatter.parse(special.getDescription(), TextFormatter.PLACEHOLDER_NAME, creatureName);

		htmlText.append(Html.BEGIN_ITALIC + special.getName() + Html.END_ITALIC);
		htmlText.append(": " + description);

		return htmlText.toString();
	}


	private static String replaceName(final String text, final String name)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_NAME, name);
	}


	private static String replaceDamage(final String text, final double damage, final boolean inPercent)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_DAMAGE, damage, inPercent);
	}


	private static String replaceLevel(final String text, final double level, final boolean inPercent)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_LEVEL, level, inPercent);
	}
}
