package com.cys.jbcrypt;

import org.junit.Test;

/**
 * @author cys
 * @date 2019/9/19
 */
public class testBCrypt {
    public static void main(String[] args) {
        //获取随机盐
        String gensalt = BCrypt.gensalt();
        System.out.println("gensalt = " + gensalt);
        //对明文进行加密
        String password = BCrypt.hashpw("chen321", gensalt);
        System.out.println("password = " + password);
    }

    /**
     * 验证加密是否正确
     */
    @Test
    public void testBCrypt() {
        boolean flag = BCrypt.checkpw("chen3211", "$2a$10$QyTJjLJRKZT66ErdiWxtFOhcZCTLHM1jm0Q4bqNJiDJ9a0GIYU/Pq");
        System.out.println("flag = " + flag);
    }
}
