package com.example.homework.home;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.homework.DBFields;
import com.example.homework.MainActivity;
import com.example.homework.R;
import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseModel;
import com.example.homework.base.ModelFailureResponse;
import com.example.homework.feeds.FeedsActivity;
import com.example.homework.weather.OpenWeatherMapClient;
import com.example.homework.weather.WeatherCondition;
import com.example.homework.weather.WeatherDataSource;
import com.example.homework.weather.WeatherHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.parse.ParseUser;

public class FragmentHome extends BaseFragment implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private Context mContext;
	private Button btnFeeds, btnSettings, btnLogout;
	private ViewSwitcher viewSwitcher;
	private TextView tvCurrentWeather, tvTime;
	private ListView linkListView;
	private LocationClient locationClient;
	private WeatherCondition weatherCondition;
	private Location lastLocation;
	private View view;
	private ApiList api = new ApiList();
	private AdapterLinks adapter;
	private List<ModelLink> linkList;

	public FragmentHome() {
		addApiInterface(api);
	}

	public static FragmentHome newInstance() {
		return new FragmentHome();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		locationClient = new LocationClient(getActivity(), this, this);
		DBFields.politics=true;
		DBFields.music=true;
		DBFields.movies = true;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		initUI(v);

		btnSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).launchSettings();

			}
		});

		btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseUser.logOut();
				FragmentManager fm = getFragmentManager();
				fm.popBackStack();

			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		SCREEN_TITLE = "Home";
		mContext = getActivity();
		view = inflater.inflate(R.layout.fragment_home, parent, false);
		return view;
	}

	@Override
	public void onResponse(BaseModel model) {
		if (model instanceof ModelList) {
			linkList = ((ModelList) model).linkList;
			adapter.setItems(linkList);

		} else if (model instanceof ModelFailureResponse) {
			Log.i("add child failure",
					((ModelFailureResponse) model).getDescription());
		}
	}

	@Override
	public void initUI(View view) {
		btnSettings = (Button) view.findViewById(R.id.btn_accountSettings);
		btnLogout = (Button) view.findViewById(R.id.btn_logout);
		tvCurrentWeather = (TextView) view.findViewById(R.id.tvCurrentWeather);
		tvTime = (TextView) view.findViewById(R.id.tvTime);
		btnFeeds = (Button) view.findViewById(R.id.btn_feeds);
		linkListView = (ListView) view.findViewById(R.id.linkList);
		viewSwitcher = (ViewSwitcher) view.findViewById(R.id.weatherSwitcher);

		linkListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(linkList.get(position).getLink());
				startActivity(i);
			}

		});
		linkListView.setAdapter(adapter);

		btnFeeds.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext.getApplicationContext(),
						FeedsActivity.class));

			}
		});
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {

	}

	@Override
	public void onConnected(Bundle arg0) {
		lastLocation = locationClient.getLastLocation();

		if (lastLocation != null) {
			locationClient.disconnect();
			updateWeatherCondition();
		}
	}

	@Override
	public void onDisconnected() {

	}

	@Override
	public void onLocationChanged(Location arg0) {

	}

	/** function for updating the weather */
	private void updateWeatherCondition() {
		WeatherDataSource dataSource = new OpenWeatherMapClient();

		dataSource.getCurrentCondition(lastLocation,
				new WeatherDataSource.Callback<WeatherCondition>() {
					@Override
					public void onSuccess(WeatherCondition arg) {
						weatherCondition = arg;
						renderWeatherCondition(view);
					}

					@Override
					public void onError() {
						Toast.makeText(getActivity(),
								"Unable to load weather condition",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	/** function for rendering the condition(the pic and the temperature */
	private void renderWeatherCondition(View v) {
		viewSwitcher.setDisplayedChild(1);
		tvCurrentWeather.setText(String.format("%.0f",
				weatherCondition.getTemp()));
		tvCurrentWeather.setCompoundDrawablesWithIntrinsicBounds(
				WeatherHelper.getIconForCondition(weatherCondition), 0,
				R.drawable.icon_w_celsius, 0);
	}

	@Override
	public void onStart() {
		super.onStart();

		onTimeChanged();
		if (lastLocation == null) {
			locationClient.connect();
		}
	}

	@Override
	public void onStop() {
		locationClient.disconnect();

		super.onStop();
	}

	/** function for setting the time text view */
	protected void onTimeChanged() {
		Calendar c = Calendar.getInstance();
		tvTime.setText(Html.fromHtml(getString(R.string.time,
				c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE))));
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		adapter = new AdapterLinks(activity);
	}

	@Override
	protected void onAfterStart() {
		api.p_getList();

	}

}
