package by.epam.naumovich.film_ordering.service.impl;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.dao.IOrderDAO;
import by.epam.naumovich.film_ordering.service.IOrderService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.AddOrderServiceException;
import by.epam.naumovich.film_ordering.service.exception.order.GetOrderServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IOrderService interface implementation that works with IOrderDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

	private static final int ORDERS_AMOUNT_ON_PAGE = 10;
	
	private final IOrderDAO orderDAO;

	@Autowired
	public OrderServiceImpl(IOrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public int create(int filmID, int userID, String price, String discount, String payment) throws ServiceException {
		if (!Validator.validateInt(filmID) || !Validator.validateInt(userID) || !Validator.validateStrings(price, discount, payment)) {
			throw new AddOrderServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		Order newOrder = new Order();
		newOrder.setFilmId(filmID);
		newOrder.setUserId(userID);
		newOrder.setDate(Date.valueOf(LocalDate.now()));
		newOrder.setTime(Time.valueOf(LocalTime.now()));
		
		try {
			float fPrice = Float.parseFloat(price);
			int fDiscount = Integer.parseInt(discount);
			float fPayment = Float.parseFloat(payment);
			newOrder.setPrice(fPrice);
			newOrder.setDiscount(fDiscount);
			newOrder.setPayment(fPayment);
			
		} catch (NumberFormatException e) {
			throw new AddOrderServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}

		Order order = orderDAO.save(newOrder);
        if (order == null) {
            throw new AddOrderServiceException(ExceptionMessages.ORDER_NOT_ADDED);
        }

		return order.getOrdNum();
	}

	@Override
	public void delete(int orderNum) throws ServiceException {
		if (!Validator.validateInt(orderNum)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
        orderDAO.delete(orderNum);
	}
	
	@Override
	public Order getByOrderNum(int orderNum) throws ServiceException {
		if (!Validator.validateInt(orderNum)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}

        Order order = orderDAO.findOne(orderNum);
        if (order == null) {
            throw new GetOrderServiceException(ExceptionMessages.ORDER_NOT_FOUND);
        }
        return order;
	}


	@Override
	public Order getByUserAndFilmId(int userID, int filmID) throws ServiceException {
		if (!Validator.validateInt(userID) || !Validator.validateInt(filmID)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
        Order order = orderDAO.findByUserIdAndFilmId(userID, filmID);
        if (order == null) {
            throw new GetOrderServiceException(ExceptionMessages.NO_FILM_USER_ORDER);
        }
        return order;
	}
	
	@Override
	public List<Order> getAllByUserId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}
		return orderDAO.findByUserIdOrderByDateDescTimeDesc(id);
	}

	@Override
	public List<Order> getAllByFilmId(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
		
		List<Order> orders = orderDAO.findByFilmIdOrderByDateDescTimeDesc(id);
        if (orders.isEmpty()) {
            throw new GetOrderServiceException(ExceptionMessages.NO_FILM_ORDERS);
        }
		return orders;
	}

	@Override
	public List<Order> getAllPart(int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetOrderServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		
		int start = (pageNum - 1) * ORDERS_AMOUNT_ON_PAGE;
        List<Order> orders = orderDAO.findAllPart(start, ORDERS_AMOUNT_ON_PAGE);

        if (orders.isEmpty()) {
            throw new GetOrderServiceException(ExceptionMessages.NO_ORDERS_IN_DB);
        }
        return orders;
	}

	@Override
	public List<Order> getAllPartByUserId(int id, int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum) || !Validator.validateInt(id)) {
			throw new GetOrderServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		int start = (pageNum - 1) * ORDERS_AMOUNT_ON_PAGE;
        List<Order> orders = orderDAO.findPartByUserId(id, start, ORDERS_AMOUNT_ON_PAGE);

        if (orders.isEmpty()) {
            throw new GetOrderServiceException(ExceptionMessages.NO_ORDERS_IN_DB);
        }

        return orders;
	}

	@Override
	public List<Order> getAllPartByFilmId(int id, int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum) || !Validator.validateInt(id)) {
			throw new GetOrderServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
		}
		
		int start = (pageNum - 1) * ORDERS_AMOUNT_ON_PAGE;
        List<Order> orders = orderDAO.findPartByFilmId(id, start, ORDERS_AMOUNT_ON_PAGE);

        if (orders.isEmpty()) {
            throw new GetOrderServiceException(ExceptionMessages.NO_ORDERS_IN_DB);
        }
        return orders;
	}

    @Override
    public long countPages() {
        long numOfOrders = orderDAO.count();
        if (numOfOrders % ORDERS_AMOUNT_ON_PAGE == 0) {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE + 1;
        }
    }

	@Override
	public long countPagesByUserId(int userId) throws ServiceException {
		if (!Validator.validateInt(userId)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
		}

        long numOfOrders = orderDAO.countByUserId(userId);
        if (numOfOrders % ORDERS_AMOUNT_ON_PAGE == 0) {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE + 1;
        }
	}

	@Override
	public long countPagesByFilmId(int filmId) throws ServiceException {
		if (!Validator.validateInt(filmId)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_FILM_ID);
		}
        long numOfOrders = orderDAO.countByFilmId(filmId);
        if (numOfOrders % ORDERS_AMOUNT_ON_PAGE == 0) {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE;
        }
        else {
            return numOfOrders / ORDERS_AMOUNT_ON_PAGE + 1;
        }
	}
}
