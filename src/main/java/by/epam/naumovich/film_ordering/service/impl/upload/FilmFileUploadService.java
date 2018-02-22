package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileNotUploadedException;
import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmFileUploadService;
import by.epam.naumovich.film_ordering.service.IFilmService;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.FILM_IMGS_UPLOAD_DIR;

@Slf4j
@Service
public class FilmFileUploadService implements IFilmFileUploadService {

    private final IFilmService filmService;

    @Autowired
    public FilmFileUploadService(IFilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public int storeFilesAndAddFilm(HttpServletRequest request) throws Exception {
        String name = null;
        String year = null;
        String director = null;
        String cast = null;
        String[] countriesArray = null;
        List<String> countries = new ArrayList<>();
        String composer = null;
        String[] genresArray = null;
        List<String> genres = new ArrayList<>();
        String length = null;
        String price = null;
        String description = null;
        FileItem posterItem = null;
        FileItem frameItem = null;

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
            List<FileItem> items = fileUpload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.FOLDER:
                            posterItem = item;
                            break;
                        case RequestAndSessionAttributes.FRAME:
                            frameItem = item;
                            break;
                        default:
                            break;
                    }

                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NAME:
                            name = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.YEAR:
                            year = item.getString();
                            break;
                        case RequestAndSessionAttributes.DIRECTOR:
                            director = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.CAST:
                            cast = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.COUNTRY:
                            countries.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.COMPOSER:
                            composer = item.getString();
                            break;
                        case RequestAndSessionAttributes.GENRE:
                            genres.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.LENGTH:
                            length = item.getString();
                            break;
                        case RequestAndSessionAttributes.PRICE:
                            price = item.getString();
                            break;
                        case RequestAndSessionAttributes.DESCRIPTION:
                            description = item.getString(FileUploadConstants.UTF_8);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        if (!countries.isEmpty()) {
            countriesArray = new String[countries.size()];
            countriesArray = countries.toArray(countriesArray);
        }
        if (!genres.isEmpty()) {
            genresArray = new String[genres.size()];
            genresArray = genres.toArray(genresArray);
        }

        int filmID = filmService.create(name, year, director, cast, countriesArray, composer,
                genresArray, length, price, description);

        HttpSession session = request.getSession(true);
        proceedFilmImages(filmID, session, posterItem, frameItem); //TODO: use Spring for file upload!
        log.debug(String.format(LogMessages.FILM_CREATED, name, filmID));
        return filmID;
    }

    @Override
    public void storeFilesAndUpdateFilm(int filmId, HttpServletRequest request) throws Exception {
        String name = null;
        String year = null;
        String director = null;
        String cast = null;
        String[] countriesArray = null;
        List<String> countries = new ArrayList<>();
        String composer = null;
        String[] genresArray = null;
        List<String> genres = new ArrayList<>();
        String length = null;
        String price = null;
        String description = null;
        FileItem posterItem = null;
        FileItem frameItem = null;
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
            factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
            List<FileItem> items = fileUpload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.FOLDER:
                            posterItem = item;
                            break;
                        case RequestAndSessionAttributes.FRAME:
                            frameItem = item;
                            break;
                    }

                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NAME:
                            name = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.YEAR:
                            year = item.getString();
                            break;
                        case RequestAndSessionAttributes.DIRECTOR:
                            director = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.CAST:
                            cast = item.getString(FileUploadConstants.UTF_8);
                            break;
                        case RequestAndSessionAttributes.COUNTRY:
                            countries.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.COMPOSER:
                            composer = item.getString();
                            break;
                        case RequestAndSessionAttributes.GENRE:
                            genres.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.LENGTH:
                            length = item.getString();
                            break;
                        case RequestAndSessionAttributes.PRICE:
                            price = item.getString();
                            break;
                        case RequestAndSessionAttributes.DESCRIPTION:
                            description = item.getString(FileUploadConstants.UTF_8);
                            break;
                    }
                }
            }
        }
        if (!countries.isEmpty()) {
            countriesArray = new String[countries.size()];
            countriesArray = countries.toArray(countriesArray);
        }
        if (!genres.isEmpty()) {
            genresArray = new String[genres.size()];
            genresArray = genres.toArray(genresArray);
        }
        filmService.update(filmId, name, year, director, cast, countriesArray, composer, genresArray,
                length, price, description);

        HttpSession session = request.getSession(true);

        if (posterItem != null) {
            writeFileItem(session, filmId, posterItem, FileUploadConstants.POSTER_FILE_NAME);
        }
        if (frameItem != null) {
            writeFileItem(session, filmId, frameItem, FileUploadConstants.FRAME_FILE_NAME);
        }

        log.debug(String.format(LogMessages.FILM_UPDATED, name, filmId));
    }

    private void proceedFilmImages(int filmID, HttpSession session, FileItem posterItem, FileItem frameItem)
            throws FileNotUploadedException {

        String absoluteFilePath = session.getServletContext()
                .getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR + filmID + "/");
        if (absoluteFilePath != null) {
            new File(absoluteFilePath).mkdir();
        }

        if (posterItem != null) {
            loadImage(posterItem, FileUploadConstants.POSTER_FILE_NAME, absoluteFilePath);
        }

        if (frameItem != null) {
            loadImage(frameItem, FileUploadConstants.FRAME_FILE_NAME, absoluteFilePath);
        }
    }

    private void loadImage(FileItem imgItem, String fileName, String absoluteFilePath) throws FileNotUploadedException {
        String systemFileName = new File(fileName).getName();
        File frame = new File(absoluteFilePath, systemFileName);
        try {
            imgItem.write(frame);
        } catch (Exception e) {
            throw new FileNotUploadedException(ErrorMessages.FILE_NOT_UPLOADED, e);
        }
    }

    private void writeFileItem(HttpSession session, int filmID, FileItem item, String fileName) throws Exception {
        String frameFileName = new File(fileName).getName();
        String absoluteFilePath = session.getServletContext().getRealPath(FILM_IMGS_UPLOAD_DIR);
        File image = new File(absoluteFilePath, frameFileName);

        item.write(image);

        String absTargetFilePath = session.getServletContext().getRealPath(FILM_IMGS_UPLOAD_DIR + filmID + "/");
        File targetImg = new File(absTargetFilePath, frameFileName);

        Files.move(image.toPath(), targetImg.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
}
