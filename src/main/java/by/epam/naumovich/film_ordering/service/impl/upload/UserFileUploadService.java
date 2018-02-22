package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserFileUploadService;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.util.FileUploader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.AVATARS_UPLOAD_DIR;
import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.AVATAR_FILE_EXTENSION;
import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.AVATAR_FILE_NAME_TEMPLATE;

@Slf4j
@Service
public class UserFileUploadService implements IUserFileUploadService {

    private final IUserService userService;
    private final ServletFileUpload servletFileUpload;

    @Autowired
    public UserFileUploadService(IUserService userService, ServletFileUpload servletFileUpload) {
        this.userService = userService;
        this.servletFileUpload = servletFileUpload;
    }

    @Override
    public Pair<Integer, String> storeFilesAndAddUser(HttpServletRequest request, HttpSession session) throws Exception {
        UserDTO userDTO = parseMultipartRequestForUser(request);

        int userId = userService.create(userDTO.getLogin(), userDTO.getName(), userDTO.getSurname(), userDTO.getPwd(),
                userDTO.getSex(), userDTO.getBDate(), userDTO.getPhone(), userDTO.getEmail(), userDTO.getAbout());

        FileUploader.loadImage(session, userDTO.getAvatarItem(), buildAvatarFileName(userId), AVATARS_UPLOAD_DIR, false);

        return Pair.of(userId, userDTO.getLogin());
    }

    @Override
    public void storeFilesAndUpdateUser(int userId, HttpServletRequest request, HttpSession session) throws Exception {
        UserDTO userDTO = parseMultipartRequestForUser(request);

        FileUploader.loadImage(session, userDTO.getAvatarItem(), buildAvatarFileName(userId), AVATARS_UPLOAD_DIR, false);

        userService.update(userId, userDTO.getName(), userDTO.getSurname(), userDTO.getPwd(), userDTO.getSex(),
                userDTO.getBDate(), userDTO.getPhone(), userDTO.getEmail(), userDTO.getAbout());
    }

    private UserDTO parseMultipartRequestForUser(HttpServletRequest request) throws FileUploadException,
            UnsupportedEncodingException {

        UserDTO userDTO = new UserDTO();

        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> items = servletFileUpload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                    userDTO.setAvatarItem(item);
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.LOGIN:
                            userDTO.setLogin(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.NAME:
                            userDTO.setName(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.SURNAME:
                            userDTO.setSurname(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.PASSWORD:
                            userDTO.setPwd(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.SEX:
                            userDTO.setSex(item.getString());
                            break;
                        case RequestAndSessionAttributes.BDATE:
                            userDTO.setBDate(item.getString());
                            break;
                        case RequestAndSessionAttributes.PHONE:
                            userDTO.setPhone(item.getString());
                            break;
                        case RequestAndSessionAttributes.EMAIL:
                            userDTO.setEmail(item.getString());
                            break;
                        case RequestAndSessionAttributes.ABOUT:
                            userDTO.setAbout(item.getString(FileUploadConstants.UTF_8));
                            break;
                    }
                }
            }
        }
        return userDTO;
    }

    private String buildAvatarFileName(int userId) {
        return AVATAR_FILE_NAME_TEMPLATE + userId + AVATAR_FILE_EXTENSION;
    }

    @Data
    private class UserDTO {
        private String login;
        private String name;
        private String surname;
        private String pwd;
        private String sex;
        private String bDate;
        private String phone;
        private String email;
        private String about;
        private FileItem avatarItem;
    }
}
