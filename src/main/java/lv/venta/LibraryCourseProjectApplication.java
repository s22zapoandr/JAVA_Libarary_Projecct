package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lv.venta.model.Author;
import lv.venta.model.Book;
import lv.venta.model.Condition;
import lv.venta.model.Genre;
import lv.venta.model.LibraryDepartment;
import lv.venta.model.Rarity;
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
            Author author1 = new Author("Joanne", "Rowling", Genre.Fantasy);
            Author author2 = new Author("John", "Tolkien", Genre.Fantasy);
            authorRepo.save(author1);
            authorRepo.save(author2);

            Book book1 = new Book("Harry Potter and the Philosopher's Stone", author1, 9, Condition.Good, Rarity.Common , 25, 1997);
            Book book2 = new Book("The Hobbit", author2, 8, Condition.Moderate, Rarity.Rare , 13, 1937);
            bookRepo.save(book1);
            bookRepo.save(book2);
        };
    }
}
