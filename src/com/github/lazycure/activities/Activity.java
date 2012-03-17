package com.github.lazycure.activities;

import java.io.Serializable;

public class Activity implements Serializable {
	
	private static final long serialVersionUID = 6614032015006300512L;
	private String name;

	public Activity(String activityName) {
		name = activityName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
}
