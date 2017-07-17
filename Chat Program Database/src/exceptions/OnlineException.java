package exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

public class OnlineException extends Exception
{
	/**
	 * OnlineException
	 */
	private static final long serialVersionUID = 7344172834487091918L;

	public void message()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Forbidden");
		alert.setHeaderText("User Online");
		alert.setContentText("This account is already logged in");
		DialogPane dp = alert.getDialogPane();
		dp.getStylesheets().add("notification.css");
		
		alert.showAndWait();
	}
}
