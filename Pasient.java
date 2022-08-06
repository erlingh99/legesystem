class Pasient
{
    private String navn;
    private String fodselsNummer;
    private static int antallPasienter = 0;
    private int ID;
    private Stabel<Resept> resepter = new Stabel<Resept>();

    public Pasient(String navn, String fodselsNummer)
    {
        this.navn = navn;
        this.fodselsNummer = fodselsNummer;
        ID = antallPasienter;
        antallPasienter++;
    }

    public String hentNavn()
    {
        return navn;
    }

    public String hentFodselsNummer()
    {
        return fodselsNummer;
    }

    public int hentID()
    {
        return ID;
    }

    public void leggTilResept(Resept r)
    {
        resepter.leggPaa(r);
    }

    public Stabel<Resept> hentResepter()
    {
        return resepter;
    }

    public String skrivUtResepter()
    {
        return resepter.toString();
    }

    @Override
    public String toString()
    {
        StringBuffer s = new StringBuffer("------Pasientinfo------\nNavn: " + navn + "\nFodselsnummer: " + fodselsNummer + "\nID: " + ID);
        if (resepter.stoerrelse() > 0)
        {
            s.append("\nHar foelgende resept" + (resepter.stoerrelse() > 1 ? "er:" : ":"));
            for(Resept r : resepter)
            {
                s.append("\n\tID:" + r.hentLegemiddel().hentID() + ", " + r.hentLegemiddel().hentNavn() + " (" + r.hentReit() + " reit)");
            }
        }
        else
        {
            s.append("\nHar ingen resepter");
        }
        return s.toString();
    }
}