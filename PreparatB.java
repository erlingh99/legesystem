class PreparatB extends Legemiddel
{
    private int vane;
    public PreparatB(String navn, double pris, double mgVirkestoff, int vanedannendeStyrke)
    {
        super(navn, pris, mgVirkestoff);
        vane = vanedannendeStyrke;
    }

    protected int hentVanedannendeStyrke()
    {
        return vane;
    }

    @Override
    public String toString() //legge til de subklasse-spesifikke egenskapene til utskrift
    {
        return super.toString() + "\nLegemiddeltype: B - vanedannende" + "\nVanedannende styrke: " + vane;
    }
}