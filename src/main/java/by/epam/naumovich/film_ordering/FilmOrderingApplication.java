package by.epam.naumovich.film_ordering;

import by.epam.naumovich.film_ordering.command.util.FileUploadConstants;
import java.io.File;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
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
     * This spring-bean method initializes and sets up a ServletFileUpload bean for uploading files in service methods
     *
     * @return created ServletFileUpload Spring bean
     */
    @Bean
    public ServletFileUpload servletFileUpload() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(FileUploadConstants.MAX_MEMORY_SIZE);
        factory.setRepository(new File(System.getProperty(FileUploadConstants.REPOSITORY)));
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setSizeMax(FileUploadConstants.MAX_REQUEST_SIZE);
        return fileUpload;
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
