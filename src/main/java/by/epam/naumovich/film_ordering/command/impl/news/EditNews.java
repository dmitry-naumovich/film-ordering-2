package by.epam.naumovich.film_ordering.command.impl.news;

import by.epam.naumovich.film_ordering.command.Command;
import by.epam.naumovich.film_ordering.command.util.ErrorMessages;
import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import by.epam.naumovich.film_ordering.command.util.JavaServerPageNames;
import by.epam.naumovich.film_ordering.command.util.LogMessages;
import by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes;
import by.epam.naumovich.film_ordering.command.util.SuccessMessages;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.EditNewsServiceException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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


import static by.epam.naumovich.film_ordering.command.util.RequestAndSessionAttributes.ERROR_MESSAGE;

/**
 * Performs the command that reads updated news parameters from the JSP and sends them to the relevant service class.
 * Uploads updated images to the required directories.
 * Checks the access rights of the user who is performing this action.
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Slf4j
public class EditNews implements Command {

	private final INewsService newsService;

	public EditNews(INewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException, ServletException, ServiceException {

		setPrevQueryAttributeToSession(request, session, log);

		int newsID = Integer.parseInt(request.getParameter(RequestAndSessionAttributes.NEWS_ID));
		if (!isAuthorized(session) || !isAdmin(session)) {
			request.setAttribute(ERROR_MESSAGE, ErrorMessages.EDIT_NEWS_RESTRICTION);
			request.getRequestDispatcher("/Controller?command=open_single_news&newsID=" + newsID)
					.forward(request, response);
		}
		else {
			String title = null;
			String text = null;
			FileItem imgItem = null;
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
				
				log.debug(String.format(LogMessages.NEWS_UPDATED, newsID));
				request.setAttribute(RequestAndSessionAttributes.SUCCESS_MESSAGE, SuccessMessages.NEWS_EDITED);
				request.getRequestDispatcher("/Controller?command=open_single_news&newsID=" + newsID)
                        .forward(request, response);
			} catch (EditNewsServiceException e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher("/Controller?command=open_news_edit_page&newsID=" + newsID)
                        .forward(request, response);
			} catch (Exception e) {
				log.error(String.format(LogMessages.EXCEPTION_IN_COMMAND,
                        e.getClass().getSimpleName(), this.getClass().getSimpleName(), e.getMessage()), e);
				request.setAttribute(ERROR_MESSAGE, e.getMessage());
				request.getRequestDispatcher(JavaServerPageNames.ERROR_PAGE).forward(request, response);
			}
		}
	}
}
