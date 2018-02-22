package by.epam.naumovich.film_ordering.service.impl.upload;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.service.IFilmFileUploadService;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.util.FileUploader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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


import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.FILM_IMGS_UPLOAD_DIR;
import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.FRAME_FILE_NAME;
import static by.epam.naumovich.film_ordering.command.util.FileUploadConstants.POSTER_FILE_NAME;

@Slf4j
@Service
public class FilmFileUploadService implements IFilmFileUploadService {

    private final IFilmService filmService;
    private final ServletFileUpload servletFileUpload;

    @Autowired
    public FilmFileUploadService(IFilmService filmService, ServletFileUpload servletFileUpload) {
        this.filmService = filmService;
        this.servletFileUpload = servletFileUpload;
    }

    @Override
    public int storeFilesAndAddFilm(HttpServletRequest request, HttpSession session) throws Exception {
        FilmDTO filmDTO = parseMultipartRequestForFilm(request);

        int filmID = filmService.create(filmDTO.getName(), filmDTO.getYear(), filmDTO.getDirector(), filmDTO.getCast(),
                filmDTO.getCountriesArray(), filmDTO.getComposer(), filmDTO.getGenresArray(), filmDTO.getLength(),
                filmDTO.getPrice(), filmDTO.getDescription());

        FileUploader.loadImage(session, filmDTO.getPosterItem(), POSTER_FILE_NAME, FILM_IMGS_UPLOAD_DIR + filmID + "/", true);
        FileUploader.loadImage(session, filmDTO.getFrameItem(), FRAME_FILE_NAME, FILM_IMGS_UPLOAD_DIR + filmID + "/", true);

        log.debug(String.format(LogMessages.FILM_CREATED, filmDTO.getName(), filmID));
        return filmID;
    }

    @Override
    public void storeFilesAndUpdateFilm(int filmId, HttpServletRequest request, HttpSession session) throws Exception {
        FilmDTO filmDTO = parseMultipartRequestForFilm(request);

        filmService.update(filmId, filmDTO.getName(), filmDTO.getYear(), filmDTO.getDirector(), filmDTO.getCast(),
                filmDTO.getCountriesArray(), filmDTO.getComposer(), filmDTO.getGenresArray(), filmDTO.getLength(),
                filmDTO.getPrice(), filmDTO.getDescription());

        FileUploader.writeFileItem(session, filmId, filmDTO.getPosterItem(), POSTER_FILE_NAME, FILM_IMGS_UPLOAD_DIR);
        FileUploader.writeFileItem(session, filmId, filmDTO.getFrameItem(), FRAME_FILE_NAME, FILM_IMGS_UPLOAD_DIR);

        log.debug(String.format(LogMessages.FILM_UPDATED, filmDTO.getName(), filmId));
    }

    private FilmDTO parseMultipartRequestForFilm(HttpServletRequest request)
            throws FileUploadException, UnsupportedEncodingException {

        FilmDTO filmDTO = new FilmDTO();
        List<String> countries = new ArrayList<>();
        List<String> genres = new ArrayList<>();

        if (ServletFileUpload.isMultipartContent(request)) {
            List<FileItem> items = servletFileUpload.parseRequest(request);

            for (FileItem item : items) {
                if (!item.isFormField() && item.getName() != null && !item.getName().isEmpty()) {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.FOLDER:
                            filmDTO.setPosterItem(item);
                            break;
                        case RequestAndSessionAttributes.FRAME:
                            filmDTO.setFrameItem(item);
                            break;
                    }
                } else {
                    switch (item.getFieldName()) {
                        case RequestAndSessionAttributes.NAME:
                            filmDTO.setName(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.YEAR:
                            filmDTO.setYear(item.getString());
                            break;
                        case RequestAndSessionAttributes.DIRECTOR:
                            filmDTO.setDirector(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.CAST:
                            filmDTO.setCast(item.getString(FileUploadConstants.UTF_8));
                            break;
                        case RequestAndSessionAttributes.COUNTRY:
                            countries.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.COMPOSER:
                            filmDTO.setComposer(item.getString());
                            break;
                        case RequestAndSessionAttributes.GENRE:
                            genres.add(item.getString());
                            break;
                        case RequestAndSessionAttributes.LENGTH:
                            filmDTO.setLength(item.getString());
                            break;
                        case RequestAndSessionAttributes.PRICE:
                            filmDTO.setPrice(item.getString());
                            break;
                        case RequestAndSessionAttributes.DESCRIPTION:
                            filmDTO.setDescription(item.getString(FileUploadConstants.UTF_8));
                            break;
                    }
                }
            }
        }

        if (!countries.isEmpty()) {
            String[] countriesArray = new String[countries.size()];
            filmDTO.setCountriesArray(countries.toArray(countriesArray));
        }
        if (!genres.isEmpty()) {
            String[] genresArray = new String[genres.size()];
            filmDTO.setGenresArray(genres.toArray(genresArray));
        }
        return filmDTO;
    }

    @Data
    private class FilmDTO {
        private String name;
        private String year;
        private String director;
        private String cast;
        private String[] countriesArray;
        private String composer;
        private String[] genresArray;
        private String length;
        private String price;
        private String description;
        private FileItem posterItem;
        private FileItem frameItem;
    }
}
