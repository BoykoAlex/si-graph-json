package org.springframework.integration.json.model;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.w3c.dom.Element;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "type")  
	@JsonSubTypes({  
	    @Type(value = SingleLinkPort.class, name = "SingleLinkPort"),  
	    @Type(value = NamedFromNodePort.class, name = "NamedFromNodePort") }) 
public class Port {
	
	private String name;
	
	public Port() {
		
	}
	
	public Port(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addToOwnerXmlNode(Element owner) {
		
	}

}
