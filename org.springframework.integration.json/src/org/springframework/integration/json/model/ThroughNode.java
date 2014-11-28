package org.springframework.integration.json.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Element;

public class ThroughNode extends IntNode {

	public ThroughNode() {
		super();
	}

	public ThroughNode(Element element, int id) {
		super(element, id);
	}
	
	@Override
	protected List<SingleLinkPort> initTargetPorts(Element element) {
		List<SingleLinkPort> anchors = new ArrayList<SingleLinkPort>(1);
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_INPUT_CHANNEL, Constants.TRANSITION_TYPE_SOLID));
		return anchors;
	}

	@Override
	protected List<SingleLinkPort> initSourcePorts(Element element) {
		List<SingleLinkPort> anchors = new ArrayList<SingleLinkPort>(1);
		anchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_OUTPUT_CHANNEL, Constants.TRANSITION_TYPE_SOLID));
		return anchors;
	}
	
	@Override
	public LinkedHashMap<String, Object> initProperties() {
		LinkedHashMap<String, Object> props = super.initProperties();
		if (element == null) {
			props.put(IntegrationSchemaConstants.ATTR_REF, null);
			props.put(IntegrationSchemaConstants.ATTR_METHOD, null);
		}
		return props;
	}

}
