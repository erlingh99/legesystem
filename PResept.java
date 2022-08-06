class PResept extends HvitResept
{
    private final int prisAvslag = 128;

    public PResept(Legemiddel legemiddel, Lege utskrivendeLege, Pasient p)
    {
        super(legemiddel, utskrivendeLege, p, 3); //alle P-resepter har reit = 3
    }

    @Override
    public double prisAaBetale()
    {
        return Math.max(hentLegemiddel().hentPris() - prisAvslag, 0); //prisen er legemiddelets pris minus 128, men minst 0
    }
}