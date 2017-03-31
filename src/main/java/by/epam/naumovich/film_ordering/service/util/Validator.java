package by.epam.naumovich.film_ordering.service.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines methods that help service classes to validate input parameters
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public final class Validator {
	
	/**
	 * Validates String object accordance to the regular expression
	 * 
	 * @param toCheck String object to be checked
	 * @param pattern String object that performs pattern using regular expression language
	 * @return true if input String object matches the pattern, false otherwise
	 */
	public static boolean validateWithPattern(String toCheck, String pattern) {
		
		if (toCheck == null || toCheck.isEmpty()) {
			return false;
		}
		
		Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(toCheck);
        return matcher.matches();
	}
	
	/**
	 * Validates all input String objects
	 * 
	 * @param strings undefined amount of String objects
	 * @return true if all input objects are not null or empty
	 */
	public static boolean validateStrings(String... strings) {
		
		for (String str : strings) {
			if (str == null || str.isEmpty()) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Validates input object
	 * 
	 * @param obj
	 * @return true if it is not null, false otherwise
	 */
	public static boolean validateObject(Object obj) {
		return obj == null ? false : true;
	}
	
	/**
	 * Validates integer value which must have positive value
	 * 
	 * @param i integer value
	 * @return true if the value is positive, false otherwise
	 */
	public static boolean validateInt(int i) {
		return i > 0;
	}
	
	/**
	 * Validates String array
	 * 
	 * @param array array of String objects
	 * @return true if all array objects are not null and not empty and the array itself is not null, false otherwise
	 */
	public static boolean validateStringArray(String[] array) {
		if (!validateObject(array)) {
			return false;
		}
		for (String s : array) {
			if (!validateStrings(s)) {
				return false;
			}
		}
		return true;
	}
}
