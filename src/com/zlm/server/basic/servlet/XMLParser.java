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
        servlet.service();

    }
}

class WebHandler extends DefaultHandler{
    private List<Entity> entities;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;

    private String tag;
    //判断解析的是Mapping 还是Servlet
    private boolean isMapping = false;


    @Override
    public void startDocument() throws SAXException {
        entities = new ArrayList<>();
        mappings = new ArrayList<>();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (null != qName){
            tag = qName;
            if(tag.equals("servlet")){
                entity = new Entity();
                isMapping = false;
            }else if (tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (null != tag){
            if (isMapping){
                if (tag.equals("servlet-name")){
                    mapping.setName(contents);
                }else if(tag.equals("url-pattern")){
                    mapping.addPattern(contents);
                }
            }else {
                if (tag.equals("servlet-name")){
                    entity.setName(contents);
                }else if(tag.equals("servlet-class")){
                    entity.setClz(contents);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(null != qName){
            if (qName.equals("servlet")){
                entities.add(entity);
            }else if (qName.equals("servlet-mapping")){
                mappings.add(mapping);
            }
        }
        tag = null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

}