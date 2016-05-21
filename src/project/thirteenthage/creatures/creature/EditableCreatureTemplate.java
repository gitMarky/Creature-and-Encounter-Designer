package project.thirteenthage.creatures.creature;

import java.io.File;

import org.jdom2.Document;
import org.jdom2.Element;

import project.thirteenthage.creatures.internal.ApplicationLogger;
import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.LoaderHelper;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class EditableCreatureTemplate extends AbstractCreatureTemplate
{
	public EditableCreatureTemplate(final ICreatureTemplate template)
	{
		setName(template.getName());
		setLevel(template.getLevel());
		setSize(template.getSize());
		getLabels().addAll(template.getLabels());
		setAttack(template.getModifierAttack());
		setAC(template.getModifierAC());
		setPD(template.getModifierPD());
		setMD(template.getModifierMD());
		setHP(template.getModifierHP());
		setInitiative(template.getModifierInitiative());
		getAttacks().addAll(template.getAttacks());
		getSpecials().addAll(template.getSpecials());
		getNastierSpecials().addAll(template.getNastierSpecials());
	}


	@Override
	public File saveToFile()
	{
		final String path = "custom/creature_" + getName().toLowerCase().replace(" ", "_") + "_" + System.currentTimeMillis() + LoaderHelper.EXTENSION_XML;
		final File targetFile = new File(Constants.RESOURCES, path);
		return saveToFile(targetFile);
	}


	@Override
	public File saveToFile(final File targetFile)
	{
		final Element nameElement = new Element(CreatureTemplate.ELEMENT_NAME);
		nameElement.setText(getName());

		final Element sizeElement = new Element(CreatureTemplate.ELEMENT_SIZE);
		sizeElement.setText(getSize().toGuiText());

		final Element levelElement = new Element(CreatureTemplate.ELEMENT_LEVEL);
		levelElement.setText(Integer.toString(getLevel()));

		final Element labelsElement = new Element(CreatureTemplate.ELEMENT_LABELS);
		for (final String labelText : getLabels())
		{
			final Element labelElement = new Element(CreatureTemplate.ELEMENT_LABEL);
			labelElement.setText(labelText);
			labelsElement.addContent(labelElement);
		}

		final Element modifiersElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS);
		final Element iniElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_INI);
		iniElement.setText(Integer.toString(getModifierInitiative()));

		final Element attackElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_ATTACK);
		attackElement.setText(Integer.toString(getModifierAttack()));

		final Element acElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_AC);
		acElement.setText(Integer.toString(getModifierAC()));

		final Element pdElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_PD);
		pdElement.setText(Integer.toString(getModifierPD()));

		final Element mdElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_MD);
		mdElement.setText(Integer.toString(getModifierMD()));

		final Element hpElement = new Element(CreatureTemplate.ELEMENT_MODIFIERS_HP);
		hpElement.setText(String.format("%.2f", getModifierHP()).replace(",", "."));

		if (getBetterDefense() == BetterDefense.MD)
		{
			mdElement.setAttribute(CreatureTemplate.ATTRIBUTE_BETTER, CreatureTemplate.ATTRIBUTE_VALUE_TRUE);
		}
		else
		{
			pdElement.setAttribute(CreatureTemplate.ATTRIBUTE_BETTER, CreatureTemplate.ATTRIBUTE_VALUE_TRUE);
		}

		modifiersElement.addContent(iniElement);
		modifiersElement.addContent(attackElement);
		modifiersElement.addContent(acElement);
		modifiersElement.addContent(pdElement);
		modifiersElement.addContent(mdElement);
		modifiersElement.addContent(hpElement);

		final Element attacksElement = new Element(CreatureTemplate.ELEMENT_ATTACKS);
		for (final IAttack attack : getAttacks())
		{
			final Element attackTemplateElement = new Element(CreatureTemplate.ELEMENT_ATTACK);
			attackTemplateElement.setAttribute(CreatureTemplate.ATTRIBUTE_ID, AttackTemplateLoader.getInstance().getId(attack));
			attacksElement.addContent(attackTemplateElement);
		}

		final Element specialsElement = new Element(CreatureTemplate.ELEMENT_SPECIALS);
		for (final ISpecial special : getSpecials())
		{
			final Element specialTemplateElement = new Element(CreatureTemplate.ELEMENT_SPECIAL);
			specialTemplateElement.setAttribute(CreatureTemplate.ATTRIBUTE_ID, SpecialTemplateLoader.getInstance().getId(special));
			specialsElement.addContent(specialTemplateElement);
		}

		final Element nastierElement = new Element(CreatureTemplate.ELEMENT_NASTIER);
		for (final ISpecial special : getNastierSpecials())
		{
			final Element specialTemplateElement = new Element(CreatureTemplate.ELEMENT_SPECIAL);
			specialTemplateElement.setAttribute(CreatureTemplate.ATTRIBUTE_ID, SpecialTemplateLoader.getInstance().getId(special));
			nastierElement.addContent(specialTemplateElement);
		}

		final Element rootElement = new Element(CreatureTemplate.ROOT_ELEMENT);

		rootElement.addContent(nameElement);
		rootElement.addContent(sizeElement);
		rootElement.addContent(levelElement);
		rootElement.addContent(labelsElement);
		rootElement.addContent(modifiersElement);
		rootElement.addContent(attacksElement);
		rootElement.addContent(specialsElement);
		rootElement.addContent(nastierElement);

		final Document document = new Document(rootElement);

		final BasicXmlFile template = new BasicXmlFile(document, targetFile);
		template.saveToFile();

		ApplicationLogger.getLogger().info("Saving new creature to: " + targetFile.getAbsolutePath());

		long oldLength = -1;
		long newLength = targetFile.length();
		for (int i = 0; oldLength != newLength && i < 60; ++i)
		{
			oldLength = newLength;
			newLength = targetFile.length();

			ApplicationLogger.getLogger().info("... still writing the file");

			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		return targetFile;
	}
}
