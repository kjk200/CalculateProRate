import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CalculateProRate {

	// This method is use to convert the input string into Date Object
	public static Date convertStringToDate(String s) throws Exception {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		//Ensure that correct date has been added
		df.setLenient(false); 
		// Parse the string as date
		Date result = df.parse(s);
		return result;
	}
	
	// This method is use to calculate the number of days between two date objects
	public static int getDays (Date d1, Date d2) {
		int result = 0;
		result = Math.round((d1.getTime()-d2.getTime())/(24*60*60*1000));
		return result;
	}
	
	// This method is used to calculate the monthly billing period based on all conditions
	public static int getBillingPeriod (Date d1) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		cal.add(Calendar.MONTH, 1);
		Date d2 = cal.getTime();
		int result = getDays (d2, d1);
		return result;
	}
	
	// This method is used to return the pro-rated monthly charge
	public static double getAmount (double charge, Date bill, Date service) {
		double result = 0;
		int billPeriod = getBillingPeriod(bill);
		int getDays = getDays(service,bill);
		result = charge - ((charge * getDays)/billPeriod);
		return Math.round((result*100)/100);
	}

	// Main Method
	public static void main(String[] args) throws Exception {
		
		double monthlyCharge = 0.0;
		Date serviceDate = new Date();
		Date billDate = new Date();
		
		Scanner s1 = new Scanner(System.in);
		
		System.out.println("***************** PRORATED SERVICE FEE CALCULATOR ********************");
		System.out.println("****** Note: All dates should be entered in MM/DD/YYYY format ***************");
		System.out.println("******Note: Service start date should be later than the monthly bill date *********");
		System.out.println("***************** ****************************** ********************");
		System.out.println();
		//Get Monthly Charge
		System.out.println("Enter Monthly Recurring Charge: ");
		monthlyCharge = s1.nextDouble();
		if (monthlyCharge <= 0) {
			System.out.println(" Recurring Monthly Charge cannot be zero or negative. Exiting the program!!");
			System.exit(0);
		}
		// Get Service Start Date
		System.out.println("Enter service start Date (use format: mm/dd/yyyy: ");
		serviceDate = convertStringToDate(s1.next());
		// Get Billing Date 
		System.out.println("Enter the current billing Date (use format: mm/dd/yyyy:");
		billDate = convertStringToDate(s1.next());
			
		
		System.out.println("Current Billing Period is for: "+getBillingPeriod(billDate)+" days");
		
		int actualBilledDays = getBillingPeriod(billDate) - getDays(serviceDate, billDate);
		if (actualBilledDays < 0) {
			System.out.println("Invalid Billing Date has been entered. The difference between service start date and next billing date is less than zero. ");
			System.exit(0);
		}
		
		System.out.println("Customer should be billed for:  "+actualBilledDays+" days");
	
		
		// Formatting the amount in two decimal spaces 
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("Prorated amount due is: "+df.format(getAmount(monthlyCharge, billDate, serviceDate)));
		

	}

}
