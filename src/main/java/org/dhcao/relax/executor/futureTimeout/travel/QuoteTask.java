package org.dhcao.relax.executor.futureTimeout.travel;

import java.util.concurrent.Callable;

/**
 * 旅行任务类
 * @Author: dhcao
 * @Version: 1.0
 */
public class QuoteTask implements Callable<TravelQuote> {

    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    @Override
    public TravelQuote call() throws Exception {
        return null;
    }
}
