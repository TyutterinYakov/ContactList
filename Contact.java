package contact;

public class Contact {
	private Long contactId;
	private String surName;
	private String givenName;
	private int contactAge;
	private String userNum;
	private String userEmail;
	
	
	public Contact() {
		super();
	}


	public Contact(Long contactId, String surName, String givenName, int contactAge, String userNum, String userEmail) {
		super();
		this.contactId = contactId;
		this.surName = surName;
		this.givenName = givenName;
		this.contactAge = contactAge;
		this.userNum = userNum;
		this.userEmail = userEmail;
	}


	public Long getContactId() {
		return contactId;
	}


	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}


	public String getSurName() {
		return surName;
	}


	public void setSurName(String surName) {
		this.surName = surName;
	}


	public String getGivenName() {
		return givenName;
	}


	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}


	public int getContactAge() {
		return contactAge;
	}


	public void setContactAge(int contactAge) {
		this.contactAge = contactAge;
	}


	public String getUserNum() {
		return userNum;
	}


	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ID: "+contactId + "\t" + surName+" "+givenName+" "+contactAge+" "+userNum+" "+userEmail;
	}
	
	
	
	
	
}
