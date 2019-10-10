package org.dhcao.relax.threadSafeInstance;

import java.math.BigInteger;

/**
 * 很简单的无状态类：
 * 1.没有数据域
 * 2.唯一的方法是void，没有其他引用。
 * 3.临时变量仅在线程栈的局部变量中
 * @Author: dhcao
 * @Version: 1.0
 */
public class StatelessBean {

    public void add(BigInteger j){
        BigInteger i = BigInteger.ONE;
        j.add(i);
    }
}
