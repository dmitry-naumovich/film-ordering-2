package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.INewsFileUploadService;
import by.epam.naumovich.film_ordering.service.INewsService;
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
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.NEWS_IMGS_UPLOAD_DIR;
import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.NEWS_IMG_FILE_NAME;

@Slf4j
@Service
public class NewsFileUploadService implements INewsFileUploadService {

    private final INewsService newsService;
    private final ServletFileUpload servletFileUpload;

    @Autowired
    public NewsFileUploadService(INewsService newsService, ServletFileUpload servletFileUpload) {
        this.newsService = newsService;
        this.servletFileUpload = servletFileUpload;
    }

    @Override
    public int storeFilesAndAddNews(HttpServletRequest request, HttpSession session) throws Exception {
        NewsDTO news = parseMultipartRequestForNews(request);
        int newsID = newsService.create(news.getTitle(), news.getText());
        FileUploader.loadImage(session, news.getFileItem(), NEWS_IMG_FILE_NAME, NEWS_IMGS_UPLOAD_DIR + newsID + "/", true);
        return newsID;
    }

    @Override
    public void storeFilesAndUpdateNews(int newsId, HttpServletRequest request, HttpSession session) throws Exception {
        NewsDTO news = parseMultipartRequestForNews(request);
        newsService.update(newsId, news.title, news.text);
        FileUploader.writeFileItem(session, newsId, news.fileItem, NEWS_IMG_FILE_NAME, NEWS_IMGS_UPLOAD_DIR);
    }

    private NewsDTO parseMultipartRequestForNews(HttpServletRequest request)
            throws FileUploadException, UnsupportedEncodingException {

        NewsDTO news = new NewsDTO();

        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> items = servletFileUpload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    if (item.getName() != null && !item.getName().isEmpty()) {
                        news.setFileItem(item);
                        break;
                    }
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NEWS_TITLE:
                            news.setTitle(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.NEWS_TEXT:
                            news.setText(item.getString(FileUploadConstants.UTF_8));
                            break;
                    }
                }
            }
        }
        return news;
    }

    @Data
    private class NewsDTO {
        private String title;
        private String text;
        private FileItem fileItem;
    }
}
