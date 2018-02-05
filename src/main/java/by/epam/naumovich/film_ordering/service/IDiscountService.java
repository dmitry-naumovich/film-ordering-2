package by.epam.naumovich.film_ordering.service;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;

/**
 * Defines methods that receive parameters from Command implementations, verify them, construct necessary entities if needed
 * and then pass them to the DAO layer, possibly getting some objects or primitive values back and passing them further back to the commands.
 * These methods operate with the Discount entity.
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
public interface IDiscountService {

    /**
     * Constructs a new discount entity based on input parameters received from the Controller layer, verifies them and either
     * passes to the DAO layer or throws an exception
     *
     * @param userID user ID
     * @param amount discount amount
     * @param endDate discount end date
     * @param endTime discount end time
     * @throws ServiceException
     */
    void create(int userID, String amount, String endDate, String endTime) throws ServiceException;

    /**
     * Constructs an updated discount entity based on input parameters received from the Controller layer, verifies them
     * and either passes to the DAO layer or throws an exception
     *
     * @param discountID discount ID
     * @param amount discount amount
     * @param endDate discount end date
     * @param endTime discount end time
     * @throws ServiceException
     */
    void update(int discountID, String amount, String endDate, String endTime) throws ServiceException;

    /**
     * Verifies the input parameter and passes it to the DAO layer, receives the Discount entity and passes it to the Controller
     * or throws an exception if it equals null
     *
     * @param id user ID
     * @return found discount object
     * @throws ServiceException
     */
    Discount getCurrentUserDiscountByID(int id) throws ServiceException;

    /**
     * Verifies the input parameter and either passes it to the DAO layer or throws an exception
     *
     * @param id ID of the discount that will be deleted
     * @throws ServiceException
     */
    void delete(int id) throws ServiceException;
}
