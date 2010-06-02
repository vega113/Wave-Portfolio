package com.aggfi.portfolio.wave.client.portfolio;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface GlobalResources extends ClientBundle {

	public static final GlobalResources INSTANCE =  GWT.create(GlobalResources.class);

	@Source("globalCSS.css")
	GlobalCSS globalCSS();

}
