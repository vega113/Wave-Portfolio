package com.aggfi.portfolio.wave.client.portfolio;

import com.aggfi.portfolio.wave.client.portfolio.ResolutionEnum;
import com.google.gwt.gadgets.client.EnumPreference;
import com.google.gwt.gadgets.client.UserPreferences;

public interface PortfolioPreferences extends UserPreferences {

  @PreferenceAttributes(display_name="Prefered resolution", default_value = "_1920x1280")
  EnumPreference<ResolutionEnum> getResolution();
}
