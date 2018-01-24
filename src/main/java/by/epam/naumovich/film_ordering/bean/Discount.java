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
 * This bean class describes a discount entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Entity
@Table(name = "discounts")
@Data
@EqualsAndHashCode
@ToString
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "d_id")
    private int id;
    @Column(name = "d_user")
    private int userID;
    @Column(name = "d_amount")
    private int amount;
    @Column(name = "d_stdate")
    private Date stDate;
    @Column(name = "d_sttime")
    private Time stTime;
    @Column(name = "d_endate")
    private Date enDate;
    @Column(name = "d_entime")
    private Time enTime;

}

