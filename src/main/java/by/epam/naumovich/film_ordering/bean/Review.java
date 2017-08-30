package by.epam.naumovich.film_ordering.bean;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes a review entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
public class Review {

    private int author;
    private int filmId;
    private String type;
    private int mark;
    private String text;
    private Date date;
    private Time time;

    @Override
    public int hashCode() {
        int hash = 47;
        hash = 7 * hash + author;
        hash = 11 * hash + filmId;
        hash = 19 * hash + mark;
        hash = 17 * hash + (date != null ? date.hashCode() : 0);
        hash = 3 * hash + (time != null ? time.hashCode() : 0);
        hash = 13 * hash + (type != null ? type.hashCode() : 0);
        hash = 31 * hash + (text != null ? text.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        Review rev = (Review) obj;
        if (author != rev.author) {
            return false;
        }
        if (filmId != rev.filmId) {
            return false;
        }
        if (mark != rev.mark) {
            return false;
        }

        if ((null == date) ? (rev.date != null) : !date.equals(rev.date)) {
            return false;
        }
        if ((null == time) ? (rev.time != null) : !time.equals(rev.time)) {
            return false;
        }
        if ((null == type) ? (rev.type != null) : !type.equals(rev.type)) {
            return false;
        }
        if ((null == text) ? (rev.text != null) : !text.equals(rev.text)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(this.getClass().getSimpleName() + " Object {");
        result.append(" Author: " + author);
        result.append(", FilmId: " + filmId);
        result.append(", Date: " + date);
        result.append(", Time: " + time);
        result.append(", Type: " + type);
        result.append(", Mark: " + mark);
        result.append(", Text: " + text);
        result.append("}");

        return result.toString();
    }


}
