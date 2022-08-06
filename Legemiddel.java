abstract class Legemiddel
{
    private static int noPrep = 0; //felles variabel for alle legemiddel-objekter
    private String navn;
    private int ID;
    private double pris;
    private double mgVirkestoff;
    public Legemiddel(String navn, double pris, double mgVirkestoff)
    {
        ID = noPrep;
        noPrep++;
        this.navn = navn;
        this.pris = pris;
        this.mgVirkestoff = mgVirkestoff;
    }

    protected int hentID()
    {
        return ID;
    }

    protected String hentNavn()
    {
        return navn;
    }

    protected double hentPris()
    {
        return pris;
    }
    
    protected double hentVirkestoff()
    {
        return mgVirkestoff;
    }

    protected void settNyPris(double nyPris)
    {
        pris = nyPris;
    }

    @Override
    public String toString()
    {
        return "------Preparatinfo------\nNavn: " + navn + "\nID: " + ID + "\nPris: " + pris + "\nVirkestoff (mg): " + mgVirkestoff;
    }
}