package com.example.homework.home;

import com.example.homework.DBFields;
import com.example.homework.base.BaseApiInterface;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ApiChangePassword extends BaseApiInterface {

	/**
	 * Function that changes the old password into a new one Sets the new
	 * password in the database and updates the entry in cloud Returns in
	 * onResponse() a ModelSuccessResponse for success and a
	 * ModelFailureResponse otherwise
	 * 
	 * @param oldPassword
	 * @param newPassword
	 */
	final void p_changePassword(final String oldPassword,
			final String newPassword) {

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		ParseUser user = ParseUser.getCurrentUser();

		query.getInBackground(user.getObjectId(), new GetCallback<ParseUser>() {
			public void done(ParseUser object, ParseException e) {
				object.setPassword(newPassword);
				object.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							DBFields.pass = newPassword;
							apiListener.onResponse(new ModelSuccessResponse());
						} else {
							if (apiListener != null) {
								apiListener
										.onResponse(new ModelFailureResponse());
							}
						}

					}
				});
			}
		});
	}

	
	/** function for saving changes for the user */
	public void p_save(final String newUsername, final String age,
			final String car) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		ParseUser user = ParseUser.getCurrentUser();
		query.getInBackground(user.getObjectId(), new GetCallback<ParseUser>() {
			public void done(ParseUser object, ParseException e) {
				object.setUsername(newUsername);
				object.put("age", age);
				object.put("car", car);
				object.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							apiListener.onResponse(new ModelSuccessResponse());
						} else {
							if (apiListener != null) {
								apiListener
										.onResponse(new ModelFailureResponse());
							}
						}

					}
				});
			}
		});
	}
}