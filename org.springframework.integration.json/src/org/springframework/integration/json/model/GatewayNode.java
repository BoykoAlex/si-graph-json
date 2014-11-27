package org.springframework.integration.json.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class GatewayNode extends IntNode {
	
	public GatewayNode() {
		super();
	}

	public GatewayNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<? extends Port> initTargetPorts(Element element) {
		List<SingleLinkPort> anchors = new ArrayList<SingleLinkPort>(1);
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_DEFAULT_REPLY_CHANNEL, Constants.TRANSITION_TYPE_SOLID));
		return anchors;
	}

	@Override
	protected List<? extends Port> initSourcePorts(Element element) {
		List<SingleLinkPort> anchors = new ArrayList<SingleLinkPort>(1);
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_DEFAULT_REQUEST_CHANNEL, Constants.TRANSITION_TYPE_SOLID));
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_ERROR_CHANNEL, Constants.TRANSITION_TYPE_DASH));
		return anchors;
	}

}
