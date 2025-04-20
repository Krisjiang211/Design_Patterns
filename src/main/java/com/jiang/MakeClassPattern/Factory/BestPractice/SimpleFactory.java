package com.jiang.MakeClassPattern.Factory.BestPractice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SimpleFactory {


    public static ProductInterface createProduct(String productName){
        return CreateUtil.create(productName);
    }


}

class CreateUtil{
    public static ProductInterface create(String productName){
        Properties properties = PropertiesUtils.read("product.properties");
        Map<String, String> kvs = PropertiesUtils.getKV(properties);
        for (String key : kvs.keySet()) {
            if (key.equals(productName)){
                ProductInterface res =(ProductInterface) PropertiesUtils.createObj(kvs.get(key));
                return res;
            }
        }
        return null;
    }
}


class PropertiesUtils{
    //支持链式调用
    //读取对象
    public static Properties read(String filename){
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(filename);
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //获取所有k-v
    public static Map<String, String> getKV(Properties properties){
        HashMap<String, String> map = new HashMap<>();
        Set<Object> objects = properties.keySet();
        for (Object object : objects) {
            String key = (String) object;
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        System.out.println("map = " + map);
        return map;
    }

    //通过包名创建对象
    public static Object createObj(String className){
        try {
            Class<?> aClass = Class.forName(className);
            Object o = aClass.getDeclaredConstructor().newInstance();
            return o;
        } catch (ClassNotFoundException e) {
            System.out.println("类不存在");
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}