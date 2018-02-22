package by.epam.naumovich.film_ordering.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.util.Pair;

public interface IUserFileUploadService {

    Pair<Integer, String> storeFilesAndAddUser(HttpServletRequest request) throws Exception;

    void storeFilesAndUpdateUser(int userId, HttpServletRequest request) throws Exception;
}
