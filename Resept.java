abstract class Resept
{
    private static int noResept = 0;
    private int ID;
    private Legemiddel legemiddel;
    private Lege utskrivendeLege;
    private Pasient p;
    private int reit;

    public Resept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit)
    {
        ID = noResept;
        noResept++;
        this.legemiddel = legemiddel;
        this.utskrivendeLege = utskrivendeLege;
        this.p = p;
        this.reit = reit;
        p.leggTilResept(this);
        utskrivendeLege.leggTilResept(this);
    }

    protected int hentID()
    {
        return ID;
    }

    protected Legemiddel hentLegemiddel()
    {
        return legemiddel;
    }

    protected Lege hentLege()
    {
        return utskrivendeLege;
    }

    protected Pasient hentPasient()
    {
        return p;
    }

    protected int hentReit()
    {
        return reit;
    }

    public boolean bruk()
    {
        if (reit > 0)
        {
            reit--;
            return true;
        }
        return false;
    }

    abstract public String farge();
    abstract public double prisAaBetale();

    @Override
    public String toString()
    {
        return "------Reseptinfo------\nLegemiddel: " + legemiddel.hentNavn() + "\nID: " + ID
            + "\nUtskrivende lege: " + utskrivendeLege.hentNavn() + "\nPasient: " + p.hentNavn()
            + "\nAntall ganger igjen paa resepten: " + reit + "\nReseptfarge: " + farge()
            + "\nPris: " + prisAaBetale() + "\nResepttype: " + this.getClass().getSimpleName();
            //trenger ikke override toString i subklassene ettersom all informasjon er tilgjengelig i denne baseklassen
            //og de abstrakte metodene som gir riktig kall pga polymorfi
    }
}