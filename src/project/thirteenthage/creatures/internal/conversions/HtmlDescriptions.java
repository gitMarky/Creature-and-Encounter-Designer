package project.thirteenthage.creatures.internal.conversions;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.TextFormatter;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

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
		description = replaceDamage(description, creatureDamage, inPercent);

		htmlText.append(Html.BEGIN_BOLD + attack.getName() + Html.END_BOLD);
		htmlText.append(" ");
		htmlText.append(String.format("%+d vs. %s", attack.getAttackBonus(), attack.getDefense()));
		htmlText.append(String.format(" - %d%s %s", damageFactor, inPercent ? "%" : "", description));
		
		for (final ITrigger trigger : attack.getTriggers())
		{
			htmlText.append(Html.LINE_BREAK);
			htmlText.append(trigger.toHtmlText());
		}


		return htmlText.toString();
	}
	

	public static String getTriggerDescription(final ITrigger trigger, final String creatureName, final double creatureDamage, final boolean inPercent)
	{
		final StringBuilder htmlText = new StringBuilder();

		htmlText.append(Html.BEGIN_ITALIC + trigger.getName() + Html.END_ITALIC);
		htmlText.append(": ");
		
		String description = replaceName(trigger.getDescription(), creatureName);
		description = replaceDamage(description, creatureDamage, inPercent);
		htmlText.append(description);

		return htmlText.toString();
	}
	
	
	public static String getSpecialDescription(final ISpecial special)
	{
		final StringBuilder htmlText = new StringBuilder();
		
		htmlText.append(Html.BEGIN_ITALIC + special.getName() + Html.END_ITALIC);
		htmlText.append( ": " + special.getDescription());
		
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
}
