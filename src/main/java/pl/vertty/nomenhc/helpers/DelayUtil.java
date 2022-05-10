package pl.vertty.nomenhc.helpers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DelayUtil {
    static {

    }

    public static void delay(long paramLong, TimeUnit paramTimeUnit) {
        long l1 = System.currentTimeMillis();
        long l2 = l1 + paramTimeUnit.toMillis(paramLong);
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        while (l2 - l1 > 0L) {
            try {
                reentrantLock.lockInterruptibly();
                condition.await(l2 - l1, TimeUnit.MILLISECONDS);
            } catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                reentrantLock.unlock();
            }
            l1 = System.currentTimeMillis();
        }
    }
}

