import java.util.List;

public class DVD extends Medium{
    private String director;
    private int FSK;

    public DVD(String title, int year, String category, List<Integer> raiting, boolean isBorrowed, int borCount,
        int medCount, String orLanguage, String director, int FSK) {
        super(title, year, category, raiting, isBorrowed, borCount, medCount, orLanguage);
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
}
