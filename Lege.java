class Lege implements Comparable<Lege>
{
    private String navn;
    private static int antLeger = 0;
    private int ID = 0;
    private Lenkeliste<Resept> utskrevneResepter = new Lenkeliste<>();

    public Lege(String navn)
    {
        this.navn = navn;
        ID = antLeger;
        antLeger++;
    }

    protected String hentNavn()
    {
        return navn;
    }

    protected int hentID()
    {
        return ID;
    }

    protected Resept skrivHvitResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        if (legemiddel instanceof PreparatA) //vanlig lege kan ikke skrive ut preparat A
        {
            throw new UlovligUtskrift(this, legemiddel);
        }
        return new HvitResept(legemiddel, this, p, reit);
    }

    protected Resept skrivBlaaResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        if (legemiddel instanceof PreparatA) //vanlig lege kan ikke skrive ut preparat A
        {
            throw new UlovligUtskrift(this, legemiddel);
        }
        return new BlaaResept(legemiddel, this, p, reit);
    }

    protected Resept skrivMilitaerResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        if (legemiddel instanceof PreparatA) //vanlig lege kan ikke skrive ut preparat A
        {
            throw new UlovligUtskrift(this, legemiddel);
        }
        return new MilitaerResept(legemiddel, this, p, reit);
    }

    protected Resept skrivPResept(Legemiddel legemiddel, Pasient p) throws UlovligUtskrift
    {
        if (legemiddel instanceof PreparatA) //vanlig lege kan ikke skrive ut preparat A
        {
            throw new UlovligUtskrift(this, legemiddel);
        }
        return new PResept(legemiddel, this, p);
    }

    protected void leggTilResept(Resept r)
    {
        utskrevneResepter.leggTil(r);
    }

    protected Lenkeliste<Resept> hentResepter()
    {
        return utskrevneResepter;
    }

    @Override
    public String toString()
    {
        StringBuffer s = new StringBuffer("------Legeinfo------\nNavn: " + navn);
        if (utskrevneResepter.stoerrelse() > 0)
        {
            s.append("\nHar skrevet ut foelgende resept" + (utskrevneResepter.stoerrelse() > 1 ? "er:" : ":"));
            for(Resept r : utskrevneResepter)
            {
                s.append("\n\tID:" + r.hentID() + ", " + r.hentLegemiddel().hentNavn() + " til " + r.hentPasient().hentNavn() + " (" + r.hentReit() + " reit)");
            }
        }
        else
        {
            s.append("\nHar ikke skrevet ut noen resepter");
        }
        return s.toString();
    }

    public int compareTo(Lege lege)
    {
        return navn.toLowerCase().compareTo(lege.hentNavn().toLowerCase());//sammenligne slik at store mot små bokstaver er likegyldig
    }
}