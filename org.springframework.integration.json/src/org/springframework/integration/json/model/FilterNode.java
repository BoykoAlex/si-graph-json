package org.springframework.integration.json.model;

import java.util.LinkedHashMap;
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

	@Override
	public LinkedHashMap<String, Object> initProperties() {
		LinkedHashMap<String, Object> initProperties = super.initProperties();
		if (element == null) {
			initProperties.put(IntegrationSchemaConstants.ATTR_THROW_EXCEPTION_ON_REJECTION, false);
		}
		return initProperties;
	}
	
	

}
