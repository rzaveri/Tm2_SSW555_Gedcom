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
        
        public static String getIndividualSexById(String id, ArrayList<Individual> indiv)
	{
		String indivSex = "";
		for(int i=0; i< indiv.size(); i++){
			Individual ind = new Individual();
			ind = indiv.get(i);
			if(ind.getId().equals(id)){
				indivSex = ind.getSex();
				break;
			}
		}
		return indivSex;
	}
}
