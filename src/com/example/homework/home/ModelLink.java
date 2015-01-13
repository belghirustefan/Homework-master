package com.example.homework.home;

import android.net.Uri;

import com.example.homework.base.BaseModel;

public class ModelLink extends BaseModel {

	private Uri link;
	private String name;
	
	public ModelLink(){
		
	}
	
	public ModelLink(Uri link,String name){
		this.link=link;
		this.name=name;
	}

	public Uri getLink() {
		return link;
	}

	public void setLink(Uri link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
