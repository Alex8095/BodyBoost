package com.alextsurkin.user.model;

import android.content.Context;

import com.alextsurkin.bodyboost.db.DatabaseHelper;

public class DBMUser implements DBMUserInterface {
	static private DBMUser instance;

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DBMUser(ctx);
		}
	}

	static public DBMUser getInstance() {
		return instance;
	}

	private DatabaseHelper helper;

	private DBMUser(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	private DatabaseHelper getHelper() {
		return helper;
	}
}
