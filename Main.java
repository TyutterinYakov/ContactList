package index;


import java.util.List;

import contact.Contact;
import dao.ContactDao;

public class Main {
	public static void main(String[] args) {

		ContactDao cd = new ContactDao();
		List <Contact> list = cd.getContactList();
		
		for(Contact ct: list) {
			System.out.println(ct.toString());
		}
		
		System.out.println();
	//	cd.getContact(85);
		
	//	cd.addContact();
		
		cd.deleteContact(95);
		
	}
}
