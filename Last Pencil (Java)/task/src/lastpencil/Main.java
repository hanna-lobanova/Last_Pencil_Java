package lastpencil;

import java.util.Scanner;
import java.util.Random;


public class Main {
    static String firstPlayer;
    static String secondPlayer;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random random = new Random();
        //int numberOfPencils = random.nextInt(1, 11);

        System.out.println("How many pencils would you like to use:");
        int numOfPencils = checkError(scan);

        System.out.println("Who will be the first (John, Jack):");
        checkPlayer(scan);

        printPencils(numOfPencils);

        while(numOfPencils > 0){
            numOfPencils = play(numOfPencils, firstPlayer, random, scan);
            if (numOfPencils>0){
                numOfPencils = play(numOfPencils, secondPlayer,random, scan); // random
            }
        }
    }


    public static int play(int pencils, String player, Random random, Scanner scan){
        System.out.println("\n" +player + "'s turn!");
        int turn = checkTurn(pencils, player, random, scan);

        if((pencils-turn)==0) {
            if (player.equals(firstPlayer)){
                System.out.println(secondPlayer + " won!");
            } else {
                System.out.println(firstPlayer + " won!");
            }
            pencils = 0;
        } else {
            pencils = pencils - turn;
            printPencils(pencils);
            return pencils;
        }
        return pencils;
    }

    public static void printPencils(int num){
        System.out.print("|".repeat(num));
    }

    public static int checkError(Scanner scan) {
        int number;

        while (true) {
            String input = scan.nextLine(); // Lies die gesamte Eingabe als String

            // Überprüfe, ob der Eingabewert numerisch ist
            if (input.trim().isEmpty()) { // Leere Eingabe abfangen
                System.out.println("The number of pencils should be numeric");
            } else if (!input.matches("\\d+")) { // Nicht-numerische Eingabe
                System.out.println("The number of pencils should be numeric");
            } else {
                number = Integer.parseInt(input); // Konvertiere den Eingabewert zu int
                if (number <= 0) { // Prüfe, ob der Wert positiv ist
                    System.out.println("The number of pencils should be positive");
                } else {
                    return number; // Gültige Eingabe zurückgeben
                }
            }
        }
    }

    public static void checkPlayer(Scanner scan) {
        while (true) {
            String inputPlayer = scan.next(); // Lies Eingabe

            if (inputPlayer.equals("John")) {
                firstPlayer = "John";
                secondPlayer = "Jack";
                break;
            } else if (inputPlayer.equals("Jack")) {
                firstPlayer = "Jack";
                secondPlayer = "John";
                break;
            } else {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }
    }

    public static int checkTurn(int pencils, String player, Random random, Scanner scan) {
        int number;
        // BOT-Strategie
        if (player.equals("Jack")){
            if (pencils % 4 == 1){
                // Verlustposition für den Bot, wähle zufällig
                number = random.nextInt(3) +1;
                System.out.println(number);
            } else if (pencils % 4 == 2){
                // Gewinnposition: Nimm 1 Bleistift
                number = 1;
                System.out.println(number);
            } else if (pencils % 4 == 3){
                // Gewinnposition: Nimm 2 Bleistifte
                number = 2;
                System.out.println(number);
            } else {
                // Gewinnposition: Nimm 3 Bleistifte
                number = 3;
                System.out.println(number);
            }

            // Wenn mehr Bleistifte entnommen werden als vorhanden
            if (number > pencils) {
                //System.out.println("Too many pencils were taken");
                return checkTurn(pencils, player, random, scan); // Rekursiver Aufruf
            }
        } else {

            // Wenn die Eingabe keine Zahl ist
            if (!scan.hasNextInt()) {
                System.out.println("Possible values: '1', '2' or '3'");
                scan.next(); // Ungültige Eingabe überspringen
                return checkTurn(pencils, player, random, scan); // Rekursiver Aufruf
            } else {
                number = scan.nextInt();

                // Wenn die Zahl nicht 1, 2 oder 3 ist
                if (number != 1 && number != 2 && number != 3) {
                    System.out.println("Possible values: '1', '2' or '3'");
                    return checkTurn(pencils, player, random, scan); // Rekursiver Aufruf
                }

                // Wenn mehr Bleistifte entnommen werden als vorhanden
                if (number > pencils) {
                    System.out.println("Too many pencils were taken");
                    return checkTurn(pencils, player, random, scan); // Rekursiver Aufruf
                }
            }
        }

        return number; // Rückgabe der gültigen Zahl
    }


}
