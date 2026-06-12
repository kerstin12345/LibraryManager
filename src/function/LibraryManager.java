package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LibraryManager {
    public static void main(String[] args) {
//        zum testen
//        LibraryManager libraryManager = new LibraryManager();
//        libraryManager.readFile(String.valueOf(Paths.get("src/resources/media.csv")));
//        System.out.println("VORHER: "+libraryManager.media);
//        libraryManager.sortByTitle();
//        System.out.println("NACHHER, Alphabetisch: "+libraryManager.media);
    }

    public List<Medium> media = new ArrayList<>();

    /**
     * Gibt die Liste aller gespeicherten Medien zurück
     *
     * @return Liste aller Medien
     */
    public List<Medium> getMedia() {
        return media;
    }

    /**
     * Liefert den String des LibraryManagers
     *
     * @return String der Medienliste
     */
    @Override
    public String toString() {
        return "{" + media +
                "}";
    }

    /**
     * Fügt anhand einer CSV-Zeile(String) ein neues Medium zur Liste hinzu.
     * Je nach Typ wird ein Book-, CD- oder DVD-Objekt erstellt.
     *
     * Erwartetes Format:
     * Typ;Titel;Jahr;Kategorie;Sprache;Ausgeliehen;Ausleihanzahl;Bestand;Extra1;Extra2
     *
     * @param line CSV-Zeile mit den Attributen des Mediums
     * @throws IllegalArgumentException falls die Zeile ungültig ist
     */
    public void addMedium(String line) {
        String[] parts = line.split(";");
        if (parts.length < 10) { //wenn was fehlt
            throw new IllegalArgumentException("Ungültig: " + line);
        }
        String type = parts[0].trim();
        String title = parts[1].trim();
        int year = Integer.parseInt(parts[2].trim());
        String category = parts[3].trim();
        String orLanguage = parts[4].trim();
        boolean isBorrowed = Boolean.parseBoolean(parts[5].trim());
        int borCount = Integer.parseInt(parts[6].trim());
        int medCount = Integer.parseInt(parts[7].trim());
        if (type.equalsIgnoreCase("book")) {//dann werden erst die unterschiedlichen behandelt
            String author = parts[8].trim();
            String isbn = parts[9].trim();
            media.add(new Book(title, year, category, orLanguage, isBorrowed, borCount, medCount, author, isbn));
        } else if (type.equalsIgnoreCase("cd")) {
            String artist = parts[8].trim();
            String album = parts[9].trim();
            media.add(new CD(title, year, category, orLanguage, isBorrowed, borCount, medCount, artist, album));
        } else if (type.equalsIgnoreCase("dvd")) {
            String director = parts[8].trim();
            int fsk = Integer.parseInt(parts[9].trim());
            media.add(new DVD(title, year, category, orLanguage, isBorrowed, borCount, medCount, director, fsk));
        } else {
            throw new IllegalArgumentException("Unbekannter Medientyp: " + type);
        }
    }

    /**
     * Liest alle Medien aus einer CSV-Datei ein
     * fügt sie der Medienliste hinzu.
     *
     * Die ersten drei Zeilen werden als Kopfzeilen übersprungen
     *
     * @param path Pfad(String) zur CSV-Datei
     */
    public void readFile(String path) {
        try (BufferedReader br = Files.newBufferedReader(Path.of(path))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                i++;
                if (i > 3) { //wegen den 1sten 3 zeilen
                    addMedium(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen!");
        }
    }

    /**
     * Entfernt ein Medium anhand von Titel und Typ
     *
     *@param title Titel des Mediums
     * @param type Medientyp (Book, CD oder DVD)
     * @return true falls ein Medium entfernt wurde,
     *sonst false
     */
    public boolean removeMedium(String title, String type) {
        for (int i = 0; i < media.size(); i++) {
            Medium m = media.get(i);
            boolean sameTitle = m.getTitle().equalsIgnoreCase(title);
            boolean sameType =
                    (type.equalsIgnoreCase("Book") && m instanceof Book) ||
                            (type.equalsIgnoreCase("CD") && m instanceof CD) ||
                            (type.equalsIgnoreCase("DVD") && m instanceof DVD);

            if (sameTitle && sameType) {
                media.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Speichert alle Medien in einer CSV-Datei.
     *
     * Bestehende Inhalte werden überschrieben.
     *
     * @param path Pfad zur Ausgabedatei
     */
    public void writeFile(String path) {
        List<String> lines = new ArrayList<>();

        // Kopfzeilen
        lines.add("book;title;year;category;orLanguage;isBorrowed;borCount;medCount;author;ISBN");
        lines.add("CD;title;year;category;orLanguage;isBorrowed;borCount;medCount;artist;album");
        lines.add("DVD;title;year;category;orLanguage;isBorrowed;borCount;medCount;director;FSK");

        for (Medium m : media) {

            if (m instanceof Book b) {

                lines.add("Book;" + b.getTitle() + ";" +
                        b.getYear() + ";" +
                        b.getCategory() + ";" +
                        b.getOrLanguage() + ";" +
                        b.isBorrowed() + ";" +
                        b.getBorCount() + ";" +
                        b.getMedCount() + ";" +
                        b.getAuthor() + ";" +
                        b.getISBN());

            } else if (m instanceof CD c) {
                lines.add("CD;" +
                        c.getTitle() + ";" +
                        c.getYear() + ";" +
                        c.getCategory() + ";" +
                        c.getOrLanguage() + ";" +
                        c.isBorrowed() + ";" +
                        c.getBorCount() + ";" +
                        c.getMedCount() + ";" +
                        c.getArtist() + ";" +
                        c.getAlbum());

            } else if (m instanceof DVD d) {
                lines.add("DVD;" +
                        d.getTitle() + ";" +
                        d.getYear() + ";" +
                        d.getCategory() + ";" +
                        d.getOrLanguage() + ";" +
                        d.isBorrowed() + ";" +
                        d.getBorCount() + ";" +
                        d.getMedCount() + ";" +
                        d.getDirector() + ";" +
                        d.getFSK());
            }
        }
        try {
            Files.write(Paths.get(path), lines);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei!");
        }
    }

    //sortiert die liste alphabetisch
    public void sortByTitle () {
            Collections.sort(media, new Comparator<Medium>() {
                @Override
                public int compare(Medium m1, Medium m2) {
                    return m1.getTitle().compareToIgnoreCase(m2.getTitle()); // Vergleicht zwei Titel
                }
            });
        }

    //nach dem Jahr sortieren
    public void sortByYear () {
            Collections.sort(media, new Comparator<Medium>() {
                @Override
                public int compare(Medium m1, Medium m2) {
                    return m2.getYear() - m1.getYear(); //wenn das ergebnis negativ ist, soll m1 vor m2 stehen
                }
            });
        }
}
