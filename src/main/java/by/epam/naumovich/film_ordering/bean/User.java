package by.epam.naumovich.film_ordering.bean;

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
@Data
@EqualsAndHashCode
@ToString
public class User {

    private int id;
    private String login;
    private String name;
    private String surname;
    private String password;
    private char sex;
    private char type;
    private Date regDate;
    private Time regTime;
    private Date birthDate;
    private String phone;
    private String email;
    private String about;

}
