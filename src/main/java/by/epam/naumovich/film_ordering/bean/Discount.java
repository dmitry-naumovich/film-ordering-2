package by.epam.naumovich.film_ordering.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes a discount entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class Discount {

    private int id;
    private int userID;
    private int amount;
    private Date stDate;
    private Time stTime;
    private Date enDate;
    private Time enTime;

}

