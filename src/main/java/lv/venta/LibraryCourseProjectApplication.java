package lv.venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;

@SpringBootApplication
public class LibraryCourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryCourseProjectApplication.class, args);
	}
	@Configuration
	public class AppConfig {

	    @Bean
	    public LibraryDepartment libraryDepartment() {
	        // Default specialization, adjust as necessary
	        return new LibraryDepartment(Genre.Fantasy);
	    }
	}
}
