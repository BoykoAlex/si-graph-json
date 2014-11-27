package org.springframework.integration.json.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.springframework.integration.json.DefaultReferenceableElementsLocator;
import org.springframework.integration.json.IModelFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "format")  
	@JsonSubTypes({  
	    @Type(value = SiGraphModel.class, name = "Integration") })
public class SiGraphModel {
	
	private Document document;
	
	private String nsUri;
	
	private IModelFactory factory;
	
	private Map<Element, IntNode> xmlToJsonCache = new HashMap<Element, IntNode>();
	
	private List<IntNode> nodes;
	
	private List<Link> links;
	
	public SiGraphModel() {
		
	}
	
	public SiGraphModel(Document document, String nsUri, IModelFactory factory) {
		this.document = document;
		this.nsUri = nsUri;
		this.factory = factory;
	}
	
	private Map<String, Node> createRefNodeRegistry() {
		Map<String, Node> refNodeRegistry = new HashMap<String, Node>();
		Map<String, Set<Node>> tempNodes = DefaultReferenceableElementsLocator.getReferenceableElements(document);
		if (tempNodes != null) {
			for(String name: tempNodes.keySet()) {
				Set<Node> set = tempNodes.get(name);
				if (set != null && set.size() > 0) {
					Node node = set.iterator().next();
					if (node == null) {
					} else {
						refNodeRegistry.put(name, node);
					}
				}
			}
		}
		return refNodeRegistry;
	}
	
	private List<IntNode> getChildrenFromXml() {
		int count = 0;
		List<IntNode> list = new ArrayList<IntNode>();
		NodeList children = document.getDocumentElement().getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if (child instanceof Element) {
				Element childElem = (Element) child;
				IntNode jsonChild = (IntNode) factory.createJsonModel(null, childElem,
						count);
				if (jsonChild != null) {
					list.add(jsonChild);
					count++;
					xmlToJsonCache.put(childElem, jsonChild);
				}
			}
		}
		return list;
	}
	
	private List<Link> getLinksFromXml() {
		List<Link> list = new ArrayList<Link>();
		if (nodes != null) {
			Map<String, IntNode> referenceRegistry = createReferenceRegistry();
			for (IntNode node : nodes) {
				list.addAll(node.getSourceLinksFromXml(referenceRegistry));
				list.addAll(node.getTargetLinksFromXml(referenceRegistry));
			}
		}
		return list;
	}
	
	private Map<String, IntNode> createReferenceRegistry() {
		Map<String, IntNode> referenceRegistry = new HashMap<String, IntNode>();
		if (xmlToJsonCache != null) {
			for (Map.Entry<String, Node> entry : createRefNodeRegistry().entrySet()) {
				IntNode node = xmlToJsonCache.get(entry.getValue());
				if (node != null) {
					referenceRegistry.put(entry.getKey(), node);
				}
			}
		}
		return referenceRegistry;
	}
	
	public void buildFromXml() {
		nodes = getChildrenFromXml();
		links = getLinksFromXml();
	}
	
	public List<IntNode> getNodes() {
		return nodes;
	}
	
	public List<Link> getLinks() {
		return links;
	}

	public void setNodes(List<IntNode> nodes) {
		this.nodes = nodes;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	@JsonIgnore
	public Document getDocument() throws ParserConfigurationException {
		if (document == null) {
			  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			  //root elements
			  Document doc = docBuilder.newDocument();

			  Element rootElement = doc.createElement("si-graph");
			  doc.appendChild(rootElement);
			  
			  if (nodes != null) {
				  for (IntNode node : nodes) {
					  Element element = node.createXmlElement(doc, rootElement);
					  if (element != null) {
						  rootElement.appendChild(element);
					  }
				  }
			  }
			  
			  if (links != null) {
				  for (Link link : links) {
					  Element element = link.createXmlElement(doc, rootElement);
					  if (element != null) {
						  rootElement.appendChild(element);
					  }
				  }
			  }
			  document = doc;
		}
		return document;
	}
	
}
