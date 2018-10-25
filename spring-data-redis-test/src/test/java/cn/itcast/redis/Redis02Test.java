package cn.itcast.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-redis-cluster.xml"})
public class Redis02Test {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /*String*/
    @Test
    public void stringTest() {
        /*设置值*/
        redisTemplate.boundValueOps("name").set("itcast");
        /*获取值*/
        String str = (String) redisTemplate.boundValueOps("name").get();

        System.out.println(str);

        redisTemplate.delete("name");
    }

    /*set类型*/
    @Test
    public void setTest() {
        redisTemplate.boundSetOps("name").add("李大华", "李中华");
        redisTemplate.boundSetOps("name").add("李小华");

        Set members = redisTemplate.boundSetOps("name").members();
        System.out.println(members);

        redisTemplate.boundSetOps("name").remove("李大华");
        Set members1 = redisTemplate.boundSetOps("name").members();
        System.out.println(members1);

        redisTemplate.delete("name");
        Set members2 = redisTemplate.boundSetOps("name").members();
        System.out.println(members2);
    }

    /*List类型*/
    @Test
    public void listTest() {
        /*右压栈,末尾添加数据*/
        redisTemplate.boundListOps("name1").rightPush("1");
        redisTemplate.boundListOps("name1").rightPush("2");
        redisTemplate.boundListOps("name1").rightPush("3");

        /*获取值（全部）*/
        List list = redisTemplate.boundListOps("name1").range(0, -1);
        System.out.println(list);

        /*左压栈，前端添加数据*/
        redisTemplate.boundListOps("name2").leftPush("1");
        redisTemplate.boundListOps("name2").leftPush("2");
        redisTemplate.boundListOps("name2").leftPush("3");

        List list1 = redisTemplate.boundListOps("name1").range(0, -1);
        System.out.println(list1);

        String a = (String) redisTemplate.boundListOps("name1").index(1);
        System.out.println(a);

        redisTemplate.boundListOps("name1").remove(0, "1");
    }


    @Test
    public void hashTest() {
        redisTemplate.boundHashOps("name").put("a","里大话");
        redisTemplate.boundHashOps("name").put("b","里二话");
        redisTemplate.boundHashOps("name").put("c","里三话");

        Set key = redisTemplate.boundHashOps("name").keys();
        System.out.println(key);

        List values = redisTemplate.boundHashOps("name").values();
        System.out.println(values);

        Object obj = redisTemplate.boundHashOps("name").get("b");
        System.out.println(obj);

        redisTemplate.boundHashOps("name").delete("c");
    }


}
