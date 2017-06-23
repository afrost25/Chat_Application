package exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InvalidUsernameException extends Exception
{
	/**
	 * Exception for Invalid Username
	 */
	private static final long serialVersionUID = 3706089183217574909L;

	public void message()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Entry");
		alert.setHeaderText("Invalid Username / Password");
		alert.setContentText("Access Denied");
		
		alert.showAndWait();
	}

}
