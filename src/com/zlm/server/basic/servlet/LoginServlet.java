package com.zlm.server.basic.servlet;

import com.zlm.server.myserver.Request;
import com.zlm.server.myserver.Response;

public class LoginServlet implements Servlet {

    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("服务器响应成功!");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("server hello!" + request.getParameter("uname"));
        response.print("</body>");
        response.print("</html>");

    }
}
