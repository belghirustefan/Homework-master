package com.example.homework.register;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework.R;
import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseFragmentActivity;
import com.example.homework.base.BaseModel;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;

public class FragmentRegister extends BaseFragment {

	private Button btnRegister;
	private EditText etUsername, etEmail, etPassword;
	private ApiRegister api = new ApiRegister();
	private Context mContext;

	public FragmentRegister() {
	}

	public static FragmentRegister newInstance() {
		return new FragmentRegister();
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
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkInternet() == false) {
					showConnectionError();
				} else {
					v.setEnabled(false);
					onRegisterPressed();
				}

			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		SCREEN_TITLE = "Register";
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_register, parent, false);
	}

	/*
	 * if internet connection is detected(see above) disables the button, stores
	 * the username and password in two strings and checks the input if ok =>
	 * goes to login function in controller else reenables the button so the
	 * user could try another credentials
	 */
	public void onRegisterPressed() {
		btnRegister.setEnabled(false);
		String username = getUsername();
		String password = getPassword();
		String email = getEmail();
		boolean cancel = false;

		if (TextUtils.isEmpty(email)) {
			showDialogBox("Please fill in the email");
			cancel = true;
		} else if (TextUtils.isEmpty(username)) {
			showDialogBox("Please insert a valid username");
			cancel = true;
		} else if (password.length() < 8) {
			showDialogBox("The password must be at least 8 characters long");
			cancel = true;
		}

		if (cancel) {
			btnRegister.setEnabled(true);
		} else {
			api.register(username, email, password);
		}
	}

	// getters for getting the input in the editTexts
	private String getUsername() {
		return etUsername.getText().toString();
	}

	private String getPassword() {
		return etPassword.getText().toString();
	}

	private String getEmail() {
		return etEmail.getText().toString();
	}

	// if login successfull, finishes this activity and launches the homescreen
	// if login failes, reenables the button and sets the editTexts to empty
	// state
	public void onResponse(BaseModel model) {
		if (model instanceof ModelSuccessResponse) {
			btnRegister.setEnabled(false);
			android.app.FragmentManager fm = getActivity().getFragmentManager();
			((BaseFragmentActivity) getActivity())
					.showToast("User Succesfully signed up. Logging in...");
			fm.popBackStack("activity",
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			api.onRegisterSuccess(getUsername(), getPassword(), getActivity());
		} else if (model instanceof ModelFailureResponse) {
			((BaseFragmentActivity) getActivity())
					.showToast("User already exists");
			btnRegister.setEnabled(true);
			etUsername.setText("");
			etEmail.setText("");
			etPassword.setText("");
		}
	}

	@Override
	public void initUI(View view) {
		etUsername = (EditText) view.findViewById(R.id.et_username);
		etPassword = (EditText) view.findViewById(R.id.et_password);
		etEmail = (EditText) view.findViewById(R.id.et_email);
		btnRegister = (Button) view.findViewById(R.id.btn_register);

	}

	@Override
	protected void onAfterStart() {
		// TODO Auto-generated method stub

	}
}
