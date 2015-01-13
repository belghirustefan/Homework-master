package com.example.homework.register;

import android.app.Activity;

import com.example.homework.DBFields;
import com.example.homework.MainActivity;
import com.example.homework.base.BaseApiInterface;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ApiRegister extends BaseApiInterface {

	public void register(String username, String email, String password) {
		ParseUser user = new ParseUser();
		user.setEmail(email);
		user.setPassword(password);
		user.setUsername(username);

		user.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null) {
					onRegister("");
				} else {
					String mess = e.getMessage();
					onRegister(mess);
				}

			}
		});
	}

	public void onRegister(String error) {
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

	public void onRegisterSuccess(String username, final String password,
			final Activity act) {
		ParseUser.logInInBackground(username, password, new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (e == null) {
					DBFields.pass = password;
					((MainActivity) act).launchHome();
				}
			}
		});

	}
}
