class PreparatC extends Legemiddel
{
    public PreparatC(String navn, double pris, double mgVirkestoff)
    {
        super(navn, pris, mgVirkestoff);
    }
    
    @Override
    public String toString()
    {
        return super.toString() + "\nLegemiddeltype: C - vanlig";
    }
}