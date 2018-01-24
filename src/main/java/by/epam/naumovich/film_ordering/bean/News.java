package by.epam.naumovich.film_ordering.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;
import org.springframework.data.annotation.Id;

/**
 * This bean class describes news entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Entity
@Table(name = "news")
@Data
@EqualsAndHashCode
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "n_id")
    private int id;
    @Column(name = "n_date")
    private Date date;
    @Column(name = "n_time")
    private Time time;
    @Column(name = "n_title")
    private String title;
    @Column(name = "n_text")
    private String text;

}
