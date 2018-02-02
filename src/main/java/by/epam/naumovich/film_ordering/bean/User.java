package by.epam.naumovich.film_ordering.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes an user entity
 *
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private int id;
    @Column(name = "u_login")
    private String login;
    @Column(name = "u_name")
    private String name;
    @Column(name = "u_surname")
    private String surname;
    @Column(name = "u_passw")
    private String password;
    @Column(name = "u_sex")
    private char sex;
    @Column(name = "u_type")
    private char type;
    @Column(name = "u_regdate")
    private Date regDate;
    @Column(name = "u_regtime")
    private Time regTime;
    @Column(name = "u_bdate")
    private Date birthDate;
    @Column(name = "u_phone")
    private String phone;
    @Column(name = "u_email")
    private String email;
    @Column(name = "u_about")
    private String about;

}
