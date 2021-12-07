package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import config.Config;
import connect.ConnectionBuilder;
import contact.Contact;

public class ContactDao {
	private static final String SELECT_ALL = "select * from contacts LIMIT ?";
	private static final String SELECT_ONE = "select * from contacts";
	private static final String INSERT_CONTACT = "insert into contacts(sur_name, given_name, contact_age, user_num, user_email) values "+
	"(?, ?, ?, ?, ?)";
	private static final String DELETE_CONTACT = "delete from contacts where contact_id=?";
	private static final String UPDATE_CONTACT = "update contacts set sur_name =?, given_name=?, contact_age=?,"
			+ " user_num=?, user_email=? where contact_id=?";
	private static int count = 0;
	private static int num=0;
	
	
	public Connection connect() throws SQLException {
		return ConnectionBuilder.getConnection();
		
	}
	

	public List<Contact> getContactList() {
		
		List<Contact> result = new LinkedList<Contact>();
		
		try(Connection con = connect(); 
				PreparedStatement stmt = con.prepareStatement(SELECT_ALL))
		{
			
			int limit =  Integer.parseInt(Config.getProperties(Config.DB_LIMIT));
			stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Contact contact = new Contact();
				contact.setContactId(rs.getLong("contact_id"));
				contact.setSurName(rs.getString("sur_name"));
				contact.setGivenName(rs.getString("given_name"));
				contact.setContactAge(rs.getInt("contact_age"));
				contact.setUserNum(rs.getString("user_num"));
				contact.setUserEmail(rs.getString("user_email"));
				
				
				result.add(contact);

				
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return result;
	}
	
	
	public void getContact(long contactId) {
		Contact ct = getContactMethod(contactId);
		if(ct!=null) {
			System.out.println("\n"+ct+"\n");
		} else {
			System.out.println("\nThis contact is not in the list\n");
		}
		
		
		
	}
	public Contact getContactMethod(long contactId) {
		try(Connection con = connect();
				PreparedStatement stmt = con.prepareStatement(SELECT_ONE);){
			
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				if(contactId==rs.getLong("contact_id")) {
					Contact ct = new Contact(
							contactId,
							rs.getString("sur_name"),
							rs.getString("given_name"),
							rs.getInt("contact_age"),
							rs.getString("user_num"),
							rs.getString("user_email")
							);
					return ct;
				}
			}
			
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public void addContact() {
		int count=0;
		try(Connection con = connect(); 
				PreparedStatement stmt = con.prepareStatement(INSERT_CONTACT))
		{
			
			List<Contact> result = addContactMethod();
			if(result!=null) {
			
				for(Contact cont: result) {
					stmt.setString(1, cont.getSurName());
					stmt.setString(2, cont.getGivenName());
					stmt.setInt(3, cont.getContactAge());
					stmt.setString(4, cont.getUserNum());
					stmt.setString(5, cont.getUserEmail());
					stmt.executeUpdate();
					count++;
			}
			}
			
			
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}  finally {
		System.out.println("Успешно добавлено "+ count +" контакт(ов)");
	}
		
	}
	
	public List<Contact> addContactMethod() {
		
	List<Contact> result = new LinkedList<Contact>();
		try {
			Scanner scan = new Scanner(System.in);
			System.out.println("Сколько контактов вы хотите добавить?");
			num = scan.nextInt();
			if(num<=0) {
				return null;
			}
			
			for(int i=0; i<num; i++) {
				
				Contact contact = new Contact();
				System.out.println("SurName: ");
				
				contact.setSurName(scan.next());
				System.out.println("GivenName: ");
				
				contact.setGivenName(scan.next());
				
				System.out.println("Age: ");
				contact.setContactAge(scan.nextInt());
				
				System.out.println("ContactNumber: ");
				
				contact.setUserNum(scan.next());
	
				System.out.println("ContactEmail: ");
				
				contact.setUserEmail(scan.next());

				result.add(contact);
//				System.out.println(result);

			}
		//	System.out.println(result);
			
			
		} catch(InputMismatchException e) {
			System.err.println("Введено некорректное значение");
		
	}
		return result;
	}
	
	public void updateContact() {
		try(Connection con = connect();
				PreparedStatement stmt = con.prepareStatement(UPDATE_CONTACT);
				){
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Введите id контакта, чтобы обновить: ");
			boolean del = false;
				long id = scan.nextLong();
			if(id>0) {
					for(Contact cont: getContactList()) {
						if(cont.getContactId()==id) {
				System.out.println("SurnName: ");
				stmt.setString(1, scan.next());
				System.out.println("GivenName: ");
				stmt.setString(2, scan.next());
				System.out.println("Age: ");
				stmt.setInt(3, scan.nextInt());
				System.out.println("ContactNumber:: ");
				stmt.setString(4, scan.next());
				System.out.println("Email: ");
				stmt.setString(5, scan.next());
				
				stmt.setLong(6, id);
				stmt.executeUpdate();
				del = true;
				System.out.println("Запись успешно обновлена");
						}
					}
					if(del==false) {
						System.out.println("Пользователь с таким id не найден");
					}
			

			} else {
				System.out.println("Запись не была обновлена");
			}
//			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch(InputMismatchException e) {
			System.err.println("Введено некорректное значение");
		}
	}
	
	public void deleteContact() {
		
		try(Connection con = connect(); 
				PreparedStatement stmt = con.prepareStatement(DELETE_CONTACT);)
		{
			boolean del =false;
			Scanner scan = new Scanner(System.in);
			System.out.println("Введите id контакта, чтобы удалить");
			long id = scan.nextLong();
			if(id>0) {
				for(Contact cont: getContactList()) {
					if(cont.getContactId()==id) {
						stmt.setLong(1, id);
						stmt.executeUpdate();
						System.out.println("Контакт с ID: "+id+" удален!");
						del=true;
					}
				}
				if(del==false){
						System.out.println("Абонент с таким id не найден");
				}
			} else {
				System.out.println("Абонента с таким id не может быть");
			}
				
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch(InputMismatchException e) {
			System.err.println("Введено некорректное значение");
		}
		
		
		
	}

	
}
