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
	@Test
	public void testFormatAndRound() {
		assertEquals("0:15", Time.formatAndRound(new Date(29*1000+15*1000*60)));
		assertEquals("0:16", Time.formatAndRound(new Date(31*1000+15*1000*60)));
		assertEquals("1:00", Time.formatAndRound(new Date(59*1000+59*1000*60)));
	}
	@Test
	public void testFormatAndRoundWithDefaultTimeZoneWorkWithNull() {
		assertEquals("0:00", Time.formatAndRoundWithDefaultTimeZone(null));
	}
	@Test
	public void testFormatAndRoundWithDay() {
		assertEquals("1d0:00", Time.formatAndRoundWithDay(59*1000+59*1000*60+23*1000*60*60));
		assertEquals("1:01", Time.formatAndRoundWithDay(1000+1000*60+1000*60*60));
	}
	@Test
	public void testHoursToMinutest() {
		assertEquals(0, Time.HoursToMinutes(0));
		assertEquals(60, Time.HoursToMinutes(1));
		assertEquals(120, Time.HoursToMinutes(2));
	}
	@Test
	public void testMinutestToHoursAndMunites() {
		assertEquals("00:00", Time.MinutestToHoursAndMunites(0));
		assertEquals("01:00", Time.MinutestToHoursAndMunites(60));
		assertEquals("02:00", Time.MinutestToHoursAndMunites(120));
		assertEquals("01:01", Time.MinutestToHoursAndMunites(61));
		assertEquals("01:59", Time.MinutestToHoursAndMunites(119));
	}
	@Test
	public void testHoursAndMinutestToMinutes() {
		assertEquals(0, Time.HoursAndMinutestToMinutes(0, 0));
		assertEquals(1, Time.HoursAndMinutestToMinutes(0, 1));
		assertEquals(60, Time.HoursAndMinutestToMinutes(1, 0));
		assertEquals(61, Time.HoursAndMinutestToMinutes(1, 1));
		assertEquals(179, Time.HoursAndMinutestToMinutes(2, 59));
	}
}
