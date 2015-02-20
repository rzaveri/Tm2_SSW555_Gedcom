/*
 * File Name: Main.java
 * Author: Keerthini Mahesh
 * Date Created: 02/14/2015
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
		ArrayList<String> newChildInFlyList;
		ArrayList<String> newSpouseInFlyList;

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
					while(tag.equals("HUSB") || tag.equals("WIFE")){						
						switch(tag) {
							case "HUSB": 
								fly.setHusbandId(arguments);
								break;
							case "WIFE":
								fly.setWifeId(arguments);
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
			case 5: arguments = (String)aLines[2] + " " + (String)aLines[3] + (String)aLines[4] ;
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
		String husbandName = "";
		String wifeName = "";
		System.out.println("----------Individuals----------");
		System.out.println("Individual ID				Name");
		for(int i=0; i< indiv.size(); i++){
			Individual ind = new Individual();
			ind = indiv.get(i);
			System.out.println(ind.getId() + "		" + ind.getName());
		}
		
		System.out.println("");
		System.out.println("-----------Families-----------");
		System.out.println("Family ID			  Husband Name			   Wife Name");
		for(int i=0; i< fly.size(); i++){
			Family family = new Family();
			family = fly.get(i);
			husbandName = getIndividualNameById(family.getHusbandId(), indiv);
			wifeName = getIndividualNameById(family.getWifeId(), indiv);
			System.out.println(family.getId() + "		" + husbandName + "       		" + wifeName);
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
