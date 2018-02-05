package by.epam.naumovich.film_ordering.command.util;

/**
 * Defines a set of String constants that describe occurred exceptions in the Command implementation classes.
 * Each of these messages will be shown to the user in case relevant exception occurs.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public final class ErrorMessages {

	public static final String ADD_DISCOUNT_RESTRICTION = "Only administrator is able to add discounts for users";
	public static final String ADD_NEWS_RESTRICTION = "Only administrator is able to add news";
	public static final String ADD_FILM_RESTRICTION = "Only administrator is able to add films";
	public static final String ADD_ORDER_RESTRICTION = "Sign in to be able to order films";
	public static final String ADMIN_CAN_NOT_ORDER = "Administrator is not able to order films.";
	public static final String BAN_USER_RESTRICTION = "Only administrator is able to ban users!";
	public static final String DELETE_DISCOUNT_RESTRICTION = "Only administrator is able to delete user discounts";
	public static final String DELETE_FILM_RESTRICTION = "Only administrator is able to delete films";
	public static final String DELETE_NEWS_RESTRICTION = "Only administrator is able to delete news";
	public static final String DELETE_REVIEW_RESTRICTION = "Only administrator and review author is able to delete the review";
	public static final String EDIT_DISCOUNT_RESTRICTION = "Only administrator is able to edit user discounts";
	public static final String EDIT_FILM_RESTRICTION = "Only administrator is able to edit films";
	public static final String EDIT_NEWS_RESTRICTION = "Only administrator is able to edit news";
	public static final String FILE_NOT_UPLOADED = "The file was not successfully uploaded to the server";
	public static final String FILM_ALREADY_ORDERED = "Sorry! You have already ordered this film.";
	public static final String FILM_ORDERS_RESTRICTION = "Only administrator is able to view film orders";
	public static final String LOG_OUT_FOR_ANOTHER_ACC = "Log out to sign into another account!";
	public static final String LOG_OUT_FOR_SIGN_UP = "Log out to be able to sign up!";
	public static final String NOTHING_FOUND = "No films found within this search query! Please, try again.";
	public static final String OPEN_ALL_ORDERS_RESTRICTION = "Only administrator is able to view all orders";
	public static final String OPEN_ALL_REVIEWS_RESTRICTION = "Only administrator is able to view all reviews";
	public static final String OPEN_ALL_USERS_RESTRICTION = "Only administrator is able to view all users";
	public static final String REVIEW_AMOUNT_RESTRICTION = "You are not able to write more than one review to a single film!";
	public static final String SIGN_IN_FOR_ORDERING = "Sign in to be able to order films";
	public static final String SIGN_IN_FOR_PROFILE = "Sign in to access your profile page";
	public static final String SIGN_IN_FOR_REVIEWING = "Sign in to be able to write reviews";
	public static final String SIGN_IN_FOR_SETTINGS = "Sign in to access or change your profile settings";
	public static final String SIGN_IN_FOR_SINGLE_ORDER = "Sign in to be able to look through the order";
	public static final String SIGN_IN_FOR_YOUR_ORDERS = "Sign in to see your orders";
	public static final String SIGN_IN_FOR_YOUR_REVIEWS = "Sign in to see your reviews";	
	public static final String UNBAN_USER_RESTRICTION = "Only administrator is able to unban users!";
	
	private ErrorMessages() {}
}
