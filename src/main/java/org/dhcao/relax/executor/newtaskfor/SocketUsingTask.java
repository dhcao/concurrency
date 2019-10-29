package org.dhcao.relax.executor.newtaskfor;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * 包装一个socket任务task
 * @Author: dhcao
 * @Version: 1.0
 */
public class SocketUsingTask<T> implements CancellableTask {

    private Socket socket;
    protected synchronized void setSocket(Socket s){
        socket = s;
    }

    /**
     * 重定义任务的取消方法，取消任务直接变更为取消socket，这样更好，保证socket在任务取消时能正确关闭
     */
    @Override
    public void cancel() {

        try{
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * new 一个Task的作用，在于创建一个SocketUsingTask，受控制的任务！
     * @return
     */
    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            public boolean cancel(boolean myInterruptIfRunning){
                try{
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(myInterruptIfRunning);
                }
            }
        };
    }

    @Override
    public Object call() throws Exception {
        return null;
    }
}
