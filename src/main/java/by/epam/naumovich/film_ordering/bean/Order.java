package by.epam.naumovich.film_ordering.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes an order entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class Order {

    private int ordNum;
    private int userId;
    private int filmId;
    private Date date;
    private Time time;
    private float price;
    private int discount;
    private float payment;

}
