package org.knoesis.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxParser {
	
	public static void main(String args[]) throws IOException {
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
//		AnchorExtractor.makePattern();
//		FileUtils.createTemplate();
		
		try {
			InputStream inputStream = new FileInputStream("/home/adarsh/test-xml");
			
			SAXParser saxParser = saxFactory.newSAXParser();
			UserHandler userHandler = new UserHandler();
			saxParser.parse(inputStream, userHandler);
			
		} catch(Throwable err) {
			err.printStackTrace ();
		}
	}
	
}
