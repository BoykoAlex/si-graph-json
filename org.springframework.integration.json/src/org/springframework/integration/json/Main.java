package org.springframework.integration.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.codehaus.jackson.node.ObjectNode;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
		if (args.length > 0) {
			File file = new File(args[0]);
			if (!file.exists()) {
				throw new IllegalArgumentException("File does not exist!");
			}
			if (!file.isFile()) {
				throw new IllegalArgumentException(file.getPath() + " is not a file!");
			}
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				ObjectNode json = ParserHelperInt.convertToJson(is);
				System.out.println(json);
				
				System.out.println(ParserHelperInt.convertJsonTextToXmlText(json.toString()));
				
				System.out.println(PaletteHelper.getPalette());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
	}
	
}
