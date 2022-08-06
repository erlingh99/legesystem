class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T>
{
    @Override
    public void leggTil(T x)
    {
        for (int i = 0; i <= stoerrelse(); i++)
        {
            if (i == stoerrelse())
            {
                super.leggTil(i,x);
                return;
            }
            else if (x.compareTo(hent(i)) < 0)
            {
                super.leggTil(i,x);
                return;
            }
        }
    }

    @Override
    public T fjern()
    {
        return fjern(stoerrelse()-1);
    }

    @Override
    public void leggTil(int pos, T x)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sett(int pos, T x)
    {
        throw new UnsupportedOperationException();
    }
}