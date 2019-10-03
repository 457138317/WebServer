package com.zlm.server.basic.servlet;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> pattern;

    public Mapping(){
        pattern = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPattern() {
        return pattern;
    }

    public void setPattern(Set<String> pattern) {
        this.pattern = pattern;
    }

    public void addPattern(String pattern){
        this.pattern.add(pattern);
    }
}
