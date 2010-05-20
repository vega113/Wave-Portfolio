package com.aggfi.portfolio.wave.client.portfolio;

import com.google.gwt.accounts.client.AuthSubStatus;
import com.google.gwt.accounts.client.User;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gdata.client.GData;
import com.google.gwt.gdata.client.GDataSystemPackage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * The following example demonstrates how to use AuthSub to authenticate and
 * identify authentication status for the supported GData systems.
 */
public class AuthSub extends Composite {
	protected String GDATA_API_KEY = "ABQIAAAAbvr6gQH1qmQkeIRFd4m2eRQGgUfFXBTwJSrdXmAUVcxdpVrjSxSCpKc56TMohMx09fePLmfMFkr2VA";

  /**
   * This method is used by the main sample app to obtain
   * information on this sample and a sample instance.
   * 
   * @return An instance of this demo.
   */

  private FlexTable mainPanel;

  /**
   * Create the main content panel for this demo and call
   * showAuthSubStatus to display the authentication status
   * for each GData system.
   */
  public AuthSub() {
    mainPanel = new FlexTable();
    mainPanel.setCellPadding(4);
    mainPanel.setCellSpacing(0);
    initWidget(mainPanel);
    /* 
     * Here we load the default package to make AuthSub available.
     * For AuthSub any of the GData packages will do.
     * */
    if (!GData.isLoaded(GDataSystemPackage.FINANCE)) {
      GData.loadGDataApi(GDATA_API_KEY, new Runnable() {
        public void run() {
          startDemo();
        }
      }, GDataSystemPackage.FINANCE);
    } else {
      startDemo();
    }
  }

  /**
   * Refreshes this demo by clearing the contents of
   * the main panel and calling showAuthSubStatus to
   * display the authentication status for each GData system.
   */
  private void refreshDemo() {
    mainPanel.clear();
    showAuthSubStatus();
  }
  
  /**
   * Display the AuthSub authentication statuses in a
   * tabular fashion with the help of a GWT FlexTable widget.
   * We initialize an array containing the name, scope, icon
   * and url for each of the supported GData systems.
   * For each system a table row is added displaying the name,
   * page, link and icon, in addition to the AuthSub status
   * and a hyperlink for logging in or logging, as appropriate.
   * The User class contains the getStatus method which is used
   * to retrieve the authentication status for the current user
   * and for a given system (scope).
   */
  private void showAuthSubStatus() {
	  String[] sys  =  { "Google Finance",
			  "http://finance.google.com/finance/feeds/",
			  "http://waveportfolio.appspot.com/gdata-finance.png",
	  "http://code.google.com/apis/finance/" };
	  final String scope = sys[1];
	  Image icon = new Image(sys[2]);
	  HTML statusLabel = new HTML();
	  Anchor actionLink = new Anchor();
	  AuthSubStatus status = User.getStatus(scope);
	  if (status == AuthSubStatus.LOGGED_IN) {
		  statusLabel.setHTML("<i>Logged in</i>");
		  actionLink.setText("Log out");
		  actionLink.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  User.logout(scope);
				  refreshDemo();
			  }
		  });
	  } else if (status == AuthSubStatus.LOGGED_OUT) {
		  statusLabel.setHTML("<i>Logged out</i>");
		  actionLink.setText("Log in");
		  actionLink.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  User.login(scope);
			  }
		  });
	  }
	  mainPanel.setWidget(0, 0, icon);
	  mainPanel.setWidget(0, 1, statusLabel);
	  mainPanel.setWidget(0, 2, new HTML("<b>"+ sys[0] + "</b>"));
	  mainPanel.setWidget(0, 3, actionLink);
  }
  
  /**
   * Displays a status message to the user.
   * 
   * @param message The message to display.
   * @param isError Indicates whether the status is an error status.
   */
  private void showStatus(String message, boolean isError) {
    mainPanel.clear();
    mainPanel.insertRow(0);
    mainPanel.addCell(0);
    Label msg = new Label(message);
    if (isError) {
      msg.setStylePrimaryName("hm-error");
    }
    mainPanel.setWidget(0, 0, msg);
  }
  
  /**
   * Starts this demo.
   */
  private void startDemo() {
    if (Window.Location.getHref().startsWith("http")) {
      showAuthSubStatus();
    } else {
      showStatus("This sample must be run over HTTP, AuthSub does not " +
          "support requests initiated from non-HTTP URIs.", true);
    }
  }
}
