package org.springframework.integration.json;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	
	private static ObjectNode convertDOMtoJson(Document document) {
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

}
