package org.springframework.integration.json.model;

import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class EnricherNode extends ThroughNode {
	
	public EnricherNode() {
		super();
	}

	public EnricherNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<SingleLinkPort> initTargetPorts(Element element) {
		List<SingleLinkPort> targetAnchors = super.initTargetPorts(element);
		targetAnchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_REPLY_CHANNEL, Constants.TRANSITION_TYPE_DASH));
		return targetAnchors;
	}

	@Override
	protected List<SingleLinkPort> initSourcePorts(Element element) {
		List<SingleLinkPort> initSourceAnchors = super.initSourcePorts(element);
		initSourceAnchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_REQUEST_CHANNEL, Constants.TRANSITION_TYPE_DASH));
		return initSourceAnchors;
	}

}
