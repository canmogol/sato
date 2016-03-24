package io.sato;


import java.util.Map;
import java.util.TreeMap;

public class UserDBPlugin implements Plugin {

    public Map<String, Integer> findLecturesOfUser(String name, Long number) {
        System.out.println("Java Hello " + name + ", " + number);
        Map<String, Integer> map = new TreeMap<>();
        map.put("Calculus", 4);
        map.put("Physics", 3);
        map.put("Chemistry", 3);
        map.put("Biology", 3);
        return map;
    }

    @Override
    public String documentation() {
        return "Java UserDBPlugin";
    }
}
