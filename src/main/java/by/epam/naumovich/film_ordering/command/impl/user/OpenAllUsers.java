package by.epam.naumovich.film_ordering.command.impl.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetDiscountServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetUserServiceException;

/**
 * Performs the command that gets all users from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class OpenAllUsers implements Command {

	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		int pageNum = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.PAGE_NUM));
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.OPEN_ALL_USERS_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else if (!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.OPEN_ALL_USERS_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.INDEX_PAGE).forward(request, response);
		}
		else {
			try {
				IUserService userService = ServiceFactory.getInstance().getUserService();
				Set<User> users = userService.getAllUsersPart(pageNum);

				List<Boolean> banList = new ArrayList<Boolean>();
				List<Discount> discountList = new ArrayList<Discount>();
				for (User u : users) {
					banList.add(userService.userIsInBan(u.getId()));
					Discount discount = null;
					try {
						discount = userService.getCurrentUserDiscountByID(u.getId());
						discountList.add(discount);
					} catch (GetDiscountServiceException e) {
						discountList.add(null);
					}
				}
				
				int totalPageAmount = userService.getNumberOfAllUsersPages();
				request.setAttribute(RequestAndSessionAttributes.NUMBER_OF_PAGES, totalPageAmount);
				request.setAttribute(RequestAndSessionAttributes.CURRENT_PAGE, pageNum);
				
				request.setAttribute(RequestAndSessionAttributes.USERS, users);
				request.setAttribute(RequestAndSessionAttributes.BAN_LIST, banList);
				request.setAttribute(RequestAndSessionAttributes.DISCOUNT_LIST, discountList);
				request.getRequestDispatcher(JavaServerPageNames.USERS_PAGE).forward(request, response);
				
			} catch (GetUserServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.USERS_PAGE).forward(request, response);	
			} catch (ServiceException e) {
				LOGGER.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
