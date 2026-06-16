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
     * Erstellt aus einzelnen Eingaben ein Medium und fügt es hinzu.
     *
     * @param type
     * @param title
     * @param year
     * @param category
     * @param orLanguage
     * @param extra1
     * @param extra2
     * @return Statusmeldung für die Oberfläche
     */
    public String addMedium(String type, String title, String year, String category,
                            String orLanguage, String extra1, String extra2) {
        if (title == null || title.trim().isEmpty()) {
            return "Kein Titel eingegeben.";
        }

        String line = type + ";" + title.trim() + ";" +
        year.trim() + ";" + category.trim() + ";" + orLanguage.trim() + ";" +
        "false" + ";" + "0" + ";" + "1" + ";" + extra1.trim() + ";" + extra2.trim();

        addMedium(line);
        return type + " wurde erfolgreich hinzugefügt.";
    }

    /**
     * Liest alle Medien aus einer CSV-Datei ein
     * fügt sie der Medienliste hinzu.
     *
     * Die ersten drei Zeilen werden übersprungen
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
     * Entfernt ein Medium
     * @param title
     * @param type
     * @return Statusmeldung
     */
    public String removeMediumWithMessage(String title, String type) {
        if (title == null || title.trim().isEmpty()) {
            return "Kein Titel eingegeben.";
        }

        boolean removed = removeMedium(title.trim(), type);
        if (removed) {
            return type + " wurde erfolgreich entfernt.";
        }
        return type + " mit diesem Titel wurde nicht gefunden.";
    }


    /**
     * Leiht ein Medium aus.
     *
     * @param title
     * @param type
     * @return Statusmeldung für die Oberfläche
     */
    public String borrowMedium(String title, String type) {
        if (title == null || title.trim().isEmpty()) {
            return "Kein Titel eingegeben.";
        }

        Medium medium = findMedium(title.trim(), type);
        if (medium == null) {
            return type + " nicht gefunden.";
        }

        if (medium.isBorrowed()) {
            return "Medium ist bereits ausgeliehen.";
        }

        medium.setBorrowed(true);
        medium.setBorCount(medium.getBorCount() + 1);
        return "Medium wurde erfolgreich ausgeliehen.";
    }

    /**
     * Gibt ein ausgeliehenes Medium zurück.
     *
     * @param title
     * @param type
     * @return Statusmeldung für die Oberfläche
     */
    public String returnMedium(String title, String type) {
        if (title == null || title.trim().isEmpty()) {
            return "Kein Titel eingegeben.";
        }

        Medium medium = findMedium(title.trim(), type);
        if (medium == null) {
            return type + " nicht gefunden.";
        }

        if (!medium.isBorrowed()) {
            return "Medium ist nicht ausgeliehen.";
        }

        medium.setBorrowed(false);
        return "Medium wurde erfolgreich zurückgegeben!";
    }

    /**
     * Speichert alle Medien in einer CSV-Datei.
     * Bestehende Inhalte werden überschrieben.
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

    /**
     * Sortiert alle Medien alphabetisch nach ihrem Titel.
     */
    public void sortByTitle () {
            Collections.sort(media, new Comparator<Medium>() {
                @Override
                public int compare(Medium m1, Medium m2) {
                    return m1.getTitle().compareToIgnoreCase(m2.getTitle()); // Vergleicht zwei Titel
                }
            });
        }

    /**
     * Sortiert alle Medien absteigend nach Erscheinungsjahr.
     * Neuere Medien werden zuerst angezeigt.
     */
    public void sortByYear () {
            Collections.sort(media, new Comparator<Medium>() {
                @Override
                public int compare(Medium m1, Medium m2) {
                    return m2.getYear() - m1.getYear(); //wenn das ergebnis negativ ist, soll m1 vor m2 stehen
                }
            });
        }

    /**
     * Gibt alle Medien als Text zurück.
      * @return Text der Medien
     */
    public String getAllMediaAsText() {
        return mediaListToText(media);
    }

    /**
     * Gibt alle ausgeliehenen Medien als Text zurück.
     * @return
     */
    public String getBorrowedMediaAsText() {
        List<Medium> borrowedMedia = new ArrayList<>();
        for (Medium medium : media) {
            if (medium.isBorrowed()) {
                borrowedMedia.add(medium);
            }
        }
        return mediaListToText(borrowedMedia);
    }

    /**
     * Sortiert nach Titel
     * @return sortierte Liste
     */
    public String sortByTitleAndGetText() {
        sortByTitle();
        return getAllMediaAsText();
    }

    /**
     * Liefert die Bezeichnungen der beiden zusätzlichen Eingabefelder
     * abhängig vom gewählten Medientyp.
     *
     * @param type Medientyp (Book, CD oder DVD)
     * @return Array mit den Bezeichnungen der Zusatzfelder
     */
    public String[] getExtraLabels(String type) {
        if (type.equalsIgnoreCase("Book")) {
            return new String[]{"Autor", "ISBN"};
        } else if (type.equalsIgnoreCase("CD")) {
            return new String[]{"Artist", "Album"};
        } else if (type.equalsIgnoreCase("DVD")) {
            return new String[]{"Director", "FSK"};
        }
        return new String[]{"Extra 1", "Extra 2"};
    }

    /**
     * Sucht ein Medium anhand von Titel und Typ.
     *
     * @param title Titel des gesuchten Mediums
     * @param type Medientyp des gesuchten Mediums
     * @return das gefundene Medium oder null, falls kein passendes Medium existiert
     */
    private Medium findMedium(String title, String type) {
        for (Medium medium : media) {
            if (isSameTitleAndType(medium, title, type)) {
                return medium;
            }
        }
        return null;
    }

    /**
     * Prüft, ob ein Medium den angegebenen Titel und Typ besitzt.
     *
     * @param medium zu prüfendes Medium
     * @param title gesuchter Titel
     * @param type gesuchter Medientyp
     * @return true, wenn Titel und Typ übereinstimmen, sonst false
     */
    private boolean isSameTitleAndType(Medium medium, String title, String type) {
        boolean sameTitle = medium.getTitle().equalsIgnoreCase(title);
        boolean sameType =
                (type.equalsIgnoreCase("Book") && medium instanceof Book) ||
                        (type.equalsIgnoreCase("CD") && medium instanceof CD) ||
                        (type.equalsIgnoreCase("DVD") && medium instanceof DVD);

        return sameTitle && sameType;
    }

    /**
     * Wandelt eine Liste von Medien in einen formatierten Text um.
     * Jedes Medium wird in einer neuen Zeile dargestellt.
     *
     * @param mediaList Liste der Medien
     * @return Stringdarstellung der gesamten Medienliste
     */
    private String mediaListToText(List<Medium> mediaList) {
        StringBuilder text = new StringBuilder();
        for (Medium medium : mediaList) {
            text.append(medium.toString()).append("\n");
        }
        return text.toString();
    }

}

