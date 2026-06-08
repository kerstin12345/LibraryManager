public class CD extends Medium{
    private String artist;
    private String album;

    public CD(String title, int year, String category, String orLanguage, String artist, String album) {
        super(title, year, category, orLanguage);
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
}
