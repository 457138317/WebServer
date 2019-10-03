package com.zlm.server.basic.servlet;

import com.zlm.server.myserver.Request;
import com.zlm.server.myserver.Response;

public class RegisterServlet implements Servlet{

    @Override
    public void service(Request request, Response response) {
        System.out.println("RegisterServlet");
    }
}
