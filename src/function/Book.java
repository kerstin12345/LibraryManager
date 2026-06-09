package function;

public class Book extends Medium{
    private String author;
    private String ISBN;

    public Book(String title, int year, String category, String orLanguage,
                boolean isBorrowed, int borCount, int medCount, String author, String ISBN) {
        super( title,  year,  category,  orLanguage,
         isBorrowed,  borCount,  medCount);
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

    @Override
    public String toString() {
        return super.toString()+author+ISBN;
    }
}
