package com.example.homework;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.example.homework.base.BaseFragmentActivity;
import com.example.homework.home.FragmentHome;
import com.example.homework.home.FragmentPassword;
import com.example.homework.home.FragmentSettings;
import com.example.homework.login.FragmentLogin;
import com.example.homework.register.FragmentRegister;
import com.example.homework.utils.ScreenListener;


public class MainActivity extends BaseFragmentActivity implements
		ScreenListener {

	private TextView tvTitle;
	private FragmentMain fragmentMain;
	private FragmentLogin fragmentLogin;
	private FragmentRegister fragmentRegister;
	private FragmentHome fragmentHome;
	private FragmentSettings fragmentSettings;
	private FragmentPassword fragmentPassword;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		setNavTitle("Homework");
		launchMain();
	}

	/** launches Main Fragment */
	private void launchMain() {
		if (fragmentMain == null) {
			fragmentMain = new FragmentMain();
		}
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentMain) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentMain.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentMain);
			} else {
				transaction.show(fragmentMain);
			}

			transaction.addToBackStack(null);
			transaction.commit();

		}

	}

	/** launches Login fragment */
	public void launchLogin() {
		if (fragmentLogin == null)
			fragmentLogin = new FragmentLogin();

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentLogin) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentLogin.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentLogin);
			} else {
				transaction.show(fragmentLogin);
			}

			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();

		}

	}

	/** launches Register fragment */
	public void launchRegister() {
		if (fragmentRegister == null)
			fragmentRegister = new FragmentRegister();

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentRegister) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentRegister.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentRegister);
			} else {
				transaction.show(fragmentRegister);
			}

			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();

		}

	}

	/** launch Home Fragment */
	public void launchHome() {
		if (fragmentHome == null) {
			fragmentHome = new FragmentHome();
		}
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentHome) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentHome.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentHome);
			} else {
				transaction.show(fragmentHome);
			}

			transaction.addToBackStack(null);
			transaction.commit();

		}

	}

	/** launch Settings Fragment */
	public void launchSettings() {
		if (fragmentSettings == null) {
			fragmentSettings = new FragmentSettings();
		}
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentSettings) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentSettings.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentSettings);
			} else {
				transaction.show(fragmentSettings);
			}

			transaction.addToBackStack(null);
			transaction.commit();

		}

	}

	/** launch Settings Fragment */
	public void launchChangePass() {
		if (fragmentPassword == null) {
			fragmentPassword = new FragmentPassword();
		}
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);

		if (currentFrag != fragmentPassword) {

			if (currentFrag != null) {
				transaction.remove(currentFrag);
			}

			if (!fragmentPassword.isAdded()) {
				transaction.add(R.id.fragments_container, fragmentPassword);
			} else {
				transaction.show(fragmentPassword);
			}

			transaction.addToBackStack(null);
			transaction.commit();

		}

	}

	/** sets the Navigation Bar title */
	private void setNavTitle(String title) {
		if (title == null) {
			return;
		}
		if (title.equals("Homework")) {
		} else {
			tvTitle.setText(title);
		}
	}

	/**
	 * if the current fragment is login, when pressing back, do not go back to
	 * the main layout which is empty
	 */
	@Override
	public void onBackPressed() {
		Fragment currentFrag = getFragmentManager().findFragmentById(
				R.id.fragments_container);
		if (currentFrag != fragmentMain)
			super.onBackPressed();
		else
			finish();
	}

	/** sets navigation title */
	public void onScreenChanged(String screenTitle) {
		setNavTitle(screenTitle);
	}
}