package com.hbyd.parks.attendancesys.future;

/**
 * Callable 的返回值
 */
public class SumTimeAnswer {
    private long timeToFinish;
    private long sum;

    public SumTimeAnswer(long timeToFinish, long sum) {
        this.timeToFinish = timeToFinish;
        this.sum = sum;
    }

    public long getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(long timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
