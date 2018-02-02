package by.epam.naumovich.film_ordering.service.impl;

import by.epam.naumovich.film_ordering.service.IDiscountService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.DiscountServiceException;
import org.junit.Test;

/**
 * Tests service layer methods overridden in DiscountServiceImpl class in a way of passing invalid parameters into service methods
 * and expecting exceptions on the output with the help of JUnit 4 framework.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class DiscountServiceImplTest {

    private IDiscountService service;

    /**
     * Tries to get current user discount by the zero ID value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=ServiceException.class)
    public void getCurrentUserDiscountByID() throws ServiceException {
        service.getCurrentUserDiscountByID(0);
    }

    /**
     * Tries to add user discount with the invalid amount value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=DiscountServiceException.class)
    public void addDiscountWithInvalidAmount() throws ServiceException {
        service.addDiscount(1, "105", "2100-01-01", "15:00:00");
    }


    /**
     * Tries to add user discount with the invalid end date value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=DiscountServiceException.class)
    public void addDiscountWithInvalidEndDate() throws ServiceException {
        service.addDiscount(1, "10", "2000-01-01", "15:00:00");
    }

    /**
     * Tries to add user discount with the invalid end time value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=DiscountServiceException.class)
    public void addDiscountWithInvalidEndTime() throws ServiceException {
        service.addDiscount(1, "10", "2100-01-01", null);
    }

    /**
     * Tries to edit user discount by the invalid discount ID value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=DiscountServiceException.class)
    public void editDiscount() throws ServiceException {
        service.editDiscount(0, "10", "2100-01-01", "15:00:00");
    }

    /**
     * Tries to delete the discount by the invalid ID value and expects for the exception.
     *
     * @throws ServiceException
     */
    @Test(expected=DiscountServiceException.class)
    public void deleteDiscount() throws ServiceException {
        service.deleteDiscount(0);
    }


}
