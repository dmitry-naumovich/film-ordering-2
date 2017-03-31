package by.epam.naumovich.film_ordering.command.util;

/**
 * Defines a set of String constants that describe messages to be logged in the Command implementation classes.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public final class LogMessages {

	public static final String DISCOUNT_ADDED = "New discount for user (ID #%s, amount %s) was added to the data source.";
	public static final String DISCOUNT_DELETED = "Discount (ID #%s) for user (ID #%s) was deleted from the data source.";
	public static final String DISCOUNT_EDITED = "Discount (ID #%s) for user (ID #%s) was updated in the data source.";
	public static final String EXCEPTION_IN_COMMAND = "%s occured in %s command. Message for user is: \"%s\".";
	public static final String FILM_ADDED = "New film %s (id #%s) was added to the data source.";
	public static final String FILM_DELETED = "Film (had id #%s) was deleted from the data source.";
	public static final String FILM_EDITED = "Film %s (id #%s) was updated in the data source.";
	public static final String NEWS_ADDED = "New news (id #%s) was added to the data source.";
	public static final String NEWS_DELETED = "News (id #%s) was deleted from the data source.";
	public static final String NEWS_EDITED = "News (id #%s) was updated in the data source.";
	public static final String ORDER_ADDED = "New order (orderNum #%s, payment %s) was added to the data source.";
	public static final String REVIEW_ADDED = "New review (userID #%s, filmID #%s) was added to the data source.";
	public static final String REVIEW_DELETED = "Review (userID #%s, filmID #%s) was deleted from the data source.";
	public static final String USER_BANNED = "User (ID #%s) was banned for the reason: \"%s\".";
	public static final String USER_LOGGED_IN = "User %s (ID #%s) logged into the system.";
	public static final String USER_LOGGED_OUT = "User %s (ID #%s) logged out from the system.";
	public static final String USER_REGISTRATED = "User %s (ID #%s) was registrated in the system.";
	public static final String USER_SETTINGS_EDITED = "User profile settings (userID #%s) were updated in the data source.";
	public static final String USER_UNBANNED = "The user (ID #%s) was unbanned.";
	
	private LogMessages() {}
	
}
