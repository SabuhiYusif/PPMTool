import java.util.ArrayList;
import java.util.List;

public class sasdas {
    public static void main(String[] args) {
        List<Integer> ls = new ArrayList<>();

        ls.add(4);
//        ls.add(1);
//        ls.add(1);
//        ls.add(1);
//        ls.add(1);

        int d = 4;
        int m = 1;
        int mm = m - 1;

        int count = 0;

        for (int i = 0; i < ls.size(); i++) {
            int sum = 0;
            for (int j = i; j <= mm; j++) {

                if (mm < ls.size()) {

                    sum += ls.get(j);
                }else{
                    break;
                }

            }
            if (sum == d){
                count++;
            }
            mm++;
        }

        System.out.println(count);
    }
}
