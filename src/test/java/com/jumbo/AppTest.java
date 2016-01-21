package com.jumbo;

import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testClient() throws IOException {
        Socket socket = new Socket();
//        socket.setSoTimeout(1);
        try{
//            Socket socket = new Socket("www.baidu.com", 443);
            SocketAddress addr = new InetSocketAddress("www.baidu.com", 4431);
            socket.connect(addr, 3000);
            boolean connected = socket.isConnected();
            if(connected){
                JOptionPane.showMessageDialog(null, "端口服务正常");
            }else{
                JOptionPane.showMessageDialog(null, "端口服务不存在");
            }
        }catch(ConnectException e){
            JOptionPane.showMessageDialog(null, "端口服务不存在");
        }catch (SocketTimeoutException e){
            JOptionPane.showMessageDialog(null, "连接超时");
        }finally{
            socket.close();
        }
    }
}