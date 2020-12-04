package com.vk.dwzkf.listcorruptor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class ListcorruptorApplication {
    public static class Remover implements Runnable {
        CountDownLatch cdl;
        SimpleLinkedList<String> list;
        String toRemove;

        public Remover(CountDownLatch cdl, SimpleLinkedList<String> list, String toRemove) {
            this.cdl = cdl;
            this.list = list;
            this.toRemove = toRemove;
        }

        @Override
        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                //nop
            }
            list.remove(toRemove);
        }
    }

    public static class MyRemover implements Runnable {
        CountDownLatch cdl;
        CorruptedLinkedList<String> list;
        String toRemove;

        public MyRemover(CountDownLatch cdl, CorruptedLinkedList<String> list, String toRemove) {
            this.cdl = cdl;
            this.list = list;
            this.toRemove = toRemove;
        }

        @Override
        public void run() {
            try {
                cdl.await();
            } catch (InterruptedException e) {
                //nop
            }
            list.remove(toRemove);
        }
    }

    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    public static void main(String[] args) {
        disableWarning();
        log.info("Regular list:");
        testSimpleList();
        System.out.println();
        log.info("Corrupted list:");
        testCorruptedList();
    }

    @SneakyThrows
    public static void testSimpleList() {
        int size = 2;
        Random r = new Random();
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        for (int index = 0; index < size; index++) {
            list.add(String.valueOf(r.nextLong()));
        }
        CountDownLatch cdl = new CountDownLatch(1);
        list.forEach(s -> new Thread(new Remover(cdl, list, s)).start());
        Thread.sleep(300);
        cdl.countDown();
        Thread.sleep(300);
        log.info("List size: {}", list.size());
        log.info("List: {}", list);
        Field first = list.getClass().getDeclaredField("first");
        first.setAccessible(true);
        Field last = list.getClass().getDeclaredField("last");
        last.setAccessible(true);
        log.info("List First Node: {}", first.get(list));
        log.info("List Last Node: {}", last.get(list));
    }

    @SneakyThrows
    public static void testCorruptedList() {
        int size = 2;
        Random r = new Random();
        CorruptedLinkedList<String> list = new CorruptedLinkedList<>();
        for (int index = 0; index < size; index++) {
            list.add(String.valueOf(r.nextLong()));
        }
        CountDownLatch cdl = new CountDownLatch(1);
        list.forEach(s -> new Thread(new MyRemover(cdl, list, s)).start());
        Thread.sleep(300);
        cdl.countDown();
        Thread.sleep(2000);
        log.info("List size: {}", list.size());
        log.info("List: {}", list);
        log.info("List First Node: {}", list.first);
        log.info("List Last Node: {}", list.last);
    }
}
