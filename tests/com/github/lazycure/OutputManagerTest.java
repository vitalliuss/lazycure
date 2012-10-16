package com.github.lazycure;

import java.util.Date;

import org.junit.Test;

import com.github.lazycure.activities.Activity;

import junit.framework.TestCase;

public class OutputManagerTest extends TestCase {

	private Date testDate = new Date(2000, 1, 1, 1, 00, 00);
	private Date secondAfter = new Date(2000, 1, 1, 1, 00, 01);
	private Date minuteAfter = new Date(2000, 1, 1, 1, 01, 00);
	private Date hourAfter = new Date(2000, 1, 1, 2, 00, 00);
	private Date dayAfter = new Date(2000, 1, 2, 1, 00, 00);
	private String SECOND = "00:00:01";
	private String MINUTE = "00:01:00";
	private String HOUR = "01:00:00";
	private String DAY = "1d00:00:00";
	private String DELIMITER = OutputManager.DELIMITER;
	private String ACTIVITY_PREFIX = OutputManager.ACTIVITY_PREFIX;

	@Test
	public void testFormatActivityStringOneDay() {
		Activity activity = new Activity("one day activity", testDate, dayAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(ACTIVITY_PREFIX);
		buffer.append(activity.getName());
		buffer.append(DELIMITER);
		buffer.append(DAY);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneHour() {
		Activity activity = new Activity("one hour activity", testDate,
				hourAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(ACTIVITY_PREFIX);
		buffer.append(activity.getName());
		buffer.append(DELIMITER);
		buffer.append(HOUR);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneMinute() {
		Activity activity = new Activity("one minute activity", testDate,
				minuteAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(ACTIVITY_PREFIX);
		buffer.append(activity.getName());
		buffer.append(DELIMITER);
		buffer.append(MINUTE);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneSecond() {
		Activity activity = new Activity("one second activity", testDate,
				secondAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(ACTIVITY_PREFIX);
		buffer.append(activity.getName());
		buffer.append(DELIMITER);
		buffer.append(SECOND);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

}
