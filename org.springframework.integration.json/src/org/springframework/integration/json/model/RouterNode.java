package org.springframework.integration.json.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
			props.put(IntegrationSchemaConstants.ATTR_EXPRESSION, null);
		}
		return props;
	}

	@Override
	public List<Link> getSourceLinksFromXml(
			Map<String, IntModelElement> referenceRegistry) {
		List<Link> sourceLinksFromXml = super.getSourceLinksFromXml(referenceRegistry);
		if (element.hasAttribute(IntegrationSchemaConstants.ATTR_INPUT_CHANNEL) && getProperties() != null) {
			String expression = (String) getProperties().get(IntegrationSchemaConstants.ATTR_EXPRESSION);
			if (expression != null) {
				for (String id : Pattern.compile("\\W").split(expression)) {
					IntModelElement target = referenceRegistry.get(id);
					if (target instanceof IEnumeratedModelElement) {
						Link link = new Link(Constants.TRANSITION_TYPE_SOLID, getId(),
								((IEnumeratedModelElement)target).getId());
						sourceLinksFromXml.add(link);
					}
				}
			}
		}
		return sourceLinksFromXml;
	}
	
	@Override
	public List<Link> getTargetLinksFromXml(
			Map<String, IntModelElement> referenceRegistry) {
		List<Link> sourceLinksFromXml = super
				.getSourceLinksFromXml(referenceRegistry);
		if (element
				.hasAttribute(IntegrationSchemaConstants.ATTR_OUTPUT_CHANNEL)
				&& getProperties() != null) {
			String expression = (String) getProperties().get(
					IntegrationSchemaConstants.ATTR_EXPRESSION);
			if (expression != null) {
				for (String id : Pattern.compile("\\W").split(expression)) {
					IntModelElement source = referenceRegistry.get(id);
					if (source instanceof IEnumeratedModelElement) {
						Link link = new Link(Constants.TRANSITION_TYPE_SOLID,
								((IEnumeratedModelElement) source).getId(),
								getId());
						sourceLinksFromXml.add(link);
					}
				}
			}
		}
		return sourceLinksFromXml;
	}	

}
