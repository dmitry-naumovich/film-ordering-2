package by.epam.naumovich.film_ordering.command.impl.film;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileNotUploadedException;
import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.AddFilmServiceException;

/**
 * Performs the command that reads new film parameters from the JSP and sends them to the relevant service class.
 * Uploads images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class AddFilm implements Command {

    private final IFilmService filmService;

    public AddFilm(IFilmService filmService) {
        this.filmService = filmService;
    }

    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
			request.getRequestDispatcher(JavaServerPageNames.LOGINATION_PAGE).forward(request, response);
		}
		else if (!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.ADD_FILM_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_all_films&pageNum=1").forward(request, response);
		}
		else {				
			try {
				int filmID = proceedAddFilmRequest(request, session);
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.FILM_ADDED);
				request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1").forward(request, response);
			} catch (AddFilmServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.FILM_ADDING_PAGE).forward(request, response);
			} catch (Exception e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}

	}
	
	private int proceedAddFilmRequest(HttpServletRequest request, HttpSession session) throws FileUploadException, 
															UnsupportedEncodingException, ServiceException, FileNotUploadedException {
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
			    ServletFileUpload  fileUpload = new ServletFileUpload(factory);
			    fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
			    List<FileItem> items = fileUpload.parseRequest(request);
			    Iterator<FileItem> iter = items.iterator();
			    
			    while (iter.hasNext()) {
			        FileItem item = iter.next();
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

			int filmID = filmService.addNewFilm(name, year, director, cast, countriesArray, composer,
					genresArray, length, price, description);
			
			//proceedFilmImages(filmID, session, posterItem, frameItem); //TODO: return file upload using Spring!
			log.debug(String.format(LogMessages.FILM_ADDED, name, filmID));
			return filmID;
	}
	
	private void proceedFilmImages(int filmID, HttpSession session, FileItem posterItem, FileItem frameItem) throws FileNotUploadedException {
		
		String absoluteFilePath = session.getServletContext().getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR + filmID + "/");
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

}
