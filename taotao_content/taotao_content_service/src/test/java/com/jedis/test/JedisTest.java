package com.jedis.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    //单机版
    @Test
    public void testJedisSingle(){
        //创建连接
        Jedis jedis = new Jedis("192.168.25.153",6379);
        jedis.set("key22","value22");
        System.out.println(jedis.get("key22"));
        jedis.close();
    }

    //连接池

    @Test
    public void testJedisPool(){
        //创建连接
        JedisPool pool = new JedisPool("192.168.25.153",6379);
        Jedis jedis = pool.getResource();
        jedis.set("key223","value223");
        System.out.println(jedis.get("key223"));
        jedis.close();//释放连接
        pool.close();//只有在应用停掉才调用
    }

    @Test
    public void testJedisCluster(){
        //创建连接
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.153",7001));
        nodes.add(new HostAndPort("192.168.25.153",7002));
        nodes.add(new HostAndPort("192.168.25.153",7003));
        nodes.add(new HostAndPort("192.168.25.153",7004));
        nodes.add(new HostAndPort("192.168.25.153",7005));
        nodes.add(new HostAndPort("192.168.25.153",7006));
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("cluster", "1111");
        System.out.println(cluster.get("cluster"));
        cluster.close();//应用(tomcat)停掉关闭
    }


}
