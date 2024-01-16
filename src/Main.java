//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static List<Wygrany> wczytajListeWygranych() {
        List<Wygrany> listaWygranych = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("wygrani.dat"))) {
            listaWygranych = (List<Wygrany>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Plik nie istnieje, można go utworzyć
            System.out.println("Plik z danymi nie istnieje. Tworzę nowy.");
        } catch (IOException | ClassNotFoundException e) {
            // Obsługa błędów lub puste wczytanie listy
            e.printStackTrace();
        }
        return listaWygranych;
    }

    public static void zapiszListeWygranych(List<Wygrany> listaWygranych) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("wygrani.dat"))) {
            oos.writeObject(listaWygranych);
        } catch (IOException e) {
            // Obsługa błędów
            e.printStackTrace();
        }
    }


    // Uzywamy do wskazania liczby w wybranym wcześniej zakresie. Nie pozwoli wybrać liczby innej niż z danego zakresu
    public static int wskazywanieLiczby(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int wskazanaLiczba;
        do {
            System.out.print("Podaj liczbę całkowitą od " + min + " do " + max);
            while (!scanner.hasNextInt()) {
                System.out.println("To nie jest liczba całkowita. Spróbuj ponownie.");
                System.out.print("Podaj liczbę całkowitą od " + min + " do " + max);
                scanner.next();
            }
            wskazanaLiczba = scanner.nextInt();
            if (wskazanaLiczba < min || wskazanaLiczba > max) {
                System.out.println("Podana liczba nie jest w zakresie od " + min + " do " + max + ". Spróbuj ponownie.");
            }
        } while (wskazanaLiczba < min || wskazanaLiczba > max);
        return wskazanaLiczba;
    }

    // Uzywamy do losowania liczb ze wskazanego zakresu
    public static int losowanieLiczy(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static int zaawansowaneAI(int min, int max, int runda, int primarMin, int primarMax) {
        int wynik;
        int rnd;
        Random random = new Random();

        if (runda == 0) {
            rnd = random.nextInt(max / 3) + max / 3;
            wynik = rnd;

        } else if (runda < 3) {
            wynik = random.nextInt(max - min + 1) + min;

        } else if (runda == 3 && min == primarMin) {
            wynik = min;

        } else if (runda == 3 && max == primarMax) {
            wynik = max;

        } else if (runda % 2 == 0) {
            wynik = random.nextInt(max - min + 1) + min;
        } else {
            wynik = random.nextInt(max - min + 1) + min;
        }

        if (wynik < min) {
            wynik = min;
        }
        if (wynik > max) {
            wynik = max;
        }

        return wynik;
    }


    public static void main(String[] args) {
        List<Wygrany> listaWygranych = wczytajListeWygranych();


// ###########     MENU >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        System.out.println("Witaj w grze!");
        System.out.println("Rozgrywka polega na odgadnięciu liczby z określonego zakresu, która została wskazana przez innego gracza.");
        System.out.println("Wygrywa ten, kto pierwszy odgadnie liczbę wskazaną przez przeciwnika.");
        System.out.println("Gra jest zarówno dla jednego jak i większej (nieograniczonej) liczby graczy.");
        System.out.println("W przypadku rozgrywki dla jednego gracza przeciwnikiem (drugim graczem) jest komputer");
        System.out.println("W przypadku rozgrywki dla dwóch i więcej graczy celem jest odgadnięcie liczby wskazanej przez kolejnego gracza (lub w przypadku ostatniego gracza wskazanie liczby pierwszego gracza).");
        System.out.println("Sposobem komunikowania się z grą jest wprowadzanie cyfr całkowitych (np. 1; 2; 10) i potwierdzenie ich klikając ENTER. W jednym miejscu gra poprosi o wprowadzenie nickname - tutaj można użyć liter ;)");
        System.out.println("A, i jeszcze jedno. Wprowadzając Nickname dobrze się zastanów - najlepsze wyniki zostaną zapisane i będą dostępne dla innych graczy");
        System.out.println("A więc zaczynajmy, POWODZENIA!");


// ###########     TRYB GRY >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        boolean exit = false;
        while (!exit) {


        System.out.println("MANU: Wybierz 1, 2, 3 lub 4:");
        System.out.println("1. JEDEN GRACZ - rozgrywka jeden na jeden z symulowanym przez komputer przeciwnikiem");
        System.out.println("2: ROZGRYWKA WIELOOSOBOWA – dla dwóch i więcej graczy");
        System.out.println("3: LISTA WYNIKÓW - wyświetl liste wyników");
        System.out.println("4: WYJDŹ - zakończ gre");



            Scanner scan = new Scanner(System.in);
            int trybGry;
            do {
                try {
                    trybGry = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException var9) {
                    trybGry = -1;
                }

                if (trybGry != 1 && trybGry != 2 && trybGry != 3 && trybGry != 4) {
                    System.out.println("Błędne dane. Wprowadź wartość 1, 2 lub 3 i potwierdź ENTER");
                }
            } while (trybGry != 1 && trybGry != 2 && trybGry != 3 && trybGry != 4);


//########### ILOŚCI GRACZY. W razie trybu dla jednego gracza jest to 2 - gracz i AI. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            if (trybGry == 1 || trybGry == 2) {


                int iloscGraczy = 2;
                int potwierdzenie = 0;

                if (trybGry == 2) {
                    Scanner scanner = new Scanner(System.in);

                    do {
                        System.out.println("Wybrano tryb dla wielu graczy. Ilość graczy nie jest ograniczona ale ze względu na dynamikę rozgrywki ilość graczy większa niż 4 nie jest rekomendowana");
                        System.out.print("Podaj ilość graczy (minimum 2): ");

                        while (!scanner.hasNextInt()) {
                            System.out.println("Podana wartość nie jest liczbą całkowitą.");
                            System.out.print("Podaj ilość graczy (minimum 2): ");
                            scanner.next();
                        }

                        iloscGraczy = scanner.nextInt();
                        if (iloscGraczy < 2) {
                            System.out.println("Ilość graczy nie może być mniejsza niż 2. Podaj ponownie.");
                        }

                        if (iloscGraczy > 4) {
                            System.out.println("Ilość graczy jest większa niż 4. Gra zadziała przy większej ilości ale ze względu na dynamikę rozgrywki ilość graczy większa niż 4 nie jest rekomendowana");
                            System.out.println("Czy na pewno ilość graczy ma wynosić: " + iloscGraczy);
                            System.out.println("1: Tak, ilość graczy ma wynosić :" + iloscGraczy + ". Dynamika rozgrywki nie jest istotna.");
                            System.out.println("2. Nie, wprowadzę inną ilość graczy");

                            do {
                                try {
                                    potwierdzenie = Integer.parseInt(scan.nextLine());
                                } catch (NumberFormatException var8) {
                                    potwierdzenie = -1;
                                }

                                if (potwierdzenie != 1 && potwierdzenie != 2) {
                                    System.out.println("Błędne dane. Wprowadź:");
                                    System.out.println("1: Tak, ilość graczy ma wynosić :" + iloscGraczy + ". Dynamika rozgrywki nie jest istotna.");
                                    System.out.println("2. Nie, wprowadzę inną ilość graczy");
                                }

                                if (potwierdzenie == 2) {
                                    iloscGraczy = 0;
                                }
                            } while (potwierdzenie != 1 && potwierdzenie != 2);
                        }
                    } while (iloscGraczy < 2);
                }


// ########## POZIOM TRUDNOŚCI >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                System.out.println("Wybierzpoziom trudności wprowadzając 1, 2 lub 3:");
                System.out.println("1. ŁATWY - zakres losowanych liczb 1 - 100");
                System.out.println("2: ŚREDNI - zakres losowanych liczb 1 - 10 000");
                System.out.println("3: TRUDNY - zakres losowanych liczb 1 - 1 000 000");

                int poziomTrudnosi;
                do {
                    try {
                        poziomTrudnosi = Integer.parseInt(scan.nextLine());
                    } catch (NumberFormatException var7) {
                        poziomTrudnosi = -1;
                    }

                    if (poziomTrudnosi != 1 && poziomTrudnosi != 2 && poziomTrudnosi != 3) {
                        System.out.println("Błędne dane. Wprowadź wartość 1, 2 lub 3 i potwierdź ENTER");
                    }
                } while (poziomTrudnosi != 1 && poziomTrudnosi != 2 && poziomTrudnosi != 3);

                int maxNum = 0;
                int minNum = 0;
                switch (poziomTrudnosi) {
                    case 1:
                        System.out.println("Wybrany poziom gry: ŁATWY");
                        maxNum = 100;
                        minNum = 1;
                        break;
                    case 2:
                        System.out.println("Wybrany poziom gry: ŚREDNI");
                        maxNum = 10000;
                        minNum = 1;
                        break;
                    case 3:
                        System.out.println("Wybrany poziom gry: TRUDNY");
                        maxNum = 1000000;
                        minNum = 1;
                }


// ##### TWORZENIE GRACZY >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                Gracz[] gracze = new Gracz[iloscGraczy];
                Scanner scanner = new Scanner(System.in);

                System.out.println(iloscGraczy);

                for (int i = 0; i < iloscGraczy; i++) {
                    if (i == (iloscGraczy - 1) && trybGry == 1) {
                        Gracz nowyGracz = new Gracz();
                        nowyGracz.nickname = "AI";
                        //nowyGracz.score = 0;
                        //nowyGracz.winner = false;
                        nowyGracz.isAI = true;
                        nowyGracz.myNumber = losowanieLiczy(minNum, maxNum);
                        nowyGracz.max = maxNum;
                        nowyGracz.min = minNum;
                        gracze[i] = nowyGracz;
                        System.out.println(gracze[i].myNumber);
                    } else {
                        System.out.print("Podaj nickname gracza " + (i + 1) + ": ");
                        String nickname = scanner.next();
                        Gracz nowyGracz = new Gracz();
                        nowyGracz.nickname = nickname;
                        //nowyGracz.score = 0;
                        //nowyGracz.winner = false;
                        //nowyGracz.isAI = false;
                        nowyGracz.max = maxNum;
                        nowyGracz.min = minNum;
                        System.out.println("Teraz wskarz twoją liczbę, którą odgadnąć ma przeciwnik");
                        nowyGracz.myNumber = wskazywanieLiczby(minNum, maxNum);
                        gracze[i] = nowyGracz;
                    }
                }


// ######### LOGIKA GRY >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                System.out.println("zaczynamy gre");

                int proba = 0;
                int kG = 0; // kolejGracza
                int szukanaLiczba;
                boolean graTrwaDalej = true;
                boolean pomocniczy = false; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki

                while (graTrwaDalej) {
                    System.out.println("Kolej gracza: " + gracze[kG].nickname);

                    if (gracze[kG].isAI == true) {
                        proba = zaawansowaneAI(gracze[kG].min, gracze[kG].max, gracze[kG].score, minNum, maxNum);
                        System.out.println("AI probuje odgadnac liczbe. Podaje: " + proba);
                    } else {
                        System.out.println("Spróbuj odgadnąć szukaną liczbę");
                        proba = wskazywanieLiczby(minNum, maxNum);
                    }


                    if (kG + 1 < gracze.length) {
                        szukanaLiczba = gracze[kG + 1].myNumber;
                    } else {
                        szukanaLiczba = gracze[0].myNumber;
                    }

                    if (proba == szukanaLiczba) {
                        System.out.println(">>> BRAWO! <<< " + proba + " to prawidłowa liczba. >>> WYGRAŁEŚ! <<< Licz podjętych prób: " + gracze[kG].score);
                        gracze[kG].winner = true;
                        if (kG + 1 != iloscGraczy) {
                            System.out.println("Nie jesteś jednak ostatnim graczem. Dajmy szaneś pozostałym graczą na dokończenie kolejki");
                        }
                        //graTrwaDalej = false; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki
                        pomocniczy = true;// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki
                    } else if (proba > szukanaLiczba) {
                        System.out.println("Za dużo. Nastepnym razem wskaz mniejsza wartośc.");
                        gracze[kG].max = proba - 1;
                    } else {
                        System.out.println("Za mało. Nastepnym razem wskaz większą wartość.");
                        gracze[kG].min = proba + 1;
                    }
                    gracze[kG].score++;
                    if (kG + 1 == iloscGraczy) {
                        kG = 0;
                        if (pomocniczy == true) {// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki
                            graTrwaDalej = false;// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki
                            System.out.println("KONIEC GRY");
                            System.out.println("*************************");
                        }// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<do poprawki
                    } else {
                        kG++;
                    }
                }

                for (Gracz gracz : gracze) {
                    if (gracz != null && gracz.winner) {
                        Wygrany wygrany = new Wygrany();
                        wygrany.nickname = gracz.nickname;
                        wygrany.score = gracz.score;
                        listaWygranych.add(wygrany);
                    }
                }
                zapiszListeWygranych(listaWygranych);
            } else if (trybGry == 3) {
                System.out.println("Oto lista graczy, którym udało się wygrać rozgrywkę. Pamiętaj, że im niższy wynik - tym lepiej.");
                System.out.println("*************************");
                for (Wygrany wygrany : listaWygranych) {
                    System.out.println(wygrany.nickname + ", Score: " + wygrany.score);

                }
                System.out.println("*************************");

            } else if (trybGry == 4) {
                exit = true;
                System.out.println("Gra zakończona. Mam nadzieję, że dobrze się bawiłeś :)");
            }
        }
    }
}



































