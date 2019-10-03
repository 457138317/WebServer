package com.zlm.server.basic.servlet;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static void main(String[] args) throws Exception {
        //1.获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2. 从解析工厂获取解析器
        SAXParser parser = factory.newSAXParser();
        //3. 编写处理器
        //4. 加载文档　Document 注册处理器
        WebHandler handler = new WebHandler();
        //5. 解析
        parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/zlm/server/basic/servlet/web.xml"), handler);

        List<Entity> entities = handler.getEntities();
        List<Mapping> mappings = handler.getMappings();
        System.out.println(entities.size());
        System.out.println(mappings.size());
        //获取注册数据
        WebContext context = new WebContext(handler.getEntities(), handler.getMappings());
        //假设输入 /login
        String className = context.getClz("/g");
        Class clz = Class.forName(className);

        Servlet servlet = (Servlet)clz.getConstructor().newInstance();
        System.out.println(servlet);


    }
}

