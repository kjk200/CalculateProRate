import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;


public class CalculateProRateTest {

	// Below test is used to verify negative scenarios - incorrect month, incorrect date, incorrect leap year date
	@Test (expected = java.text.ParseException.class) 
	public void testConvertStringToDate() throws Exception {
		// Invalid Month 
		String s1 = "13/02/2010";
		// Invalid Day
		String s2 = "12/32/2010";
		// Invalid Leap Year Date
		String s3 = "02/29/2010";
		CalculateProRate.convertStringToDate(s1);
		CalculateProRate.convertStringToDate(s2);
		CalculateProRate.convertStringToDate(s3);
	}

	// Below test is used to verify few scenarios regarding getting number of days between two dates
	@Test
	public void testGetDays() throws Exception {
		CalculateProRate c1 = new CalculateProRate();
		Date d1 = CalculateProRate.convertStringToDate("01/01/2010");
		Date d2 = CalculateProRate.convertStringToDate("01/11/2010");
		Date d3 = CalculateProRate.convertStringToDate("02/01/2010");
		Date d4 = CalculateProRate.convertStringToDate("02/01/2010");
		assertEquals("Days between 01/11/2010 and 01/01/2010 must be 10", 10, c1.getDays(d2, d1));
		assertEquals("Days between 02/01/2010 and 01/01/2010 must be 31", 31, c1.getDays(d3, d1));
		assertEquals("Days between 02/01/2010 and 02/01/2010 must be zero", 0, c1.getDays(d3, d4));
	}
 
	//  Below tests are used to verify the next billing cycle based on a certain date
	@Test
	public void testGetBillingPeriod() throws Exception {
		// Non-Leap Year test for Feb
		String s1 = "01/29/2011";
		// Leap Year test for Feb
		String s2 = "01/29/2012";
		// Consecutive 31 days months - July and August
		String s3 = "07/31/2013";
		// Test for successive 31 and 30 days months - March and April
		String s4 = "03/31/2014";
		
		assertEquals("Billing cycle for "+s1+" should be 30 days", 30,CalculateProRate.getBillingPeriod(CalculateProRate.convertStringToDate(s1)));
		assertEquals("Billing cycle for "+s2+" should be 31 days", 31, CalculateProRate.getBillingPeriod(CalculateProRate.convertStringToDate(s2)));
		assertEquals("Billing cycle for "+s3+" should be 31 days", 31, CalculateProRate.getBillingPeriod(CalculateProRate.convertStringToDate(s3)));
		assertEquals("Billing cycle for "+s4+" should be 30 days", 30, CalculateProRate.getBillingPeriod(CalculateProRate.convertStringToDate(s4)));
	}

	// Below tests are used to validate the pro-rated amount due
	@Test
	public void testGetAmount() throws Exception {
		Date billing = CalculateProRate.convertStringToDate("01/01/2011");
		Date service1 = CalculateProRate.convertStringToDate("01/02/2011");
		Date service2 = CalculateProRate.convertStringToDate("01/15/2011");
		Date service3 = CalculateProRate.convertStringToDate("02/01/2011");
		double fee = 3100.00;
		 // Test scenario where service start date is 1 day after billing start date
		assertEquals("Pro-rate amount due for "+service1+" should be 3000 ", 3000, CalculateProRate.getAmount(fee, billing, service1), 0.001);
		// Test scenario where service start date is 14 day after billing start date
		assertEquals("Pro-rate amount due for "+service2+" should be 1700 ", 1700, CalculateProRate.getAmount(fee, billing, service2), 0.001);
		// Test scenario where service start date same day as billing start date - No pro-rate due here. 
		assertEquals("Pro-rate amount due for "+service3+" should be zero ", 0, CalculateProRate.getAmount(fee, billing, service3), 0.001);
	}

	
}

