package com.zlm.server.myserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

public class Request {

    //协议信息
    private String requestInfo;
    //请求方式
    private String method;
    //请求URI
    private String url;
    //请求参数
    private String queryStr;
    private Map<String, List<String>> paramMap;
    private final String BLANK = " ";
    private final String CRLF = "\r\n";

    public Request(){

    }
    public Request(InputStream is){
        paramMap = new HashMap<>();
        byte[] datas = new byte[1024*1024];
        int len;
        try {
            len = is.read(datas);
            this.requestInfo = new String(datas, 0, len);
            System.out.println(requestInfo);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        //分解字符串
        parseRequestInfo();
    }

    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }

    private void parseRequestInfo(){

        System.out.println(requestInfo);
        System.out.println("－－－－－1.获取请求方式－－－－－");
        this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase();
        this.method = this.method.trim();
        System.out.println(method);
        System.out.println("－－－－－2.获取URL－－－－－");
        //1.获取/的位置
        int startIndex = this.requestInfo.indexOf("/")+1;
        //2.获取HTTP/的位置
        int endIdx = this.requestInfo.indexOf("HTTP/");
        //3. 分割字符串
        this.url = this.requestInfo.substring(startIndex, endIdx);
        System.out.println(url);
        //4. 获取问号的位置
        int queryIdx = this.url.indexOf("?");
        if (queryIdx >= 0){
            String[] urlArray = this.url.split("\\?");
            this.url = urlArray[0];
            queryStr = urlArray[1];
        }
        System.out.println(url);
        System.out.println(queryStr);
        System.out.println("－－－－－2.获取请求参数－－－－－");

        if (method.equals("post")){
            String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
            if (null == queryStr){
                queryStr = qStr;
            }else {
                queryStr += "&"+qStr;
            }
        }
        queryStr = null == queryStr ? "":queryStr;
        System.out.println(queryStr);

        convertMap();
    }

    //处理请求参数为 Map
    private void convertMap(){
        //分割字符串
        String[] keyValues = this.queryStr.split("&");
        for (String query : keyValues){
            //再次分割字符串　=
            String[] kv = queryStr.split("=");
            kv = Arrays.copyOf(kv, 2);
            //获取key 和 value
            String key = kv[0];
            String value = kv[1] == null ? null:decode(kv[1], "utf-8");
            //存储到Map中
            if(!paramMap.containsKey(key)){
                paramMap.put(key, new ArrayList<>());
            }
            paramMap.get(key).add(value);
        }
    }

    /**
     * 通过Name 获取对应多个值
     * @param key
     * @return
     */
    public String[] getParameterValues(String key){
        List<String> values = this.paramMap.get(key);
        if (null == values || values.size() < 1){
            return null;
        }
        return values.toArray(new String[0]);
    }

    private String decode(String value, String enc){
        try {
            return java.net.URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
    /**
     * 通过name 获取对应的多个值
     * @param key
     * @return
     */
    public String getParameter(String key){
        String[] values = getParameterValues(key);
        return values == null ? null : values[0];
    }
}
