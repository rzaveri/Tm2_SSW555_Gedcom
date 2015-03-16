package SSW555.stevens.edu;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class Utilities {
	/*
	 * Converts String to Date type
	 */
	public static Date convertStringToDate(String date) {
		Date convertedDate = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	    	convertedDate = sdf.parse(date);
		}
		catch(ParseException ex){
			ex.printStackTrace();
		}
    	return convertedDate;
	}
	
	/*
	 * Compares dates and returns 2 if date1 is after date2, returns 1 if date1 is before date2, 
	 * returns 0 if date 1 is equal to date 2, returns -1 if date1 and/or date2 are null
	 */
	public static int compareDates(Date date1, Date date2) {
		int compareValue = -1; 
		if(date1 != null && date2 != null) {
			if(date1.after(date2))
	    		compareValue = 2;
    		
			else if(date1.before(date2))
	    		compareValue = 1;
	    	
			else if(date1.equals(date2))
	    		compareValue = 0;	    	
		}
    	return compareValue;
	}
	
	/*
	 * Returns true if Birth Date after Current date
	 */
	public static boolean checkBirthDateAfterCurrentDate(Date birthDate) {
		Date currentDate = new Date();
		int compareValue = compareDates(birthDate, currentDate);
		if(compareValue == 2)
			return true;
		else
			return false;
	}
	
	/*
	 * Returns true if Death Date after Current date
	 */
	public static boolean checkDeathDateAfterCurrentDate(Date deathDate) {
		Date currentDate = new Date();
		int compareValue = compareDates(deathDate, currentDate);
		if(compareValue == 2)
			return true;
		else
			return false;
	}
	
	/*
	 * Returns true if Marriage Date after Current date
	 */
	public static boolean checkMarriageDateAfterCurrentDate(Date marriageDate) {
		Date currentDate = new Date();
		int compareValue = compareDates(marriageDate, currentDate);
		if(compareValue == 2)
			return true;
		else
			return false;
	}
	
	/*
	 * Returns true if Divorce Date after Current date
	 */
        
	public static boolean checkDivorceDateAfterCurrentDate(Date divorceDate) {
		Date currentDate = new Date();
		int compareValue = compareDates(divorceDate, currentDate);
		if(compareValue == 2)
			return true;
		else
			return false;
	}
        
        public static boolean checkDeathDateAfterBirthDate(Date birthDate, Date deathDate)
        {
            int compareValue = compareDates(birthDate,deathDate);
                    if(compareValue==2)
                        return true;
                    else
                        return false;
        }
        
        public static boolean checkMarriageDateBeforeBirthDate(Date MarriageDate, Date birthDate)
        {
            int compareValue = compareDates(birthDate,MarriageDate);
                    if(compareValue==2)
                        return true;
                    else
                        return false;
        }
        
        

}
