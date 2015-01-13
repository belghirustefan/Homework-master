package com.example.homework.login;

import com.example.homework.DBFields;
import com.example.homework.base.BaseApiInterface;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ApiLogin extends BaseApiInterface {

	/**
	 * Takes the username and password as parameters, two Strings
	 * Logs the user(in another thread) in the database
	 * if no error is encounter it passes an empty string in onLogin(String error)
	 * if an exception occurs , it passes the exception's message in onLogin(String error)
	 * @param username
	 * @param password
	 */
	public void login(String username, final String password) {
		ParseUser.logInInBackground(username, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					DBFields.pass = password;
					onLogin("");
				} else {
					String mess = e.getMessage();
					onLogin(mess);
				}

			}
		});
	}

	/**
	 * Returns the ModelSuccessResponse if the String received in function is empty
	 * Returns the ModelFailureResponse if the String received in function is not empty along with the error message
	 * @param error
	 */
	public void onLogin(String error) {
		if (apiListener != null) {
			if (error.equals("")) {
				apiListener.onResponse(new ModelSuccessResponse());

			} else {
				ModelFailureResponse mfr = new ModelFailureResponse();
				mfr.setDescription(error);
				apiListener.onResponse(mfr);
			}
		}
	}
}
