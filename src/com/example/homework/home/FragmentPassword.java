package com.example.homework.home;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework.DBFields;
import com.example.homework.MainActivity;
import com.example.homework.R;
import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseModel;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;

public class FragmentPassword extends BaseFragment {

	Button changePassBtn;
	EditText oldPassEt, newPassEt, newPassConfirmEt;
	String oldPassword, newPassword;
	Context mContext;
	ApiChangePassword api = new ApiChangePassword();

	public FragmentPassword() {
	}

	public static FragmentPassword newInstance() {
		return new FragmentPassword();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addApiInterface(api);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		initUI(v);

		changePassBtn.setOnClickListener(new OnClickListener() {

			// checks for password fields input and launches the change password
			// function
			@Override
			public void onClick(View v) {
				onChangePassClicked();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		SCREEN_TITLE = "Change Password";
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_password, parent, false);
	}

	@Override
	public void onResponse(BaseModel model) {
		if (model instanceof ModelSuccessResponse) {
			Toast.makeText(mContext.getApplicationContext(),
					"Password Changed Successfully", Toast.LENGTH_LONG).show();
			FragmentManager fm = getFragmentManager();
			for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
				fm.popBackStack();
			}
			((MainActivity) getActivity()).launchLogin();
		} else if (model instanceof ModelFailureResponse) {
			// showErrorDialog("Username or password incorrect");
			Toast.makeText(mContext.getApplicationContext(),
					"An error occured.Try again!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void initUI(View view) {
		oldPassEt = (EditText) view.findViewById(R.id.oldPassEt);
		newPassEt = (EditText) view.findViewById(R.id.newPassEt);
		newPassConfirmEt = (EditText) view.findViewById(R.id.newPassConfirmEt);
		changePassBtn = (Button) view.findViewById(R.id.changePasswordBtn);
	}

	/** checks the input for password and calls the change password method */
	public void onChangePassClicked() {
		if (!oldPassEt.getEditableText().toString().equals(DBFields.pass)) {
			Toast.makeText(mContext.getApplicationContext(), "Wrong password",
					Toast.LENGTH_LONG).show();
		} else if (newPassEt.getEditableText().toString().length() < 8) {
			Toast.makeText(mContext.getApplicationContext(),
					"The new password should be at least 8 characters long",
					Toast.LENGTH_LONG).show();
		} else if (!newPassEt.getEditableText().toString()
				.equals(newPassConfirmEt.getEditableText().toString())) {
			Toast.makeText(mContext.getApplicationContext(),
					"Passwords do not match", Toast.LENGTH_LONG).show();
		} else {
			api.p_changePassword(oldPassEt.getEditableText().toString(),
					newPassEt.getEditableText().toString());
		}

	}

	@Override
	protected void onAfterStart() {
		// TODO Auto-generated method stub

	}
}