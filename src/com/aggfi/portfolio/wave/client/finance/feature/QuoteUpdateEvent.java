
package com.aggfi.portfolio.wave.client.finance.feature;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Event fired when data state changes.  
 */
public class QuoteUpdateEvent extends GwtEvent<QuoteUpdateEventHandler> {

  /**
   * Handler type.
   */
  private static Type<QuoteUpdateEventHandler> TYPE;
  private final JavaScriptObject data;

  /**
   * Gets the type associated with this event.
   *
   * @return returns the handler type
   */
  public static Type<QuoteUpdateEventHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<QuoteUpdateEventHandler>();
    }
    return TYPE;
  }

  static QuoteUpdateEvent fire(HasHandlers source, JavaScriptObject data) {
    // If no handlers exist, then type can be null.
    if (TYPE != null) {
      final QuoteUpdateEvent event = new QuoteUpdateEvent(data);
      source.fireEvent(event);
      return event;
    }
    return null;
  }

  public QuoteUpdateEvent(JavaScriptObject data) {
    this.data = data;
  }

  protected void dispatch(QuoteUpdateEventHandler handler) {
    handler.onUpdate(this);
  }

  @SuppressWarnings("unchecked")
  public Type<QuoteUpdateEventHandler> getAssociatedType() {
    return (Type) TYPE;
  }
  
  public Data getData(){
	  return asData(data);
  }
  
  private final native Data asData(JavaScriptObject jso) /*-{
	return jso;
  }-*/;

}
