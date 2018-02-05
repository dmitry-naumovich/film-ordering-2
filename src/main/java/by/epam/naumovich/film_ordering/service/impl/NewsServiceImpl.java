package by.epam.naumovich.film_ordering.service.impl;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import by.epam.naumovich.film_ordering.bean.News;
import by.epam.naumovich.film_ordering.dao.INewsDAO;
import by.epam.naumovich.film_ordering.service.INewsService;
import by.epam.naumovich.film_ordering.service.exception.ServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.AddNewsServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.EditNewsServiceException;
import by.epam.naumovich.film_ordering.service.exception.news.GetNewsServiceException;
import by.epam.naumovich.film_ordering.service.util.ExceptionMessages;
import by.epam.naumovich.film_ordering.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * INewsService interface implementation that works with INewsDAO implementation
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
@Service
public class NewsServiceImpl implements INewsService {

	private static final int NEWS_AMOUNT_ON_PAGE = 8;
	
	private final INewsDAO newsDAO;

	@Autowired
    public NewsServiceImpl(INewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    @Override
	public int create(String title, String text) throws ServiceException {
		if (!Validator.validateStrings(title, text)) {
			throw new AddNewsServiceException(ExceptionMessages.INVALID_NEWS_TITLE_OR_TEXT);
		}
		News news = new News();
		news.setTitle(title);
		news.setText(text);
		news.setDate(Date.valueOf(LocalDate.now()));
		news.setTime(Time.valueOf(LocalTime.now()));

        News createdNews = newsDAO.save(news);
        if (createdNews == null) {
            throw new AddNewsServiceException(ExceptionMessages.NEWS_NOT_ADDED);
        }
		
		return createdNews.getId();
	}

	@Override
	public void delete(int id) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_NEWS_ID);
		}
        newsDAO.delete(id);
	}


	@Override
	public void update(int id, String title, String text) throws ServiceException {
		if (!Validator.validateInt(id)) {
			throw new ServiceException(ExceptionMessages.CORRUPTED_NEWS_ID);
		}
		else if (!Validator.validateStrings(title, text)) {
			throw new EditNewsServiceException(ExceptionMessages.INVALID_NEWS_TITLE_OR_TEXT);
		}

        News existingNews = newsDAO.findOne(id);
        if (existingNews == null) {
            throw new EditNewsServiceException(ExceptionMessages.NEWS_NOT_PRESENT);
        }

		News news = new News();
		news.setId(id);
        news.setDate(existingNews.getDate());
        news.setTime(existingNews.getTime());
        news.setTitle(title);
        news.setText(text);
        newsDAO.save(news);
	}

	@Override
	public List<News> getByYear(int year) throws ServiceException {
		List<News> news = newsDAO.findByYear(year);
        if (news.isEmpty()) {
            throw new GetNewsServiceException(String.format(ExceptionMessages.NO_NEWS_WITHIN_YEAR, year));
        }
		return news;
	}

	@Override
	public List<News> getByMonth(int month, int year) throws ServiceException {
		List<News> set = newsDAO.findByMonthAndYear(month, year);
        if (set.isEmpty()) {
            throw new GetNewsServiceException(String.format(ExceptionMessages.NO_NEWS_WITHIN_MONTH, month, year));
        }
		return set;
	}

	@Override
	public List<News> getFourLastNews() throws ServiceException {
		List<News> news = newsDAO.findAllByOrderByDateDescTimeDesc();
        if (news.isEmpty()) {
            throw new GetNewsServiceException(ExceptionMessages.NO_NEWS_IN_DB);
        }
		return news.subList(0, 4);
	}

	@Override
	public News getById(int id) throws ServiceException {
		News news = newsDAO.findOne(id);
        if (news == null) {
            throw new GetNewsServiceException(ExceptionMessages.NEWS_NOT_PRESENT);
        }
		return news;
	}

	@Override
	public List<News> getAllPart(int pageNum) throws ServiceException {
		if (!Validator.validateInt(pageNum)) {
			throw new GetNewsServiceException(ExceptionMessages.CORRUPTED_PAGE_NUM);
		}
		int start = (pageNum - 1) * NEWS_AMOUNT_ON_PAGE;

        List<News> news = newsDAO.findAllPart(start, NEWS_AMOUNT_ON_PAGE);
        if (news == null) {
            throw new GetNewsServiceException(ExceptionMessages.NO_NEWS_IN_DB);
        }
        return news;
	}

	@Override
	public long countPages() {
        long numOfNews = newsDAO.count();
        if (numOfNews % NEWS_AMOUNT_ON_PAGE == 0) {
            return numOfNews / NEWS_AMOUNT_ON_PAGE;
        } else {
            return numOfNews / NEWS_AMOUNT_ON_PAGE + 1;
        }
    }
}
