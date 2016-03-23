package org.knoesis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.knoesis.config.Config;

public class FileUtils {
	
	public static void createTemplate() throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(Config.OUTPUT_FILE_NAME), true));
		bWriter.write("source_page\tdestination_page\tanchor_text\n");
		bWriter.close();
	}
	
	public static void writeToFile(String content) throws IOException {
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(Config.OUTPUT_FILE_NAME), true));
		bWriter.write(content);
		bWriter.close();
	}
	
	public static void writeToFile(String fileName, String content) {
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(fileName), true));
			bWriter.write(content);
			bWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}
