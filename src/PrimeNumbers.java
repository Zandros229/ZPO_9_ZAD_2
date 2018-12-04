import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PrimeNumbers extends RecursiveTask<List> {
    static final int SEQUENTIAL_THRESHOLD = 5000;

    int low;
    int high;
    private volatile List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    private final ForkJoinPool fjPool = new ForkJoinPool();
    private Long time = 0L;


    PrimeNumbers(int lo, int hi) {
        low   = lo;
        high  = hi;
    }
    private boolean isPrimaryNumber(int number){

        if(number == 1){
            return false;
        }
        if(number == 2 || number == 3){
            return true;
        }
        if(number > 3){

            if(number % 2 == 0){
                return false;
            }

            for(int i=3; i<=Math.sqrt(number); i=i+2){
                if(number % i == 0){
                    return  false;
                }
            }
        }
        return true;
    }


    @Override
    protected List compute() {
        if(high - low <= SEQUENTIAL_THRESHOLD) {
            long primaryNumbersCount = 0;
            for(int i=low; i<high; i++){
                if(isPrimaryNumber(i)){
                    primaryNumbersCount+=1;
                    list.add(i);
                }
            }

            return list;
        } else {
            int mid = low + (high - low) / 2;
            PrimeNumbers left  = new PrimeNumbers(low, mid);
            PrimeNumbers right = new PrimeNumbers(mid, high);
            long startTime = System.nanoTime();
            left.fork();
            List listR = right.compute();
            List listL = left.join();
            List returnList = new ArrayList();

            returnList.addAll(listL);
            returnList.addAll(listR);
            long endTime = System.nanoTime() - startTime;
            time += endTime;

            return returnList;
        }
    }


    List countPrimes(int start, int end){
        System.out.println(getTime());
        return fjPool.invoke(new PrimeNumbers(start,end));
    }

    public Long getTime() {
        return time;
    }
}

