package org.springframework.integration.json.model;

import org.w3c.dom.Element;

/**
 * @author aboyko
 *
 */
public class SingleLinkPort extends Port {
	
	private String linkType;
	private String oppositeEndId;
	private String oppositeEndSemanticId;
	
	public SingleLinkPort() {
		super();
	}
	
	public SingleLinkPort(String name, String linkType) {
		super(name);
		this.linkType = linkType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getOppositeEndId() {
		return oppositeEndId;
	}

	public void setOppositeEndId(String oppositeEndId) {
		this.oppositeEndId = oppositeEndId;
	}

	public String getOppositeEndSemanticId() {
		return oppositeEndSemanticId;
	}

	public void setOppositeEndSemanticId(String oppositeAnchorId) {
		this.oppositeEndSemanticId = oppositeAnchorId;
	}
	
	public void addToOwnerXmlNode(Element owner) {
		if (oppositeEndSemanticId != null) {
			owner.setAttribute(getName(), oppositeEndSemanticId);
		}
	}

	
}
