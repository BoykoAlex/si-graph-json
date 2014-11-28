package org.springframework.integration.json.model;

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
	public String getName() {
		if (getProperties() != null) {
			String ref = (String) getProperties().get(IntegrationSchemaConstants.ATTR_REF);
			String method = (String) getProperties().get(IntegrationSchemaConstants.ATTR_METHOD);
			if (ref != null) {
				if (method != null) {
					return ref + '.' + method + "()";
				} else {
					return ref;
				}
			}
		}
		return super.getName();
	}

//	@Override
//	protected List<SingleLinkPort> initSourcePorts(Element element) {
//		List<SingleLinkPort> initSourceAnchors = super.initSourcePorts(element);
//		initSourceAnchors.add(createSingleLinkAnchor(element, IntegrationSchemaConstants.ATTR_REF, Constants.TRANSITION_TYPE_DASH));
//		return initSourceAnchors;
//	}

}
