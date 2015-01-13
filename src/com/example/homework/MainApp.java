package com.example.homework;

import android.app.Application;

import com.parse.Parse;

public class MainApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "0zaTy71sVrMv0q3M1mGd6s8RgCoP1GecxLLOFa2q",
				"9Rye1KpTNdyF3iS87qIAllU7daa2cqAVqdij1nbN");
	}

}