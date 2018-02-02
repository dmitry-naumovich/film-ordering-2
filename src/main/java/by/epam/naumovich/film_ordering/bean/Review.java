package by.epam.naumovich.film_ordering.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Entity
@Table(name = "reviews")
@Data
@EqualsAndHashCode
@ToString
public class Review {

    public Review() {
        id = new ReviewPK();
    }

    @EmbeddedId
    private ReviewPK id;

    @Column(name = "r_type")
    private String type;
    @Column(name = "r_mark")
    private int mark;
    @Column(name = "r_text")
    private String text;
    @Column(name = "r_date")
    private Date date;
    @Column(name = "r_time")
    private Time time;

    public int getAuthor() {
        return id.getAuthor();
    }

    public int getFilmId() {
        return id.getFilmId();
    }

    public void setAuthor(int author) {
        id.setAuthor(author);
    }

    public void setFilmId(int filmId) {
        id.setFilmId(filmId);
    }
}
