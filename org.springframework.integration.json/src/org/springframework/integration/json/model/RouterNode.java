package org.springframework.integration.json.model;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class RouterNode extends ThroughNode {
	
	public RouterNode() {
		super();
	}

	public RouterNode(Element element, int id) {
		super(element, id);
	}

	@Override
	protected List<SingleLinkPort> initSourcePorts(Element element) {
		List<SingleLinkPort> anchors = super.initSourcePorts(element);
		
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_DEFAULT_OUTPUT_CHANNEL, Constants.TRANSITION_TYPE_SOLID));

		return anchors;
	}

	@Override
	public LinkedHashMap<String, Object> initProperties() {
		LinkedHashMap<String, Object> props = super.initProperties();
		if (element == null) {
			props.put(IntegrationSchemaConstants.ATTR_REF, null);
			props.put(IntegrationSchemaConstants.ATTR_METHOD, null);
			props.put(IntegrationSchemaConstants.ATTR_CHANNEL_RESOLVER, null);
			props.put(IntegrationSchemaConstants.ATTR_RESOLUTION_REQUIRED, null);
			props.put(IntegrationSchemaConstants.ATTR_IGNORE_CHANNEL_NAME_RESOLUTION_FAILURES, null);
			props.put(IntegrationSchemaConstants.ATTR_TIMEOUT, null);
		}
		return props;
	}	

}
