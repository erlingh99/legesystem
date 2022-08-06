class Spesialist extends Lege implements GodkjenningsFritak
{
    private int kontrollID;
    public Spesialist(String navn, int kontrollID)
    {
        super(navn);
        this.kontrollID = kontrollID;
    }
    
    @Override
    public int hentKontrollID()
    {
        return kontrollID;
    }

    @Override
    protected Resept skrivHvitResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        return new HvitResept(legemiddel, this, p, reit);
    }

    @Override
    protected Resept skrivBlaaResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        return new BlaaResept(legemiddel, this, p, reit);
    }

    @Override
    protected Resept skrivMilitaerResept(Legemiddel legemiddel, Pasient p, int reit) throws UlovligUtskrift
    {
        return new MilitaerResept(legemiddel, this, p, reit);
    }

    @Override
    protected Resept skrivPResept(Legemiddel legemiddel, Pasient p) throws UlovligUtskrift
    {
        return new PResept(legemiddel, this, p);
    }

    @Override
    public String toString()
    {
        return super.toString() + "\nSpesialist - kontroll ID: " + kontrollID;
    }
}