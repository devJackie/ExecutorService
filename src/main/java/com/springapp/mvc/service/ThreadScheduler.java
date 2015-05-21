package com.springapp.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2015-02-12.
 */
@Configuration
@ComponentScan(basePackages = "com.springapp.mvc")
@EnableScheduling
public class ThreadScheduler {

    @Autowired
    ApplicationContext applicationContext;

    //스레드를 관리하는 메소드
//    @Scheduled(cron = "*/2 * * * * *")
    @Scheduled(fixedDelay = 2000)
    public void startExecutorThread() throws ExecutionException, InterruptedException {

        List<Object> threadList = new ArrayList<Object>();

        for (int i = 0; i < 2; i++) {
            MyThread myThread = (MyThread) applicationContext.getBean("myThread");
            threadList.add(myThread);
        }

        Map<Object, Future<Object>> thread_results = makeExecutorThreadPool(threadList.size(), threadList);

        returnsExcutorThread(thread_results);
    }

    //실제 thread pool을 생성하고 thread를 실행하는 메소드
    public Map<Object, Future<Object>> makeExecutorThreadPool(int size, List<Object> works) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(size);

        Map<Object, Future<Object>> result = new TreeMap<Object, Future<Object>>();

        for (Iterator<Object> _works = works.iterator(); _works.hasNext(); ) {
            Object _work = _works.next();

            Future<Object> future = es.submit((MyThread) _work);
//            System.out.println("hashcode : " + ((MyThread) _work).hashCode());
            //MyThread의 @Scope는 prototype, 즉 각각의 객체가 새로 생성되는 것을 확인하기 위해 hashcode를 담는다.
            result.put(((MyThread) _work).hashCode(), future);
        }
        es.shutdown();
        return result;
    }

    /**
     * Returns excutor thread.
     *
     * @param thread_results the thread _ results
     */
//Callable의 call 메소드에서 리턴 받은 값을 확인하는 메소드
    public void returnsExcutorThread(Map<Object, Future<Object>> thread_results) {
        for (Map.Entry<Object, Future<Object>> ret : thread_results.entrySet()) {
            try {
                Map<Object, Object> return_map = (Map<Object, Object>) ret.getValue().get();
//                System.out.println("return thread result : "+return_map.get("number_list"));
            } catch (Exception e) {
                System.out.println("error : "+e);
            }
        }
    }

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/WEB-INF/mvc-dispatcher-servlet.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(ThreadScheduler.class);

        MyThread myThread = context.getBean("myThread", MyThread.class);
        System.out.println(myThread);
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
