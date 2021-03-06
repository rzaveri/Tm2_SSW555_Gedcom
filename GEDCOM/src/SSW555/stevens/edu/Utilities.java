package SSW555.stevens.edu;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
	
	/*
	 * Returns true if Divorce Date before Marriage date
	 */
	public static boolean checkDivorceDateBeforeMarriageDate(Date divorceDate, Date marriageDate) {
		int compareValue = compareDates(divorceDate, marriageDate);
		if(compareValue == 1)
			return true;
		else
			return false;
	}
	
	/* Returns true if divorce Date before Birth date
	 * @input: divorce date,birth date
	 * @output: boolean
	 */
	public static boolean checkDivorceDateBeforeBirthDate(Date divorceDate,Date birthDate) {
		
		int compareValue = compareDates(divorceDate, birthDate);
		if(compareValue == 1)
			return true;
		else
			return false;
	}
	
	public static void checkIfMarriedToOwnChild(ArrayList<Individual> indiv, Family family) {
		String husbandId, wifeId, childId;
		ArrayList<String> children;
		
		if(family.getHusbandId() != null && family.getWifeId() != null && family.getChildren() != null) {
			husbandId = family.getHusbandId();	
			wifeId = family.getWifeId();			
			children = family.getChildren();
		
			for(int j= 0; j< children.size(); j++) {
				childId = children.get(j);
				if(childId.equals(husbandId)) {
					System.out.println("Error - Family " + family.getId() + " has wife " + wifeId + " ("+ Individual.getIndividualNameById(wifeId, indiv) + ") married to her own child " + childId + " (" + Individual.getIndividualNameById(childId, indiv) + ")");					
				}
				if(childId.equals(wifeId)) {
					System.out.println("Error - Family " + family.getId() + " has husband " + husbandId + " (" + Individual.getIndividualNameById(husbandId, indiv) + ") married to his own child " + " (" + Individual.getIndividualNameById(childId, indiv) + ")");					
				}
			}
		}
	}
	
    public static boolean checkDeathDateAfterBirthDate(Date birthDate, Date deathDate)
    {
        int compareValue = compareDates(birthDate,deathDate);
                if(compareValue==2)
                    return true;
                else
                    return false;
    }
    
    public static boolean checkMarriageDateBeforeBirthDate(Date birthDate, Date MarriageDate)
        {
            int compareValue = compareDates(birthDate,MarriageDate);
                    if(compareValue==2)
                        return true;
                    else
                        return false;
        }
    
	/* Returns true if Marriage Date after Death date
	* @input: divorce date
	* @output: boolean
	*/
	public static boolean checkMarriageDateAfterDeathDate(Date marriageDate,Date deathDate) {
	
	int compareValue = compareDates(marriageDate, deathDate);
	if(compareValue == 2)
		return true;
	else
		return false;
	}
    
    /*
     * Check For marriage to sibling
     * @input: ChildInFamily,SpouseInFamily
     * @output: boolean
     */
    public static boolean checkMarriageToSibling(ArrayList<String> ChildInFamily,ArrayList<String> SpouseInFamily) {
   
    	for(int i=0;i< ChildInFamily.size();i++){
    		for(int k=0; k< SpouseInFamily.size(); k++){
    			if(ChildInFamily.get(i).equals(SpouseInFamily.get(k)))
    	            return true;
    			else
    	            continue;
    	    	}
    		}
    	return false;
    }
   	
    /*
     * Returns true if individual is married to multiple people at the same time
     */
    public static boolean checkIfMarriedToMultiplePeople(Individual ind, ArrayList<String> spouseInFamily) {
    	String familyId;
    	Family fly;
    	int currentSpouseCount = 0;
    	String spouseId = null;
    	Individual spouse;
    	
    	if(spouseInFamily.size() > 1) {
    		for(int i =0; i< spouseInFamily.size(); i++) {
    			familyId = spouseInFamily.get(i);
    			fly = getFamilyById(familyId, Main.familyList);
    			if(fly != null) {
    				if (ind.getSex().equals("M"))
    					spouseId = fly.getWifeId();
    				if(ind.getSex().equals("F"))
    					spouseId = fly.getHusbandId();
    				if(spouseId != null) {
    					spouse = getIndividualById(spouseId, Main.indivList);
    					//increase currentSpouseCount by one if spouse is not dead or divorced with the individual
	    				if(spouse.getDeathDate() == null && fly.getDivorceDate() == null ) {
	    					currentSpouseCount += 1;
	    				}
    				}
    			}
    		}
    		if (currentSpouseCount > 1) 
    			return true;
    	}
    return false;	
    }
    
    /*
	 * Get Individual by ID
	 */
	public static Individual getIndividualById(String id, ArrayList<Individual> indiv)
	{
		Individual individual = null;
		for(int i=0; i< indiv.size(); i++){
			individual = new Individual();
			individual = indiv.get(i);
			if(individual.getId().equals(id)){
				return individual;
			}
		}
		return individual;
	}
	
	/*
	 * Get Family by ID
	 */
	public static Family getFamilyById(String id, ArrayList<Family> family)
	{
		Family fly = null;
		for(int i=0; i< family.size(); i++){
			fly = new Family();
			fly = family.get(i);
			if(fly.getId().equals(id)){
				return fly;
			}
		}
		return fly;
	}
	
	/*
	 * Get spouse list of an individual
	 */
	public static ArrayList<Individual> getSpousesOfIndividual(Individual ind, ArrayList<String> spouseInFamily){
		String familyId;
    	Family fly;
    	String spouseId = null;
    	Individual spouse = null;	
    	ArrayList<Individual> spouseList = new ArrayList<Individual>();	;		
    		for(int i =0; i< spouseInFamily.size(); i++) {
    			familyId = spouseInFamily.get(i);
    			fly = getFamilyById(familyId, Main.familyList);
    			if(fly != null) {
    				if (ind.getSex().equals("M"))
    					spouseId = fly.getWifeId();
    				if(ind.getSex().equals("F"))
    					spouseId = fly.getHusbandId();
    				if(spouseId != null) 
    					spouse = getIndividualById(spouseId, Main.indivList);
    				if(spouse != null)
    					spouseList.add(spouse);   							    				
    			}
    		}
    	return spouseList;
	}
	
	/*
	 * Return true if an individual is widow or widower
	 */
	public static boolean checkIfWidowOrWidower(Individual ind, ArrayList<String> spouseInFamily) {
		//get all spouses and check if they all have death date
		ArrayList<Individual> spouseList = new ArrayList<Individual>();
		spouseList = getSpousesOfIndividual(ind, spouseInFamily);
		Boolean widowFlag = true;
		for (int i=0; i< spouseList.size(); i++) {
			if(spouseList.get(i).getDeathDate() == null )
				widowFlag = false;
		}
		return widowFlag;
	}
        
        public static boolean checkMarriedToDeadPerson(Individual ind, ArrayList<String> spouseInFamily) {  
  	String familyId;
    	Family fly;
    	String spouseId = null; 
    	Individual spouse=null;
    	int compareValue =0;

    		for(int i =0; i< spouseInFamily.size(); i++) {
    			familyId = spouseInFamily.get(i);
    			fly = getFamilyById(familyId, Main.familyList);
    			if(fly != null) {
    				if (ind.getSex().equals("M"))
    					spouseId = fly.getWifeId();
    				if(ind.getSex().equals("F"))
    					spouseId = fly.getHusbandId();
    				if(spouseId != null)
    					spouse = getIndividualById(spouseId, Main.indivList);
    				if( spouse != null && fly.getMarriageDate() != null && spouse.getDeathDate() != null ) {
    					compareValue = compareDates(Utilities.convertStringToDate(spouse.getDeathDate()),Utilities.convertStringToDate(fly.getMarriageDate()));                  
    				}
    			}
    		}
    		if (compareValue == 2) 
    			return true;
    	
    return false;	
    }

        
    public static void listDivorcesRemarried(ArrayList<Individual> indiv){
    	Individual ind;
    	System.out.println("---------------------List of Divorces who are re-married---------------------");
		for(int i=0; i< indiv.size(); i++){
			ind = new Individual();
			ind = indiv.get(i);
			String familyId;
	    	Family fly;
	    	Boolean divorce=false;
	    	ArrayList<String> spouseInFamily=ind.getSpouseInFamily();
	    	if(spouseInFamily!=null && spouseInFamily.size()>1){
	      		for(int j =0; j< spouseInFamily.size(); j++) {
	    			familyId = spouseInFamily.get(j);
	    			fly = getFamilyById(familyId, Main.familyList);
	    			if(fly != null && fly.getDivorceDate()!=null) {
	    				divorce=true;
	    			}
	      		}
	      		if(divorce)
	      		System.out.println(ind.getId() + " " + ind.getName());
	    	}
        }
    }


    public static void checkDivorceBeforeDeathOfBothSpouses(Family fly) {  
  	
    	int compareValueSpouse =0,compareValueIndiv=0;
        String deathDateHusband=null, deathDateWife=null, husbName=null, wifeName=null;
        Individual husband, wife;
        husband = getIndividualById(fly.getHusbandId(),Main.indivList);
        wife = getIndividualById(fly.getWifeId(),Main.indivList);
        
        deathDateHusband = husband.getDeathDate();
        deathDateWife = wife.getDeathDate();
        husbName = husband.getName();
        wifeName = wife.getName();
        if(fly.getDivorceDate() != null && deathDateHusband != null)  {
    	compareValueSpouse = compareDates(Utilities.convertStringToDate(deathDateHusband),Utilities.convertStringToDate(fly.getDivorceDate()));                  
        //compareValueIndiv = compareDates(Utilities.convertStringToDate(deathDateWife),Utilities.convertStringToDate(fly.getDivorceDate()));                  
        }
        
        if(fly.getDivorceDate() != null && deathDateWife != null) {
    	//compareValueSpouse = compareDates(Utilities.convertStringToDate(deathDateHusband),Utilities.convertStringToDate(fly.getDivorceDate()));                  
        compareValueIndiv = compareDates(Utilities.convertStringToDate(deathDateWife),Utilities.convertStringToDate(fly.getDivorceDate()));                  
        }
        if(compareValueSpouse == 1)
        {
            System.out.println("Error - In Family "+ fly.getId() + "Husband " + husbName + " has death date (" + deathDateHusband + ") before his divorce date (" + fly.getDivorceDate()+")");
            
            //System.out.println("Error - Husband " + husbName + " or Wife " + wifeName + " have death before their divorce (" + fly.getDivorceDate() + ")");
        }
        if(compareValueIndiv == 1)
        {
            System.out.println("Error - In Family "+ fly.getId() + "Wife " + wifeName + " has death date (" + deathDateWife + ") before her divorce date (" + fly.getDivorceDate()+")");
        }
        
    	
    }
    
    /*
	 * Return true if an individual is married to step sibling
	 */
    public static boolean listIndivMarriedToStepSibling(Individual ind, ArrayList<String> spouseInFamily, ArrayList<String> childInFamily) {   	
    	ArrayList<Individual> spouseList = new ArrayList<Individual>();
    	spouseList = getSpousesOfIndividual(ind, spouseInFamily); 	//Get Spouse of individual
    	
    	ArrayList<String> spouseChildList = new ArrayList<String>() ; 
    	for (int i=0; i< spouseList.size(); i++) {
    		if(spouseList.get(i).getChildInFamily() != null)	//get families where spouse is a child 
    			spouseChildList = spouseList.get(i).getChildInFamily() ;
    		
    		for(int j = 0; j < spouseChildList.size(); j++) {
    			for (int k=0; k < childInFamily.size(); k++) {
    				if(spouseChildList.get(j).equals(childInFamily.get(k)))		//Compare if they have common families were individual and spouse are child
    						return true;
    			}
    		}
    	}
    	return false;
    }
    
    /*
   	Returns true if individual's death date is not less than 150 years after birth date
    */
    public static boolean checkIfDeath150YearsAfterBirth(Date birthDate, Date deathDate) {
    	 Date dateAfter150YrsOfBirth; 
    			 
    	 Calendar cal = Calendar.getInstance(); 
    	 cal.setTime(birthDate); 
    	 cal.add(Calendar.YEAR, 150);
    	 dateAfter150YrsOfBirth = cal.getTime();
    	 
    	 int compareValue = compareDates(dateAfter150YrsOfBirth, deathDate);
         if(compareValue == 1 || compareValue == 0)
             return true;
         else
             return false;
    }
    
    public static boolean checkParentBirthDateAfterChildBirthDate(Date childBirthDate, Date parentBirthDate) {
        int compareValue = compareDates(parentBirthDate,childBirthDate);
                if(compareValue==2)
                    return true;
                else
                    return false;
    }
        
    public static int getDifferenceYear(Date oldDate, Date newDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        if (oldDate.compareTo(newDate) > 0) {
            c1.setTime(newDate);
            c2.setTime(oldDate);
        } 
        else {
            
            return 0;

        }
        int year = 0;
        int month = 0;
        int days = 0;
        boolean doneMonth = false;
        boolean doneYears = false;
        while (c1.before(c2)) {
            //log.debug("Still in Loop");
            if (!doneYears) {
                c1.add(Calendar.YEAR, 1);
                year++;
            }
            if (c1.after(c2) || doneYears) {
                if (!doneYears) {
                    doneYears = true;
                    year--;
                    c1.add(Calendar.YEAR, -1);
                }   
                if (!doneMonth) {
                    c1.add(Calendar.MONTH, 1);
                    month++;
                }
                if (c1.after(c2) || doneMonth) {
                    if (!doneMonth) {
                        doneMonth = true;
                        month--;
                        c1.add(Calendar.MONTH, -1);
                    }

                    c1.add(Calendar.DATE, 1);
                    days++;
                    if (c1.after(c2)) {
                        days--;
                    }
                    // this will not be executed
                    if (days == 31 || month==12) {
                        break;
                    }
                }
            }
        }
        //System.out.println(year + " years, " + month + " months, " + days + " days");
        return year;
        }
        
        public static boolean checkMotherChildBirthDifferenceGreaterThan60(Date childBirthDate, Date motherBirthDate)
        {
            int d = getDifferenceYear(childBirthDate,motherBirthDate);
            if(d==0)
            {
                return false;
            }
            if(d >= 60)
            {
                return true;
            }
            if(d < 60)
            {
                return false;
            }
            return false;
            
        }
        
        public static boolean checkFatherChildBirthDifferenceGreaterThan80(Date childBirthDate, Date fatherBirthDate)
        {
            int d = getDifferenceYear(childBirthDate,fatherBirthDate);
            if(d==0)
            {
                return false;
            }
            if(d >= 80)
            {
                return true;
            }
            if(d < 80)
            {
                return false;
            }
            return false;
            
        }
        
        /*
    	Returns true if current date is not less than 150 years after birth date
        */
        public static boolean checkIfCurrentDate150YearsAfterBirth(Date birthDate) {
        	 Date dateAfter150YrsOfBirth; 
        	 Date currentDate = new Date();
        	 Calendar cal = Calendar.getInstance(); 
        	 cal.setTime(birthDate); 
        	 cal.add(Calendar.YEAR, 150);
        	 dateAfter150YrsOfBirth = cal.getTime();
        	 
        	 int compareValue = compareDates(dateAfter150YrsOfBirth, currentDate);
             if(compareValue == 1 || compareValue == 0)
                 return true;
             else
                 return false;
        }
        
        /*
       	Returns true if child's birth date is 9 month after father's death
        */
        public static boolean checkIfBirthDateAfter9MonthDeath(Date birthDate, Date deathDate) {
        	 Date dateAfter9months; 
        			 
        	 Calendar cal = Calendar.getInstance(); 
        	 cal.setTime(deathDate); 
        	 cal.add(Calendar.MONTH,9);
        	 dateAfter9months = cal.getTime();
        	 
        	 int compareValue = compareDates(dateAfter9months, birthDate);
             if(compareValue == 1)
                 return true;
             else
                 return false;
        }
        /*
       	Returns true if child's birth date is after mother's death date
        */
        public static void checkChildBornAfterMothersDeath(Individual mother, ArrayList<String> children) {
        	if( mother!= null && children !=null) {
    	    	for(int i=0; i < children.size(); i++) {
    	    		Individual child = getIndividualById(children.get(i), Main.indivList);
    	    		if(mother.getDeathDate() != null && child.getBirthDate() != null) {
    		    		int compareValue = compareDates(convertStringToDate(child.getBirthDate()), convertStringToDate(mother.getDeathDate()));
    		    		if(compareValue == 2) {
    		    			System.out.println("Error - Child " + child.getId() + " (" + child.getName() + ") has birth date (" + child.getBirthDate() + ") after mother "  + mother.getName() + " death date (" + mother.getDeathDate() + ") ");
    		    		}    	
    	    		}
    	    	}
        	}
        }
        
        // Checks if age difference of siblings is less than 8 months apart
        public static void checkAgeDifferenceOfSiblings(ArrayList<String> children) {
        	if(children !=null) {
    		    for(int i = 0; i < children.size(); i++) {
    		    	Individual childToCompare = getIndividualById(children.get(i), Main.indivList);
    		    	for (int j= i + 1; j < children.size(); j++) {
    		    		Individual sibling = getIndividualById(children.get(j), Main.indivList);
    		    		if(childToCompare.getBirthDate()!= null && sibling.getBirthDate() != null) {
    		    	    	Date childToCompareBirthDate = convertStringToDate(childToCompare.getBirthDate());
    		    	    	Date siblingBirthDate = convertStringToDate(sibling.getBirthDate());
    		    	    	if(!childToCompareBirthDate.equals(siblingBirthDate)) { //Ignore multiple births such as twins, triplets etc.
    		    	         double monthsApart = Math.abs(Math.floor(((childToCompareBirthDate.getTime() - siblingBirthDate.getTime()) / (1000*60*60*24*30.5))));
    		    	         if (monthsApart < 8) 
    		    	        	 System.out.println("Error - Siblings " + childToCompare.getId() + " (" + childToCompare.getName() + ", Birth Date: " + childToCompare.getBirthDate() + " ) and " + sibling.getId() + " (" + sibling.getName() + ", Birth Date: " + sibling.getBirthDate() + " )  has birth dates less than 8 months apart  ");
    		    	    	}
    		    		}
    		    	}
    		    }
    	    }
        }
        /*
       	Returns true if child's birth date is after 9 months of father's death date, unless wife re-marry
        */
        public static void checkChildBornFathersDeath(Individual father, ArrayList<String> children) {
        	if( father!= null && children !=null) {
    	    	for(int i=0; i < children.size(); i++) {
    	    		Individual child = getIndividualById(children.get(i), Main.indivList);
    	    		if(father.getDeathDate() != null && child.getBirthDate() != null && checkIfBirthDateAfter9MonthDeath(convertStringToDate(child.getBirthDate()),convertStringToDate(father.getDeathDate()))) {
    		    		System.out.println("Error - Child " + child.getId() + " (" + child.getName() + ") has birth date (" + child.getBirthDate() + ") 9 months after father "  + father.getName() + " death date (" + father.getDeathDate() + ") ");
 	   	    		}
    	    	}
        	}
        }
}



