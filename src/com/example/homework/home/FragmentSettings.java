package com.example.homework.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homework.MainActivity;
import com.example.homework.R;
import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseModel;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.base.ModelSuccessResponse;
import com.parse.ParseUser;

public class FragmentSettings extends BaseFragment {
	private Context mContext;
	private Button btnChangePass, btnSave;
	private EditText etUsername, etEmail, etAge, etCar;
	private ApiChangePassword api = new ApiChangePassword();

	public FragmentSettings() {
	}

	public static FragmentSettings newInstance() {
		return new FragmentSettings();
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

		btnChangePass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).launchChangePass();

			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				api.p_save(getUsername(), getAge(), getCar());

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		SCREEN_TITLE = "Settings";
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_settings, parent, false);
	}

	@Override
	public void onResponse(BaseModel model) {
		if (model instanceof ModelSuccessResponse) {
			Toast.makeText(getActivity(), "Changes saved successfully",
					Toast.LENGTH_LONG).show();
		} else if (model instanceof ModelFailureResponse) {
			showDialogBox("Changes not saved successfully");
		}
	}

	@Override
	public void initUI(View view) {
		btnChangePass = (Button) view.findViewById(R.id.changePasswordEt);
		btnSave = (Button) view.findViewById(R.id.salveazaBtn);
		etUsername = (EditText) view.findViewById(R.id.changeUserNameEt);
		etAge = (EditText) view.findViewById(R.id.changeAgeEt);
		etCar = (EditText) view.findViewById(R.id.changeCar);
		etEmail = (EditText) view.findViewById(R.id.changeMailEt);

		ParseUser user = ParseUser.getCurrentUser();
		etUsername.setText(user.getUsername());
		etEmail.setText(user.getEmail());
		etEmail.setEnabled(false);
		if (user.getString("age") != null)
			etAge.setText(user.getString("age"));
		if (user.getString("car") != null)
			etCar.setText(user.getString("car"));
	}

	public String getUsername() {
		return etUsername.getEditableText().toString();
	}

	public String getAge() {
		return etAge.getEditableText().toString();
	}

	public String getCar() {
		return etCar.getEditableText().toString();
	}

	@Override
	protected void onAfterStart() {
		// TODO Auto-generated method stub

	}
}
