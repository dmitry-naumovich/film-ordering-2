package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface INewsFileUploadService {

    int storeFilesAndAddNews(HttpServletRequest request, HttpSession session) throws Exception;

    void storeFilesAndUpdateNews(int newsId, HttpServletRequest request, HttpSession session) throws Exception;
}
