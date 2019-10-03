package com.zlm.server.basic.servlet;

import com.zlm.server.myserver.Request;
import com.zlm.server.myserver.Response;

public interface Servlet {
    void service(Request request, Response response);
}
