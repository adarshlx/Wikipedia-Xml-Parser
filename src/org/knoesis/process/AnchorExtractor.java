package org.knoesis.process;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.knoesis.model.Page;
import org.knoesis.utils.FileUtils;

public class AnchorExtractor {
	
	private static Pattern pattern;
	private static Matcher matcher;
	
	public static void extractAnchors(Page page) throws IOException {
		String tokens[] = page.getText().split("\n");
		StringBuilder sBuilder = new StringBuilder();
		
		for(String token : tokens) {
			matcher = pattern.matcher(token);
			sBuilder.append(mergePatterns(page));
		}
		
		FileUtils.writeToFile(sBuilder.toString());
	}
	
	public static void makePattern() {
		pattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
	}
	
	private static String mergePatterns(Page page) {
		StringBuilder sBuilder = new StringBuilder();
		
		try {
			while(matcher.find()) {
				if(!matcher.group(1).trim().startsWith("Category:") && !matcher.group(1).trim().startsWith("File:") && !matcher.group(1).trim().startsWith("Image:") && !matcher.group(1).trim().startsWith("Media:")
						&& !matcher.group(1).trim().startsWith("category:") && !matcher.group(1).trim().startsWith("file:") && !matcher.group(1).trim().startsWith("image:") && !matcher.group(1).trim().startsWith("media:")
						&& !matcher.group(1).trim().startsWith("wikt:")) {
					String tokens[] = matcher.group(1).split("\\|");
					if(tokens.length > 1)
						sBuilder.append(page.getId() + "\t" + page.getTitle() + "\t" + tokens[0] + "\t" + tokens[1] + "\n");
					else
						sBuilder.append(page.getId() + "\t" + page.getTitle() + "\t" + tokens[0] + "\t" + tokens[0] + "\n");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
		
		return sBuilder.toString();
	}

}
