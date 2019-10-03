package com.zlm.server.basic.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<Entity> entities = null;
    private List<Mapping> mappings = null;

    //key 用servlet-name, value class
    private Map<String, String> entityMap = new HashMap<>();
    //key: url-pattern  value: servlet-name
    private Map<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;

        //　将 entityList转成对应的map
        for(Entity entity: entities){
            entityMap.put(entity.getName(), entity.getClz());
        }

        //　将 map 的 List 转成对应的map
        for(Mapping mapping:mappings){
            for(String pattern:mapping.getPattern()){
                mappingMap.put(pattern, mapping.getName());
            }
        }

    }

    public String getClz(String pattern){
        String name = mappingMap.get(pattern);
        return entityMap.get(name);
    }
}
