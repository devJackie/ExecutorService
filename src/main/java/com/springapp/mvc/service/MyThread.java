package com.springapp.mvc.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015-02-12.
 */
@Scope(value = "prototype")
@Component("myThread")
public class MyThread implements Callable<Object> {

    private String _param = null;
    
//    public MyThread(String param) {
//        this._param = param;
//    }

    @Override
    public Map<Object, Object> call() throws Exception {

        Map<Object, Object> map = new TreeMap<Object, Object>();
        List<Object> numberList = new ArrayList<Object>();
        for (int i = 0; i < 5; i++) {
            numberList.add(i);
        }
        map.put("number_list", numberList);
        System.out.println("[ thread name : "+Thread.currentThread().getName()+" ]");
        return map;
    }
}
