package org.springframework.integration.json;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.integration.json.model.SiGraphModel;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserHelperInt {
	
	public static ObjectNode convertToJson(InputStream is) throws SAXException, IOException, ParserConfigurationException {
	    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(is));
		return convertDOMtoJson(document);
	}
	
	public static ObjectNode convertDOMtoJson(Document document) {
		SiGraphModel model = new SiGraphModel(document, "http://www.springframework.org/schema/integration", new IntegrationModelFactory());
		model.buildFromXml();
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		return om.convertValue(model, ObjectNode.class);
	}
	
	public static Document convertToXml(InputStream is) throws ParserConfigurationException, JsonParseException, JsonMappingException, IOException {
	    SiGraphModel model = new ObjectMapper().readValue(is, SiGraphModel.class);
	    return model.getDocument();
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
	
	public static ObjectNode convertXmlTextToJson(String xml) throws SAXException, IOException, ParserConfigurationException {
		return convertToJson(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public static String convertXmlTextToJsonText(String xml) throws SAXException, IOException, ParserConfigurationException {
		return convertXmlTextToJson(xml).toString();
	}
	
	public static Document convertJsonTexttoXml(String json) throws JsonParseException, JsonMappingException, ParserConfigurationException, IOException {
	    return convertToXml(new ByteArrayInputStream(json.getBytes()));
	}
	
	public static String convertJsonTextToXmlText(String json) throws JsonParseException, JsonMappingException, ParserConfigurationException, IOException, TransformerException {
		Document doc = convertJsonTexttoXml(json);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		printDocument(doc, baos);
		return baos.toString();
	}
	
}
