package org.springframework.integration.json.model;

import java.util.Collections;
import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class InboundChannelAdapterNode extends IntNode {

	public InboundChannelAdapterNode() {
		super();
	}

	public InboundChannelAdapterNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<? extends Port> initSourcePorts(Element element) {
		return Collections.singletonList(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_CHANNEL, Constants.TRANSITION_TYPE_SOLID));
	}
	
}
