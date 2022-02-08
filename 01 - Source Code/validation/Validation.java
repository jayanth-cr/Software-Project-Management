package validation;

import java.util.*;
import java.util.regex.Pattern;
import java.sql.Date;

public class Validation {

	
	public static List<String> validateProject(String title, String description) {
		List<String> error = new LinkedList<String>();
		if ( ! ( isName(title.trim()) ) ) {
			error.add("Title");
		}
		if (  description.trim().equals("")  ) {
			error.add("Description");
		}
		return (error);
	}
	public static List<String> validateTask(String title, String cost, Date date) {
		List<String> error = new LinkedList<String>();
		if( !( isName(title.trim())) ) {
			error.add("Title");
		}
		if( !( isCost(cost.trim()) ) ) {
			error.add("Cost");
		}
		if( ! ( isDueDate(date) ) ) {
			error.add("Date");
		}
		return (error);
	}

	public static List<String> validateUser(String name, String email, String password) {
		List<String> error = new LinkedList<String>();
		if ( ! ( isName(name.trim()) ) ) {
			error.add("Name");
		}
		if ( ! ( isEmail(email.trim()) ) ) {
			error.add("Email");
		}

		if ( ! ( isStrongPassword(password.trim()) ) ) {
			error.add("Password");
		}
		
		return (error);
	}
	public static List<String> validateUser(String email, String password) {
		List<String> error = new LinkedList<String>();
		if ( ! ( isEmail(email.trim()) ) ) {
			error.add("Email");
		}

		if ( ! ( password.length() > 0 ) ) {
			error.add("Password");
		}
		
		return (error);
	}
	public static boolean isName(String name){
        return Pattern.matches("[a-zA-Z ]+", name);
    }
	public static boolean isEmail(String email){
        return Pattern.matches("([a-z\\_\\.\\-0-9]+)@([a-z\\-0-9]+)\\.([a-z]{2,4}\\.?[a-z]*)", email);
    }
	public static boolean isStrongPassword(String password){
        return password.length() >= 8 && Pattern.matches(".*[a-z]+.*", password) && Pattern.matches(".*[A-Z]+.*", password) && Pattern.matches(".*[0-9]+.*", password) && Pattern.matches(".*[^a-zA-Z0-9]+.*", password);
    }
	public static boolean isCost(String cost){
		try {
			Integer.parseInt(cost);
			return(true);
		}
		catch(Exception e) {
			return(false);
		}
	
	}
	public static boolean isDueDate(Date date) {
		long millis=System.currentTimeMillis();
		Date today=new Date(millis);
		if( today.before(date)  ) {
			return(true);
		}
		return(false);
	}

}
