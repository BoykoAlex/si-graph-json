package org.springframework.integration.json;

import org.w3c.dom.Element;

public interface IModelFactory {
	
	Object createJsonModel(Object parent, Element childElement, int order);

}
