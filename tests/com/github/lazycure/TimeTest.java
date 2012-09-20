package com.github.lazycure;


import org.junit.Test;

import junit.framework.TestCase;

public class TimeTest extends TestCase {

	@Test
	public void testMillisToShortDHMSZero() {
		assertEquals("00:00:00", Time.millisToShortDHMS(0));
	}
	@Test
	public void testMillisToShortDHMSSimple() {
		assertEquals("01:01:01", Time.millisToShortDHMS(1000+1000*60+1000*60*60));
	}
	@Test
	public void testMillisToShortDHMSMaxDay() {
		assertEquals("23:59:59", Time.millisToShortDHMS(59*1000+59*1000*60+23*1000*60*60));
	}
	@Test
	public void testMillisToShortDHMSOneDay() {
		assertEquals("1d00:00:00", Time.millisToShortDHMS(60*1000+59*1000*60+23*1000*60*60));
	} 
}
