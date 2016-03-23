package org.knoesis.utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;







//import org.knoesis.config.Constants.Wikipedia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private List<String> entities = new ArrayList<String>();
	private DocumentBuilderFactory factory;
	public DocumentBuilder builder;
	
	
	public XMLParser() {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("Error initializing the object");
			e.printStackTrace();
		}
	}
	
	private Document init(String url) {
		try {
			Document document = builder.parse(url);
			return document;
		} catch (SAXException e) {
			System.out.println(url);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public Set<String> getCategoriesFromXml(String url) {
//		Set<String> categories = new TreeSet<String>();
//		NodeList nList = init(url).getElementsByTagName("rev");
//			
//		for(int i = 0; i < nList.getLength(); i++) {
//			Node node = nList.item(i);
//			
//			Element element = (Element) node;
//			String text = element.getTextContent();				
//			String lines[] = text.split("\n");
//				
//			for(String line : lines) {
//				if(line.contains(Wikipedia.CATEGORY)) {
//					categories.add(line.split(":")[1].split("]")[0].replace("|", "").trim());
//				}
//			}
//		}
//		return categories;
//	}
	
	public String getLinks(Document document) {
		NodeList nList = document.getElementsByTagName("page");
		String title = "";
		
		for(int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			
			Element element = (Element) node;
			title = element.getAttribute("title");
		}
		
		return title;
	}
	
	public String getIntroduction(Document document) {
		NodeList nList = document.getElementsByTagName("extract");
		
		Node node = nList.item(0);
		Element element = (Element) node;
		
		try {
			String introduction = element.getTextContent();
			return introduction;
		} catch(NullPointerException exception) {
			return null;
		}
		
	}
	
	public String getContent(Document document) {
		NodeList nList = document.getElementsByTagName("rev");
		StringBuilder sBuilder = new StringBuilder();
		
		for(int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			Element element = (Element) node;
			
			try {
				String content = element.getTextContent();
				sBuilder.append(content);
			} catch(NullPointerException exception) {
				continue;
			}
		}
		
		return sBuilder.toString();
		
	}
	
	public void displayEntities() {
		System.out.println(entities.size());
		Iterator<String> iterator = entities.iterator();
		
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	
	public List<String> getEntities() {
		return entities;
	}
	
	public static void main(String args[]) throws IOException {
		XMLParser parser = new XMLParser();
		BufferedReader bReader = new BufferedReader(new FileReader(new File("/home/adarsh/wikipedia/latest/myparser/uniq_page_ids")));
		String line = null;
		
		while((line = bReader.readLine()) != null) {
			String title = parser.getLinks(parser.init("https://en.wikipedia.org/w/api.php?action=query&prop=info&inprop=url&format=xml&pageids=" + line.trim()));
			System.out.println(title + "\t" + line.trim());
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("/home/adarsh/wikipedia/latest/myparser/ids_to_page_name_mapping"), true));
			bWriter.write(line.trim() + "\t" + title.trim() + "\n");
			bWriter.close();
		}
		
		bReader.close();
	}

	
}