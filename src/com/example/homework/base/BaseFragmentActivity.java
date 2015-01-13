package com.example.homework.base;


public class BaseFragmentActivity extends Activity implements BaseApiListener {

	private Handler mainHandler = new Handler();
	private List<BaseApiInterface> apiInterfaces = new ArrayList<BaseApiInterface>();

	protected void addApiInterface(BaseApiInterface apiInterface) {
		apiInterfaces.add(apiInterface);
	}

	private void setApiInterfacesListener(BaseApiListener listener) {
		int size = apiInterfaces.size();
		for (int i = 0; i < size; i++) {
			apiInterfaces.get(i).setApiListener(listener);
		}
	}

	@Override
	public void onResponse(BaseModel model) {
		// TODO Auto-generated method stub

	}

	/**
	 * Implementation for the application states(life cycles)
	 */
	@Override
	protected void onPause() {
		super.onPause();
		setApiInterfacesListener(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		setApiInterfacesListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	public void showToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			if (Looper.myLooper() == Looper.getMainLooper()) {
				Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			} else {
				mainHandler.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}

	/** displays a dialog box with a given message */
	protected void showDialogBox(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

	/** check for internet connection */
	public boolean checkInternet() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			return false;
		} else
			return true;
	}

}