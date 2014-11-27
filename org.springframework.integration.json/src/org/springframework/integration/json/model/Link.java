package org.springframework.integration.json.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Link extends IntModelElement {
	
	private int from;
	
	private int to;
	
	private String sourcePort;
	
	private String targetPort;
	
	public Link() {
	}
	
	public Link(String type, int sourceId, int targetId) {
		super();
		setType(type);
		setFrom(sourceId);
		setTo(targetId);
		initProperties();
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(String targetPort) {
		this.targetPort = targetPort;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	@Override
	/**
	 * TODO: Reconsider if channel becomes a link
	 */
	public Element createXmlElement(Document document, Element parent) {
		return null;
	}
	
	
}
