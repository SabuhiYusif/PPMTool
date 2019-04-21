import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;
import java.util.Collections;

public class GCD {

    public static void main(String[] args) {
        int gcd = findGCD(8,12);
//        int lcd = findLCD(1, 1);
//        System.out.println(lcd);
//        System.out.println(gcd);
        int[] a = {100, 99, 98, 97, 96, 95, 94, 93, 92, 91};
        int[] b = {1, 2, 3, 4, 5 ,6 ,7, 8 ,9 ,10};
        System.out.println(getTotalX(a, b));

    }

    private static int findMin(int[] a){
        int min = Integer.MAX_VALUE;
        for(Integer i: a){
            if(i < min){
                min = i;
            }
        }
        return min;
    }
    private static int getTotalX(int[] a, int[] b){
        int count = 0;
        if( a.length > 0){
            int lcm = a[0];
            for(Integer i: a){
                lcm = findLCD(lcm, i);
            }
            int lc = lcm;
            boolean ok = true;
            int m = findMin(b);
            while (lcm <= m && lcm >0){
                ok = true;
                for (Integer i: b) {
                    if (i % lcm != 0 ) {
                        ok = false;
                        lcm += lc;
                    }
                }
                if(ok) {
                    count++;
                    lcm += lc;
                }

            }
            return count;
        }

        return 0;

    }

    public static int findGCD(int a, int b) {
        while (a > 0 && b > 0) {

            if (a >= b) {
                a = a % b;
            }
            else {
                b = b % a;
            }
        }

        return a + b;
    }

    private static int findLCD(int a, int b){
        return a * b/(findGCD(a,b));
    }


    private static int findMax(int a, int b) {
        return (a > b) ? a : b;
    }
    private static int findMin(int a, int b) {
        return (a < b) ? a : b;
    }
}
