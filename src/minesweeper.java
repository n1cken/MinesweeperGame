import java.util.Random;
import java.util.Scanner;

public class minesweeper {

    public static String[] letters = {"   ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static String[] line    = {"  +--------------------"};

    public static String  [][] board      = new String[10][11];  //Skriva ut brädet
    public static int     [][] checkState = new int [10][11];  // förvara states
    public static boolean [][] flagState  = new boolean [10][11];
    public static boolean alreadyUsed = false; //Test OK.

    // render( x, y )

    public static String unused = " -";
    public static String used   = " .";
    public static String flag   = " F";
    public static String clear   = " .";


    public static boolean gameOver = false;
    static int antalBomb = 15;
    static int genererad = 0;
    public static boolean hit = false;

    static boolean newgame = true;

    public static void main(String[] args) {

        render(-1, -1);

        System.out.println("");
        System.out.println("");
        System.out.println("> ");

        while (newgame) {

            Scanner prompt = new Scanner(System.in);
            String input = prompt.nextLine();
            int langdAvInput = input.length();

            String command = input.split(" ")[0];

            switch (command) {
                case "q":
                    newgame = false;

                case "r":

                    if (langdAvInput == 4) {
                        String input2 = input.substring(1, langdAvInput);

                        String cord1 = input2.substring(1, 2);//kanske 1, 2
                        String userInput = cord1.toUpperCase();

                        String xkordinat = input2.substring(2, 3); // 2, 3

                        int xcord = Integer.parseInt(xkordinat);
                        int ycord = generateXCords(userInput);

                        forsok(xcord, ycord + 1);
                    }
                    else
                        System.out.print("unknown command");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("> ");
                    break;

                case "f":

                    if (langdAvInput == 4) {
                        String input2 = input.substring(1, langdAvInput);

                        String cord1 = input2.substring(1, 2);//kanske 1, 2
                        String userInput = cord1.toUpperCase();

                        String xkordinat = input2.substring(2, 3); // 2, 3

                        int xcord = Integer.parseInt(xkordinat);
                        int ycord = generateXCords(userInput);

                        flagga(xcord, ycord + 1);



                    }
                    else
                         System.out.print("unknown command");
                         System.out.println("");
                         System.out.println("");
                         System.out.println("> ");

                    break;

                default:

                    System.out.print("syntax error");
            }
        }
    }

    public static void flagState(int x, int y) {
        flagState[x][y] = true;
    }


    public static int checkState(int x, int y) {

        if (checkState[x][y] == -1) { //bomb

        }
        if (checkState[x][y] == 0) { //safe

        }
        if (checkState[x][y] == 1) { // använd

        }
        if (checkState[x][y] == 2) {

        }
        if (checkState[x][y] == 3) {

        }

        return checkState[x][y];
    }

    public static void placeBomb(int x, int y) {

        boolean bomb = new Random().nextInt(5) == 0;

        if (bomb) {
            antalBomb-=1;

            if (antalBomb <= -1) {
                checkState[x][y] = 0;    //safe
            }
            else checkState[x][y] = -1;  //bomb
        }

    }


    public static int generateXCords(String xkordinat) {
        String kordinat2 = xkordinat.toUpperCase();
        if (kordinat2.endsWith("A")) {
            genererad = 0;  //render ( x, y ) ---    render [0][y]
        }
        if (kordinat2.endsWith("B")) {
            genererad = 1;
        }
        if (kordinat2.endsWith("C")) {
            genererad = 2;
        }
        if (kordinat2.endsWith("D")) {
            genererad = 3;
        }
        if (kordinat2.endsWith("E")) {
            genererad = 4;
        }
        if (kordinat2.endsWith("F")) {
            genererad = 5;
        }
        if (kordinat2.endsWith("G")) {
            genererad = 6;
        }
        if (kordinat2.endsWith("H")) {
            genererad = 7;
        }
        if (kordinat2.endsWith("I")) {
            genererad = 8;
        }
        if (kordinat2.endsWith("J")) {
            genererad = 9;
        }

        return genererad;
    }

    /////////////////       RENDER        /////////////
    public static void render(int x, int y) {

        for (String i : letters) {
            System.out.print(i + " ");  //SKRIVER UT VARJE RAD MED MELLANRUM
        }

        System.out.println(); //NY RAD
        System.out.print(line[0]);   // AVGRÄNSARE

        String[][] board = new String[10][11];

        for (int i = 0; i < board.length; i++) {
            System.out.println(""); // NY RAD
            for (int j = 0; j < board[i].length; j++) {  //Skapar ny rad i tabellen
                if (j == 0) { // Första kolumnen blir radnumrering
                    System.out.print(i + " |");
                }

                if (j > 0) { //resterande rader blir får en state
                    placeBomb(i, j);

                    System.out.print(unused);

                }
            }
        }
    }
    //////////////////////RENDER SLUTAR ////////////////////////////////


    //////////// VID FÖRSÖK AV KORDINAT RENDERAS REDAN GENERERAD BOARD ///////////
    public static void forsok(int x, int y) {

        for (String i : letters) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.print(line[0]);

        for (int i = 0; i < board.length; i++) {
            System.out.println();
            for (int j = 0; j < board[i].length; j++) {
                if (j == 0) { // Första kolumnen blir radnumrering
                    System.out.print(i + " |");
                }

                if (j > 0) {

                    if (i == x && y == j) {  //When input cordinates x, y MATCHES for loop i, j
                        if (checkState[i][j] == 1) {
                            alreadyUsed();
                        }

                        else

                        board[i][j] = used;

                        if (board[i][j].equals(used)) {

                            if (checkState[i][j] == -1) {  //BOMB? Game Over
                                bombHit();
                                continue;
                            }

                            if (flagState[i][j]) {         // FLAG? Change to X
                                flagState[i][j] = !flagState[i][j];
                                checkState[i][j] = 1;
                            }

                            else                           // ELSE? Change to X
                                checkState[i][j] = 1;

                                if (checkState[i][j] != -1) { //CLEAR CHECKSTATE = 0
                                    safeSpot(i, j);
                            }
                        }
                    }


                    if (flagState[i][j]) {
                        System.out.print(" F");
                    }

                    else if (checkState[i][j] == 1) {
                        System.out.print(used);
                    }

                    else if (gameOver) {
                         if (checkState[i][j] == -1) { // 0 = neutral; -1 = bomb; 1 = clear; 2 = Num; 3 = Flag
                            System.out.print(" M");
                        }

                        else
                            System.out.print(unused);
                            newgame = false;

                    }
                    else
                        System.out.print(unused);
                }
            }
        }

        if (alreadyUsed) {
            System.out.println("");
            System.out.print("Already used, try again.");
            alreadyUsed = false;
        }

        System.out.println("");
        System.out.println("");
        System.out.println("> ");

        if (gameOver) {
            System.out.println("You hit a bomb! GAME OVER.");
        }
    }

    public static void bombHit() {
        System.out.print(" M");
        gameOver = true;
    }


    public static void safeSpot(int x, int y) {
        if (checkState[x][y] == 0) {
            board[x][y] = ". ";
        }
    }


    //////////  FLAGGNING  ///////////////////
    public static void flagga(int x, int y) {

        for (String i : letters) {
            System.out.print(i + " ");  //SKRIVER UT VARJE RAD MED MELLANRUM
        }

        System.out.println(); //NY RAD
        System.out.print(line[0]);   // AVGRÄNSARE

        for (int i = 0; i < board.length; i++) {
            System.out.println(); // NY RAD
            for (int j = 0; j < board[i].length; j++) {  //Skapar ny rad i tabellen
                if (j == 0) { // Första kolumnen blir radnumrering
                    System.out.print(i + " |");
                }

                if (j > 0) {
                    if (i == x && y == j) {  //När X och Y är samma som tabellens rendering-räkning i och j, markera med X

                        if (checkState[i][j] == 1) {
                            alreadyUsed();
                        }
                        else
                            flagState(i, j);
                    }

                    if (checkState[i][j] == 1) {
                        System.out.print(used);

                    }

                    else if (flagState[i][j]) {
                        System.out.print(flag);
                    }

                    else
                        System.out.print(unused);
                }
            }
        }

        if (alreadyUsed) {
            System.out.println("");
            System.out.print("Already used, cannot flag.");
            alreadyUsed = false;
        }

        System.out.println("");
        System.out.println("");
        System.out.println("> ");


    }
/////////// FLAGGA SLUT ///////////////

    public static void alreadyUsed() {
        alreadyUsed = true;
    }


}





