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
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		
		ArrayList<String> newChildInFlyList;
		ArrayList<String> newSpouseInFlyList;
		ArrayList<String> newChildren;

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

				//Store Family Information
				else if(tag.equals("FAM")) {
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
            }
            printIndivAndFly(indivList, familyList);
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
            catch(NullPointerException ex) {
            }
        }    
	}

	/*
	 * Gets parts of the line
	 */
	public static void getLineParts(String[] aLines)
	{
		level = (String)aLines[0];
		
		//Check for 2 kinds of lines that start with 0 level and assign tags appropriately
		if(level.equals("0")) {
			if(aLines.length == 3) {
				tag =(String)aLines[2];
				xrefID = (String) aLines[1];
			}
			else{
				tag = (String)aLines[1];
			}
		}
		else{
			tag =(String)aLines[1];
			switch(aLines.length) {
			case 5: arguments = (String)aLines[2] + " " + (String)aLines[3] + " " + (String)aLines[4] ;
			break;
			case 4: arguments = (String)aLines[2] + " " + (String)aLines[3];
				break;
			case 3:arguments = (String) aLines[2];
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
	
	/*
	 * Prints Individuals and Families
	 */
	public static void printIndivAndFly(ArrayList<Individual> indiv, ArrayList<Family> fly){
		String husbandName, wifeName;
		Individual ind;
		Family family;
		System.out.println("----------Individuals----------");
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
			if (ind.getBirthDate() != null) 
				System.out.println("Birth Date: "  + ind.getBirthDate());
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
		
		System.out.println(".....................................................................................");
		System.out.println();
		System.out.println("-----------Families-----------");
		System.out.println();
		for(int i=0; i< fly.size(); i++){
			family = new Family();
			family = fly.get(i);
			if(family.getId() != null)
				System.out.println("ID: "  +  family.getId());
			if(family.getHusbandId() != null) {
				husbandName = getIndividualNameById(family.getHusbandId(), indiv);
				System.out.println("Husband Name: "  + husbandName);
			}
			if(family.getWifeId() != null) {
				wifeName = getIndividualNameById(family.getWifeId(), indiv);
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
	
	/*
	 * Get the name of the Individual by ID
	 */
	public static String getIndividualNameById(String id, ArrayList<Individual> indiv)
	{
		String indivName = "";
		for(int i=0; i< indiv.size(); i++){
			Individual ind = new Individual();
			ind = indiv.get(i);
			if(ind.getId().equals(id)){
				indivName = ind.getName();
				break;
			}
		}
		return indivName;
	}
	
}

