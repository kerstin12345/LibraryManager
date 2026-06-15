package function;

public class Book extends Medium{
    private String author;
    private String ISBN;

    /**
     * Erstellt ein neues Buch.
     * @param title Titel des Buches
     * @param year Erscheinungsjahr
     * @param category Kategorie
     * @param orLanguage Originalsprache
     * @param isBorrowed Ausleihstatus
     * @param borCount Anzahl der Ausleihen
     * @param medCount Anzahl der Exemplare
     * @param author Autor des Buches
     * @param ISBN ISBN des Buches
     */
    public Book(String title, int year, String category, String orLanguage, boolean isBorrowed, int borCount, int medCount, String author, String ISBN) {
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

    /**
     * Liefert eine Stringdarstellung des Buches.
     * @return Stringdarstellung des Buches
     */
    @Override
    public String toString() {
        return "Book: " +
                getTitle() + ", " +
                getYear() + ", " +
                getCategory() + ", " +
                getOrLanguage() + ", " +
                getAuthor() + ", " +
                getISBN();
    }
}
