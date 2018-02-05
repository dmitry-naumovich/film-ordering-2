package by.epam.naumovich.film_ordering.dao.impl;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.dao.IDiscountDAO;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests DAO layer methods overridden in MySQLDiscountDAO class in a way of comparing expected and actual results with the help of JUnit 4 framework.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 *
 */
public class MySQLDiscountDAOTest {

    private IDiscountDAO dao;

    /**
     * This object will be compared to the actual object taken from the DAO layer.
     *
     */
    private Discount expectedDiscount;

    /**
     * Executes every time before a single method call and initializes expected Discount object.
     *
     */
    @Before
    public void initTestDiscount() {
        expectedDiscount = new Discount();
        expectedDiscount.setUserID(2);
        expectedDiscount.setAmount(10);
        expectedDiscount.setStDate(Date.valueOf(LocalDate.now()));
        expectedDiscount.setStTime(Time.valueOf(LocalTime.now()));
        expectedDiscount.setEnDate(Date.valueOf("2020-01-01"));
        expectedDiscount.setEnTime(Time.valueOf(LocalTime.now()));
    }

    /**
     * Adds expectedDiscount to the data source via DAO layer, gets it back by user ID and compares two results.
     * Tests if valid discount entity is returned by user ID.
     */
    @Test
    public void getCurrentUserDiscountByID() throws InterruptedException {


        dao.save(expectedDiscount);
        Thread.sleep(1000);
        Discount actualDiscount = dao.findDiscountByUserId(expectedDiscount.getUserID()).get(0);
        dao.delete(actualDiscount.getId());

        Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
        Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
        Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
        Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
        Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
        Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());

    }

    /**
     * Adds expectedDiscount to the data source via DAO layer, gets it back by user ID and compares two results.
     * Tests if the discount was correctly added.
     */
//	@Test
//	public void addDiscount() throws InterruptedException {
//
//
//		int discountID = dao.save(expectedDiscount).getId();
//		Thread.sleep(1000);
//		Discount actualDiscount = dao.getDiscountByID(discountID);
//		dao.delete(discountID);
//
//		Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
//		Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
//		Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
//		Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
//		Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
//		Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());
//	}

    /**
     * Adds expectedDiscount to the data source via DAO layer, edits it, gets it back and compares two results.
     * Tests if the discount was correctly edited.
     */
//	@Test
//	public void editDiscount() throws InterruptedException {
//
//
//		int discountID = dao.save(expectedDiscount).getId();
//		Thread.sleep(1000);
//		expectedDiscount.setAmount(15);
//		expectedDiscount.setStTime(expectedUser.getRegTime());
//		expectedDiscount.setId(discountID);
//		dao.updateDiscount(expectedDiscount);
//		Thread.sleep(1000);
//		Discount actualDiscount = dao.getDiscountByID(discountID);
//		dao.delete(discountID);
//
//		Assert.assertEquals(expectedDiscount.getAmount(), actualDiscount.getAmount());
//		Assert.assertEquals(expectedDiscount.getUserID(), actualDiscount.getUserID());
//		Assert.assertEquals(expectedDiscount.getStDate(), actualDiscount.getStDate());
//		Assert.assertEquals(expectedDiscount.getStTime(), actualDiscount.getStTime());
//		Assert.assertEquals(expectedDiscount.getEnDate(), actualDiscount.getEnDate());
//		Assert.assertEquals(expectedDiscount.getEnTime(), actualDiscount.getEnTime());
//	}

    /**
     * Adds expectedDiscount to the data source via DAO layer, deletes it and then tries to get it back expecting the null result.
     * Tests if the discount was correctly deleted.
     */
//	@Test
//	public void delete() {
//
//
//		int discountID = dao.save(expectedDiscount).getId();
//		dao.delete(discountID);
//		Discount actualDiscount = dao.getDiscountByID(discountID);
//
//		Assert.assertNull(actualDiscount);
//	}


}
