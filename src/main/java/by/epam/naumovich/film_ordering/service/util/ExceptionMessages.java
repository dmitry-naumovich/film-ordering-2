package by.epam.naumovich.film_ordering.service.util;

/**
 * Defines a set of String constants that describe occurred exceptions in the service layer classes
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public final class ExceptionMessages {

	public static final String ALREADY_TAKEN_LOGIN = "This login is already taken! Please, choose another one.";
	public static final String BIRTHDATE_RIGHT_FORMAT = "BirthDate must follow \"YYYY-MM-DD\" format";
	public static final String CORRUPTED_DISCOUNT_ID = "Corrupted discount ID! Please, try again.";
	public static final String CORRUPTED_FILM_ID = "Corrupted film ID! Please, try again.";
	public static final String CORRUPTED_FILM_REQUIRED_FIELDS = "At least one of the next fields is corrupted: name, year, director, length, price";
	public static final String CORRUPTED_INPUT_PARAMETERS = "Corrupted input parameters! Please, try again.";
	public static final String CORRUPTED_LENGTH_OR_REASON = "Corrupted ban length or reason! Please, try again.";
	public static final String CORRUPTED_LOGIN = "The login is corrupted. Sorry!";
	public static final String CORRUPTED_LOGIN_OR_PWD = "Corrupted login or password. Sorry!";
	public static final String CORRUPTED_NAME_SURN_SEX = "At least one of the next fields is corrupted or empty: name, surname, sex.";
	public static final String CORRUPTED_NEWS_ID = "Corrupted news ID! Please, try again.";
	public static final String CORRUPTED_PAGE_NUM = "Corrupted page number! Please, try again.";
	public static final String CORRUPTED_USER_ID = "Corrupted user ID! Please, try again.";
	public static final String COUNTRIES_NOT_AVAILABLE = "No countries available in the database! Please, try again.";
	public static final String DISCOUNT_DATE_TIME_RIGHT_FORMAT = "End date must follow \"YYYY-MM-DD\" format and end time must follow \"HH-MM-SS\" format";
	public static final String DISCOUNT_NOT_FOUND = "This user has not current discount.";
	public static final String FILM_NOT_ADDED = "The film was not added the database! Sorry! Please, try again";
	public static final String FILM_NOT_PRESENT = "The film is not present in the database";
	public static final String GENRES_NOT_AVAILABLE = "No genres available in the database! Please, try again.";
	public static final String INVALID_BAN_LENGTH = "Ban length must be positive integer number. Please, try again.";
	public static final String INVALID_BIRTHDATE = "The year must exceed %s and the date can not exceed today";
	public static final String INVALID_DISCOUNT_AMOUNT = "Discount amount must be positive integer value between 0 and 100! Please, try again.";
	public static final String INVALID_DISCOUNT_END = "Discount end date and time must exceed now! Please, try again.";
	public static final String INVALID_EMAIL = "The e-mail you entered is not valid. Please, try again.";
	public static final String INVALID_FILM_LENGTH = "The film length must be positive integer value. Please, try again!";
	public static final String INVALID_FILM_PRICE = "The film price must be positive float or integer value. Please, try again!";
	public static final String INVALID_FILM_YEAR = "The film year must contain 4 numbers. Please, try again!";
	public static final String INVALID_LOGIN = "Login must start with the letter and consist at least 3 symbols (latin letters and numbers)";
	public static final String INVALID_NEWS_TITLE_OR_TEXT = "Invalid news title or text! Please, try again.";
	public static final String INVALID_PASSWORD = "Password must be at least 4 symbols";
	public static final String INVALID_PHONE_NUM = "Phone number must contain %s numbers";
	public static final String INVALID_REVIEW_TYPE = "Review type can only be positive, negative or neutral! Please, try again.";
	public static final String LOGIN_NOT_REGISTRATED = "No such login registrated. Please, try again!";
	public static final String NEWS_NOT_ADDED = "The news was not added to the database. Sorry!";
	public static final String NEWS_NOT_PRESENT = "The news is not present in the database.";
	public static final String NO_FILM_ORDERS = "This film has not been ordered yet";
	public static final String NO_FILM_REVIEWS = "No reviews for this film";
	public static final String NO_FILM_USER_ORDER = "This user has not ordered this film yet";
	public static final String NO_FILM_USER_REVIEW = "This user has not written any reviews to this film yet";
	public static final String NO_FILMS_FOUND = "No films found by your query! Please, try again.";
	public static final String NO_FILMS_IN_DB = "No films in the database. Sorry!";
	public static final String NO_NEWS_IN_DB = "No news in the database. Sorry!";
	public static final String NO_ORDERS_IN_DB = "No orders in the database. Sorry!";
	public static final String NO_NEWS_WITHIN_MONTH = "No news within %s month of %s year found";
	public static final String NO_NEWS_WITHIN_YEAR = "No news within %s year found";
	public static final String NO_REVIEWS_IN_DB = "No reviews in the database. Sorry!";
	public static final String NO_USER_ORDERS_YET = "This user has not ordered anything yet";
	public static final String NO_USER_REVIEWS_YET = "This user has not written any reviews yet";
	public static final String NO_USERS_IN_BAN_NOW = "No users in ban now. Sorry!";
	public static final String NO_USERS_IN_DB = "No users in the database. Sorry!";
	public static final String ORDER_NOT_ADDED = "The order was not added the database! Sorry! Please, try again.";
	public static final String ORDER_NOT_FOUND = "The order was not found in the database! Sorry! Please, try again.";
	public static final String REVIEW_MARK_RANGE = "Review mark must be between 0 and 5 (both including)";
	public static final String REVIEW_TEXT_LENGTH = "Review text must be at least 50 symbols!";
	public static final String SOURCE_ERROR = "The error occured in the data source!";
	public static final String UNSUCCESSFULL_SIGN_UP = "User was not successfully registered. Sorry.";
	public static final String USER_NOT_FOUND = "No user found by this ID. Sorry!";
	public static final String WRONG_PASSWORD = "Wrong password! Please, try again.";
	public static final String YOU_ARE_BANNED = "You are banned until %s. The reason is: \"%s\"";

	private ExceptionMessages() {}
}
