package by.epam.naumovich.film_ordering.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes an order entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "o_num")
    private int ordNum;
    @Column(name = "o_user")
    private int userId;
    @Column(name = "o_film")
    private int filmId;
    @Column(name = "o_date")
    private Date date;
    @Column(name = "o_time")
    private Time time;
    @Column(name = "o_fprice")
    private float price;
    @Column(name = "o_discount")
    private int discount;
    @Column(name = "o_paym")
    private float payment;

}
