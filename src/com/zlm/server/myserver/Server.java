package com.zlm.server.myserver;

import com.zlm.server.basic.servlet.LoginServlet;
import com.zlm.server.basic.servlet.Servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 1.使用 ServerSocket建立连接
 * 2. 返回响应协议
 * 3. 封装响应信息
 *  a. 内容可以动态添加
 *  b. 只关注状态码
 */
public class Server {

    private ServerSocket serverSocket;


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.receive();
    }
    //启动服务
    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            System.out.println("服务器启动失败!");
        }
    }

    //接受连接
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接!");

            Request request = new Request(client);

            Response response = new Response(client);

            Servlet servlet = new LoginServlet();
            servlet.service(request, response);
            //关注状态码
            response.pushToBrowser(200);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误!");
        }
    }


    //停止服务
    public void stop(){

    }
}
