class PreparatA extends Legemiddel
{
    private int styrke;
    public PreparatA(String navn, double pris, double mgVirkestoff, int narkotiskStyrke)
    {
        super(navn, pris, mgVirkestoff);
        styrke = narkotiskStyrke;
    }

    protected int hentNarkotiskStyrke()
    {
        return styrke;
    }

    @Override
    public String toString() //legge til de unike egenskapene til utskriften
    {
        return super.toString() + "\nLegemiddeltype: A - narkotisk" + "\nNarkotisk styrke: " + styrke;
    }
}