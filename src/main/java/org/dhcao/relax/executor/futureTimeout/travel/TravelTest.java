package org.dhcao.relax.executor.futureTimeout.travel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 在ExecutorService的invokeAll方法，可以代理一组task，并且根据这组task的顺序返回对应顺序的future
 * @Author: dhcao
 * @Version: 1.0
 */
public class TravelTest {

    ExecutorService exec = Executors.newFixedThreadPool(4);

    public List<TravelQuote> getRankedTravelQuotes(
            TravelInfo travelInfo,
            Set<TravelCompany> companies,
            Comparator<TravelQuote> ranking,
            long time,
            TimeUnit unit) throws InterruptedException{

        // 1.创建一组任务,放到一个arrayList里面
        List<QuoteTask> tasks = new ArrayList<QuoteTask>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }

        // 2.ExecutorService的invokeAll方法可以代理一个list的任务，返回的future顺序跟task一个样子
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks);

        // 3.将返回的Future中的TravelQuote取出来，放到一边
        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        futures.forEach(f -> {

            // task的顺序来处理future
            tasks.forEach(task -> {
                try {
                    quotes.add(f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });

        });

        Collections.sort(quotes,ranking);
        return quotes;

    }
}
