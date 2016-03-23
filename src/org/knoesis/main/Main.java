package org.knoesis.main;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.knoesis.config.Config;
import org.knoesis.parser.UserHandler;
import org.knoesis.process.AnchorExtractor;

public class Main {
	
	public static void main(String args[]) {
		Config.setConstants(args);
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		AnchorExtractor.makePattern();
		try {
            InputStream xmlInput  = new FileInputStream(Config.INPUT_FILE_NAME);

            SAXParser saxParser = saxParserFactory.newSAXParser();
            UserHandler handler   = new UserHandler();
            saxParser.parse(xmlInput, handler);

        } catch (Throwable err) {
            err.printStackTrace ();
        }
	}

}
