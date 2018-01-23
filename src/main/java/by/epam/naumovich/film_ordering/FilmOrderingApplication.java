package by.epam.naumovich.film_ordering;

import by.epam.naumovich.film_ordering.controller.Controller;
import by.epam.naumovich.film_ordering.controller.filter.CharsetFilter;
import by.epam.naumovich.film_ordering.controller.listener.SimpleSessionAttributeListener;
import by.epam.naumovich.film_ordering.controller.listener.SimpleSessionListener;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FilmOrderingApplication {

    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharsetFilter());
        registration.setEnabled(true);
        registration.addInitParameter("characterEncoding", "UTF-8");
        registration.setName("charsetFilter");
        registration.addUrlPatterns("/controller");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new Controller(), "/controller");
    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionAttributeListener> simpleSessionAttributeListenerRegistrationBean() {
//        ServletListenerRegistrationBean<HttpSessionAttributeListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new SimpleSessionAttributeListener());
//        return bean;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionListener> simleSessionListenerRegistrationBean() {
//        ServletListenerRegistrationBean<HttpSessionListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new SimpleSessionListener());
//        return bean;
//    }

    public static void main(String[] args) {
        SpringApplication.run(FilmOrderingApplication.class, args);
    }
}
