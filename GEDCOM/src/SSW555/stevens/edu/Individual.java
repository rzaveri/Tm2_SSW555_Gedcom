/*
author Richa
Project Version 2
*/
package SSW555.stevens.edu;

import java.util.ArrayList;

public class Individual 
{
	
	private String id;
	private String name;
	private String sex;
	private String birthDate;
	private String deathDate;
	private ArrayList<String> spouseInFamily;
	private ArrayList<String> childInFamily;
	
//	public Individual() {
//		this.setId("");
//		this.setName("");
//		this.setSex("");
//		this.setBirthDate("");
//		this.setDeathDate("");
//		this.setSpouseInFamily(null);
//		this.setChildInFamily(null);
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public ArrayList<String> getChildInFamily() {
		return childInFamily;
	}

	public void setChildInFamily(ArrayList<String> childInFamily) {
		this.childInFamily = childInFamily;
	}

	public ArrayList<String> getSpouseInFamily() {
		return spouseInFamily;
	}

	public void setSpouseInFamily(ArrayList<String> spouseInFamily) {
		this.spouseInFamily = spouseInFamily;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}