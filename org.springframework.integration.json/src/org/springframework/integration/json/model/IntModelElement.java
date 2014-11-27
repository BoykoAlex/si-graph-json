package org.springframework.integration.json.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class IntModelElement {

	private LinkedHashMap<String, Object> properties;
	
	private String type;

	protected Element element;

	public IntModelElement() {
	}
	
	public IntModelElement(Element element) {
		this.element = element;
		this.type = element.getNodeName();
		this.type = element.getNodeName();
		this.properties = initProperties();
	}
	
	protected LinkedHashMap<String, Object> initProperties() {
		LinkedHashMap<String, Object> props = new LinkedHashMap<String, Object>();
		if (element != null) {
			for (int i = 0; i < element.getAttributes().getLength(); i++) {
				Node n = element.getAttributes().item(i);
				if (n instanceof Attr) {
					Attr attr = (Attr) n;
					String propertyName = attr.getNodeName();
					if (!ignoreProperty(propertyName)) {
						props.put(propertyName, attr.getValue());
					}
				}
			}
		}
		return props.isEmpty() ? null : props;
	}

	
	protected boolean ignoreProperty(String name) {
		return false;
	}

	public LinkedHashMap<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(LinkedHashMap<String, Object> properties) {
		this.properties = properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Element createXmlElement(Document document, Element parent) {
		Element element = document.createElement(getType());
		if (getProperties() != null) {
			for (Map.Entry<String, Object> entry : getProperties().entrySet()) {
				element.setAttribute(entry.getKey(), entry.getValue().toString());
			}
		}
		return element;
	}

}
