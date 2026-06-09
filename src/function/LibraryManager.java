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

    public List<Medium> getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "{" + media +
                "}";
    }

    // function.Medium in liste hinzufügen
    public void addMedium(String line){
        String[] parts = line.split(";");
        if (parts.length < 7) { //wenn was fehlt
            throw new IllegalArgumentException("Ungültig: " + line);
        }
        String type = parts[0].trim();
        String title = parts[1].trim();
        int year = Integer.parseInt(parts[2].trim());
        String category = parts[3].trim();
        String orLanguage = parts[4].trim();
        if (type.equalsIgnoreCase("book")) {//dann werden erst die unterschiedlichen behandelt
            String author = parts[5].trim();
            String isbn = parts[6].trim();
            media.add(new Book(title,year,category,orLanguage, author,isbn));
        }
        else if (type.equalsIgnoreCase("cd")) {
            String artist = parts[5].trim();
            String album = parts[6].trim();
            media.add(new CD(title, year, category, orLanguage, artist, album));
        }
        else if (type.equalsIgnoreCase("dvd")) {
            String director = parts[5].trim();
            int fsk = Integer.parseInt(parts[6].trim());
            media.add(new DVD(title,year, category, orLanguage, director, fsk));
        }
        else {
            throw new IllegalArgumentException("Unbekannter Medientyp: " + type);
        }
    }

    //Datei einlesen und Medien hinzufuegen
    public void readFile(String path) {
        try (BufferedReader br = Files.newBufferedReader(Path.of(path))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                i++;
                if(i > 3){ //wegen den 1sten 3 zeilen
                    addMedium(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen!");
        }
    }

    // function.Medium aus liste entfernen, nach Titel
    public void removeMedium(String title) {
        for (int i = 0; i < media.size(); i++) {
            if (media.get(i).getTitle().equalsIgnoreCase(title)) {
                media.remove(i);
                return;
            }
        }
    }

    public void writeFile(String path) {
        List<String> lines = new ArrayList<>();

        // Kopfzeilen
        lines.add("book;title;year;category;orLanguage;author;ISBN");
        lines.add("CD;title;year;category;orLanguage;artist;album");
        lines.add("DVD;title;year;category;orLanguage;director;FSK");

        for (Medium m : media) {

            if (m instanceof Book b) {

                lines.add("Book;" + b.getTitle() + ";" +
                        b.getYear() + ";" +
                        b.getCategory() + ";" +
                        b.getOrLanguage() + ";" +
                        b.getAuthor() + ";" +
                        b.getISBN());

            } else if (m instanceof CD c) {
                lines.add("CD;" +
                        c.getTitle() + ";" +
                        c.getYear() + ";" +
                        c.getCategory() + ";" +
                        c.getOrLanguage() + ";" +
                        c.getArtist() + ";" +
                        c.getAlbum());

            } else if (m instanceof DVD d) {
                lines.add("DVD;" +
                        d.getTitle() + ";" +
                        d.getYear() + ";" +
                        d.getCategory() + ";" +
                        d.getOrLanguage() + ";" +
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
    public void sortByTitle() {
        Collections.sort(media, new Comparator<Medium>() {
            @Override
            public int compare(Medium m1, Medium m2) {
                return m1.getTitle().compareToIgnoreCase(m2.getTitle()); // Vergleicht zwei Titel
            }
        });
    }

    //nach dem Jahr sortieren
    public void sortByYear() {
        Collections.sort(media, new Comparator<Medium>() {
            @Override
            public int compare(Medium m1, Medium m2) {
                return m2.getYear() - m1.getYear(); //wenn das ergebnis negativ ist, soll m1 vor m2 stehen
            }
        });
    }

}
