package org.dhcao.relax.executor.changeInterrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 重写非标准的interrupt方法
 * @Author: dhcao
 * @Version: 1.0
 */
public class ReaderThread extends Thread{

    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    /**
     * 重写interrupt方法，调用次方法将关闭socket链接
     */
    public void interrupt(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
        }
    }

    public void run(){

        try{
            final byte[] bytes = new byte[1024];
            while (true){
                int count = in.read(bytes);
                if(count < 0){
                    break;
                }
                else if(count > 0){
                    //  todo 处理数据
                }
            }
        } catch (IOException e){

        }

    }
}
