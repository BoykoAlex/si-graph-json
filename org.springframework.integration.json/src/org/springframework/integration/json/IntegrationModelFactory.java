/*******************************************************************************
 *  Copyright (c) 2012 VMware, Inc.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *      VMware, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.integration.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.json.model.EnricherNode;
import org.springframework.integration.json.model.FilterNode;
import org.springframework.integration.json.model.GatewayNode;
import org.springframework.integration.json.model.InboundChannelAdapterNode;
import org.springframework.integration.json.model.IntModelElement;
import org.springframework.integration.json.model.IntNode;
import org.springframework.integration.json.model.OutboundChannelAdapterNode;
import org.springframework.integration.json.model.RouterNode;
import org.springframework.integration.json.model.ServiceActivatorNode;
import org.springframework.integration.json.model.ThroughNode;
import org.w3c.dom.Element;


public class IntegrationModelFactory implements IModelFactory {
	
	public static Map<String, Class<? extends IntNode>> SUPPORTED_ELEMENTS = new HashMap<String, Class<? extends IntNode>>();
	static {
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_AGGREGATOR, FilterNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_BRIDGE, ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_CHAIN, ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_CHANNEL, IntNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_IN, ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_OUT, ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_CONTROL_BUS,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_DELAYER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_ENRICHER,
				EnricherNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_EXCEPTION_TYPE_ROUTER,
				RouterNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_FILTER,
				FilterNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_GATEWAY,
				GatewayNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_HEADER_ENRICHER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_HEADER_FILTER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_HEADER_VALUE_ROUTER,
				RouterNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_INBOUND_CHANNEL_ADAPTER,
				InboundChannelAdapterNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_JSON_TO_OBJECT_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_LOGGING_CHANNEL_ADAPTER,
				OutboundChannelAdapterNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_MAP_TO_OBJECT_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_OBJECT_TO_JSON_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_OBJECT_TO_MAP_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_OBJECT_TO_STRING_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_OUTBOUND_CHANNEL_ADAPTER,
				OutboundChannelAdapterNode.class);
		SUPPORTED_ELEMENTS
				.put(IntegrationSchemaConstants.ELEM_PAYLOAD_DESERIALIZING_TRANSFORMER,
						ThroughNode.class);
		SUPPORTED_ELEMENTS
				.put(IntegrationSchemaConstants.ELEM_PAYLOAD_SERIALIZING_TRANSFORMER,
						ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_PAYLOAD_TYPE_ROUTER,
				RouterNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_PUBLISH_SUBSCRIBE_CHANNEL,
				IntNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_RECIPIENT_LIST_ROUTER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_RESEQUENCER,
				FilterNode.class);
		SUPPORTED_ELEMENTS
				.put(IntegrationSchemaConstants.ELEM_RESOURCE_INBOUND_CHANNEL_ADAPTER,
						InboundChannelAdapterNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_ROUTER,
				RouterNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_SERVICE_ACTIVATOR,
				ServiceActivatorNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_SPLITTER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(
				IntegrationSchemaConstants.ELEM_SYSLOG_TO_MAP_TRANSFORMER,
				ThroughNode.class);
		SUPPORTED_ELEMENTS.put(IntegrationSchemaConstants.ELEM_TRANSFORMER,
				ThroughNode.class);
	};

	@Override
	public IntNode createJsonModel(Object parent, Element childElement,
			int order) {
		Class<? extends IntNode> clazz = SUPPORTED_ELEMENTS.get(IntModelElement.extractName(childElement));
		if (clazz != null) {
			try {
				Constructor<? extends IntNode> constructor = clazz.getConstructor(Element.class, int.class);
				if (constructor != null) {
					return constructor.newInstance(childElement, order);
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
