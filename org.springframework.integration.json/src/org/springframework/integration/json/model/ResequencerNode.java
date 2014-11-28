package org.springframework.integration.json.model;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class ResequencerNode extends ThroughNode {
	
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
			initProperties.put(IntegrationSchemaConstants.ATTR_CORRELATION_STRATEGY, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_CORRELATION_STRATEGY_METHOD, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_SEND_TIMEOUT, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_SEND_PARTIAL_RESULT_ON_TIMEOUT, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_RELEASE_PARTIAL_SEQUENCES, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_TRACKED_CORRELATION_ID_CAPACITY, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_REAPER_INTERVAL, null);
			initProperties.put(IntegrationSchemaConstants.ATTR_TIMEOUT, null);
		}
		return initProperties;
	}

	

}
