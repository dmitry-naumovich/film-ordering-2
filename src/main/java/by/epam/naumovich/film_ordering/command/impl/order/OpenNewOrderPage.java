package by.epam.naumovich.film_ordering.command.impl.order;

import by.epam.naumovich.film_ordering.service.IDiscountService;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.naumovich.film_ordering.bean.Film;
import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.GetFilmServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetDiscountServiceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Performs the command that forwards request and response from the service layer and passes it to the relevant JSP.
 * Checks the access rights of the user who is performing this action.
 * Checks if user has not ordered this film yet.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class OpenNewOrderPage implements Command {

	private final IFilmService filmService;
	private final IOrderService orderService;
	private final IUserService userService;
	private final IDiscountService discountService;

    public OpenNewOrderPage(IFilmService filmService, IOrderService orderService, IUserService userService,
                            IDiscountService discountService) {
        this.filmService = filmService;
        this.orderService = orderService;
        this.userService = userService;
        this.discountService = discountService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		log.info(query);

		String lang = fetchLanguageFromSession(session);
		
		int filmID = fetchFilmIdFromRequest(request);
		
		if (!isAuthorized(session)) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_ORDERING);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else if (isAdmin(session)) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.ADMIN_CAN_NOT_ORDER);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1").forward(request, response);
		}
		else {
			boolean already = false;
			int userID = fetchUserIdFromSession(session);
			try {
				List<Order> orders = orderService.getAllByUserId(userID);
				for (Order o : orders) {
					if (o.getFilmId() == filmID) {
						already = true;
						request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.FILM_ALREADY_ORDERED);
						request.getRequestDispatcher("/Controller?command=open_single_order&orderNum=" + o.getOrdNum()).forward(request, response);
					}
				}
			} catch (GetOrderServiceException e) {
				
			} catch (ServiceException e) {
				already = true;
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
			
			if (!already) {
				try {
					Film film = filmService.getByID(filmID, lang);
					int discount;
					try {
						discount = discountService.getCurrentUserDiscountByID(userID).getAmount();
					} catch (GetDiscountServiceException e) {
						discount = 0;
					}
					float orderSum = film.getPrice() * (1.0f - discount/100f);   
					
					request.setAttribute(RequestAndSessionAttributes.FILM, film);
					request.setAttribute(RequestAndSessionAttributes.DISCOUNT_AMOUNT, discount);
					request.setAttribute(RequestAndSessionAttributes.ORDER_SUM, orderSum);
			
					request.getRequestDispatcher(JavaServerPageNames.FILM_ORDERING_PAGE).forward(request, response);
					
				} catch (GetFilmServiceException e) {
					log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
					request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
					request.getRequestDispatcher(JavaServerPageNames.FILM_ORDERING_PAGE).forward(request, response);
					
				} catch (ServiceException e) {
					log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
					request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
					request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
				}
			}
			
		}
	}

}
