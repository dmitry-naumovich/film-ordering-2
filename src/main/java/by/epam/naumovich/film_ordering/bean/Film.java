package by.epam.naumovich.film_ordering.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This bean class describes a film entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class Film {

    private int id;
    private String name;
    private int year;
    private String director;
    private String country;
    private String genre;
    private String actors;
    private String composer;
    private String description;
    private int length;
    private float rating;
    private float price;

}
