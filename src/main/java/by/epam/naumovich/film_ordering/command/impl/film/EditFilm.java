package by.epam.naumovich.film_ordering.command.impl.film;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IFilmService;
import by.epam.naumovich.film_ordering.service.ServiceFactory;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.film.EditFilmServiceException;

/**
 * Performs the command that reads updated film parameters from the JSP and sends them to the relevant service class.
 * Uploads updated images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class EditFilm implements Command {


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		
		int filmID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.FILM_ID));
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null |
				!Boolean.parseBoolean(session.getAttribute(RequestAndSessionAttributes.IS_ADMIN).toString())) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.EDIT_FILM_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1").forward(request, response);
		}
		else {
			String name = null;
			String year = null;
			String director = null;
			String cast = null;
			String[] countriesArray = null;
			List<String> countries = new ArrayList<String>();
			String composer = null;
			String[] genresArray = null;
			List<String> genres = new ArrayList<String>();
			String length = null;
			String price = null;
			String description = null;
			FileItem posterItem = null;
			FileItem frameItem = null;
			try {
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
				IFilmService filmService = ServiceFactory.getInstance().getFilmService();
				filmService.editFilm(filmID, name, year, director, cast, countriesArray, composer, genresArray, length, price, description);
				
				if (posterItem != null) {
					try {
						String folderFileName = new File(FileUploadConstants.POSTER_FILE_NAME).getName();
						String absoluteFilePath = session.getServletContext().getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR);
						File image = new File(absoluteFilePath, folderFileName);
						posterItem.write(image);
						
						String absTargetFilePath = session.getServletContext().getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR + filmID + "/");
		        		File targetImg = new File(absTargetFilePath, folderFileName);
		        		
					    Files.move(image.toPath(), targetImg.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
						request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
					}
				}
				if (frameItem != null) {
					try {
						String frameFileName = new File(FileUploadConstants.FRAME_FILE_NAME).getName();
						String absoluteFilePath = session.getServletContext().getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR);
						File image = new File(absoluteFilePath, frameFileName);
						frameItem.write(image);
						
						String absTargetFilePath = session.getServletContext().getRealPath(FileUploadConstants.FILM_IMGS_UPLOAD_DIR + filmID + "/");
		        		File targetImg = new File(absTargetFilePath, frameFileName);
		        		
					    Files.move(image.toPath(), targetImg.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
						request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
					}
				}
				
				log.debug(String.format(LogMessages.FILM_EDITED, name, filmID));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.FILM_EDITED);
				request.getRequestDispatcher("/Controller?command=open_single_film&filmID=" + filmID + "&pageNum=1").forward(request, response);
			} catch (EditFilmServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_film_edit_page&filmID=" + filmID).forward(request, response);
			} catch (ServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			} catch (Exception e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
