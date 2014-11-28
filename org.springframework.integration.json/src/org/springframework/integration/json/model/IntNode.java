package org.springframework.integration.json.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.springframework.integration.json.IntegrationSchemaConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "nodeType")  
	@JsonSubTypes({  
	    @Type(value = FilterNode.class, name = "filter"),  
	    @Type(value = ThroughNode.class, name = "through"),
	    @Type(value = IntNode.class, name = "channel"),
	    @Type(value = EnricherNode.class, name = "enricher"),
	    @Type(value = RouterNode.class, name = "router"),
	    @Type(value = GatewayNode.class, name = "gateway"),
	    @Type(value = InboundChannelAdapterNode.class, name = "inbound-channel-adapter"),
	    @Type(value = OutboundChannelAdapterNode.class, name = "outbound-channel-adapter"),
	    @Type(value = ServiceActivatorNode.class, name = "service-activator"),
	    @Type(value = ResequencerNode.class, name = "resequencer"),
	    @Type(value = ChannelNode.class, name = "channel"),
	    }) 
public class IntNode extends IntModelElement implements IEnumeratedModelElement, INamedModelElement {
		
	private int id;
	
	private List<? extends Port> sourcePorts;
	
	private List<? extends Port> targetPorts;
	
	public IntNode() {
	}
	
	public IntNode(Element element, int id) {
		super(element);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public List<? extends Port> getSourcePorts() {
		return sourcePorts;
	}

	public List<? extends Port> getTargetPorts() {
		return targetPorts;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSourcePorts(List<? extends Port> sourceAnchors) {
		this.sourcePorts = sourceAnchors;
	}

	public void setTargetPorts(List<? extends Port> targetAnchors) {
		this.targetPorts = targetAnchors;
	}

	protected List<? extends Port> initSourcePorts(Element element) {
		return null;
	}
	
	protected List<? extends Port> initTargetPorts(Element element) {
		return null;
	}

	public LinkedHashMap<String, Object> initProperties() {
		this.sourcePorts = initSourcePorts(element);
		this.targetPorts = initTargetPorts(element);
		LinkedHashMap<String, Object> props = super.initProperties();
		if (element == null) {
			props.put(IntegrationSchemaConstants.ATTR_AUTO_STARTUP, null);
			props.put(IntegrationSchemaConstants.ATTR_ORDER, null);
		}
		return props; 
	}
	
	@Override
	protected boolean ignoreProperty(String propertyName) {
		return super.ignoreProperty(propertyName)
				|| isPortProperty(sourcePorts, propertyName)
				|| isPortProperty(targetPorts, propertyName);
	}

	private static boolean isPortProperty(List<? extends Port> ports, String property) {
		if (ports != null) {
			for (Port port : ports) {
				if (port.getName().equals(property)) {
					return true;
				}
			}
		}
		return false;
	}
	
	static protected SingleLinkPort createSingleLinkAnchor(Element element, String attributeName, String transitionType) {
		SingleLinkPort anchor = new SingleLinkPort(attributeName, transitionType);
		if (element != null && element.hasAttribute(attributeName)) {
			anchor.setOppositeEndSemanticId(element.getAttribute(attributeName));
		}
		return anchor;
	}
	
	@JsonIgnore
	public List<Link> getSourceLinksFromXml(Map<String, IntModelElement> referenceRegistry) {
		List<Link> list = new ArrayList<Link>();
		if (getSourcePorts() != null) {
			for (Port a : getSourcePorts()) {
				if (a instanceof SingleLinkPort) {
					SingleLinkPort port = (SingleLinkPort) a;
					IntModelElement target = referenceRegistry.get(port
							.getOppositeEndSemanticId());
					if (target instanceof IEnumeratedModelElement) {
						Link link = new Link(port.getLinkType(), getId(),
								((IEnumeratedModelElement)target).getId());
						link.setSourcePort(a.getName());
						list.add(link);
					}
				}
			}
		}
		return list;
	}

	@JsonIgnore
	public List<Link> getTargetLinksFromXml(Map<String, IntModelElement> referenceRegistry) {
		List<Link> list = new ArrayList<Link>();
		if (getTargetPorts() != null) {
			for (Port a : getTargetPorts()) {
				if (a instanceof SingleLinkPort) {
					SingleLinkPort port = (SingleLinkPort) a;
					IntModelElement source = referenceRegistry.get(port
							.getOppositeEndSemanticId());
					if (source instanceof IEnumeratedModelElement) {
						Link link = new Link(port.getLinkType(), ((IEnumeratedModelElement)source).getId(),
								getId());
						link.setTargetPort(a.getName());
						list.add(link);
					}
				}
			}
		}
		return list;
	}

	public Element createXmlElement(Document document, Element parent) {
		Element element = super.createXmlElement(document, parent);
		if (getSourcePorts() != null) {
			for (Port port : getSourcePorts()) {
				port.addToOwnerXmlNode(element);
			}
		}
		if (getTargetPorts() != null) {
			for (Port port : getTargetPorts()) {
				port.addToOwnerXmlNode(element);
			}
		}
		return element;
	}

	@Override
	public String getName() {
		return getType();
	}
	
	public void setName(String name) {
		// nothing - name only for JSON serialization
	}
	
}
