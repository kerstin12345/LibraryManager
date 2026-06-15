package function;

import java.util.List;

public abstract class Medium {
    private String title;
    private int year;
    private String category;
    private boolean isBorrowed;
    private int borCount;
    private int medCount;
    private String orLanguage;

    /**
     * Erstellt ein neues Medium mit den gemeinsamen Attributen.
     *
     * @param title Titel des Mediums
     * @param year Erscheinungsjahr
     * @param category Kategorie/Genre des Mediums
     * @param orLanguage Originalsprache
     * @param isBorrowed Ausleihstatus
     * @param borCount Anzahl der Ausleihen
     * @param medCount Anzahl vorhandener Exemplare
     */
    public Medium(String title, int year, String category, String orLanguage,
                  boolean isBorrowed, int borCount, int medCount) {
        this.title = title;
        this.year = year;
        this.category = category;
        this.orLanguage = orLanguage;
        this.isBorrowed = isBorrowed;
        this.borCount = borCount;
        this.medCount = medCount;
    }

    /**
     * Liefert eine Stringdarstellung des Mediums.
     * @return Stringdarstellung des Mediums
     */
    @Override
    public String toString() {
        return title + year + category + orLanguage;
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
