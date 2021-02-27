package bg.startit.spring.firstspringproject.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {

  @Id
  @GeneratedValue
  private long id;
  private String title;
  private String author;
  private String genre;
  private String notes;
  private float price;
  private boolean available;
  private boolean funny;


  // This create Many-to-One relation to User
  @ManyToOne
  private User user;

  // Alternatively we can use
  // @OneToOne (on a single object),
  // @OneToMany (on array or collection of other @Entity objects)
  // @ManyToMany (on array of collection of other @Entity objects)

  // Avoid using @OneToMany and @ManyToMany - it loads more objects and makes you program SLOW
  // In some cases you can't avoid it, so live with it.

  // If you annotate one entity with the above annotation you create a bi-directional link
  // (in the database) between two @Entity object.
  // But it is not necessary *both* of those objects to link to each other. You may annotate
  // only of the entities.
  // For the reverse connection, you can use query methods of the repositories.



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return id == book.id && Float.compare(book.price, price) == 0 && available == book.available && funny == book.funny && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre) && Objects.equals(notes, book.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, genre, notes, price, available, funny);
  }

  public boolean isFunny() {
    return funny;
  }

  public void setFunny(boolean funny) {
    this.funny = funny;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

}
