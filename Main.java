
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main {

    //Interfes potrzebny do lambdy
    interface Person {
        int processPerson(String str1, String str2);
    }

    public static void main(String[] args){
        //Pierwszy ArrayList
        ArrayList<String> people = new ArrayList();

        //Drugi ArrayList, lepiej się na nim pracuje
        ArrayList<String[]> peopleSplit = new ArrayList();

        int max1 = 0, max2 = 0;
        int maxIndex1 = 0, maxIndex2 = 0;
        int min1 = 0, min2 = 0;
        int minIndex1 = 0, minIndex2 = 0;

        //Metoda lambda
        Person suma = (str1, str2) -> {
            return (Integer.parseInt(str1) + Integer.parseInt(str2));
        };

        String file = "C://Money.txt"; //Pełna ścieżka do pliku
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.peek(e -> System.out.println("Value: " + e)) //Podejrzenie strumienia
            .forEach(people::add); //dodajemy do pierwszej ArrayListy
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dodaj elementy z pierwszej ArrayListy, do drugiej ArrayListy (tej lepszej)
        for (int i=0; i<people.size();i++){
            peopleSplit.add(people.get(i).split(" "));
        }

        //Zapisz indexy najlepiej zarabiajacych polaków
        for (int i=0; i<peopleSplit.size();i++){
            if (peopleSplit.get(i)[2].equalsIgnoreCase("PL")){
                if (Integer.parseInt(peopleSplit.get(i)[3]) > max1){
                    max2 = max1;
                    maxIndex2 = maxIndex1;
                    max1 = Integer.parseInt(peopleSplit.get(i)[3]);
                    maxIndex1 = i;
                } else if (Integer.parseInt(peopleSplit.get(i)[3]) > max2){
                    max2 = Integer.parseInt(peopleSplit.get(i)[3]);
                    maxIndex2 = i;
                }
            }
        }

        //Przyjmij, że najlepiej zarabiający, są jednoczesnie najgorzej zarabiającymy
        min1 = max2;
        minIndex1 = maxIndex2;
        min2 = max1;
        minIndex2 = maxIndex1;

        //Zapisz indexy najgorzej zarabiajacych polaków
        for (int i=0; i<peopleSplit.size();i++){
            if (peopleSplit.get(i)[2].equalsIgnoreCase("PL")){
                if (Integer.parseInt(peopleSplit.get(i)[3]) < min1){
                    min2 = min1;
                    minIndex2 = minIndex1;
                    min1 = Integer.parseInt(peopleSplit.get(i)[3]);
                    minIndex1 = i;
                } else if (Integer.parseInt(peopleSplit.get(i)[3]) < min2){
                    min2 = Integer.parseInt(peopleSplit.get(i)[3]);
                    minIndex2 = i;
                }
            }
        }

        System.out.print("\nSuma zarobków najwięcej zarabiających polaków: ");
        //Uzyj tej naszej lambdy
        System.out.println(suma.processPerson(
                peopleSplit.get(maxIndex1)[3], peopleSplit.get(maxIndex2)[3]));
        System.out.print("\nSuma zarobków najmnniej zarabiających polaków: ");
        //Uzyj tej naszej lambdy x2
        System.out.println(suma.processPerson(
                peopleSplit.get(minIndex1)[3], peopleSplit.get(minIndex2)[3]));
    }
}
