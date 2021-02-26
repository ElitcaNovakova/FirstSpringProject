package bg.startit.spring.firstspringproject.repository;

import bg.startit.spring.firstspringproject.model.Book;
import bg.startit.spring.firstspringproject.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, Long> {

  // Repositories идват със един куп вградени методи, но ние можем да добавим собствени,
  // които изпълняват специфични за нашето приложение заявки към базата данни.

  // Докато в relation DB се използва SQL, в Spring Data Repositories
  // използваме специални ключови думи, като част от името на метода:
  // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

  // да намерим всички книги, взети от този потребител
  List<Book> findByUser(User user);

  // търсене по пълно име на автор: "Петър Берон"
  List<Book> findByAuthor(String author);
  List<Book> findByAuthorIs(String author);
  List<Book> findByAuthorEquals(String author);

  // не е "Петър Берон"
  List<Book> findByAuthorNot(String author);

  // търсене по подстринг: "Петър", "Берон", "тър"
  List<Book> findByAuthorContains(String authorSubstring);

  // търсене по подстринг без малки/големи букви: "петър"
  List<Book> findByAuthorContainsIgnoreCase(String authorSubstring);

  // всички книги от Петър Берон и Александър Дюма
  // repository.findByAuthorIn(List.of("Петър Берон", "Александър Дюма"))
  List<Book> findByAuthorIn(List<String> names);

  // всички available книги
  List<Book> findByAvailableTrue();
  List<Book> findByAvailable(boolean available);

  // Like e подобно на Regex
  // % - The percent sign represents zero, one, or multiple characters
  //_ - The underscore represents a single character
  // LIKE %петър% -> ...... петър ......
  // LIKE %петър -> ....... петър
  // LIKE п_тър -> пзтър
  List<Book> findByAuthorLike(String likeSyntax);


  // с цена по-голяма от дадената
  List<Book> findByPriceGreaterThan(float price);

  // с цена по-голяма от зададената И сортирано по цена
  List<Book> findByPriceGreaterThanOrderByPrice(float price);

  // всички книги на Петър Берон, които са available?
  List<Book> findByAuthorAndAvailableTrue(String author);
  List<Book> findByAuthorAndAvailable(String author, boolean available);

  // също като горе, но няма да върне всички книги, а ще ги върне
  // на страници
  // Pageable има: page number, page size, page offset ... критерии за сортиране
  Page<Book> findByAuthorAndAvailableTrue(String author, Pageable pageable);

  // PageRequest.of(1, 100, Sort.by(Direction.DESC, "author", "price"));
  // ^ имплементира Pageable
  // Дай ми - страница 1, от 100 елемента на страница, сортирано по автор и после по цена, намаляващо:
  // Петър Берон - 10
  // Петър Берон - 9
  // Хросто Ботев - 100


  // за напреднали и любопитни
  // директно извикване на JPQL/HQL (подобие на SQL)
  @Query("SELECT author FROM Book WHERE id = :bookId")
  String getBookAuthor(long bookId);

}
