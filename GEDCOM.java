/*
 * File Name: GEDCOM.java
 * Team: Team 2 - Abhishek, Keerthini, Richa
 * Date Created: 02/08/2015
 */
package CS513.stevens.edu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GEDCOM {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String inputDirectory=System.getProperty("user.dir");
		File inputFile = new File(inputDirectory +"/input.ged");
	    BufferedReader br = null;
	    
	        try {
	            FileReader fr = new FileReader(inputFile);
	            br = new BufferedReader(fr);
	            
	            String line="";
	    	    String level = "";
	    		String tag = "";
	    		
	            while((line = br.readLine()) != null ) {
	            	String[] aLines = line.trim().split(" ");
					level = (String)aLines[0];
					
					//Check for 2 kinds of lines that start with 0 level and assign tags appropriately
					if(level.equals("0") && aLines.length == 3)
						tag =(String)aLines[2];
					else
						tag = (String)aLines[1];
					
	                System.out.println("Line: " + line);
	                System.out.println("Level: " + level);
	                if(isValidTag(tag))
	                	System.out.println("Tag: " + tag);
	                else
	                	System.out.println("Invalid Tag");
	                System.out.println("");              	                
	            }
	        } catch (FileNotFoundException e) {
	            System.out.println("File not found: " + inputFile.toString());
	        } catch (IOException e) {
	            System.out.println("Unable to read file: " + inputFile.toString());
	        } catch(Exception e) {
	        	System.out.println("Exception:" + e.getMessage());
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
}
		
		
		
		