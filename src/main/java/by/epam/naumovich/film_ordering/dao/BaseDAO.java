package by.epam.naumovich.film_ordering.dao;

import by.epam.naumovich.film_ordering.dao.exception.DAOException;
import java.util.List;

public interface BaseDAO<T> {

    int create(T entity) throws DAOException;

    void update(int id, T entity) throws DAOException;

    T getById(int id) throws DAOException;

    List<T> getAll() throws DAOException;

    void delete(int id) throws DAOException;
}
