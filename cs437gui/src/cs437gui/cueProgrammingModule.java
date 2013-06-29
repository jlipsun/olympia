package cs437gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class cueProgrammingModule {

	private ArrayList<Cue> cues;

	public cueProgrammingModule() {
		cues = new ArrayList<Cue>();
	}

	public void removeAllCues() {
		cues.removeAll(cues);
	}

	public void addCue(Cue newCue) {
		cues.add(newCue);
		OlympiaMainGui.g.setupSouthContainer();

		ArrayList<Cue> sortedCues = new ArrayList<Cue>();
		for (int c = 0; c < cues.size(); ++c) {
			Cue cue = cues.get(c);

			int insert = sortedCues.size();
			for (int s = 0; s < sortedCues.size(); ++s) {
				Cue sortedCue = sortedCues.get(s);
				if (cue.getStartTime() <  sortedCue.getStartTime()) {
					insert = 0;
					break;
				}
			}
			sortedCues.add(insert, cue);
		}

		cues = sortedCues;
	}

	public void removeCue(Cue oldCue) {
		cues.remove(oldCue);
		OlympiaMainGui.g.setupSouthContainer();
	}

	public ArrayList<Cue> getAllCues() {
		return cues; 
	}

	private void setExtraData(Document document, Element element, String name, String text) {
		Element dataElement = document.createElement(name);
		Text textElement = document.createTextNode(text);
		dataElement.appendChild(textElement);
		element.appendChild(dataElement);
	}

	private String getExtraData(Element element, String name) {
		NodeList childElements = element.getElementsByTagName(name);
		Element childElement = (Element)childElements.item(0);
		if (childElement == null)	return null;
		String text = childElement.getTextContent();
		return text;
	}

	public boolean saveCuesToFile(String path) {
		boolean success = false;

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document document = docBuilder.newDocument();
			Element rootElement = document.createElement("cues");
			if (OlympiaMainGui.videoURL != null)	rootElement.setAttribute("video", OlympiaMainGui.videoURL.toString());
			if (OlympiaMainGui.scoreURL != null)	rootElement.setAttribute("score", OlympiaMainGui.scoreURL.toString());
			if (OlympiaMainGui.librettoURL != null)	rootElement.setAttribute("libretto", OlympiaMainGui.librettoURL.toString());
			document.appendChild(rootElement);

			for (int c = 0; c < cues.size(); ++c) {
				Cue cue = cues.get(c);
				Element cueElement = document.createElement("cue");

				cueElement.setAttribute("startTime", Double.toString(cue.getStartTime()));
				cueElement.setAttribute("endTime", Double.toString(cue.getEndTime()));
				cueElement.setAttribute("moduleID", Double.toString(cue.getModuleID()));

				switch (cue.getModuleID()) {
					default: // mechanical cue
						MechanicalCue mechanicalCue = (MechanicalCue)cue;
						setExtraData(document, cueElement, "description", mechanicalCue.getDescription());
						setExtraData(document, cueElement, "x", Integer.toString(mechanicalCue.getX()));
						setExtraData(document, cueElement, "y", Integer.toString(mechanicalCue.getY()));
						break;

					case 4: // supertitle cue
						SupertitleCue supertitleCue = (SupertitleCue)cue;
						setExtraData(document, cueElement, "title", supertitleCue.getTitle());
						break;

					case 5: // lighting cue
						// set extra data from lighting cue
						LightingCue lightingCue = (LightingCue)cue;
						setExtraData(document, cueElement, "xPosition", Integer.toString(lightingCue.getXposition()));
						setExtraData(document, cueElement, "yPosition", Integer.toString(lightingCue.getYposition()));
						setExtraData(document, cueElement, "redComponent", Integer.toString(lightingCue.getRvalue()));
						setExtraData(document, cueElement, "greenComponent", Integer.toString(lightingCue.getGvalue()));
						setExtraData(document, cueElement, "blueComponent", Integer.toString(lightingCue.getBvalue()));
						setExtraData(document, cueElement, "goboPattern", lightingCue.getGoboPattern());
						setExtraData(document, cueElement, "flood", Integer.toString(lightingCue.getFlood()));
						setExtraData(document, cueElement, "tilt", Integer.toString(lightingCue.getTilt()));
						setExtraData(document, cueElement, "pan", Integer.toString(lightingCue.getPan()));
						break;

					case 6: // special effects cue
						SpecialEffectsCue specialEffectsCue = (SpecialEffectsCue)cue;
						setExtraData(document, cueElement, "subModuleID", Integer.toString(specialEffectsCue.getSubModuleID()));
						setExtraData(document, cueElement, "component", Integer.toString(specialEffectsCue.getComponent()));
						setExtraData(document, cueElement, "value", Integer.toString(specialEffectsCue.getValue()));
						break;

					// to do here: add case for remaining cue types;
				}

				rootElement.appendChild(cueElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(path));
			transformer.transform(source, result);

			success = true;
		}

		catch (ParserConfigurationException e)	{e.printStackTrace();}
		catch (TransformerException e)			{e.printStackTrace();}

		return success;
	}

	public boolean loadCuesFromFile(String path) {
		boolean success = false;
		cues.removeAll(cues);

		try {
			File xmlFile = new File(path);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse(xmlFile);

			Element rootElement = document.getDocumentElement();
			rootElement.normalize();
	
			String videoPath = rootElement.getAttribute("video");
			if (videoPath.length() > 0) {
				OlympiaMainGui.videoURL = new URL(videoPath);
				OlympiaMainGui.g.loadVideo(OlympiaMainGui.videoURL);
			}

			String scorePath = rootElement.getAttribute("score");
			if (scorePath.length() > 0) {
				OlympiaMainGui.scoreURL = new URL(scorePath);
				OlympiaMainGui.g.displayScore();
			}

			String librettoPath = rootElement.getAttribute("libretto");
			if (scorePath.length() > 0) {
				OlympiaMainGui.librettoURL = new URL(librettoPath);
				OlympiaMainGui.g.displayLibretto();
			}

			NodeList cueElements = rootElement.getElementsByTagName("cue");

			for (int c = 0; c < cueElements.getLength(); ++c) {
				Element cueElement = (Element)cueElements.item(c);

				double startTime = Double.parseDouble(cueElement.getAttribute("startTime"));
				double endTime = Double.parseDouble(cueElement.getAttribute("endTime"));
				int moduleID = (int)Double.parseDouble(cueElement.getAttribute("moduleID"));

				Cue cue = null;
	
				switch (moduleID) {
					default:
						// not associated with a module, just break
						break;

					case 0:
					case 1:
					case 2:
					case 3: // mechanical cue
						String description = getExtraData(cueElement, "despriotion");
						int xs = (int)Double.parseDouble(getExtraData(cueElement, "x"));
						int ys = (int)Double.parseDouble(getExtraData(cueElement, "y"));
						cue = new MechanicalCue(startTime, endTime, moduleID, description, xs, ys);
						break;

					case 4: // supertitle cue
						String title = getExtraData(cueElement, "title");
						cue = new SupertitleCue(startTime, endTime, title);
						break;

					case 5: // lighting cue
						int x = (int)Double.parseDouble(getExtraData(cueElement, "xPosition"));
						int y = (int)Double.parseDouble(getExtraData(cueElement, "yPosition"));
						int red = (int)Double.parseDouble(getExtraData(cueElement, "redComponent"));
						int green = (int)Double.parseDouble(getExtraData(cueElement, "greenComponent"));
						int blue = (int)Double.parseDouble(getExtraData(cueElement, "blueComponent"));
						String goboPattern = getExtraData(cueElement, "goboPattern");
						int flood = (int)Double.parseDouble(getExtraData(cueElement, "flood"));
						int tilt = (int)Double.parseDouble(getExtraData(cueElement, "tilt"));
						int pan = (int)Double.parseDouble(getExtraData(cueElement, "pan"));
						cue = new LightingCue(startTime, endTime, x, y, red, green, blue, goboPattern, flood, tilt, pan);
						break;

					case 6: // special effects cue
						int subModuleID = (int)Double.parseDouble(getExtraData(cueElement, "subModuleID"));
						int component = (int)Double.parseDouble(getExtraData(cueElement, "component"));
						int value = (int)Double.parseDouble(getExtraData(cueElement, "value"));
						cue = new SpecialEffectsCue(startTime, endTime, subModuleID, component, value);
						break;
				}

				if (cue != null)	this.addCue(cue);
			}

			success = true;
		}

		catch (NumberFormatException e)			{e.printStackTrace();}
		catch (ParserConfigurationException e)	{e.printStackTrace();}
		catch (SAXException e)					{e.printStackTrace();}
		catch (IOException e)					{e.printStackTrace();}

		if (success == false)	{
			cues.removeAll(cues);
		}

		return success;
	}
}