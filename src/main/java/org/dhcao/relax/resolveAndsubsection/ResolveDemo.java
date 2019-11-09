package org.dhcao.relax.resolveAndsubsection;

import java.util.Set;

/**
 * 锁分解：讲一个锁分解为2个锁，减少竞争
 * 如下：服务器需要监视每时每刻的用户数量和请求数量
 * 当一个用户登陆、注销、开始查询或者结束查询时，调用相应的add和remove方法进行数据统计。
 *
 * 分解之前：这里都使用内置锁
 * @Author: dhcao
 * @Version: 1.0
 */
public class ResolveDemo {

    public final Set<String> user;
    public final Set<String> queries;

    public ResolveDemo(Set<String> user, Set<String> queries) {
        this.user = user;
        this.queries = queries;
    }

    public synchronized void addUser(String u){
        user.add(u);
    }
    public synchronized void addQuery(String q){
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        user.remove(u);
    }
    public synchronized void removeQuery(String q){
        queries.remove(q);
    }

}

/**
 * 分解之后：使用2个锁
 */
class ResolveDemo1 {

    public final Set<String> user;
    public final Set<String> queries;

    public ResolveDemo1(Set<String> user, Set<String> queries) {
        this.user = user;
        this.queries = queries;
    }

    public void addUser(String u){
        synchronized (user){
            user.add(u);
        }
    }

    public void addQuery(String q){
        synchronized (queries){
            queries.add(q);
        }
    }

    public void removeUser(String u) {
        synchronized (user){
            user.remove(u);
        }
    }

    public void removeQuery(String q){
        synchronized (queries){
            queries.remove(q);
        }
    }

}


