class MilitaerResept extends HvitResept
{
    public MilitaerResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p, int reit)
    {
        super(legemiddel, utskrivendeLege, p, reit);
    }

    @Override
    public double prisAaBetale()
    {
        return 0; //gratis for vernepliktige
    }
}