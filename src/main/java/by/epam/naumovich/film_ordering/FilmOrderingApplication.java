package by.epam.naumovich.film_ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
// excluded so that the application uses commons-fileupload instead of Servlet 3 Multipart support //todo: tmp
@EnableAutoConfiguration(exclude = MultipartAutoConfiguration.class)
public class FilmOrderingApplication extends WebMvcConfigurerAdapter {

    /**
     * Forwards base url ("/") request to index page
     *
     * @param registry view controller registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.jsp");
    }

    /**
     * Spring Boot Application's main method
     *
     * @param args command-line args
     */
    public static void main(String[] args) {
        SpringApplication.run(FilmOrderingApplication.class, args);
    }
}
