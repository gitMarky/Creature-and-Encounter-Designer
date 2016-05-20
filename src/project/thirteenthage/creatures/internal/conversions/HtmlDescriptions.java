package project.thirteenthage.creatures.internal.conversions;

import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.TextFormatter;
import project.thirteenthage.creatures.internal.interfaces.IAttack;

/**
 * Descriptions for various interfaces.
 */
public class HtmlDescriptions
{
	public static String getAttackDescription(final IAttack attack, final String creatureName, final double creatureDamage, final boolean inPercent)
	{
		final StringBuilder htmlText = new StringBuilder();
		int damageFactor = Conversions.round(attack.getDamageFactor() * (inPercent ? 100.0 : 1.0));

		String description = replaceName(attack.getDescription(), creatureName);
		if (inPercent)
		{
			description = makeDamageRelative(description);
		}
		description = replaceDamage(description, creatureDamage);

		htmlText.append(Html.BEGIN_BOLD + attack.getName() + Html.END_BOLD);
		htmlText.append(" ");
		htmlText.append(String.format("%+d vs. %s", attack.getAttackBonus(), attack.getDefense()));
		htmlText.append(String.format(" - %d%s %s", damageFactor, inPercent ? "%" : "", description));

		return htmlText.toString();
	}


	private static String replaceName(final String text, final String name)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_NAME, name);
	}


	private static String makeDamageRelative(final String text)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_DAMAGE, TextFormatter.PLACEHOLDER_DAMAGE + "%");
	}


	private static String replaceDamage(final String text, final double damage)
	{
		return TextFormatter.parse(text, TextFormatter.PLACEHOLDER_DAMAGE, damage);
	}
}
