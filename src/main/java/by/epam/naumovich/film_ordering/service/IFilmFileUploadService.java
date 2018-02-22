package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;

public interface IFilmFileUploadService {

    int storeFilesAndAddFilm(HttpServletRequest request) throws Exception;

    void storeFilesAndUpdateFilm(int filmId, HttpServletRequest request) throws Exception;
}
