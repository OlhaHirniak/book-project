package mate.academy.bookproject;

import java.math.BigDecimal;
import mate.academy.bookproject.model.Book;
import mate.academy.bookproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookProjectApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication
                .run(BookProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book gameOfThrones = new Book();
                gameOfThrones.setTitle("GameOfThrones");
                gameOfThrones.setPrice(BigDecimal.valueOf(100));
                gameOfThrones.setIsbn("978-0-000-0000");
                gameOfThrones.setAuthor("George R.R. Martin");
                gameOfThrones.setDescription("About control of the Iron Throne in Westeros");
                gameOfThrones.setCoverImage("Iron Throne cover image");

                bookService.save(gameOfThrones);
                System.out.println(bookService.findAll());
            }
        };
    }
}

