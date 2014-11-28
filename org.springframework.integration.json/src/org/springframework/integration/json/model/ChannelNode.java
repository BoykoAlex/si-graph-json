package org.springframework.integration.json.model;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class ChannelNode extends IntNode {
	
	public ChannelNode() {
		super();
	}
	
	public ChannelNode(Element element, int id) {
		super(element, id);
	}

	@Override
	public String getName() {
		if (getProperties() != null && getProperties().containsKey(IntegrationSchemaConstants.ATTR_ID)) {
			return getProperties().get(IntegrationSchemaConstants.ATTR_ID).toString();
		}
		return super.getName();
	}

}
