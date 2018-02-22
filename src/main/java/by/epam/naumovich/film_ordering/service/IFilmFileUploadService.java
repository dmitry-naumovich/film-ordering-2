package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface IFilmFileUploadService {

    int storeFilesAndAddFilm(HttpServletRequest request, HttpSession session) throws Exception;

    void storeFilesAndUpdateFilm(int filmId, HttpServletRequest request, HttpSession session) throws Exception;
}