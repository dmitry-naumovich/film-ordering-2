package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;

public interface IFilmFileUploadService {

    int storeFilesAndAddFilm(HttpServletRequest request) throws Exception;

    void storeFilesAndUpdateFilm(HttpServletRequest request) throws Exception;
}
