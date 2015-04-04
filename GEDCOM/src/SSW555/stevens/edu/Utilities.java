package SSW555.stevens.edu;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
    		if (compareValue == 1) 
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
}
