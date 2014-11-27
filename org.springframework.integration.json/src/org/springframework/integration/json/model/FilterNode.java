package org.springframework.integration.json.model;

import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class FilterNode extends ThroughNode {
	
	public FilterNode() {
		super();
	}

	public FilterNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<SingleLinkPort> initSourcePorts(Element element) {
		List<SingleLinkPort> initSourceAnchors = super.initSourcePorts(element);
		initSourceAnchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_DISCARD_CHANNEL, Constants.TRANSITION_TYPE_DASH));
		return initSourceAnchors;
	}

}