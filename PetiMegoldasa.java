package com.jestroo.core.config;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Valasztas {

    private static final int JOGOSULTAK_SZAMA = 12345;

    private List<Kepviselo> kepviselok = new ArrayList<>();

    private DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        Valasztas valasztas = new Valasztas();
        //A program elso argumentuma a bementi file eleresi utja.
        if (args.length >= 1) {
            File szavazatok = new File(args[0]);
            //Ellenorizzuk hogy a file letezik es valoban file es nem konyvtar (Javaban mindketto File objektumnak minosul)
            if (szavazatok.exists() && szavazatok.isFile()) {
                valasztas.futtat(szavazatok);
            } else {
                throw new RuntimeException("A megadott file nem letezik.");
            }
        } else {
            throw new RuntimeException("A program elso argumentuma a bemeneti file utvonala kell hogy legyen.");
        }
    }

    private void futtat(File szavazatok) {
        feladat1(szavazatok);
        System.out.println("");
        feladat2();
        System.out.println("");
        feladat3();
        System.out.println("");
        feladat4();
        System.out.println("");
        feladat5();
        System.out.println("");
        feladat6();
        System.out.println("");
        feladat7();
    }

    private void feladat1(File szavazatok) {
        System.out.println("1-es feladat: File beolvasasa kezdodik");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(szavazatok)));
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] elemek = line.split("\\s");
                    if (elemek.length != 5) {
                        throw new IllegalArgumentException("Minden sornak 5 elemet kell tartalmaznia.");
                    }

                    Part part;
                    if (elemek[4].equals("-")) {
                        part = Part.FUGG;
                    } else {
                        part = Part.valueOf(elemek[4]);
                    }
                    kepviselok.add(new Kepviselo(Integer.parseInt(elemek[0]), Integer.parseInt(elemek[1]), elemek[2], elemek[3], part));
                } catch (Exception e) {
                    System.out.println("Az alabbi sor hibas:" + line);
                    e.printStackTrace();
                }
            }
            System.out.println("1-es feladat vege: File beolvasva");
        } catch (Exception e) {
            System.out.println("Hiba tortent a file feldolgozasa kozben.");
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
    }

    private void feladat2() {
        System.out.println("2-es feladat megoldasa: " + kepviselok.size());
    }

    private void feladat3() {
        System.out.println("3-as feladat:");
        String vezeteknev = getInput("Adja meg egy képviselőjelölt vezetéknevét: ");
        String keresztnev = getInput("Adja meg egy képviselőjelölt utónevét: ");
        boolean talalat = false;
        for (Kepviselo kepviselo : kepviselok) {
            if (kepviselo.keresztnev.equalsIgnoreCase(keresztnev) && kepviselo.vezeteknev.equalsIgnoreCase(vezeteknev)) {
                System.out.println("3-as feladat megoldasa: a bekert kepviselo szavazatainak szama:" + kepviselo.szavazatokSzama);
                talalat = true;
                break;
            }
        }
        if (!talalat) {
            System.out.println("Ilyen nevű képviselőjelölt nem szerepel a nyilvántartásban!");
        }

    }

    private void feladat4() {
        System.out.println("4-as feladat:");
        int osszesSzavazat = 0;
        for (Kepviselo kepviselo : kepviselok) {
            osszesSzavazat += kepviselo.szavazatokSzama;
        }
        System.out.println(
                "A választáson " + osszesSzavazat + " állampolgár, a jogosultak " + df.format(((float) osszesSzavazat / (float) JOGOSULTAK_SZAMA) * 100) + "%-a vett részt.");
    }

    private void feladat5() {
        System.out.println("5-ös feladat:");
        Map<Part, Integer> osszegPartonkent = new HashMap<>();
        int osszesSzavazat = 0;
        for (Kepviselo kepviselo : kepviselok) {
            osszesSzavazat += kepviselo.szavazatokSzama;
            if (osszegPartonkent.get(kepviselo.part) == null) {
                osszegPartonkent.put(kepviselo.part, kepviselo.szavazatokSzama);
            } else {
                osszegPartonkent.put(kepviselo.part, osszegPartonkent.get(kepviselo.part) + kepviselo.szavazatokSzama);
            }
        }
        for (Part part : osszegPartonkent.keySet()) {
            System.out.println(part.nev + "= " + df.format((float) osszegPartonkent.get(part) * 100 / (float) osszesSzavazat) + "%");
        }
    }

    private void feladat6() {
        System.out.println("6-os feladat:");
        int legtobb = 0;
        Set<Kepviselo> kivalasztottak = new HashSet<>();
        for (Kepviselo kepviselo : kepviselok) {
            if (kepviselo.szavazatokSzama > legtobb) {
                kivalasztottak.clear();
                kivalasztottak.add(kepviselo);
                legtobb = kepviselo.szavazatokSzama;
            } else if (kepviselo.szavazatokSzama == legtobb) {
                kivalasztottak.add(kepviselo);
            }
        }

        System.out.println("Legtobb szavazatot kaptak:");
        for (Kepviselo kepviselo : kivalasztottak) {
            System.out.println(kepviselo.vezeteknev + " " + kepviselo.keresztnev + " " + (kepviselo.part == Part.FUGG ? "független" : kepviselo.part.name()));
        }
    }

    private void feladat7() {
        System.out.println("7-es feladat:");
        Map<Integer, Kepviselo> gyoztesek = new HashMap<>();
        for (Kepviselo kepviselo : kepviselok) {
            if (gyoztesek.get(kepviselo.kerulet) == null) {
                gyoztesek.put(kepviselo.kerulet, kepviselo);
            } else {
                if (gyoztesek.get(kepviselo.kerulet).szavazatokSzama < kepviselo.szavazatokSzama) {
                    gyoztesek.put(kepviselo.kerulet, kepviselo);
                }
            }
        }
        List<Integer> keruletek = new ArrayList<>(gyoztesek.keySet());
        Collections.sort(keruletek);

        File fout = new File("kepviselok.txt");


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int kerulet : keruletek) {
                Kepviselo gyoztes = gyoztesek.get(kerulet);
                bw.write(kerulet + " " + gyoztes.vezeteknev + " " + gyoztes.keresztnev + " " + gyoztes.part.name());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Hiba tortent az eredmeny kiirasa kozben.");
            e.printStackTrace();
        }

    }

    private String getInput(String prompt) {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        System.out.print(prompt);
        System.out.flush();

        try {
            return stdin.readLine();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private enum Part {
        GYEP("Gyümölcsevők Pártja"),
        HEP("Húsevők Pártja"),
        TISZ("Tejivók Szövetsége"),
        ZEP("Zöldségevők Pártja"),
        FUGG("Független jelöltek");

        final String nev;

        Part(String nev) {
            this.nev = nev;
        }
    }

    private class Kepviselo {

        private int kerulet;

        private int szavazatokSzama;

        private String vezeteknev;

        private String keresztnev;

        private Part part;

        public Kepviselo(int kerulet, int szavazatokSzama, String vezeteknev, String keresztnev, Part part) {
            this.kerulet = kerulet;
            this.szavazatokSzama = szavazatokSzama;
            this.vezeteknev = vezeteknev;
            this.keresztnev = keresztnev;
            this.part = part;
        }
    }
}
