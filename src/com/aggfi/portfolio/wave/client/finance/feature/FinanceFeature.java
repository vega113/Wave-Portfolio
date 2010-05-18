
package com.aggfi.portfolio.wave.client.finance.feature;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.gadgets.client.GadgetFeature;

/**
 * Wave Feature class.
 */
public class FinanceFeature implements GadgetFeature {

	private Quote quote = null;

  private static class FinanceWaveEventBus extends HandlerManager
      implements HasHandlers {

    /**
     * Creates a handler manager with the given source. Handlers will be fired
     * in the order that they are added.
     * 
     * @param source the event source
     */
    public FinanceWaveEventBus(Object source) {
      super(source);
    }

    /**
     * Creates a handler manager with the given source, specifying the order in
     * which handlers are fired.
     * 
     * @param source the event source
     * @param fireInReverseOrder true to fire handlers in reverse order
     */
    public FinanceWaveEventBus(Object source, boolean fireInReverseOrder) {
      super(source, fireInReverseOrder);
    }
  }

  private static FinanceWaveEventBus financeWaveEventBus;
  private static FinanceFeature wave;

  private FinanceFeature(){
  }
//
//
  public native Quote initQuoteInstance() /*-{
  		return new google.finance.Quote();
 	}-*/;
  
  public synchronized Quote getQuoteInstance(){
	  if(quote == null){
		  quote = initQuoteInstance();
	  }
	  return quote;
  }
 

  /**
   * Adds a {@link ParticipantUpdateEvent} handler.
   *
   * @param handler
   *          the handler
   * @return the registration for the event
   */
  public HandlerRegistration addQuoteUpdateEventHandler(
		  QuoteUpdateEventHandler handler) {
    return ensureFinanceWaveEventBus().addHandler(QuoteUpdateEvent.getType(), handler);
  }


  
  /**
   * Register the stateUpdated method to be called when the state is updated.
   */
  private  void registerQuoteStateUpdateCallback() {
   getQuoteInstance().addListener();
  };

  /**
   * This method is called from the wave JavaScript library on Mode changes.
   */
  @SuppressWarnings("unused")
  private static void quoteUpdateEvent(JavaScriptObject data) {
	  QuoteUpdateEvent.fire(financeWaveEventBus, data);
  }


  /**
   * Helper method to initialize the waveEventBus and registers all the handlers
   * with the native wave JavaScript library.
   *
   * @return a HandleManager
   */
  private HandlerManager ensureFinanceWaveEventBus() {
    if (wave == null) {
      wave = this;
    }
    if (financeWaveEventBus == null) {
    	financeWaveEventBus = new FinanceWaveEventBus(this);
      registerQuoteStateUpdateCallback();
    }
    return financeWaveEventBus;
  }

//  

}
