package com.example.homework.base;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.example.homework.R;

public abstract class BaseFragment extends Fragment implements BaseApiListener {

	protected String SCREEN_TITLE = "base";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private List<BaseApiInterface> apiInterfaces = new ArrayList<BaseApiInterface>();

	/**
	 * call this in oncreate for adding apiinterfaces<br/>
	 * 
	 * @author Andrei
	 * @param apiInterface
	 */
	protected void addApiInterface(BaseApiInterface apiInterface) {
		apiInterfaces.add(apiInterface);
	}

	/**
	 * sets apilisteners for this activity
	 * 
	 * @author Andrei
	 * @param listener
	 */
	private void setApiInterfacesListener(BaseApiListener listener) {
		int size = apiInterfaces.size();
		for (int i = 0; i < size; i++) {
			apiInterfaces.get(i).setApiListener(listener);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		setApiInterfacesListener(this);
		onAfterStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		setApiInterfacesListener(null);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	public abstract void initUI(View view);

	/** displays a dialog box with a given message */
	public void showDialogBox(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(message);
		builder.setCancelable(true);
		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		(builder.create()).show();
	}

	/** show an alert dialog for Internet connection failure */
	protected void showConnectionError() {
		showDialogBox(getString(R.string.noInternet));
	}

	public boolean checkInternet() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}

	protected abstract void onAfterStart();
}