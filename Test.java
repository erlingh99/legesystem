public class Test
{
    public static void main(String[] args)
    {
        Pasient Tore = new Pasient("Tore Juel", "123344568");

        //System.out.println(Tore);

        Resept en = new BlaaResept(new PreparatA("diclofenac", 100, 10, 10), new Spesialist("hans", 123), Tore, 5);

        for (Resept r : Tore.hentResepter())
        {
            System.out.println(r);
        }
    }    
}