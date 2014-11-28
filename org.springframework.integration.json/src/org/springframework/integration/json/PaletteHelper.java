package org.springframework.integration.json;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.integration.json.model.Constants;
import org.springframework.integration.json.model.IntModelElement;

public class PaletteHelper {
	
	public enum Groups {
		Connections,
		Channels,
		Routing,
		Transformation,
		Endpoints
	}
	
	private static Map<String, Info> ELEMENT_INFO = new HashMap<String, Info>();
	static {
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_AGGREGATOR, 
				new Info(IntegrationSchemaConstants.ELEM_AGGREGATOR, IntegrationSchemaConstants.ELEM_AGGREGATOR, "aggregator.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_BRIDGE,
				new Info(IntegrationSchemaConstants.ELEM_BRIDGE, IntegrationSchemaConstants.ELEM_BRIDGE, "bridge.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_CHAIN, 
				new Info(IntegrationSchemaConstants.ELEM_CHAIN, IntegrationSchemaConstants.ELEM_CHAIN, "chain.png", Groups.Endpoints.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_CHANNEL,
				new Info(IntegrationSchemaConstants.ELEM_CHANNEL, IntegrationSchemaConstants.ELEM_CHANNEL, "channel.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_IN,
				new Info(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_IN, IntegrationSchemaConstants.ELEM_CLAIM_CHECK_IN, "claim-check.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_OUT,
				new Info(IntegrationSchemaConstants.ELEM_CLAIM_CHECK_OUT, IntegrationSchemaConstants.ELEM_CLAIM_CHECK_OUT, "claim-check.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_CONTROL_BUS,
				new Info(IntegrationSchemaConstants.ELEM_CONTROL_BUS, IntegrationSchemaConstants.ELEM_CONTROL_BUS, "control-bus.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_DELAYER,
				new Info(IntegrationSchemaConstants.ELEM_DELAYER, IntegrationSchemaConstants.ELEM_DELAYER, "delayer.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_ENRICHER,
				new Info(IntegrationSchemaConstants.ELEM_ENRICHER, IntegrationSchemaConstants.ELEM_ENRICHER, "enricher.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_EXCEPTION_TYPE_ROUTER,
				new Info(IntegrationSchemaConstants.ELEM_EXCEPTION_TYPE_ROUTER, IntegrationSchemaConstants.ELEM_EXCEPTION_TYPE_ROUTER, "exception-router.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_FILTER,
				new Info(IntegrationSchemaConstants.ELEM_FILTER, IntegrationSchemaConstants.ELEM_FILTER, "filter.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_GATEWAY,
				new Info(IntegrationSchemaConstants.ELEM_GATEWAY, IntegrationSchemaConstants.ELEM_GATEWAY, "inbound-gateway.png", Groups.Endpoints.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_HEADER_ENRICHER,
				new Info(IntegrationSchemaConstants.ELEM_HEADER_ENRICHER, IntegrationSchemaConstants.ELEM_HEADER_ENRICHER, "enricher.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_HEADER_FILTER,
				new Info(IntegrationSchemaConstants.ELEM_HEADER_FILTER, IntegrationSchemaConstants.ELEM_HEADER_FILTER, "content-filter.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_HEADER_VALUE_ROUTER,
				new Info(IntegrationSchemaConstants.ELEM_HEADER_VALUE_ROUTER, IntegrationSchemaConstants.ELEM_HEADER_VALUE_ROUTER, "router.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_INBOUND_CHANNEL_ADAPTER,
				new Info(IntegrationSchemaConstants.ELEM_INBOUND_CHANNEL_ADAPTER, IntegrationSchemaConstants.ELEM_INBOUND_CHANNEL_ADAPTER, "inbound-adapter.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_JSON_TO_OBJECT_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_JSON_TO_OBJECT_TRANSFORMER, IntegrationSchemaConstants.ELEM_JSON_TO_OBJECT_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_LOGGING_CHANNEL_ADAPTER,
				new Info(IntegrationSchemaConstants.ELEM_LOGGING_CHANNEL_ADAPTER, IntegrationSchemaConstants.ELEM_LOGGING_CHANNEL_ADAPTER, "outbound-adapter.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_MAP_TO_OBJECT_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_MAP_TO_OBJECT_TRANSFORMER, IntegrationSchemaConstants.ELEM_MAP_TO_OBJECT_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_OBJECT_TO_JSON_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_OBJECT_TO_JSON_TRANSFORMER, IntegrationSchemaConstants.ELEM_OBJECT_TO_JSON_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_OBJECT_TO_MAP_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_OBJECT_TO_MAP_TRANSFORMER, IntegrationSchemaConstants.ELEM_OBJECT_TO_MAP_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_OBJECT_TO_STRING_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_OBJECT_TO_STRING_TRANSFORMER, IntegrationSchemaConstants.ELEM_OBJECT_TO_STRING_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_OUTBOUND_CHANNEL_ADAPTER,
				new Info(IntegrationSchemaConstants.ELEM_OUTBOUND_CHANNEL_ADAPTER, IntegrationSchemaConstants.ELEM_OUTBOUND_CHANNEL_ADAPTER, "outbound-adapter.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_PAYLOAD_DESERIALIZING_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_PAYLOAD_DESERIALIZING_TRANSFORMER, IntegrationSchemaConstants.ELEM_PAYLOAD_DESERIALIZING_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_PAYLOAD_SERIALIZING_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_PAYLOAD_SERIALIZING_TRANSFORMER, IntegrationSchemaConstants.ELEM_PAYLOAD_SERIALIZING_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_PAYLOAD_TYPE_ROUTER,
				new Info(IntegrationSchemaConstants.ELEM_PAYLOAD_TYPE_ROUTER, IntegrationSchemaConstants.ELEM_PAYLOAD_TYPE_ROUTER, "router.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_PUBLISH_SUBSCRIBE_CHANNEL,
				new Info(IntegrationSchemaConstants.ELEM_PUBLISH_SUBSCRIBE_CHANNEL, IntegrationSchemaConstants.ELEM_PUBLISH_SUBSCRIBE_CHANNEL, "pubsub-channel.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_RECIPIENT_LIST_ROUTER,
				new Info(IntegrationSchemaConstants.ELEM_RECIPIENT_LIST_ROUTER, IntegrationSchemaConstants.ELEM_RECIPIENT_LIST_ROUTER, "recipient-list.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_RESEQUENCER,
				new Info(IntegrationSchemaConstants.ELEM_RESEQUENCER, IntegrationSchemaConstants.ELEM_RESEQUENCER, "resequencer.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_RESOURCE_INBOUND_CHANNEL_ADAPTER,
				new Info(IntegrationSchemaConstants.ELEM_RESOURCE_INBOUND_CHANNEL_ADAPTER, IntegrationSchemaConstants.ELEM_RESOURCE_INBOUND_CHANNEL_ADAPTER, "inbound-adapter.png", Groups.Channels.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_ROUTER,
				new Info(IntegrationSchemaConstants.ELEM_ROUTER, IntegrationSchemaConstants.ELEM_ROUTER, "router.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_SERVICE_ACTIVATOR,
				new Info(IntegrationSchemaConstants.ELEM_SERVICE_ACTIVATOR, IntegrationSchemaConstants.ELEM_SERVICE_ACTIVATOR, "service-activator.png", Groups.Endpoints.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_SPLITTER,
				new Info(IntegrationSchemaConstants.ELEM_SPLITTER, IntegrationSchemaConstants.ELEM_SPLITTER, "splitter.png", Groups.Routing.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_SYSLOG_TO_MAP_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_SYSLOG_TO_MAP_TRANSFORMER, IntegrationSchemaConstants.ELEM_SYSLOG_TO_MAP_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(IntegrationSchemaConstants.ELEM_TRANSFORMER,
				new Info(IntegrationSchemaConstants.ELEM_TRANSFORMER, IntegrationSchemaConstants.ELEM_TRANSFORMER, "transformer.png", Groups.Transformation.toString()));
		ELEMENT_INFO.put(Constants.TRANSITION_TYPE_SOLID,
				new Info("Connection", "Connects artifacts to channels", "connection-solid.gif", Groups.Connections.toString()));
		ELEMENT_INFO.put(Constants.TRANSITION_TYPE_DASH,
				new Info("mapping recipient/wire-tap", "Mapping recipient or wire-tap between atifacts and channels", "connection-dash.gif", Groups.Connections.toString()));
	};

	private static class Info {
		
		@JsonProperty String icon;
		@JsonProperty String group;
		@JsonProperty String description;
		@JsonProperty String name;

		Info(String name, String description, String icon, String group) {
			this.name = name;
			this.description = description;
			this.icon = icon;
			this.group = group;
		}
		
	}
	
	private static ArrayNode paletteJson = null;
	
	public static ArrayNode getPalette() {
		if (paletteJson == null) {
			paletteJson = initPaletteJson();
		}
		return paletteJson;
	}
	
	private static ArrayNode initPaletteJson() {
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Inclusion.NON_NULL);
		ArrayNode palette = new ArrayNode(JsonNodeFactory.instance);
		for (Map.Entry<String, Info> entry : ELEMENT_INFO.entrySet()) {
			ObjectNode json = om.convertValue(entry.getValue(), ObjectNode.class);
			Class<? extends IntModelElement> clazz = IntegrationModelFactory.SUPPORTED_ELEMENTS.get(entry.getKey());
			if (clazz != null) {
				try {
					IntModelElement defaultObj = clazz.newInstance();
					defaultObj.initProperties();
					if (defaultObj.getProperties() != null) {
						json.put("properties", om.convertValue(
								defaultObj.getProperties(), ObjectNode.class));
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
			palette.add(json);
		}
		return palette;
	}

}
