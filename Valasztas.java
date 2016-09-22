package valasztas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Valasztas {

    public static void main(String[] args) throws IOException {
//1. Olvassa be a szavazatok.txt fájl adatait, majd ezek felhasználásával oldja meg a következő feladatokat! Az adatfájlban legfeljebb 100 képviselőjelölt adatai szerepelnek.
        System.out.println("1. feladat");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("szavazatok.txt"));
        ArrayList<Kepviselo> kepviselok = new ArrayList<>();
        String beolvasas; // Miért kell beletenni változóba?

        while ((beolvasas = bufferedReader.readLine()) != null) {

            String[] beolvasasok = beolvasas.split(" ");

            int valasztokerulet = Integer.parseInt(beolvasasok[0]);
            int szavazat = Integer.parseInt(beolvasasok[1]);
            String csaladNev = beolvasasok[2];
            String keresztNev = beolvasasok[3];
            String teljesNev = csaladNev + " " + keresztNev;
            Part part;
            if (beolvasasok[4].equals("GYEP")) {
                part = Part.GYEP;
            } else if (beolvasasok[4].equals("HEP")) {
                part = Part.HEP;
            } else if (beolvasasok[4].equals("TISZ")) {
                part = Part.TISZ;
            } else if (beolvasasok[4].equals("ZEP")) {
                part = Part.ZEP;
            } else {
                part = Part.FUGG;
            }

            kepviselok.add(new Kepviselo(valasztokerulet, szavazat, csaladNev, keresztNev, teljesNev, part));
        }

        for (Kepviselo kepviselo : kepviselok) {
            System.out.println(
                    "választókerület: " + kepviselo.valasztokerulet
                    + ", szavazat: " + kepviselo.szavazat
                    + ", családnév: " + kepviselo.csaladNev
                    + ", keresztnév: " + kepviselo.keresztNev
                    + ", teljes név: " + kepviselo.teljesNev
                    + ", párt: " + kepviselo.part
            );
        }
        System.out.println("A 'szavazatok.txt' fájl adatai beolvasva.");
//2. Hány képviselőjelölt indult a helyhatósági választáson? A kérdésre egész mondatban válaszoljon az alábbi mintához hasonlóan: A helyhatósági választáson 92 képviselőjelölt indult.
        System.out.println("2. feladat");
        System.out.println("A helyhatósági választáson " + kepviselok.size() + " képviselőjelölt indult.");
//3. Kérje be egy képviselőjelölt vezetéknevét és utónevét, majd írja ki a képernyőre, hogy az illető hány szavazatot kapott! Ha a beolvasott név nem szerepel a nyilvántartásban, úgy jelenjen meg a képernyőn az „Ilyen nevű képviselőjelölt nem szerepel a nyilvántartásban!” figyelmeztetés! A feladat megoldása során feltételezheti, hogy nem indult két azonos nevű képviselőjelölt a választáson.
        System.out.println("3. feladat");

        String felhasznaloCsaladnev = getInput("Adja meg egy képviselőjelölt vezetéknevét: ");
        String felhasznaloKeresztnev = getInput("Adja meg egy képviselőjelölt utónevét: ");
        String bekertAdat = felhasznaloCsaladnev.toLowerCase().trim() + " " + felhasznaloKeresztnev.toLowerCase().trim();
        Kepviselo talalat = null;
        int osszesSzavazat = 0;
        double gyepSzavazat = 0;
        double hepSzavazat = 0;
        double tiszSzavazat = 0;
        double zepSzavazat = 0;
        double fuggSzavazat = 0;
        int legtobbSzavazat = 0;

        for (Kepviselo kepviselo : kepviselok) {
            if (legtobbSzavazat < kepviselo.szavazat) {
                legtobbSzavazat = kepviselo.szavazat;
            }
            osszesSzavazat += kepviselo.szavazat;
            if (kepviselo.teljesNev.toLowerCase().equals(bekertAdat)) {
                talalat = kepviselo;
            }
            if (kepviselo.part.equals(Part.GYEP)) {
                gyepSzavazat += (double) kepviselo.szavazat;
            } else if (kepviselo.part.equals(Part.HEP)) {
                hepSzavazat += (double) kepviselo.szavazat;
            } else if (kepviselo.part.equals(Part.TISZ)) {
                tiszSzavazat += (double) kepviselo.szavazat;
            } else if (kepviselo.part.equals(Part.ZEP)) {
                zepSzavazat += (double) kepviselo.szavazat;
            } else {
                fuggSzavazat += (double) kepviselo.szavazat;
            }
        }
        ArrayList<Integer> legtobbek = new ArrayList<>();
        int index = 0;
        for (Kepviselo kepviselo : kepviselok) {
            if (kepviselo.szavazat == legtobbSzavazat) {
                legtobbek.add(index);
            }
            index++;

        }
        if (talalat != null) {
            System.out.println(talalat.teljesNev + " " + talalat.szavazat + " szavazatot kapott.");
        } else {
            System.out.println("Ilyen nevű képviselőjelölt nem szerepel a nyilvántartásban!");
        }

//4. Határozza meg, hányan adták le szavazatukat, és mennyi volt a részvételi arány! (A részvételi arány azt adja meg, hogy a jogosultak hány százaléka vett részt a szavazáson.) A részvételi arányt két tizedesjegy pontossággal, százalékos formában írja ki a képernyőre! Például: A választáson 5001 állampolgár, a jogosultak 40,51%-a vett részt. 
        System.out.println(
                "4. feladat");
        double valasztasraJogosult = 12345;
        double osszesSzavazatDouble = (double) osszesSzavazat;
        double reszveteliArany = osszesSzavazat * 100 / valasztasraJogosult;
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println(
                "A választáson " + osszesSzavazat + " állampolgár, a jogosultak " + df.format(reszveteliArany) + "%-a vett részt.");
//5. Határozza meg és írassa ki a képernyőre az egyes pártokra leadott szavazatok arányát az összes leadott szavazathoz képest két tizedesjegy pontossággal! A független jelölteket együtt, „Független jelöltek” néven szerepeltesse! Például: Zöldségevők Pártja= 12,34% Független jelöltek= 23,40% 
        System.out.println(
                "5. feladat");
        System.out.println(
                "GYEP: " + df.format(gyepSzavazat * 100 / osszesSzavazatDouble) + "%");
        System.out.println(
                "HEP: " + df.format(hepSzavazat * 100 / osszesSzavazatDouble) + "%");
        System.out.println(
                "TISZ: " + df.format(tiszSzavazat * 100 / osszesSzavazatDouble) + "%");
        System.out.println(
                "ZEP: " + df.format(zepSzavazat * 100 / osszesSzavazatDouble) + "%");
        System.out.println(
                "FUGG: " + df.format(fuggSzavazat * 100 / osszesSzavazatDouble) + "%");
//6. Melyik jelölt kapta a legtöbb szavazatot? Jelenítse meg a képernyőn a képviselő vezeték- és utónevét, valamint az őt támogató párt rövidítését, vagy azt, hogy független! Ha több ilyen képviselő is van, akkor mindegyik adatai jelenjenek meg!
        System.out.println(
                "6. feladat");
        System.out.println(legtobbSzavazat);
        for (int i = 0; i < legtobbek.size(); i++) {
            System.out.println(kepviselok.get(legtobbek.get(i)).teljesNev + " " + kepviselok.get(legtobbek.get(i)).part);
        }
//7. Határozza meg, hogy az egyes választókerületekben kik lettek a képviselők! Írja ki a választókerület sorszámát, a győztes vezeték- és utónevét, valamint az őt támogató párt rövidítését, vagy azt, hogy független egy-egy szóközzel elválasztva a kepviselok.txt nevű szöveges fájlba! Az adatok a választókerületek száma szerinti sorrendben jelenjenek meg! Minden sorba egy képviselő adatai kerüljenek
        System.out.println("7. feladat");
        int valasztokerulet = 1;
        int legtobbKeruletenkent = 0;
        int legtobbIndexe = 0;
        for (int i = 0; i < kepviselok.size(); i++) {

            if (valasztokerulet == kepviselok.get(i).valasztokerulet) {
                if (kepviselok.get(i).szavazat > legtobbKeruletenkent) {
                    legtobbKeruletenkent = kepviselok.get(i).szavazat;
                    legtobbIndexe = i;
                }
            }
            if (i == kepviselok.size() - 1) {
                System.out.println(kepviselok.get(legtobbIndexe).valasztokerulet + " "
                        + kepviselok.get(legtobbIndexe).teljesNev + " "
                        + kepviselok.get(legtobbIndexe).part);
                i = 0;
                legtobbIndexe = 0;
                valasztokerulet++;
                legtobbKeruletenkent = 0;
            }
            if (valasztokerulet > 8) {
                break;
            }
        }
    }

    private static String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();

        }
    }

    private static class Kepviselo {

        int valasztokerulet;
        int szavazat;
        String csaladNev;
        String keresztNev;
        String teljesNev;
        Part part;

        public Kepviselo(int valasztokerulet, int szavazat, String csaladNev, String keresztNev, String teljesNev, Part part) {
            this.valasztokerulet = valasztokerulet;
            this.szavazat = szavazat;
            this.csaladNev = csaladNev;
            this.keresztNev = keresztNev;
            this.teljesNev = teljesNev;
            this.part = part;
        }
    }

    private enum Part {
        GYEP,
        HEP,
        TISZ,
        ZEP,
        FUGG
    }

}
