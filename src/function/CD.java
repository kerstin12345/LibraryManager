package function;

public class CD extends Medium{
    private String artist;
    private String album;

    /**
     * Erstellt eine neue CD.
     *
     * @param title Titel der CD
     * @param year Erscheinungsjahr
     * @param category Kategorie
     * @param orLanguage Originalsprache
     * @param isBorrowed Ausleihstatus
     * @param borCount Anzahl der Ausleihen
     * @param medCount Anzahl der Exemplare
     * @param artist Interpret
     * @param album Albumname
     */
    public CD(String title, int year, String category, String orLanguage, boolean isBorrowed, int borCount, int medCount, String artist, String album) {
        super(title, year, category, orLanguage, isBorrowed, borCount, medCount);
        this.artist = artist;
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Liefert eine Stringdarstellung der CD.
     *
     * @return Stringdarstellung der CD
     */
    @Override
    public String toString() {
        return "CD: " +
                getTitle() + ", " +
                getYear() + ", " +
                getCategory() + ", " +
                getOrLanguage() + ", " +
                getArtist() + ", " +
                getAlbum();
    }
}
