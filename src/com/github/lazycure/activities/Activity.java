package com.github.lazycure.activities;

import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable {
	
	private static final long serialVersionUID = 6614032015006300512L;
	private String name;
	private Date start;
	private Date finish;

	public Activity(String activityName, Date startTime, Date finishTime) {
		name = activityName;
		start = startTime;
		finish = finishTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setStartTime(Date start) {
		this.start = start;
	}

	public Date getStartTime() {
		return start;
	}
	
	public void setFinishTime(Date finish) {
		this.finish = finish;
	}

	public Date getFinishTime() {
		return finish;
	}
	
	public String toString() {
		return name;
	}
	
}
