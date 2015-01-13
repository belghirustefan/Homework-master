package com.example.homework.home;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;

import com.example.homework.base.BaseApiInterface;
import com.example.homework.base.ModelFailureResponse;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ApiList extends BaseApiInterface {

	/** function for getting the link list from parse */
	public void p_getList() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("links");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				List<ModelLink> linkList = new ArrayList<ModelLink>();
				String error;

				if (e == null) {
					for (int i = 0; i < objects.size(); i++) {
						linkList.add(new ModelLink(Uri.parse(objects.get(i)
								.getString("link")), objects.get(i).getString(
								"name")));
					}
					error = "";
					p_onGetList(linkList, error);
				} else {
					error = e.getMessage();
					p_onGetList(linkList, error);
				}
			}
		});
	}

	/**
	 * function for sending the list or the failure of getting the list in the
	 * fragment
	 */
	public void p_onGetList(List<ModelLink> linkList, String error) {
		if (apiListener != null) {
			if (linkList.size() != 0) {
				ModelList linkListModel = new ModelList();
				linkListModel.linkList = linkList;
				apiListener.onResponse(linkListModel);
			} else if (linkList.size() == 0) {
				ModelFailureResponse modelFailureResponse = new ModelFailureResponse();
				modelFailureResponse.setDescription(error);
				apiListener.onResponse(modelFailureResponse);
			}
		}
	}
}
