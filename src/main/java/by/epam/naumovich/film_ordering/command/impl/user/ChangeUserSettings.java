package by.epam.naumovich.film_ordering.command.impl.user;

import java.io.File;
import java.io.IOException;
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
import by.epam.naumovich.film_ordering.command.util.QueryUtil;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.UserUpdateServiceException;

/**
 * Performs the command that reads updated user parameters from the JSP and sends them to the relevant service class.
 * Uploads updated images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class ChangeUserSettings implements Command {

	private final IUserService userService;

	public ChangeUserSettings(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(RequestAndSessionAttributes.PREV_QUERY, query);
		System.out.println(query);
		
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) == null) {
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.SIGN_IN_FOR_SETTINGS);
			request.getRequestDispatcher(JavaServerPageNames.LOGIN_PAGE).forward(request, response);
		}
		else {
			int userID = (int)session.getAttribute(RequestAndSessionAttributes.USER_ID);
			String name = null;
			String surname = null;
			String pwd = null;
			String sex = null;
			String bDate = null;
			String phone = null;
			String email = null;
			String about = null;
			
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
				        if (!item.isFormField()) {
				        	String fileName = new File(FileUploadConstants.AVATAR_FILE_NAME_TEMPLATE + userID + FileUploadConstants.AVATAR_FILE_EXTENSION).getName(); 
			                String absoluteFilePath = session.getServletContext().getRealPath(FileUploadConstants.AVATARS_UPLOAD_DIR);
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
				log.debug(String.format(LogMessages.USER_SETTINGS_EDITED, userID));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.SETTINGS_UPDATED);
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
			} catch (UserUpdateServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_user_settings&userID=" + userID).forward(request, response);
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
