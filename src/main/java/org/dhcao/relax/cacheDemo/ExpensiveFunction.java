package org.dhcao.relax.cacheDemo;

import java.math.BigInteger;

/**
 * @Author: dhcao
 * @Version: 1.0
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    public BigInteger compute(String arg) throws InterruptedException {

        return new BigInteger(arg);
    }
}
