/*
 * File Name: Main.java
 * Author: Team 2: Abhishek, Keerthini, Richa
 * Date Created: 02/14/2015
 * Date Updated: 02/27/2015
 */
package SSW555.stevens.edu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private static String xrefID;
	private static String line="";
	private static String[] lineParts;
    private static String level = "";
    private static String tag = "";
	private static String arguments = "";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputDirectory=System.getProperty("user.dir");
		File inputFile = new File(inputDirectory +"/input.ged");
				
		ArrayList<Individual> indivList = new ArrayList<Individual>();
		ArrayList<Family> familyList = new ArrayList<Family>();
		
		BufferedReader br = null;
        try {
        	FileReader fr = new FileReader(inputFile);  		
            br = new BufferedReader(fr);
            line = br.readLine();
            while(line  != null ) {
            	if(!(tag.equals("INDI") || tag.equals("FAM"))){
	            	line = br.readLine();
	            	if(line != null)
	            		lineParts = line.trim().split(" ");
	        		getLineParts(lineParts);   
            	}
            	
            	//Store Individuals Information
				if (tag.equals("INDI")) {
					storeIndividualDetails(indivList, br);
				}

				//Store Family Information
				else if(tag.equals("FAM")) {
					storeFamilyDetails(familyList, br);
				}		
            }
            printIndivAndFly(indivList, familyList);
            displayErrors(indivList, familyList);
            checkSex(indivList,familyList);
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + inputFile.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + inputFile.toString());
        } catch(Exception e) {
        	System.out.println("Exception:" + e.getMessage());
        	e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Unable to close file: " + inputFile.toString());
            }
        }    
	}

	private static void storeIndividualDetails(ArrayList<Individual> indivList,	BufferedReader br) throws IOException {
		ArrayList<String> newChildInFlyList;
		ArrayList<String> newSpouseInFlyList;
		// get new individual
		Individual indiv = new Individual();
		indiv.setId(xrefID);
		line = br.readLine();
		lineParts = line.trim().split(" ");
		getLineParts(lineParts);  
		
		while(tag.equals("NAME") || tag.equals("SEX") || tag.equals("BIRT")|| tag.equals("DEAT")|| tag.equals("FAMC")|| tag.equals("FAMS")){
			
			switch(tag) {
				case "NAME":
					indiv.setName(line.substring(7));
					break;
				
				case "SEX":
					indiv.setSex(arguments);
					break;
				
				case "BIRT":
					line = br.readLine();
					lineParts = line.trim().split(" ");
					getLineParts(lineParts);
					indiv.setBirthDate(arguments);
					break;
				
				case "DEAT":
					line = br.readLine();
					lineParts = line.trim().split(" ");
					getLineParts(lineParts);
					indiv.setDeathDate(arguments);
					break;
				
				case "FAMC":
					if(indiv.getChildInFamily() != null) 
						newChildInFlyList =  indiv.getChildInFamily();								
					else 
						newChildInFlyList = new ArrayList<String>();													

					newChildInFlyList.add(arguments);
					indiv.setChildInFamily(newChildInFlyList);
					break;
				
				case "FAMS":
					if(indiv.getSpouseInFamily() != null) 
						newSpouseInFlyList =  indiv.getSpouseInFamily();								
					else 
						newSpouseInFlyList = new ArrayList<String>();	
					
					newSpouseInFlyList.add(arguments);
					indiv.setSpouseInFamily(newSpouseInFlyList);
					break;
				
				default:System.out.println("Missed Line: " + line);
					break;
			}					
			line = br.readLine();
			lineParts = line.trim().split(" ");
			getLineParts(lineParts);    
			
		}
		indivList.add(indiv);
	}
	
	private static void storeFamilyDetails(ArrayList<Family> familyList, BufferedReader br) throws IOException {
		ArrayList<String> newChildren;
		//get new Family
		Family fly = new Family();
		fly.setId(xrefID);
		line = br.readLine();
		lineParts = line.trim().split(" ");
		getLineParts(lineParts);  
		while(tag.equals("HUSB") || tag.equals("WIFE") || tag.equals("MARR") || tag.equals("DIV") || tag.equals("CHIL")){						
			switch(tag) {
				case "HUSB": 
					fly.setHusbandId(arguments);
					break;
					
				case "WIFE":
					fly.setWifeId(arguments);
					break;
					
				case "MARR":
					line = br.readLine();
					lineParts = line.trim().split(" ");
					getLineParts(lineParts);
					fly.setMarriageDate(arguments);
					break;
				
				case "DIV":
					line = br.readLine();
					lineParts = line.trim().split(" ");
					getLineParts(lineParts);
					fly.setDivorceDate(arguments);
					break;
					
				case "CHIL":
					if(fly.getChildren() != null) 
						newChildren =  fly.getChildren();								
					else 
						newChildren = new ArrayList<String>();	
					
					newChildren.add(arguments);
					fly.setChildren(newChildren);
					break;
					
				default:System.out.println("Missed Line: " + line);
					break;
			}
			line = br.readLine();
			lineParts = line.trim().split(" ");
			getLineParts(lineParts);    
		}
		familyList.add(fly);
	}

	/*
	 * Gets parts of the line
	 */
	public static void getLineParts(String[] lineParts)
	{
		level = (String)lineParts[0];
		
		//Check for 2 kinds of lines that start with 0 level and assign tags appropriately
		if(level.equals("0")) {
			if(lineParts.length == 3) {
				tag =(String)lineParts[2];
				xrefID = (String) lineParts[1];
			}
			else{
				tag = (String)lineParts[1];
			}
		}
		else{
			tag =(String)lineParts[1];
			switch(lineParts.length) {
			case 5: arguments = (String)lineParts[2] + " " + (String)lineParts[3] + " " + (String)lineParts[4] ;
			break;
			case 4: arguments = (String)lineParts[2] + " " + (String)lineParts[3];
				break;
			case 3:arguments = (String) lineParts[2];
				break;
			default: arguments = " ";
				break;
			}
		}		     
	}
	
	/*
	 * Check for valid tag
	 */
	public static boolean isValidTag(String tag){
		//Valid tags array as per Project Overview document
		String[] validTags = {"INDI", "NAME", "SEX", "BIRT", "DEAT","FAMC", "FAMS", "FAM","MARR","HUSB","WIFE","CHIL","DIV","DATE","TRLR","NOTE"};
		for (String tagName : validTags){
			if (tag.equals(tagName))
				return true;
		}		
		return false;
	}
	
	private static void checkSex(ArrayList<Individual> indiv,ArrayList<Family> fly) {
		String husbName = "";
                String wifeName = "";
                String gender = "";
                String genderF = "";
               // CharSequence male = 'M';
		Family family;
                
		System.out.println("---------------------Check Sex for Husband = Male & Wife = Female---------------------");
		System.out.println();
		for(int i=0; i< fly.size(); i++){
			family = new Family();
			family = fly.get(i);         
                        if(family.getHusbandId() != null) {
                        gender = Individual.getIndividualSexById(family.getHusbandId(), indiv);
                        husbName = Individual.getIndividualNameById(family.getHusbandId(), indiv);
                        if(gender.equals("M"))
                            System.out.println(husbName + "is male which is correct");
                        else
                            System.out.println(husbName + " shows female which is wrong gender");
                         } 
                        
                        if(family.getWifeId() != null){
                        gender = Individual.getIndividualSexById(family.getWifeId(), indiv);
                        wifeName = Individual.getIndividualNameById(family.getWifeId(), indiv);
                        if(gender.equals("F"))
                            System.out.println(wifeName + "is female which is correct");
                        else
                            System.out.println(wifeName + "shows male which is wrong gender");
                          }
                        
                        
                        //else
                           // System.out.println("Wrong gender for husband/wife");
                }
	}
	
	/*
	 * Prints Individuals and Families
	 */
	public static void printIndivAndFly(ArrayList<Individual> indiv, ArrayList<Family> fly){
		printIndividualSummary(indiv);	
		System.out.println();
		printFamilySummary(indiv, fly);
	}

	private static void printIndividualSummary(ArrayList<Individual> indiv) {
		Individual ind;
		System.out.println("--------------------Summary of Individuals--------------------");
		System.out.println();
		
		for(int i=0; i< indiv.size(); i++){
			ind = new Individual();
			ind = indiv.get(i);
			if (ind.getId() != null) 
				System.out.println("ID: "  + ind.getId());
			if (ind.getName() != null) 
				System.out.println("Name: "  + ind.getName());
			if (ind.getSex() != null) 
				System.out.println("SEX: "  + ind.getSex());
			if (ind.getBirthDate() != null) {
				System.out.println("Birth Date: "  + ind.getBirthDate());
			}
			if (ind.getDeathDate() != null) 
				System.out.println("Death Date: "  + ind.getDeathDate());
			if (ind.getChildInFamily() != null) {
				ArrayList<String> childInFamilies = ind.getChildInFamily();
				System.out.print("Child in Family IDs: ");
				for(int j=0; j< childInFamilies.size(); j++)
					System.out.print(childInFamilies.get(j) + "		");
				System.out.println();
			}
			if (ind.getSpouseInFamily() != null) {
				ArrayList<String> spouseInFamilies = ind.getSpouseInFamily();
				System.out.print("Spouse in Family IDs: ");
				for(int k=0; k< spouseInFamilies.size(); k++)
					System.out.print(spouseInFamilies.get(k) + "	");
				System.out.println();
			}
			
			System.out.println();
		}
	}
	
	private static void printFamilySummary(ArrayList<Individual> indiv,	ArrayList<Family> fly) {
		String husbandName;
		String wifeName;
		Family family;
		System.out.println("---------------------Summary of Families---------------------");
		System.out.println();
		for(int i=0; i< fly.size(); i++){
			family = new Family();
			family = fly.get(i);
			if(family.getId() != null)
				System.out.println("ID: "  +  family.getId());
			if(family.getHusbandId() != null) {
				husbandName = Individual.getIndividualNameById(family.getHusbandId(), indiv);
				System.out.println("Husband Name: "  + husbandName);
			}
			if(family.getWifeId() != null) {
				wifeName = Individual.getIndividualNameById(family.getWifeId(), indiv);
				System.out.println("Wife Name: "  + wifeName);
			}
			if(family.getMarriageDate() != null)
				System.out.println("Marriage Date: "  +  family.getMarriageDate());
			if(family.getDivorceDate() != null)
				System.out.println("Divorce Date: "  +  family.getDivorceDate());
			if (family.getChildren() != null) {
				ArrayList<String> childrenInFamilies = family.getChildren();
				System.out.print("Children in Family: ");
				for(int j=0; j< childrenInFamilies.size(); j++)
					System.out.print(childrenInFamilies.get(j) + "	");
				System.out.println();
			}
			System.out.println();
		}
	}

	public static void displayErrors(ArrayList<Individual> indiv, ArrayList<Family> fly) {
		System.out.println("!!!--------------------------- Summary of Errors-------------------------------!!!");
		System.out.println();	
		displayErrorsOfIndividuals(indiv);	
		displayErrorsOfFamilies(indiv, fly);
	}
	
	private static void displayErrorsOfIndividuals(ArrayList<Individual> indiv) {
		Individual ind;
		for(int i=0; i< indiv.size(); i++){
			ind = new Individual();
			ind = indiv.get(i);
			
			//Checks for Birth date after Current date
			if(Utilities.checkBirthDateAfterCurrentDate(Utilities.convertStringToDate(ind.getBirthDate())))
				System.out.println("Error - Individual " + ind.getId() + " (" + ind.getName() + ") has Birth date (" + ind.getBirthDate() +") after Current date!" );
			
			//Checks for Death date after Current date
			if(ind.getDeathDate() != null && Utilities.checkDeathDateAfterCurrentDate(Utilities.convertStringToDate(ind.getDeathDate())))
				System.out.println("Error - Individual " + ind.getId() + " (" + ind.getName() + ") has Death date (" + ind.getDeathDate() +") after Current date!" );
			
			if(ind.getBirthDate()!=null && ind.getDeathDate() != null && Utilities.checkDeathDateAfterBirthDate(Utilities.convertStringToDate(ind.getBirthDate()),Utilities.convertStringToDate(ind.getDeathDate())))
                System.out.println("Error - Individual " + ind.getId() + " (" + ind.getName() + ") has Birth date (" + ind.getBirthDate() +") after Death date (" +ind.getDeathDate() +")" );	
		}
	}
	
	private static void displayErrorsOfFamilies(ArrayList<Individual> indiv, ArrayList<Family> fly) {
		Family family;
		for(int j=0; j< fly.size(); j++){
			family = new Family();
			family = fly.get(j);
			
			//Checks for Marriage date after Current date
			if(family.getMarriageDate() != null && Utilities.checkMarriageDateAfterCurrentDate(Utilities.convertStringToDate(family.getMarriageDate())))
				System.out.println("Error - Family " + family.getId() + " has Marriage date (" + family.getMarriageDate() +") after Current date!" );
			
			//Checks for Divorce date after Current date
			if(family.getDivorceDate() != null && Utilities.checkDeathDateAfterCurrentDate(Utilities.convertStringToDate(family.getDivorceDate())))
				System.out.println("Error - Family " + family.getId() + " has Divorce date (" + family.getDivorceDate() +") after Current date!" );
			
			//Checks for Divorce date before Marriage date
			if(family.getDivorceDate() != null && family.getMarriageDate() != null && Utilities.checkDivorceDateBeforeMarriageDate(Utilities.convertStringToDate(family.getDivorceDate()), Utilities.convertStringToDate(family.getMarriageDate())))
				System.out.println("Error - Family " + family.getId() + " has Divorce date (" + family.getDivorceDate() +") before Marriage date(" + family.getMarriageDate() +")!" );
			
			Utilities.checkIfMarriedToOwnChild(indiv, family);
		}
		System.out.println();	
	}
}
