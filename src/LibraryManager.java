import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private List<Medium> media = new ArrayList<>();

    //- Medium in liste hinzufügen
    public void addMedium(String line){
        String[] parts = line.split(";");
        if (parts.length < 7) { //wenn was fehlt
            throw new IllegalArgumentException("Ungültig: " + line);
        }
        String type = parts[0];
        String title = parts[1];
        int year = Integer.parseInt(parts[2]);
        String category = parts[3];
        String orLanguage = parts[4];
        if (type.equalsIgnoreCase("book")) {//dann werden erst die unterschiedlichen behandelt
            String author = parts[5];
            String isbn = parts[6];
            media.add(new Book(title,year,category,orLanguage, author,isbn));
        }
        else if (type.equalsIgnoreCase("cd")) {
            String artist = parts[5];
            String album = parts[6];
            media.add(new CD(title, year, category, orLanguage, artist, album));
        }
        else if (type.equalsIgnoreCase("dvd")) {
            String director = parts[5];
            int fsk = Integer.parseInt(parts[6]);
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
            while ((line = br.readLine()) != null) {
                addMedium(line);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen!");
        }
    }

    //- Medium aus liste entfernen, nach Titel
    public void removeMedium(String title) {
        for (int i = 0; i < media.size(); i++) {
            if (media.get(i).getTitle().equalsIgnoreCase(title)) {
                media.remove(i);
                return;
            }
        }
    }



//   gibt zuruck ob ein Buch ueberfaellig ist
//    public boolean isLate(Medium medium){
//      return false;
//    }
//
//
//    public void borrow(Medium medium){
//
//    }
//    gibt eine Liste mit allen Medien zurueck, die im Jahr year publiziert wurden
//    public List<Medium> findByYear(int year){
//        return null;
//    }

}
