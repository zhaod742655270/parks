package com.hbyd.parks.attendancesys.future;

import java.util.concurrent.Callable;

/**
 * Created by allbutone on 2014/10/28.
 */
public class CounterCallable implements Callable<SumTimeAnswer> {

    private long start;

    private long end;

    public CounterCallable(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public SumTimeAnswer call() throws Exception {
        long sum = 0;
        long startTime = System.currentTimeMillis();

        for (long i = start; i <= end; i++) {
            sum += i;
        }
        
        long endTime = System.currentTimeMillis();

        return new SumTimeAnswer(endTime - startTime, sum);
    }
}
