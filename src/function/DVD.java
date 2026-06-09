package function;

public class DVD extends Medium{
    private String director;
    private int FSK;

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

    @Override
    public String toString() {
        return super.toString()+director+FSK;
    }
}
