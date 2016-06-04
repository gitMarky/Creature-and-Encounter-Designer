package project.thirteenthage.creatures.internal;

public class Html
{
	public static String BEGIN = "<html>";
	public static String END = "</html>";

	public static final String BEGIN_ITALIC = "<i>";
	public static final String END_ITALIC = "</i>";

	public static final String BEGIN_BOLD = "<b>";
	public static final String END_BOLD = "</b>";

	public static final String BEGIN_UNDERLINE = "<u>";
	public static final String END_UNDERLINE = "</u>";

	public static final String BEGIN_PARAGRAPH = "<p>";
	public static final String END_PARAGRAPH = "</p>";
	
	public static final String BEGIN_BODY = "<body>";
	public static final String END_BODY = "</body>";
	
	public static final String BEGIN_DIV = "<div>";
	public static final String END_DIV = "</div>";

	public static String LINE_BREAK = "<br>";

	public static String tableRow(final String... entries)
	{
		final StringBuilder description = new StringBuilder();
		
		description.append("<tr>");
		
		for (final String entry : entries)
				{
					description.append("<td>" + entry + "</td>");
				}
		
		description.append("</tr>");
		
		return description.toString();
	}
}
