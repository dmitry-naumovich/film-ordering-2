package by.epam.naumovich.film_ordering.bean;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes news entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Data
public class News {

    private int id;
    private Date date;
    private Time time;
    private String title;
    private String text;

    @Override
    public int hashCode() {
        int hash = 11;
        hash = 7 * hash + id;
        hash = 47 * hash + ((null != date) ? date.hashCode() : 0);
        hash = 1 * hash + ((null != time) ? time.hashCode() : 0);
        hash = 17 * hash + ((null != title) ? title.hashCode() : 0);
        hash = 31 * hash + ((null != text) ? text.hashCode() : 0);
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

        News news = (News) obj;
        if (id != news.id) {
            return false;
        }
        if ((null == date) ? (news.date != null) : !date.equals(news.date)) {
            return false;
        }
        if ((null == time) ? (news.time != null) : !time.equals(news.time)) {
            return false;
        }
        if ((null == title) ? (news.title != null) : !title.equals(news.title)) {
            return false;
        }
        if ((null == text) ? (news.text != null) : !text.equals(news.text)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(this.getClass().getSimpleName() + " Object {");
        result.append(" ID: " + id);
        result.append(", Date: " + date);
        result.append(", Time: " + time);
        result.append(", Title: " + title);
        result.append(", Text: " + text);
        result.append("}");

        return result.toString();
    }


}
