package control;

import model.IPhotoModel;

/**
 * @author Conrado Uraga
 * This encompasses all methods common to all controls.  The user is able to consume input from
 * the client.
 */
public interface IPhotoControl {
	/**
	 * reads the command the user inputs in the commandLine
	 * command must be precise to the specification given to us
	 */
	public String[] readCommand();
}