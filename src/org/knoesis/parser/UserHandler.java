package org.knoesis.parser;

import java.io.IOException;
import java.util.Stack;

import org.knoesis.model.Page;
import org.knoesis.process.AnchorExtractor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler {
	
	private Page page;
	private Stack<String> elementStack = new Stack<String>();
	private StringBuilder sBuilder;
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.elementStack.push(qName);
		
		if(qName.equalsIgnoreCase("page")) {
			page = new Page();
		} else if(qName.equalsIgnoreCase("text")) {
			sBuilder = new StringBuilder();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(this.elementStack.pop().equalsIgnoreCase("text")) {
			try {
				if(sBuilder != null && sBuilder.length() > 0) {
					page.setText(sBuilder.toString());
					AnchorExtractor.extractAnchors(page);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		String value = new String(ch, start, length).trim();
		
		if(value.length() == 0)
			return;
		
		if(currentElement().equalsIgnoreCase("id") && currentElementParent().equalsIgnoreCase("page")) {
			page.setId(value);
			System.out.println("\t" + value);
		} else if(currentElement().equalsIgnoreCase("text")) {
			sBuilder.append(value);
		} else if(currentElementParent().equalsIgnoreCase("page") && currentElement().equalsIgnoreCase("title")) {
			page.setTitle(value);
			System.out.print(value);
		}
	}
	
	private String currentElement() {
		return this.elementStack.peek();
	}
	
	private String currentElementParent(){
		if (this.elementStack.size() < 2)
			return null;
		
		return this.elementStack.get(elementStack.size() - 2);
	}
	
}
