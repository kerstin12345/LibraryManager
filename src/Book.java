import java.util.List;

public class Book extends Medium{
    private String author;
    private String ISBN;

    public Book(String title, int year, String category, List<Integer> raiting, boolean isBorrowed, int borCount,
        int medCount, String orLanguage, String author, String ISBN) {
        super(title, year, category, raiting, isBorrowed, borCount, medCount, orLanguage);
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
