import java.util.Scanner;

public class VendingMachin {
    enum coin
    {
        ONE, TWO, FIVE, TEN;
    }
    public static void main(String[] args)
    {
         final int Snack=15;
         int total =0;
        Scanner sc=new Scanner(System.in);
        System.out.println("snack price is :"+Snack);
        System.out.println("The coin value ( ONE, TWO, FIVE, TEN) :");
        while(total<Snack)
        {
            System.out.print("Enter the coin value :");
            String input=sc.next().toUpperCase();
            coin c;
            try{
                c=coin.valueOf(input);
            }
            catch(IllegalArgumentException e ){
                System.out.println("invalid coin value");
                continue;

            }
            int result=switch(c)
{
    case ONE->1;
    case TWO->2;
    case FIVE->5;
    case TEN->10;
}
    ;total+=result;
    System.out.println("total amount is :"+total);

        }
        System.out.println("change is :"+(total-Snack));
sc.close();
    }
}
