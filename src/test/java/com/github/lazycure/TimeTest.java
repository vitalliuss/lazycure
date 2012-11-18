package test.java.com.github.lazycure;


import java.util.Date;

import main.java.com.github.lazycure.Time;

import org.junit.Test;

import junit.framework.TestCase;

public class TimeTest extends TestCase {

	@Test
	public void testMillisToShortDHMSZero() {
		assertEquals("0:00:00", Time.formatWithDay(0));
	}
	@Test
	public void testMillisToShortDHMSSimple() {
		assertEquals("1:01:01", Time.formatWithDay(1000+1000*60+1000*60*60));
	}
	@Test
	public void testMillisToShortDHMSMaxDay() {
		assertEquals("23:59:59", Time.formatWithDay(59*1000+59*1000*60+23*1000*60*60));
	}
	@Test
	public void testFormatWithDay() {
		assertEquals("1d0:00:00", Time.formatWithDay(new Date(24*1000*60*60)));
	}
	@Test
	public void testFormatWithNull() {
		assertEquals("0:00:00", Time.format(null));
	}
	@Test
	public void testFormatIgnoresDay() {
		assertEquals("0:00:01", Time.format(new Date(1*1000+24*1000*60*60)));
	}
	@Test
	public void testTimeZoneWorkWithNull() {
		assertEquals("0:00:00", Time.formatWithDefaultTimeZone(null));
	}
}
