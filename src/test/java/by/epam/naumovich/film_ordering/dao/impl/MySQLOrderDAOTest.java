package by.epam.naumovich.film_ordering.dao.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import by.epam.naumovich.film_ordering.bean.Order;
import by.epam.naumovich.film_ordering.dao.IOrderDAO;
import by.epam.naumovich.film_ordering.dao.exception.DAOException;

/**
 * Tests DAO layer methods overridden in MySQLOrderDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLOrderDAOTest {

	private IOrderDAO dao;

	/**
	 * Database type used in this test suite.
	 * 
	 */
	private static final String MYSQL = "mysql";
	/**
	 * This object will be compared to the actual object taken from the DAO layer.
	 * 
	 */
	private Order expectedOrder;
	
	/**
	 * Executes every time before a single method call and initializes expected Order object.
	 * 
	 */
	@Before
	public void initTestOrder() {
		expectedOrder = new Order();
		expectedOrder.setUserId(1);
		expectedOrder.setFilmId(5);
		expectedOrder.setDate(Date.valueOf(LocalDate.now()));
		expectedOrder.setTime(Time.valueOf(LocalTime.now()));
		expectedOrder.setPrice(10.0f);
		expectedOrder.setDiscount(10);
		expectedOrder.setPayment(9.0f);
	}
	
	/**
	 * Adds expectedOrder to the data source via DAO layer, gets it back and compares two results.
	 * Tests if the order was correctly added.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void addOrder() throws DAOException {
		int orderNum = dao.save(expectedOrder).getOrdNum();
        Order actualOrder = dao.findOne(orderNum);
        dao.delete(orderNum);

        Assert.assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        Assert.assertEquals(expectedOrder.getFilmId(), actualOrder.getFilmId());
        Assert.assertEquals(expectedOrder.getDate(), actualOrder.getDate());
        Assert.assertEquals(expectedOrder.getTime(), actualOrder.getTime());
        Assert.assertEquals(expectedOrder.getPrice(), actualOrder.getPrice(), 0.00f);
        Assert.assertEquals(expectedOrder.getDiscount(), actualOrder.getDiscount());
        Assert.assertEquals(expectedOrder.getPayment(), actualOrder.getPayment(), 0.00f);	
	}
	
	/**
	 * Adds expectedOrder to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
	 * Tests if the order was correctly deleted.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void deleteOrder() throws DAOException {
		int orderNum = dao.save(expectedOrder).getOrdNum();
        dao.delete(orderNum);
        Order actualOrder = dao.findOne(orderNum);
		
        Assert.assertNull(actualOrder);
	}
	
	/**
	 * Adds expectedOrder to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if the valid order entity is returned by orderNum.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrderByOrderNum() throws DAOException {
		int orderNum = dao.save(expectedOrder).getOrdNum();
        Order actualOrder = dao.findOne(orderNum);
        dao.delete(orderNum);
        
        Assert.assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        Assert.assertEquals(expectedOrder.getFilmId(), actualOrder.getFilmId());
        Assert.assertEquals(expectedOrder.getDate(), actualOrder.getDate());
        Assert.assertEquals(expectedOrder.getTime(), actualOrder.getTime());
        Assert.assertEquals(expectedOrder.getPrice(), actualOrder.getPrice(), 0.00f);
        Assert.assertEquals(expectedOrder.getDiscount(), actualOrder.getDiscount());
        Assert.assertEquals(expectedOrder.getPayment(), actualOrder.getPayment(), 0.00f);	
	}
	
	/**
	 * Adds expectedOrder to the data source via DAO layer, gets it back by ID and compares two results.
	 * Tests if the valid order entity is returned by user and film IDs.
	 * Then tests if null object is returned again by same IDs after it deletion.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrderByUserAndFilmId() throws DAOException {
		int orderNum = dao.save(expectedOrder).getOrdNum();
        Order actualOrder = dao.findByUserIdAndFilmId(expectedOrder.getUserId(), expectedOrder.getFilmId());
        dao.delete(orderNum);
        
        Assert.assertEquals(expectedOrder.getUserId(), actualOrder.getUserId());
        Assert.assertEquals(expectedOrder.getFilmId(), actualOrder.getFilmId());
        Assert.assertEquals(expectedOrder.getDate(), actualOrder.getDate());
        Assert.assertEquals(expectedOrder.getTime(), actualOrder.getTime());
        Assert.assertEquals(expectedOrder.getPrice(), actualOrder.getPrice(), 0.00f);
        Assert.assertEquals(expectedOrder.getDiscount(), actualOrder.getDiscount());
        Assert.assertEquals(expectedOrder.getPayment(), actualOrder.getPayment(), 0.00f);	
        
        Order nullOrder = dao.findByUserIdAndFilmId(expectedOrder.getUserId(), expectedOrder.getFilmId());
        Assert.assertNull(nullOrder);
	}
	
	/**
	 * Gets orders by user ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrdersByUserId() throws DAOException {
		List<Order> userOrders1 = dao.findByUserIdOrderByDateDescTimeDesc(1);
		List<Order> userOrders2 = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getUserId() == 1) {
				userOrders2.add(o);
			}
		}
		
		Assert.assertEquals(userOrders1, userOrders2);	
	}
	
	/**
	 * Gets orders part by user ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrdersPartByUserId() throws DAOException {
		List<Order> userOrders1 = dao.findPartByUserId(1, 0, 3);
		List<Order> userOrders2 = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getUserId() == 1) {
				userOrders2.add(o);
			}
		}
		List<Order> list = new ArrayList<>(userOrders2);
		userOrders2 = new ArrayList<>(list.subList(0, 3));
		
		Assert.assertEquals(userOrders1, userOrders2);	
	}
	
	/**
	 * Gets orders by film ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrdersByFilmId() throws DAOException {
		List<Order> filmOrders1 = dao.findByFilmIdOrderByDateDescTimeDesc(1);
		List<Order> filmOrders2 = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getFilmId() == 1) {
				filmOrders2.add(o);
			}
		}
		
		Assert.assertEquals(filmOrders1, filmOrders2);	
	}
	
	/**
	 * Gets orders part by film ID in two different ways and compares results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getOrdersPartByFilmId() throws DAOException {
		List<Order> filmOrders1 = dao.findPartByFilmId(1, 0, 3);
		List<Order> filmOrders2 = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getFilmId() == 1) {
				filmOrders2.add(o);
			}
		}
		List<Order> list = new ArrayList<>(filmOrders2);
		filmOrders2 = new ArrayList<>(list.subList(0, 3));
		
		Assert.assertEquals(filmOrders1, filmOrders2);	
	}
	
	/**
	 * Gets the amount of orders in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfOrders() throws DAOException {
		int ordersNum1 = (int)dao.count();
		List<Order> allOrders = dao.findAllByOrderByDateDescTimeDesc();
		int ordersNum2 = allOrders.size();
		
		Assert.assertEquals(ordersNum1, ordersNum2);
	}
	
	/**
	 * Gets the part of all orders from the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getAllOrdersPart() throws DAOException {
		List<Order> particularOrders1 = dao.findAllPart(0, 6);
		List<Order> allOrders = new ArrayList<>(dao.findAllByOrderByDateDescTimeDesc());
		List<Order> particularOrders2 = new ArrayList<>(allOrders.subList(0, 6));
		
		Assert.assertEquals(particularOrders1, particularOrders2);	
	}
	
	/**
	 * Gets the amount of user orders in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfUserOrders() throws DAOException {
		int ordersNum1 = (int)dao.countByUserId(1);
		List<Order> userOrders = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getUserId() == 1) {
				userOrders.add(o);
			}
		}
		int ordersNum2 = userOrders.size();
		
		Assert.assertEquals(ordersNum1, ordersNum2);
	}
	
	/**
	 * Gets the amount of film orders in the data source in two different ways and compares the results which must be equal.
	 * 
	 * @throws DAOException
	 */
	@Test
	public void getNumberOfFilmOrders() throws DAOException {
		int ordersNum1 = (int)dao.countByFilmId(1);
		List<Order> filmOrders = new ArrayList<>();
		for (Order o : dao.findAllByOrderByDateDescTimeDesc()) {
			if (o.getFilmId() == 1) {
				filmOrders.add(o);
			}
		}
		int ordersNum2 = filmOrders.size();
		
		Assert.assertEquals(ordersNum1, ordersNum2);
	}
	
}
