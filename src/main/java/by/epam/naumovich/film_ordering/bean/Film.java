package by.epam.naumovich.film_ordering.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * This bean class describes a film entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Entity
@Table(name = "films")
@Data
@EqualsAndHashCode
@ToString
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "f_id")
    private int id;
    @Column(name = "f_name")
    private String name;
    @Column(name = "f_year")
    private int year;
    @Column(name = "f_direct")
    private String director;
    @Column(name = "f_country")
    private String country;
    @Column(name = "f_genre")
    private String genre;
    @Column(name = "f_actors")
    private String actors;
    @Column(name = "f_composer")
    private String composer;
    @Column(name = "f_description")
    private String description;
    @Column(name = "f_length")
    private int length;
    @Column(name = "f_rating")
    private float rating;
    @Column(name = "f_price")
    private float price;

}
