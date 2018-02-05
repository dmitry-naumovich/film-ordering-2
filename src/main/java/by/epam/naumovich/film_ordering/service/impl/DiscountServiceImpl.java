package by.epam.naumovich.film_ordering.service.impl;

import by.epam.naumovich.film_ordering.bean.Discount;
import by.epam.naumovich.film_ordering.dao.IDiscountDAO;
import by.epam.naumovich.film_ordering.service.IDiscountService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.DiscountServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.GetDiscountServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements IDiscountService {

    private final IDiscountDAO discountDAO;

    @Autowired
    public DiscountServiceImpl(IDiscountDAO discountDAO) {
        this.discountDAO = discountDAO;
    }

    @Override
    public void create(int userID, String amount, String endDate, String endTime) throws ServiceException {
        if (!Validator.validateInt(userID)) {
            throw new DiscountServiceException(ExceptionMessages.CORRUPTED_USER_ID);
        }

        if (!Validator.validateStrings(amount, endDate, endTime)) {
            throw new DiscountServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
        }
        Discount discount = new Discount();
        discount.setUserID(userID);
        discount = validateAndSetAmount(discount, amount);
        discount = validateAndSetEndDateTime(discount, endDate, endTime);
        discountDAO.save(discount);
    }

    @Override
    public void update(int discountID, String amount, String endDate, String endTime) throws ServiceException {
        if (!Validator.validateInt(discountID)) {
            throw new DiscountServiceException(ExceptionMessages.CORRUPTED_DISCOUNT_ID);
        }

        if (!Validator.validateStrings(amount, endDate, endTime)) {
            throw new DiscountServiceException(ExceptionMessages.CORRUPTED_INPUT_PARAMETERS);
        }
        Discount d = discountDAO.findOne(discountID);
        if (d == null) {
            throw new DiscountServiceException(ExceptionMessages.DISCOUNT_NOT_FOUND);
        }
        Discount discount = new Discount();
        discount.setId(discountID);
        discount.setUserID(d.getUserID());
        discount = validateAndSetAmount(discount, amount);
        discount = validateAndSetEndDateTime(discount, endDate, endTime);
        discountDAO.save(discount);
    }

    @Override
    public Discount getCurrentUserDiscountByID(int id) throws ServiceException {
        if (!Validator.validateInt(id)){
            throw new ServiceException(ExceptionMessages.CORRUPTED_USER_ID);
        }

        List<Discount> discounts = discountDAO.findDiscountByUserId(id);
        if (discounts.isEmpty()) {
            throw new GetDiscountServiceException(ExceptionMessages.DISCOUNT_NOT_FOUND);
        }
        return discounts.get(0);
    }

    @Override
    public void delete(int discountID) throws ServiceException {
        if (!Validator.validateInt(discountID)) {
            throw new DiscountServiceException(ExceptionMessages.CORRUPTED_DISCOUNT_ID);
        }
        discountDAO.delete(discountID);
    }


    private Discount validateAndSetAmount(Discount discount, String amount) throws ServiceException {
        try {
            int dAmount = Integer.parseInt(amount);
            if (dAmount <= 0 || dAmount > 100) {
                throw new DiscountServiceException(ExceptionMessages.INVALID_DISCOUNT_AMOUNT);
            }
            discount.setAmount(dAmount);

        } catch (NumberFormatException e) {
            throw new DiscountServiceException(ExceptionMessages.INVALID_DISCOUNT_AMOUNT);
        }
        return discount;
    }

    private Discount validateAndSetEndDateTime(Discount discount, String endDate, String endTime) throws ServiceException {
        try {
            Date currentDate = Date.valueOf(LocalDate.now());
            Date discountEndDate = Date.valueOf(endDate);
            Time currentTime = Time.valueOf(LocalTime.now());
            Time discountEndTime = Time.valueOf(endTime);

            if (currentDate.after(discountEndDate) || (currentDate.equals(discountEndDate) && currentTime.after(discountEndTime))) {
                throw new DiscountServiceException(ExceptionMessages.INVALID_DISCOUNT_END);
            }
            discount.setEnDate(Date.valueOf(endDate));
            discount.setEnTime(Time.valueOf(endTime));
            discount.setStDate(currentDate);
            discount.setStTime(currentTime);
        } catch (IllegalArgumentException e) {
            throw new DiscountServiceException(ExceptionMessages.DISCOUNT_DATE_TIME_RIGHT_FORMAT);
        }
        return discount;
    }
}
