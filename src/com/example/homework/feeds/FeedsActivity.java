package com.example.homework.feeds;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.homework.DBFields;
import com.example.homework.R;
import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseFragmentActivity;
import com.example.homework.utils.ScreenListener;

public class FeedsActivity extends BaseFragmentActivity implements
		ScreenListener {
	private TextView tvTitle;
	HashMap<String, Boolean> interests = new HashMap<String, Boolean>();
	List<ModelLayout> layouts;
	int counter = 0;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_feed);
		setNavTitle("Feeds");
		layouts = new ArrayList<ModelLayout>();
		getInterest();
		JSONObject mainObject = parseJSONData();
		JSONArray array;
		try {
			array = mainObject.getJSONArray("layouts");
			for (int i = 0; i < 4; i++) {
				try {
					JSONObject object = array.getJSONObject(i);
					ModelLayout ml = new ModelLayout();
					ml.setInterest(object.getString("interest"));
					ml.setType(object.getString("type"));
					ml.setLabel(object.getString("label"));
					ml.setDescription(object.getString("description"));
					ml.setColor(object.getString("color"));
					ml.setAction(object.getString("action"));
					layouts.add(ml);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (ModelLayout ml : layouts) {
			inflateLayout(ml);
			Log.d("interese", ml.getInterest() + " ");
		}
	}

	/** sets the Navigation Bar title */
	private void setNavTitle(String title) {
		if (title == null) {
			return;
		}
		if (title.equals("Feeds")) {
		} else {
			tvTitle.setText(title);
		}
	}

	/** sets navigation title */
	public void onScreenChanged(String screenTitle) {
		setNavTitle(screenTitle);
	}

	public void getInterest() {
		interests.put("movies", DBFields.movies);
		interests.put("music", DBFields.music);
		interests.put("politics", DBFields.politics);
		interests.put("link", DBFields.link);
	}

	public JSONObject parseJSONData() {
		String jsonString = null;
		JSONObject jsonObject = null;
		try {
			InputStream inputStream = getAssets().open("JSON.txt");
			int sizeOfJSONFile = inputStream.available();
			byte[] bytes = new byte[sizeOfJSONFile];
			inputStream.read(bytes);
			inputStream.close();

			jsonString = new String(bytes, "UTF-8");
			jsonObject = new JSONObject(jsonString);

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (JSONException x) {
			x.printStackTrace();
			return null;
		}
		return jsonObject;
	}

	public void inflateLayout(ModelLayout ml) {
		if (findViewById(R.id.fragments_container) != null) {
			if (counter == 1) {
				BaseFragment firstFragment = new FirstFragment();
				getFragmentManager().beginTransaction()
						.add(R.id.fragments_container1, firstFragment).commit();
				// checkFragmentType(ml, firstFragment);
			} else if (counter == 2) {
				BaseFragment secondFragment = new SecondFragment();
				getFragmentManager().beginTransaction()
						.add(R.id.fragments_container2, secondFragment)
						.commit();
				// checkFragmentType(ml, secondFragment);
			} else if (counter == 3) {
				BaseFragment thirdFragment = new ThirdFragment();
				getFragmentManager().beginTransaction()
						.add(R.id.fragments_container3, thirdFragment).commit();
				// checkFragmentType(ml, thirdFragment);
			}

		}
	}

	// public void checkFragmentType(ModelLayout ml, BaseFragment fragment) {
	// if(fragment instanceof FirstFragment){
	// if(ml.getInterest()=="movies"){
	// fragment.getView().inflate(context, resource, root)
	// }
	// }
	// }
}
