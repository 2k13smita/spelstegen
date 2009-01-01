package spelstegen.client.widgets;

import spelstegen.client.LoginListener;
import spelstegen.client.MainApplication;
import spelstegen.client.SpelstegenServiceAsync;
import spelstegen.client.entities.Player;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.KeyboardListenerAdapter;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Class that draws the login panel
 * 
 * @author Henrik Segesten
 *
 */
public class LoginPanel extends DialogBox {
	
	private TextBox usernameBox;
	private PasswordTextBox passwordBox;
	
	public LoginPanel(final SpelstegenServiceAsync spelstegenService, final LoginListener loginHandler) {
		super(false);
		setText("Logga in");
		setAnimationEnabled(true);
		
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setSpacing(MainApplication.VERTICAL_SPACING);
		
		HorizontalPanel usernamePanel = MainApplication.createStandardHorizontalPanel();
		Label lblUserName = new Label("Användarnamn (epost):");
		lblUserName.setWidth("200px");
		lblUserName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		usernamePanel.add(lblUserName);
		usernameBox = new TextBox();
		usernamePanel.add(usernameBox);
		usernameBox.setWidth("200px");
		
		HorizontalPanel passwordPanel = MainApplication.createStandardHorizontalPanel();
		Label lblPassword = new Label("Lösenord:");
		lblPassword.setWidth("200px");
		lblPassword.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		passwordPanel.add(lblPassword);
		passwordBox = new PasswordTextBox();
		passwordBox.setWidth("200px");
		passwordPanel.add(passwordBox);
		
		VerticalPanel fieldPanel = new VerticalPanel();
		fieldPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		fieldPanel.setSpacing(10);
		fieldPanel.add(usernamePanel);
		fieldPanel.add(passwordPanel);
		
		final AsyncCallback<Player> callback = new AsyncCallback<Player>() {

			public void onFailure(Throwable caught) {
				Window.alert("Inloggningen misslyckades. " + caught.getMessage());
			}

			public void onSuccess(Player result) {
				if (result != null) {
					loginHandler.loggedIn(result);
				} else {
					Window.alert("Fel lösenord eller epostadress, försök igen.");
				}
				LoginPanel.this.hide();
			}
		};
		final Command loginCommand = new Command() {
			public void execute() {
				spelstegenService.logIn(usernameBox.getText().trim(), Player.md5(passwordBox.getText()), callback);
			}
		};
		
		KeyboardListener keyboardListener = new KeyboardListenerAdapter() {
			public void onKeyPress(Widget sender, char keyCode, int modifiers) {
				if (keyCode == KEY_ENTER) {
					loginCommand.execute();
				}
			}
		};
		usernameBox.addKeyboardListener(keyboardListener);
		passwordBox.addKeyboardListener(keyboardListener);
		
		PushButton loginButton = new PushButton("Ok");
		loginButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				loginCommand.execute();
			}
		});
		PushButton cancelButton = new PushButton("Avbryt");
		cancelButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				LoginPanel.this.hide();
			}
		});
		HorizontalPanel buttonPanel = MainApplication.createStandardHorizontalPanel();
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);
		
		mainPanel.add(fieldPanel);
		mainPanel.add(buttonPanel);
		
		this.add(mainPanel);
	}

	public void setUserNameFocus(boolean focused) {
		usernameBox.setFocus(focused);
	}
}
