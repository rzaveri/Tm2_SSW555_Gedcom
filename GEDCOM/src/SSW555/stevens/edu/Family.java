//author: Richa Zaveri
package SSW555.stevens.edu;

public class Family 
{
	private String id;
	private String husbandId;
	private String wifeId;
        private String marriageDate;
        private String divorceDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHusbandId() {
		return husbandId;
	}
	public void setHusbandId(String husbandId) {
		this.husbandId = husbandId;
	}
	public String getWifeId() {
		return wifeId;
	}
	public void setWifeId(String wifeId) {
		this.wifeId = wifeId;
	}
        public String getMarriageDate() {
            return marriageDate;
        }
         public void setMarriageDate(String marriageDate) {
            this.marriageDate = marriageDate;
         }
        public String getDivorceDate() {
            return divorceDate;
        }
         public void setDivorceDate(String divorceDate) {
            this.divorceDate = divorceDate;
        }
}

