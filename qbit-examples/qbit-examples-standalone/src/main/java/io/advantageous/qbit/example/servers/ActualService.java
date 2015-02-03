package io.advantageous.qbit.example.servers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.boon.Boon.puts;

/**
 * Created by rhightower on 2/2/15.
 */
public class ActualService {

    final Map<Integer, String> map = new HashMap<Integer, String>();

    public double addKey(int key, String value) {

        double dvalue = 0.0;
        int ivalue=0;

        if (key == 0) {
            for (long index = 0; index < 100_000L; index++) {

                dvalue = dvalue + index * 1000;
                ivalue = (int) dvalue;
                ivalue = ivalue % 13;
            }
        } else if (key == 1) {


            final Set<Integer> integers = map.keySet();

            for (Integer k : integers) {
                dvalue += k + map.get(k).hashCode();
            }
        } else {
            map.put(key, value);
            return map.get(key).hashCode();
        }

        return (dvalue += ((double) ivalue));
    }

    public static void main(String... args) {

        for (int index = 0 ; index < 10; index++) {
            long start = System.currentTimeMillis();

            ActualService actualService = new ActualService();
            puts(actualService.addKey(0, "foo"));

            long stop = System.currentTimeMillis();

            long duration = stop - start;

            puts(duration);
        }
        ActualService actualService = new ActualService();

        puts(actualService.addKey(1, "foo"));


        puts(actualService.addKey(3, "foo"));

    }
}