package com.cys.snowflake;

import com.cys.utils.IdWorker;

/**
 * @author cys
 * @date 2019/9/17
 */
public class TestSnowflake {

    /**
     * 测试雪花算法
     * @param args
     */
    public static void main(String[] args) {
        IdWorker worker = new IdWorker(1,1);
        for (int i = 0; i < 10000; i++) {
            long id = worker.nextId();
            System.out.println("id = " + id);
        }
    }

}
