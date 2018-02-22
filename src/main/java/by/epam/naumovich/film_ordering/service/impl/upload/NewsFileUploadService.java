package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsFileUploadService;
import by.epam.naumovich.film_ordering.service.INewsService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.LogMessages.EXCEPTION_IN_COMMAND;
import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

@Slf4j
@Service
public class NewsFileUploadService implements INewsFileUploadService {

    private final INewsService newsService;

    @Autowired
    public NewsFileUploadService(INewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public int storeFilesAndAddNews(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);

        String title = null;
        String text = null;
        FileItem imgItem = null;
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
                    if (item.getName() != null && !item.getName().isEmpty()) {
                        imgItem = item;
                        break;
                    }
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NEWS_TITLE:
                            title = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.NEWS_TEXT:
                            text = item.getString(FileUploadConstants.UTF_8);
                            break;
                    }
                }
            }
        }

        int newsID = newsService.create(title, text);

        String absoluteFilePath = session.getServletContext()
                .getRealPath(FileUploadConstants.NEWS_IMGS_UPLOAD_DIR + newsID + "/");
        if (absoluteFilePath != null) {
            new File(absoluteFilePath).mkdir();
        }

        if (imgItem != null) {
            try {
                String fileName = new File(FileUploadConstants.NEWS_IMG_FILE_NAME).getName();
                File image = new File(absoluteFilePath, fileName);
                imgItem.write(image);
            } catch (IOException e) {
                log.error(String.format(EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
            }
        }
        return newsID;

    }

    @Override
    public void storeFilesAndUpdateNews(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(true);
        int newsID = Integer.parseInt(request.getAttribute("entityId").toString());

        String title = null;
        String text = null;
        FileItem imgItem = null;
        if (ServletFileUpload.isMultipartContent(request)) {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
            List<FileItem> items = fileUpload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = iter.next();
                if (!item.isFormField()) {
                    if (item.getName() != null && !item.getName().isEmpty()) {
                        imgItem = item;
                        break;
                    }
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NEWS_TITLE:
                            title = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.NEWS_TEXT:
                            text = item.getString(FileUploadConstants.UTF_8);
                            break;
                    }
                }
            }
        }

        newsService.update(newsID, title, text);

        if (imgItem != null) {
            try {
                String fileName = new File(FileUploadConstants.NEWS_IMG_FILE_NAME).getName();
                String absoluteFilePath = session.getServletContext()
                        .getRealPath(FileUploadConstants.NEWS_IMGS_UPLOAD_DIR);
                File image = new File(absoluteFilePath, fileName);
                imgItem.write(image);

                String absTargetFilePath = session.getServletContext()
                        .getRealPath(FileUploadConstants.NEWS_IMGS_UPLOAD_DIR + newsID + "/");
                File targetImg = new File(absTargetFilePath, fileName);

                Files.move(image.toPath(), targetImg.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
                request.setAttribute(ERROR_MESSAGE, e.getMessage());
            }
        }
    }
}
