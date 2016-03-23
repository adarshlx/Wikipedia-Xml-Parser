package org.knoesis.preparation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EntitySpotter {
	
	public static void main(String args[]) throws IOException {
		BufferedReader bReader = new BufferedReader(new FileReader(new File("/home/adarsh/annotated_movie_tweets")));
		String line = null;
		StringBuilder output = new StringBuilder();
		
		while((line = bReader.readLine()) != null) {
			output.append(line.trim()).append("\t").append(spotEntitiies(line.trim())).append("\n");
		}
		
		bReader.close();
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("/home/adarsh/annotated_movie_tweets_entities")));
		bWriter.write(output.toString());
		bWriter.close();
	}
	
	private static String spotEntitiies(String tweet) {
		String tokens[] = tweet.split(" ");
		StringBuilder entities = new StringBuilder();
		boolean flag = false;
		
		for(int i = 0; i < tokens.length; i ++) {
			String annotation[] = tokens[i].split("/");
			
			if(annotation[1].equals("B-ENTITY")) {
				flag = true;
				entities.append(annotation[0]);
			} else if(annotation[1].equals("I-ENTITY") || annotation[1].equals("O-ENTITY")) {
				entities.append(" ").append(annotation[0]);
			} else {
				if(flag == true) {
					flag = false;
					entities.append(", ");
				}
			}
		}
		
		if(flag == false && entities.length() > 0) {
			return entities.substring(0, entities.length() - 2);
		}
		
		return entities.toString();
	}

}
