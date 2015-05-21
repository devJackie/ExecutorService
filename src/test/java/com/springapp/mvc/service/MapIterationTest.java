package com.springapp.mvc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:test-mvc-dispatcher-servlet.xml"
})
public class MapIterationTest {

    @Test
    public void 맵_반복_테스트() {
        Map<Object, Object> map = new TreeMap<Object, Object>();

        map.put("키1", "값1");
        map.put("키2", "값2");
        map.put("키3", "값3");
        map.put("키4", "값4");
        map.put("키5", "값5");

        //e.g1
        for (Iterator<Object> keys = map.keySet().iterator(); keys.hasNext();) {
            String key = (String) keys.next();
            System.out.println(String.format("e.g1 => 키 : %s, 값 : %s", key, map.get(key)));
        }

        System.out.println("===============================================================");

        //e.g2
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println(String.format("e.g2 => 키 : %s, 값 : %s", key, value));
        }
    }
}