package test.java.com.github.lazycure;

import java.util.Date;

import main.java.com.github.lazycure.OutputManager;
import main.java.com.github.lazycure.Time;
import main.java.com.github.lazycure.activities.Activity;

import org.junit.Test;


import junit.framework.TestCase;

public class OutputManagerTest extends TestCase {

	private Date testDate = new Date(2000, 1, 1, 1, 00, 00);
	private Date secondAfter = new Date(2000, 1, 1, 1, 00, 01);
	private Date minuteAfter = new Date(2000, 1, 1, 1, 01, 00);
	private Date hourAfter = new Date(2000, 1, 1, 2, 00, 00);
	private Date dayAfter = new Date(2000, 1, 2, 1, 00, 00);
	private String SECOND = "1:00:00 one second activity 0:00:01 1:00:01";
	private String MINUTE = "1:00:00 one minute activity 0:01:00 1:01:00";
	private String HOUR = "1:00:00 one hour activity 1:00:00 2:00:00";
	private String DAY = "1:00:00 one day activity 1d0:00:00 1:00:00";

	@Test
	public void testFormatActivityStringOneDay() {
		Activity activity = new Activity("one day activity", testDate, dayAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(DAY);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneHour() {
		Activity activity = new Activity("one hour activity", testDate,
				hourAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(HOUR);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneMinute() {
		Activity activity = new Activity("one minute activity", testDate,
				minuteAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(MINUTE);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

	@Test
	public void testFormatActivityStringOneSecond() {
		Activity activity = new Activity("one second activity", testDate,
				secondAfter);
		StringBuffer buffer = new StringBuffer();
		buffer.append(SECOND);
		assertEquals(buffer.toString(),
				OutputManager.formatActivityString(activity));
	}

}
