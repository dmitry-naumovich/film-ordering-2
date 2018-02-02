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

import by.epam.naumovich.film_ordering.bean.User;
import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.IUserService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.user.ServiceSignUpException;

/**
 * Performs the command that reads new user parameters from the JSP and sends them to the relevant service class.
 * Uploads images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class SignUp implements Command {

	private final IUserService userService;

	public SignUp(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(RequestAndSessionAttributes.AUTHORIZED_USER) != null) {
			int userID = Integer.parseInt(session.getAttribute(RequestAndSessionAttributes.USER_ID).toString());
			request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, ErrorMessages.LOG_OUT_FOR_SIGN_UP);
			request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
		} else {
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
				
				
				int userID = userService.addUser(login, name, surname, pwd, sex, bDate, phone, email, about);
				
				if (avatarItem != null) {
					try {
						String fileName = new File(FileUploadConstants.AVATAR_FILE_NAME_TEMPLATE + userID + FileUploadConstants.AVATAR_FILE_EXTENSION).getName(); 
			        	String absoluteFilePath = session.getServletContext().getRealPath(FileUploadConstants.AVATARS_UPLOAD_DIR);
		                File avatar = new File(absoluteFilePath, fileName);
		                avatarItem.write(avatar);
					} catch (IOException e) {
						log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
						request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
					}
				}
				
				
				User user = userService.getUserByLogin(login);
				session.setAttribute(RequestAndSessionAttributes.AUTHORIZED_USER, login);
				session.setAttribute(RequestAndSessionAttributes.USER_ID, user.getId());
				session.setAttribute(RequestAndSessionAttributes.IS_ADMIN, 'a' == user.getType());
				log.debug(String.format(LogMessages.USER_REGISTRATED, login, userID));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.SUCCESSFUL_SIGN_UP);
				request.getRequestDispatcher("/Controller?command=open_user_profile&userID=" + userID).forward(request, response);
			} catch (ServiceSignUpException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND, e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(RequestAndSessionAttributes.ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_sign_up_page").forward(request, response);
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
