import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List listTest = new ArrayList<>();
    public static List twinNumbersList = new ArrayList();

    static void getTwinNumbers(ArrayList<Integer> list){
        for(int i=list.size()-2; i>0; i--){
            if(list.get(i+1)-list.get(i)==2){
                twinNumbersList.add(list.get(i));
                twinNumbersList.add(list.get(i+1));
                System.out.println("Twin numbers #" + twinNumbersList.size()/2 + ": " + list.get(i) + " " + list.get(i+1));
                if(twinNumbersList.size()>=10)break;
            }
        }
    }

    public static void main(String[] args) {

        int a,b;
        a = 1;
        b = 100000;

        PrimeNumbers primaryNumber = new PrimeNumbers(a,b);
        listTest = primaryNumber.compute();
        System.out.println("Primary numbers:");
        listTest.forEach(e -> {
            System.out.print(e + " ");
        });
        System.out.println();

        System.out.println("Primary numbers count: " + listTest.size());
        System.out.println("Time: " + primaryNumber.getTime());

        getTwinNumbers((ArrayList<Integer>) listTest);


    }
}
