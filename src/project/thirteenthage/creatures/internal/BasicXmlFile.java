package project.thirteenthage.creatures.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class BasicXmlFile
{
	private final File _file;
	private Document _doc;


	public BasicXmlFile(final File file)
	{
		_file = file;
		_doc = null;

		SAXBuilder builder = new SAXBuilder();
		try
		{
			_doc = builder.build(_file);
		} catch (JDOMException e)
		{
			_doc = new Document();
		} catch (IOException e)
		{
			throw new IllegalStateException();
		}
	}


	public boolean saveToFile()
	{
		return saveToFile(_file);
	}


	public boolean saveToFile(final File file)
	{
		// get object to see output of prepared document
		XMLOutputter xmlOutput = new XMLOutputter();

		// passed fileWriter to write content in specified file
		xmlOutput.setFormat(Format.getPrettyFormat());
		// xmlOutput.output(_doc, new
		// FileWriter("generatedXmlFiles/generatedXml.xml"));
		try
		{
			xmlOutput.output(_doc, new FileWriter(file));
			return true;
		} catch (IOException e)
		{
			return false;
		}
	}


	public File getFile()
	{
		return _file;
	}


	public Document getDocument()
	{
		return _doc;
	}


	public Element getRoot()
	{
		return getDocument().getRootElement();
	}
}
