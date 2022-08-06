class BlaaResept extends Resept
{
    private final double prisFaktor = 0.25;
    public BlaaResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit)
    {
        super(legemiddel, utskrivendeLege, p, reit);
    }

    @Override
    public String farge()
    {
        return "blaa";
    }

    @Override
    public double prisAaBetale() //75% rabatt på legemiddelet
    {
        return hentLegemiddel().hentPris()*prisFaktor;
    }
}