package org.springframework.integration.json.model;

import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class ServiceActivatorNode extends ThroughNode {

	public ServiceActivatorNode() {
		super();
	}

	public ServiceActivatorNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<SingleLinkPort> initSourcePorts(Element element) {
		List<SingleLinkPort> initSourceAnchors = super.initSourcePorts(element);
		initSourceAnchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_REF, Constants.TRANSITION_TYPE_DASH));
		return initSourceAnchors;
	}

}
