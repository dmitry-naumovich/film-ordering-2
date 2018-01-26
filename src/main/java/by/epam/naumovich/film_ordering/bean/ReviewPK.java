package by.epam.naumovich.film_ordering.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ReviewPK implements Serializable {

    @Column(name = "r_author")
    private int author;
    @Column(name = "r_film")
    private int filmId;
}
