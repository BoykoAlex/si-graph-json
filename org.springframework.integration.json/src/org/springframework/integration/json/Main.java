package org.springframework.integration.json;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.node.ObjectNode;
import org.w3c.dom.Document;
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
				
				Document document = ParserHelperInt.convertToXml(new ByteArrayInputStream(json.toString().getBytes()));
				printDocument(document, System.out);
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
	}
	
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}

}
