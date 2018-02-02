package by.epam.naumovich.film_ordering.controller;

import by.epam.naumovich.film_ordering.service.IDiscountService;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.IReviewService;
import by.epam.naumovich.film_ordering.service.IUserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Sets the accordance between command names performed in CommandName enumeration and Command implementation classes
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 * @see CommandName
 * @see Command
 */
@Component
public class CommandHelper {

	private IFilmService filmService;
	private INewsService newsService;
	private IOrderService orderService;
	private IReviewService reviewService;
	private IUserService userService;
	private IDiscountService discountService;

    private Map<CommandName, Command> commands = new HashMap<>();

	@Autowired
    public CommandHelper(IFilmService filmService, INewsService newsService, IOrderService orderService,
                         IReviewService reviewService, IUserService userService, IDiscountService discountService) {
        this.filmService = filmService;
        this.newsService = newsService;
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.discountService = discountService;
        putCommands();
    }

    /**
     * Takes CommandName object by command name string and defines relevant Command interface implementation to be returned
     * @param name command name
     * @return Command interface implementation
     */
    Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }

	/**
	 * Puts all existing commands to the map where the key is the CommandName enumeration object
	 */
	private void putCommands() {
        commands.put(CommandName.ADD_DISCOUNT, new AddDiscount(discountService));
        commands.put(CommandName.ADD_FILM, new AddFilm(filmService));
        commands.put(CommandName.ADD_NEWS, new AddNews(newsService));
		commands.put(CommandName.ADD_ORDER, new AddOrder(orderService));
		commands.put(CommandName.ADD_REVIEW, new AddReview(reviewService));
		commands.put(CommandName.BAN_USER, new BanUser(userService));
		commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
		commands.put(CommandName.CHANGE_USER_SETTINGS, new ChangeUserSettings(userService));
		commands.put(CommandName.DELETE_DISCOUNT, new DeleteDiscount(discountService));
		commands.put(CommandName.DELETE_FILM, new DeleteFilm(filmService));
		commands.put(CommandName.DELETE_NEWS, new DeleteNews(newsService));
		commands.put(CommandName.DELETE_REVIEW, new DeleteReview(reviewService));
		commands.put(CommandName.EDIT_DISCOUNT, new EditDiscount(discountService));
		commands.put(CommandName.EDIT_FILM, new EditFilm(filmService));
		commands.put(CommandName.EDIT_NEWS, new EditNews(newsService));
		commands.put(CommandName.GET_NOVELTY, new GetNovelty(filmService, orderService));
		commands.put(CommandName.GET_SIDEBAR_NEWS, new GetSidebarNews(newsService));
		commands.put(CommandName.LOGIN, new Login(userService));
		commands.put(CommandName.LOGOUT, new Logout());
		commands.put(CommandName.OPEN_ABOUT_US_PAGE, new OpenAboutUsPage());
		commands.put(CommandName.OPEN_ALL_FILMS, new OpenAllFilms(filmService, orderService));
		commands.put(CommandName.OPEN_ALL_NEWS, new OpenAllNews(newsService));
		commands.put(CommandName.OPEN_ALL_ORDERS, new OpenAllOrders(filmService, orderService, userService));
		commands.put(CommandName.OPEN_ALL_REVIEWS, new OpenAllReviews(filmService, reviewService, userService));
		commands.put(CommandName.OPEN_ALL_USERS, new OpenAllUsers(userService, discountService));
		commands.put(CommandName.OPEN_FEEDBACK_PAGE, new OpenFeedbackPage());
		commands.put(CommandName.OPEN_FILM_EDIT_PAGE, new OpenFilmEditPage(filmService));
		commands.put(CommandName.OPEN_FILM_ORDERS, new OpenFilmOrders(filmService, orderService, userService));
		commands.put(CommandName.OPEN_HELP_PAGE, new OpenHelpPage());
		commands.put(CommandName.OPEN_LOGINATION_PAGE, new OpenLoginationPage());
		commands.put(CommandName.OPEN_NEW_FILM_PAGE, new OpenNewFilmPage(filmService));
		commands.put(CommandName.OPEN_NEW_NEWS_PAGE, new OpenNewNewsPage());
		commands.put(CommandName.OPEN_NEW_ORDER_PAGE, new OpenNewOrderPage(filmService, orderService, userService, discountService));
		commands.put(CommandName.OPEN_NEW_REVIEW_PAGE, new OpenNewReviewPage(filmService, reviewService));
		commands.put(CommandName.OPEN_NEWS_EDIT_PAGE, new OpenNewsEditPage(newsService));
		commands.put(CommandName.OPEN_SIGN_UP_PAGE, new OpenSignUpPage());
		commands.put(CommandName.OPEN_SINGLE_FILM, new OpenSingleFilm(filmService, orderService, reviewService, userService));
		commands.put(CommandName.OPEN_SINGLE_NEWS, new OpenSingleNews(newsService));
		commands.put(CommandName.OPEN_SINGLE_ORDER, new OpenSingleOrder(filmService, orderService, userService));
		commands.put(CommandName.OPEN_SINGLE_REVIEW, new OpenSingleReview(filmService, reviewService, userService));
		commands.put(CommandName.OPEN_USER_PROFILE, new OpenUserProfile(userService, discountService));
		commands.put(CommandName.OPEN_USER_SETTINGS, new OpenUserSettings(userService));
		commands.put(CommandName.OPEN_USER_ORDERS, new OpenUserOrders(filmService, orderService, userService));
		commands.put(CommandName.OPEN_USER_REVIEWS, new OpenUserReviews(filmService, reviewService));
		commands.put(CommandName.OPEN_WIDENED_SEARCH_PAGE, new OpenWidenedSearchPage(filmService));
		commands.put(CommandName.SIGN_UP, new SignUp(userService));
		commands.put(CommandName.SEARCH_FILMS, new SearchFilms(filmService, orderService));
		commands.put(CommandName.SEARCH_FILMS_WIDENED, new SearchFilmsWidened(filmService, orderService));
		commands.put(CommandName.UNBAN_USER, new UnbanUser(userService));
	}

}
