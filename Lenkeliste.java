import java.util.Iterator;

class Lenkeliste<T> implements Liste<T>
{
    private class Node
    {
        Node neste = null;
        Node forrige = null;
        T data = null;
        public Node(T data)
        {
            this.data = data;
        }
    }

    private class LenkelisteIterator implements Iterator<T>
    {
        private int teller = 0;
        public boolean hasNext()
        {
            if (teller < size)
            {
                return true;
            }
            return false;
        }

        public T next()
        {
            T hentet = hent(teller);
            teller++;
            return hentet;
        }
    }

    private Node start = null; //peker til første objekt i listen
    private Node slutt = null; //peker til siste objekt i listen
    private int size = 0; //antall elementer i listen
    private Node p = null; //Node-peker for iterering gjennom listen

    public int stoerrelse()
    {
        int teller = 0;
        p = start;
        while (p != null)
        {
            p = p.neste;
            teller++;
        }
        if (teller != size)
        {
            throw new RuntimeException("Teller matcher ikke size: " + teller + " - "+ size);
        }
        return size;
    }

    public void leggTil(int pos, T x)
    {
        if(pos > size)
        {
            throw new UgyldigListeIndeks(pos);
        }
        if (pos == size) //sett inn på slutten
        {
            p = new Node(x);
            p.forrige = slutt;
            slutt = p;
            if (pos == 0) //også på starten, siden det er eneste element
            {
                start = p;
            }
            else
            {
                p.forrige.neste = p; //da er det et element før som kan peke på p
            }
            size++;
            return;         
        }

        p = hentNode(pos); //henter noden på posisjonen den nye noden skal inn på
        Node ny = new Node(x);
        if (pos == 0)
        {
            start = ny;
        }
        else
        {
            p.forrige.neste = ny; //noden før p sin neste-peker skal peke på den nye noden
        }

        ny.forrige = p.forrige; //ny noden skal peke på noden før p
        ny.neste = p; //Den nye noden sin neste-peker skal peke på p
        p.forrige = ny; //p sin forrige-peker skal peke på den nye noden
        
        size++;
    }

    public void leggTil(T x)
    {
        leggTil(size, x);
    }

    public void sett(int pos, T x)
    {
        p = hentNode(pos);
        p.data = x;
    }

    private Node hentNode(int pos)
    {
        if (pos >= size || pos < 0)
        {
            if (size == 0) //om listen er tom
            {
                pos = -1; //kaste med feilkode -1
            }
            throw new UgyldigListeIndeks(pos);
        }

        if (size-pos < pos) //kortere å iterere baklengs
        {
            p = slutt;
            for (int i = size-1; i > pos; i--)
            {
                p = p.forrige;
            }
            return p;
        }

        p = start;
        for (int i = 0; i < pos; i++)
        {
            p = p.neste;
        }
        return p;
    }

    public T hent(int pos)
    {
        return hentNode(pos).data;
    }

    public T fjern(int pos)
    {
        p = hentNode(pos);
        if (size - 1 == 0) //eneste elementet skal fjernes
        {
            start = null;
            slutt = null;
        }
        else if (pos == 0) //første element skal fjernes
        {
            start = p.neste;
            start.forrige = null;
        }
        else if (pos == size - 1) //siste element skal fjernes
        {
            slutt = p.forrige;
            slutt.neste = null;
        }
        else //et element med elementer på begge sider skal fjernes
        {
            p.forrige.neste = p.neste;
            p.neste.forrige = p.forrige;
        }
        size--;
        return p.data;
    }

    public T fjern()
    {
        return fjern(0); //første elementets posisjon
    }

    @Override
    public String toString()
    {
        String[] ut = new String[size];
        for (int i = 0; i < size; i++)
        {
            ut[i] = hent(i).toString();
        }
        return String.join("\n\n", ut);
    }

    public Iterator<T> iterator()
    {
        return new LenkelisteIterator();
    }
}