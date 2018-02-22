package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IUserFileUploadService;
import by.epam.naumovich.film_ordering.service.IUserService;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

@Slf4j
@Service
public class UserFileUploadService implements IUserFileUploadService {

    private final IUserService userService;

    @Autowired
    public UserFileUploadService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public Pair<Integer, String> storeFilesAndAddUser(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);

        String login = null;
        String name = null;
        String surname = null;
        String pwd = null;
        String sex = null;
        String bDate = null;
        String phone = null;
        String email = null;
        String about = null;
        FileItem avatarItem = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
            ServletFileUpload  fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
            List<FileItem> items = fileUpload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                    avatarItem = item;
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.LOGIN:
                            login = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.NAME:
                            name = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.SURNAME:
                            surname = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.PASSWORD:
                            pwd = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.SEX:
                            sex = item.getString();
                            break;
                        case RequestAndSessionAttributes.BDATE:
                            bDate = item.getString();
                            break;
                        case RequestAndSessionAttributes.PHONE:
                            phone = item.getString();
                            break;
                        case RequestAndSessionAttributes.EMAIL:
                            email = item.getString();
                            break;
                        case RequestAndSessionAttributes.ABOUT:
                            about = item.getString(FileUploadConstants.UTF_8);
                            break;
                    }
                }
            }
        }


        int userID = userService.create(login, name, surname, pwd, sex, bDate, phone, email, about);

        if (avatarItem != null) {
            try {
                String fileName = new File(FileUploadConstants.AVATAR_FILE_NAME_TEMPLATE + userID +
                        FileUploadConstants.AVATAR_FILE_EXTENSION).getName();
                String absoluteFilePath = session.getServletContext()
                        .getRealPath(FileUploadConstants.AVATARS_UPLOAD_DIR);
                File avatar = new File(absoluteFilePath, fileName);
                avatarItem.write(avatar);
            } catch (IOException e) {
                log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
            }
        }

        return Pair.of(userID, login);
    }

    @Override
    public void storeFilesAndUpdateUser(int userID, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);

        String name = null;
        String surname = null;
        String pwd = null;
        String sex = null;
        String bDate = null;
        String phone = null;
        String email = null;
        String about = null;

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
            ServletFileUpload  fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
            List<FileItem> items = fileUpload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField()) {
                    String fileName = new File(FileUploadConstants.AVATAR_FILE_NAME_TEMPLATE
                            + userID + FileUploadConstants.AVATAR_FILE_EXTENSION).getName();
                    String absoluteFilePath = session.getServletContext()
                            .getRealPath(FileUploadConstants.AVATARS_UPLOAD_DIR);
                    File uploadedFile = new File(absoluteFilePath, fileName);

                    item.write(uploadedFile);
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NAME:
                            name = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.SURNAME:
                            surname = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.PASSWORD:
                            pwd = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.SEX:
                            sex = item.getString();
                            break;
                        case RequestAndSessionAttributes.BDATE:
                            bDate = item.getString();
                            break;
                        case RequestAndSessionAttributes.PHONE:
                            phone = item.getString();
                            break;
                        case RequestAndSessionAttributes.EMAIL:
                            email = item.getString();
                            break;
                        case RequestAndSessionAttributes.ABOUT:
                            about = item.getString(FileUploadConstants.UTF_8);
                            break;
                    }
                }
            }
        }

        userService.update(userID, name, surname, pwd, sex, bDate, phone, email, about);
    }
}
