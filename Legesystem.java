import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.*;

class Legesystem
{
    static SortertLenkeliste<Lege> leger = new SortertLenkeliste<>();
    static Lenkeliste<Pasient> pasienter = new Lenkeliste<>();
    static Lenkeliste<Resept> resepter = new Lenkeliste<>();
    static Lenkeliste<Legemiddel> legemidler = new Lenkeliste<>();

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Velkommen til legesystemet, vil du lese inn data fra fil? (Y/N)");
        String in = taInput();
        if (in.toLowerCase().equals("y"))
        {
            readFromFile();
        }
        while (!in.equals("quit"))
        {
            hovedmeny();
            in = taInput().toLowerCase();
            switch (in)
            {
                case "print":
                    printAll();
                    break;
                case "add":
                    add();
                    break;
                case "use":
                    use();
                    break;
                case "stats":
                    stats();
                    break;
                case "write":
                    writeToFile();
                    break;
                default:
                    System.out.println("Ugyldig input: " + in);
            }
        }
        input.close();
    }
    
    private static void hovedmeny()
    {
        System.out.print("\n-----------------------------Legesystem hovedmeny-----------------------------\n");
        System.out.printf("%-65s%s","- For aa skrive ut all informasjon i systemet, skriv:","\"print\"\n");
        System.out.printf("%-65s%s","- For aa legge til nytt element i systemet, skriv:","\"add\"\n");
        System.out.printf("%-65s%s","- For aa bruke en eksisterende resept fra pasient, skriv:","\"use\"\n");
        System.out.printf("%-65s%s","- For aa se statistikk, skriv:","\"stats\"\n");
        System.out.printf("%-65s%s","- For aa skrive all data til fil, skriv:","\"write\"\n");
        System.out.printf("%-65s%s","- For aa avslutte, skriv:","\"quit\"\n");
    }

    private static void printAll()
    {
        System.out.println();
        print(pasienter);
        print(legemidler);
        print(leger);
        print(resepter);
    }

    private static void print(Lenkeliste ut)
    {
        if (ut.stoerrelse() > 0)
        {
            System.out.println(ut + "\n");
        }
    }

    private static void add()
    {
        System.out.println("\nAlternativer for aa legge til nytt element");
        System.out.printf("%-65s%s","- For aa lese fra fil, skriv:", "\"file\"\n");
        System.out.printf("%-65s%s","- For aa legge til manuelt, skriv:","\"manual\"\n");
        String in = taInput().toLowerCase();
        switch (in)
        {
            case "file":
                readFromFile();
                break;
            case "manual":
                addManual();
                break;
            default:
                System.out.println("Ugyldig input: " + in);
                return;
        }
    }

    private static void readFromFile()
    {
        System.out.println("Filplassering:");
        String bane = taInput();
        File open = new File(bane);
        Scanner leser = null;
        try
        {
            leser = new Scanner(open);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ugyldig bane: " + bane + "\nProv igjen fra >add>file.");
            return;
        }

        String type = null;
        while (leser.hasNextLine())
        {
            String[] linje = leser.nextLine().split(",");
            for (int i = 0; i < linje.length; i++)
            {
                linje[i] = linje[i].trim();
            }

            if (linje[0].charAt(0) == '#') //Overskrift, neste ordet sier hvilken type
            {
                type = linje[0].split(" ")[1].trim();
                continue;
            }

            if (type.equals("Legemidler"))
            {
                String navn = linje[0]; String prepType = linje[1];
                double pris = Double.parseDouble(linje[2]); double virkestoff = Double.parseDouble(linje[3]);
                int styrke = 0; 
                if (!prepType.equals("c"))
                {
                    styrke = Integer.parseInt(linje[4]);
                }
                addLegemiddel(navn, prepType, virkestoff, pris, styrke);
            }
            else if (type.equals("Leger"))
            {
                String navn = linje[0]; int kontrollID = Integer.parseInt(linje[1]);
                addLege(navn, kontrollID);
            }
            else if (type.equals("Resepter"))
            {
                String typeResept = linje[0]; int medNr = Integer.parseInt(linje[1]);
                String legeNavn = linje[2]; int persID = Integer.parseInt(linje[3]);
                int reit = 1;
                if (!typeResept.equals("prevensjon"))
                {
                    reit = Integer.parseInt(linje[4]);
                }
                addResept(typeResept, legeNavn, medNr, persID, reit);
            }
            else if (type.equals("Pasienter"))
            {
                String navn = linje[0]; String fnr = linje[1];
                addPasient(navn, fnr);
            }
        }
        leser.close();
        System.out.println("Innlesing av " + open.getName() + " ferdig");
    }

    private static void addManual()
    {
        System.out.printf("%-65s%s","- For aa legge til en lege, skriv:", "\"lege\"\n");
        System.out.printf("%-65s%s","- For aa legge til en pasient, skriv:","\"pasient\"\n");
        System.out.printf("%-65s%s","- For aa legge til en resept, skriv:", "\"resept\"\n");
        System.out.printf("%-65s%s","- For aa legge til et legemiddel, skriv:","\"legemiddel\"\n");
        String in = taInput().toLowerCase();
        try
        {
            switch (in)
            {
                case "lege":
                    addLege(taInput("Hva heter legen?"), Integer.parseInt(taInput("Spesialist kontrollID? 0 hvis ingen")));
                    break;
                case "pasient":
                    addPasient(taInput("Hva heter pasienten?"), taInput("Fodselsnummer?"));
                    break;
                case "resept":
                    String t = taInput("Type resept? (hvit, blaa, prevensjon, militaer)");
                    listProcess(leger, d -> System.out.println(d.hentNavn()));
                    String l = taInput("Legenavn?");
                    listProcess(legemidler, m -> System.out.println(m.hentID() + ": " + m.hentNavn()));
                    int med = Integer.parseInt(taInput("Legemiddel ID?"));
                    listProcess(pasienter, p -> System.out.println(p.hentID() + ": " + p. hentNavn()));
                    addResept(t, l, med, Integer.parseInt(taInput("Pasient ID?")), Integer.parseInt(taInput("reit?")));
                    break;
                case "legemiddel":
                    addLegemiddel(taInput("Navn på legemiddel:"), taInput("Type:"), Double.parseDouble(taInput("virkestoff")), Double.parseDouble(taInput("pris")), Integer.parseInt(taInput("Styrke")));
                    break;
                default:
                    System.out.println("Ugyldig input: " + in);
                    return;
            }  
        }
        catch (NumberFormatException e)
        {
            System.out.println("Skriv inn et tall der det skal");
        }
    }

    private static void use()
    {
        if (pasienter.stoerrelse() == 0)
        {
            System.out.println("Det er ingen pasienter i systemet");
            return;
        }

        System.out.println("\nHvilken pasient vil du se resepter for?");
        for(int i = 0; i < pasienter.stoerrelse(); i++)
        {
            Pasient p = pasienter.hent(i);
            System.out.println(i + ": " + p.hentNavn() + ", (fnr) " + p.hentFodselsNummer());
        }

        String inn = taInput();
        int in = sjekkTallInn(inn);

        if (in < 0 || in >= pasienter.stoerrelse())
        {
            System.out.println("Ugyldig input: " + inn);
            return;
        }
        
        Pasient p = pasienter.hent(in);

        System.out.println("\nValgt pasient: " + p.hentNavn() + ", (fnr) " + p.hentFodselsNummer());

        if (p.hentResepter().stoerrelse() == 0)
        {
            System.out.println(p.hentNavn() + " har ingen resepter.");
            return;
        }

        System.out.println("Hvilken resept vil du bruke?");
        for(int i = 0; i < p.hentResepter().stoerrelse(); i++)
        {
            Resept r = p.hentResepter().hent(i);
            System.out.println(i + ": " + r.hentLegemiddel().hentNavn() + " (" + r.hentReit() + " reit)");
        }

        inn = taInput();
        in = sjekkTallInn(inn);

        if (in < 0 || in >= p.hentResepter().stoerrelse())
        {
            System.out.println("Ugyldig input: " + inn);
            return;
        }

        Resept hentet = p.hentResepter().hent(in);

        if (hentet.bruk())
        {
            System.out.println("\nBrukte resept paa " + hentet.hentLegemiddel().hentNavn() + ". Antall gjenvaerende reit: " + hentet.hentReit());
        }
        else
        {
            System.out.println("\nKunne ikke bruke resept paa " + hentet.hentLegemiddel().hentNavn() + " (ingen gjenvaerende reit).");
        }
    }

    private static void stats()
    {
        int teller = listCounter(resepter, (Resept r) -> r.hentLegemiddel() instanceof PreparatB);
        System.out.printf("%-65s%s","Antall utskrevne resepter paa vanedannende legemidler:",teller+"\n\n");
        System.out.println("Statistikk over resepter til narkotiske legemidler:");
        System.out.printf("%-58s%s", "Utskrevet av:", "Antall:\n");
        for(Lege l : leger)
        {
            teller = listCounter(l.hentResepter(), r -> r.hentLegemiddel() instanceof PreparatA);
            if (teller > 0)
            {
                System.out.printf("%-58s%s", "\t" + l.hentNavn(), teller+"\n");
            }
        }

        System.out.printf("%-41s%s", "Innehavere av resepter:", "Antall gyldige resepter:\n");
        for(Pasient p: pasienter)
        {
            teller = listCounter(p.hentResepter(), r -> r.hentLegemiddel() instanceof PreparatA);
            if (teller > 0)
            {
                System.out.printf("%-58s%s", "\t" + p.hentNavn(), teller+"\n");
            }
        }
    }

    private static void writeToFile()
    {
        try
        {
            File ut = new File("utfil.txt");
            PrintStream a = new PrintStream(ut);
            a.println("# Pasienter (navn, fnr)");
            for(Pasient p : pasienter)
            {
                a.println(p.hentNavn() + ", " + p.hentFodselsNummer());
            }
            a.println("# Legemidler (navn, type, pris, virkestoff [, styrke])");
            for(Legemiddel l : legemidler)
            {
                String klasse = l.getClass().getSimpleName().toLowerCase();
                a.print(l.hentNavn() + ", " + klasse.charAt(l.getClass().getSimpleName().length()-1)
                        + ", " + l.hentPris() + ", " + l.hentVirkestoff());
                try
                {
                    PreparatA prepa = (PreparatA)l;
                    a.println(", " + prepa.hentNarkotiskStyrke());
                }
                catch (ClassCastException e)
                {
                    try
                    {
                        PreparatB prepb = (PreparatB)l;
                        a.println(", " + prepb.hentVanedannendeStyrke());
                    }
                    catch (ClassCastException cce)
                    {
                        a.println();
                    }
                }
                
            }
            a.println("# Leger (navn, kontrollid / 0 hvis vanlig lege)");
            for(int i = 0; i < leger.stoerrelse(); i++)
            {
                final int j = i;
                Lege l = listFinder(leger, lege -> lege.hentID() == j);
                a.print(l.hentNavn() + ", ");
                try
                {
                    Spesialist s = (Spesialist)l;
                    a.println(s.hentKontrollID());
                }
                catch (ClassCastException e)
                {
                    a.println("0");
                }
            }
            a.println("# Resepter (legemiddelNummer, legeNavn, persID, reit)");
            for(Resept r : resepter)
            {
                int lengde = r.getClass().getSimpleName().length();
                final int RESEPT_LENGDE = "resept".length();
                String klasseShort = r.getClass().getSimpleName().toLowerCase().substring(0, lengde-RESEPT_LENGDE);
                if (klasseShort.equals("p"))
                {
                    klasseShort = "prevensjon";
                }
                a.print(klasseShort + ", " + r.hentLegemiddel().hentID() + ", " + r.hentLege().hentNavn() + ", " + r.hentPasient().hentID());
                if (!(r instanceof PResept))
                {
                    a.println(", " + r.hentReit());
                }
                else
                {
                    a.println();
                }
            }
            a.close();
            System.out.println("Filen heter utfil.txt og ligger her:\n" + ut.getCanonicalPath());
        }
        catch (Exception e)
        {
            System.out.println("Kunne ikke lage filen");
        }
    }

    private static int sjekkTallInn(String in)
    {
        int inn;
        try
        {
            inn = Integer.parseInt(in);
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
        return inn;
    }

    private static String taInput(String s)
    {
        System.out.println(s);
        return taInput();
    }

    private static String taInput()
    {
        System.out.print("> ");
        String in = input.nextLine();
        if (in.toLowerCase().equals("quit"))
        {
            input.close();
            System.exit(1);
        }
        return in;
    }

    private static <T> void listProcess(Iterable<T> liste, Consumer<T> action)
    {
        for(T t : liste)
        {
            action.accept(t);
        }
    }

    private static <T> int listCounter(Iterable<T> liste, Predicate<T> filter)
    {
        int teller = 0;
        for(T t : liste)
        {
            if(filter.test(t))
            {
                teller++;
            }
        }
        return teller;
    }

    private static <T> T listFinder(Iterable<T> liste, Predicate<T> filter)
    {
        for(T t : liste)
        {
            if(filter.test(t))
            {
                return t;
            }
        }
        return null;
    }

    public static void addLegemiddel(String navn, String type, double virkestoff, double pris, int styrke)
    {
        if (listFinder(legemidler, l -> l.hentNavn().equals(navn)) != null)
        {
            System.out.print("Det finnes allerede et legemiddel i systemet med navnet " + navn);
            return;
        }
        else if (virkestoff < 0 || pris < 0 || styrke <0)
        {
            System.out.println("Ugyldig virkestoff/pris/styrke");
            return;
        }
        switch (type) //Switch sammenligner med equals
        {
            case "a":
                legemidler.leggTil(new PreparatA(navn, pris, virkestoff, styrke));
                break;
            case "b":
                legemidler.leggTil(new PreparatB(navn, pris, virkestoff, styrke));
                break;
            case "c":
                legemidler.leggTil(new PreparatC(navn, pris, virkestoff));
                break;
            default:
                System.out.println("Ugyldig type: " + type);
                break;
        }
    }

    public static void addLege(String navn, int kontrollID)
    {
        if (listFinder(leger, l -> l.hentNavn().equals(navn)) != null)
        {
            System.out.println("Det finnes allerede en lege med navnet " + navn + " i systemet");
            return;
        }
        switch (kontrollID)
        {
            case 0:
                leger.leggTil(new Lege(navn));
                break;
            default:
                leger.leggTil(new Spesialist(navn, kontrollID));
                break;
        }
    }

    public static void addPasient(String navn, String fnr)
    {
        if (listFinder(pasienter, p -> p.hentFodselsNummer().equals(fnr)) != null)
        {
            System.out.println("Det finnes allerede en pasient med fodselsnummeret " + fnr + " i systemet");
            return;
        }
        pasienter.leggTil(new Pasient(navn, fnr));
    }

    public static void addResept(String type, String legeNavn, int medID, int pasientID, int reit)
    {
        Lege leg = listFinder(leger, l -> l.hentNavn().equals(legeNavn));
        Pasient pas = listFinder(pasienter, p -> p.hentID() == pasientID);
        Legemiddel med = listFinder(legemidler, m -> m.hentID() == medID);
        
        if (leg == null || pas == null || med == null)
        {
            System.out.println("Kunne ikke lage resept, finner ikke lege/pasient/legemiddel");
            return;
        }
        else if (reit <= 0)
        {
            System.out.println("Ugyldig stoerrelse paa reit: " + reit);
            return;
        }
        try
        {
            switch (type)
            {
                case "blaa":
                    resepter.leggTil(leg.skrivBlaaResept(med, pas, reit));
                    break;
                case "hvit":
                    resepter.leggTil(leg.skrivHvitResept(med, pas, reit));
                    break;
                case "militaer":
                    resepter.leggTil(leg.skrivMilitaerResept(med, pas, reit));
                    break;
                case "prevensjon":
                    resepter.leggTil(leg.skrivPResept(med, pas));
                    break;
                default:
                    System.out.println(type + " er ikke en godtatt resepttype");
                    return;
            }
        }
        catch (UlovligUtskrift e)
        {
            System.out.println("Ulovlig utskrift. " + leg.hentNavn() + " har ikke lov til aa " 
                                + "skrive ut " + med.hentNavn());
            return;
        }
    }
}