package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.repo.IAuthorRepo;
import lv.venta.repo.IBookRepo;

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

    @Bean
    public CommandLineRunner dataInitializer(IAuthorRepo authorRepo, IBookRepo bookRepo) {
        return args -> {
            Author author1 = new Author("J.K. Rowling");
            Author author2 = new Author("J.R.R. Tolkien");
            authorRepo.save(author1);
            authorRepo.save(author2);

            Book book1 = new Book("Harry Potter and the Philosopher's Stone", author1, Genre.Fantasy);
            Book book2 = new Book("The Hobbit", author2, Genre.Fantasy);
            bookRepo.save(book1);
            bookRepo.save(book2);
        };
    }
}
