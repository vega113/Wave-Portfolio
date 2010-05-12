
package com.aggfi.portfolio.wave.client.finance;

import com.google.gwt.event.shared.EventHandler;

public interface QuoteUpdateEventHandler extends EventHandler {
  void onUpdate(QuoteUpdateEvent event);
}
