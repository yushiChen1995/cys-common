package com.cys.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author cys
 * @date 2019/7/10
 * redis 集群代码演示
 */
public class ClusterJedisDemo {
    public static void main(String[] args) throws IOException {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.182.128", 6379));
        nodes.add(new HostAndPort("192.168.182.128", 6380));
        nodes.add(new HostAndPort("192.168.182.128", 6381));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("num", "456");
        System.out.println("jedisCluster = " + jedisCluster.get("num"));
        jedisCluster.close();
    }
}
