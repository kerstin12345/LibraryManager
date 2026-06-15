package function;

public class DVD extends Medium{
    private String director;
    private int FSK;

    /**
     * Erstellt eine neue DVD.
     *
     * @param title Titel der DVD
     * @param year Erscheinungsjahr
     * @param category Kategorie
     * @param orLanguage Originalsprache
     * @param isBorrowed Ausleihstatus
     * @param borCount Anzahl der Ausleihen
     * @param medCount Anzahl der Exemplare
     * @param director Regisseur
     * @param FSK Altersfreigabe
     */
    public DVD(String title, int year, String category, String orLanguage,
               boolean isBorrowed, int borCount, int medCount, String director, int FSK) {
        super(title, year, category, orLanguage, isBorrowed, borCount, medCount);
        this.director = director;
        this.FSK = FSK;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public int getFSK() {
        return FSK;
    }
    public void setFSK(int FSK) {
        this.FSK = FSK;
    }

    /**
     * Liefert eine Stringdarstellung der DVD.
     *
     * @return Stringdarstellung der DVD
     */
    @Override
    public String toString() {
        return "DVD: " +
                getTitle() + ", " +
                getYear() + ", " +
                getCategory() + ", " +
                getOrLanguage() + ", " +
                getDirector() + ", " +
                getFSK();
    }
}
