package by.epam.naumovich.film_ordering.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes a review entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class Review {

    private int author;
    private int filmId;
    private String type;
    private int mark;
    private String text;
    private Date date;
    private Time time;

}
