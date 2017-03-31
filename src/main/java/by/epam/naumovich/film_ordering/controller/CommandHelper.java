package by.epam.naumovich.film_ordering.controller;

import java.util.HashMap;
import java.util.Map;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.impl.*;
import by.epam.naumovich.film_ordering.command.impl.film.*;
import by.epam.naumovich.film_ordering.command.impl.navigation.*;
import by.epam.naumovich.film_ordering.command.impl.news.*;
import by.epam.naumovich.film_ordering.command.impl.order.*;
import by.epam.naumovich.film_ordering.command.impl.review.*;
import by.epam.naumovich.film_ordering.command.impl.user.*;

/**
 * Sets the accordance between command names performed in CommandName enumeration and Command implementation classes
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 * @see CommandName
 * @see Command
 */
public class CommandHelper {

	private static final CommandHelper instance = new CommandHelper();
	private Map<CommandName, Command> commands = new HashMap<>();
	
	/**
	 * Puts all existing commands to the map where the key is the CommandName enumeration object
	 */
	public CommandHelper() {
		commands.put(CommandName.ADD_DISCOUNT, new AddDiscount());
		commands.put(CommandName.ADD_FILM, new AddFilm());
		commands.put(CommandName.ADD_NEWS, new AddNews());
		commands.put(CommandName.ADD_ORDER, new AddOrder());
		commands.put(CommandName.ADD_REVIEW, new AddReview());
		commands.put(CommandName.BAN_USER, new BanUser());
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
		commands.put(CommandName.CHANGE_USER_SETTINGS, new ChangeUserSettings());
		commands.put(CommandName.DELETE_DISCOUNT, new DeleteDiscount()); 
		commands.put(CommandName.DELETE_FILM, new DeleteFilm()); 
		commands.put(CommandName.DELETE_NEWS, new DeleteNews());
		commands.put(CommandName.DELETE_REVIEW, new DeleteReview());
		commands.put(CommandName.EDIT_DISCOUNT, new EditDiscount());
		commands.put(CommandName.EDIT_FILM, new EditFilm());
		commands.put(CommandName.EDIT_NEWS, new EditNews());
		commands.put(CommandName.GET_NOVELTY, new GetNovelty());
		commands.put(CommandName.GET_SIDEBAR_NEWS, new GetSidebarNews());
		commands.put(CommandName.LOGIN, new Login());
		commands.put(CommandName.LOGOUT, new Logout());
		commands.put(CommandName.OPEN_ABOUT_US_PAGE, new OpenAboutUsPage());
		commands.put(CommandName.OPEN_ALL_FILMS, new OpenAllFilms());
		commands.put(CommandName.OPEN_ALL_NEWS, new OpenAllNews());
		commands.put(CommandName.OPEN_ALL_ORDERS, new OpenAllOrders());
		commands.put(CommandName.OPEN_ALL_REVIEWS, new OpenAllReviews());
		commands.put(CommandName.OPEN_ALL_USERS, new OpenAllUsers());
		commands.put(CommandName.OPEN_FEEDBACK_PAGE, new OpenFeedbackPage());
		commands.put(CommandName.OPEN_FILM_EDIT_PAGE, new OpenFilmEditPage());
		commands.put(CommandName.OPEN_FILM_ORDERS, new OpenFilmOrders());
		commands.put(CommandName.OPEN_HELP_PAGE, new OpenHelpPage());
		commands.put(CommandName.OPEN_LOGINATION_PAGE, new OpenLoginationPage());
		commands.put(CommandName.OPEN_NEW_FILM_PAGE, new OpenNewFilmPage());
		commands.put(CommandName.OPEN_NEW_NEWS_PAGE, new OpenNewNewsPage());
		commands.put(CommandName.OPEN_NEW_ORDER_PAGE, new OpenNewOrderPage());
		commands.put(CommandName.OPEN_NEW_REVIEW_PAGE, new OpenNewReviewPage());
		commands.put(CommandName.OPEN_NEWS_EDIT_PAGE, new OpenNewsEditPage());
		commands.put(CommandName.OPEN_SIGN_UP_PAGE, new OpenSignUpPage());
		commands.put(CommandName.OPEN_SINGLE_FILM, new OpenSingleFilm());
		commands.put(CommandName.OPEN_SINGLE_NEWS, new OpenSingleNews());
		commands.put(CommandName.OPEN_SINGLE_ORDER, new OpenSingleOrder());
		commands.put(CommandName.OPEN_SINGLE_REVIEW, new OpenSingleReview());
		commands.put(CommandName.OPEN_USER_PROFILE, new OpenUserProfile());		
		commands.put(CommandName.OPEN_USER_SETTINGS, new OpenUserSettings());
		commands.put(CommandName.OPEN_USER_ORDERS, new OpenUserOrders());
		commands.put(CommandName.OPEN_USER_REVIEWS, new OpenUserReviews());
		commands.put(CommandName.OPEN_WIDENED_SEARCH_PAGE, new OpenWidenedSearchPage());
		commands.put(CommandName.SIGN_UP, new SignUp());
		commands.put(CommandName.SEARCH_FILMS, new SearchFilms());
		commands.put(CommandName.SEARCH_FILMS_WIDENED, new SearchFilmsWidened());
		commands.put(CommandName.UNBAN_USER, new UnbanUser());
	}
	
	/**
	 * Takes CommandName object by command name string and defines relevant Command interface implementation to be returned
	 * @param name command name
	 * @return Command interface implementation
	 */
	public Command getCommand(String name) {
		CommandName commandName = CommandName.valueOf(name);
		Command command = commands.get(commandName);
		return command;
	}
	
	public static CommandHelper getInstance() {
		return instance;
	}
}
