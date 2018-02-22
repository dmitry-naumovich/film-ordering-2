package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.data.util.Pair;

public interface IUserFileUploadService {

    Pair<Integer, String> storeFilesAndAddUser(HttpServletRequest request, HttpSession session) throws Exception;

    void storeFilesAndUpdateUser(int userId, HttpServletRequest request, HttpSession session) throws Exception;
}
