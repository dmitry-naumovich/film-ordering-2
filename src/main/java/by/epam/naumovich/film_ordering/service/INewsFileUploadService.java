package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;

public interface INewsFileUploadService {

    int storeFilesAndAddNews(HttpServletRequest request) throws Exception;

    void storeFilesAndUpdateNews(HttpServletRequest request) throws Exception;
}
