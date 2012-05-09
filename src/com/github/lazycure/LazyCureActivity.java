package com.github.lazycure;

import android.app.Activity;

public class LazyCureActivity extends Activity {

	public LazyCureActivity() {
		super();
	}

	protected LazyCureApplication getStuffApplication() {
		return (LazyCureApplication)getApplication();
	}

}