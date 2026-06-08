import java.util.List;

public abstract class Medium {
    private String title;
    private int year;
    private String category;
    private List<Integer> raiting;
    private boolean isBorrowed;
    private int borCount;
    private int medCount;
    private String orLanguage;

    public Medium(String title, int year, String category, String orLanguage) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.orLanguage = orLanguage;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public List<Integer> getRaiting() {
        return raiting;
    }
    public void setRaiting(List<Integer> raiting) {
        this.raiting = raiting;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
    public int getBorCount() {
        return borCount;
    }
    public void setBorCount(int borCount) {
        this.borCount = borCount;
    }
    public int getMedCount() {
        return medCount;
    }
    public void setMedCount(int medCount) {
        this.medCount = medCount;
    }
    public String getOrLanguage() {
        return orLanguage;
    }
    public void setOrLanguage(String orLanguage) {
        this.orLanguage = orLanguage;
    }
}
