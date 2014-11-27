/*******************************************************************************
 * Copyright (c) 2006, 2011 Spring IDE Developers
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Spring IDE Developers - initial API and implementation
 *******************************************************************************/
package org.springframework.integration.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Default implementation of {@link IReferenceableElementsLocator} that returns
 * every {@link Node} that has an <code>id</code> attribute.
 * @author Christian Dupuis
 * @author Terry Denney
 * @since 2.0
 */
public class DefaultReferenceableElementsLocator {

	public static Map<String, Set<Node>> getReferenceableElements(Document document) {
		Map<String, Set<Node>> nodes = new HashMap<String, Set<Node>>();
		NodeList childNodes = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (hasAttribute(node, "id")) {
				addNodeToMap(getAttribute(node, IntegrationSchemaConstants.ATTR_ID), node, nodes);
			}
			else if (hasAttribute(node, "alias")) {
				addNodeToMap(getAttribute(node, "alias"), node, nodes);
			}
		}
		return nodes;
	}
	
	private static void addNodeToMap(String name, Node node, Map<String, Set<Node>> nodes) {
		Set<Node> matchedNodes = nodes.get(name);
		if (matchedNodes == null) {
			matchedNodes = new HashSet<Node>();
			nodes.put(name, matchedNodes);
		}
		matchedNodes.add(node);
	}
	
	private static final boolean hasAttribute(Node node, String attributeName) {
		return (node != null && node.hasAttributes() && node.getAttributes().getNamedItem(attributeName) != null);
	}
	
	private static final String getAttribute(Node node, String attributeName) {
		if (hasAttribute(node, attributeName)) {
			return node.getAttributes().getNamedItem(attributeName).getNodeValue();
		}
		return null;
	}


}
