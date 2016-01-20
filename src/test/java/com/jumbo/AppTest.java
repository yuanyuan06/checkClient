package com.jumbo;

import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testClient() throws IOException {
        ServerSocket socket = new ServerSocket(12345);
        Socket accept = socket.accept();

        JOptionPane.showMessageDialog(null, "有客户端连接到本机12345");


    }
}

