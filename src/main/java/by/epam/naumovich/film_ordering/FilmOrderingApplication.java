package by.epam.naumovich.film_ordering;

import by.epam.naumovich.film_ordering.controller.filter.CharsetFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class FilmOrderingApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.jsp");
    }

    @Bean
    public FilterRegistrationBean charsetFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CharsetFilter());
        registration.setEnabled(true);
        registration.addInitParameter("characterEncoding", "UTF-8");
        registration.setName("charsetFilter");
        registration.addUrlPatterns("/Controller");
        registration.setOrder(1);
        return registration;
    }

//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionAttributeListener> simpleSessionAttributeListenerRegistrationBean() {
//        ServletListenerRegistrationBean<HttpSessionAttributeListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new SimpleSessionAttributeListener());
//        return bean;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionListener> simpleSessionListenerRegistrationBean() {
//        ServletListenerRegistrationBean<HttpSessionListener> bean = new ServletListenerRegistrationBean<>();
//        bean.setListener(new SimpleSessionListener());
//        return bean;
//    }

    public static void main(String[] args) {
        SpringApplication.run(FilmOrderingApplication.class, args);
    }
}
