package com.mazhe.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: 反射转换工具类
 * @Author: huanggengjia
 * @Date: Create in 10:01 2019/4/18
 * @Modified By:
 */
@Slf4j
public class ReflectionUtils {
    /**
     * 把map转为java bean
     *
     * @param map
     * @param clazz
     * @return
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
                        || field.isSynthetic()) {
                    // 静态变量、synthetic和父类对象变量性忽略
                    continue;
                }
                // boolean类型
                if (field.getType().getName().equals("boolean")) {
                    field.setBoolean(t, Boolean.valueOf((String) map.get(field.getName())));
                } else if (field.getType().getName().equals("java.lang.Integer")) {
                    if (null != map.get((field.getName()))){
                        field.setInt(t, Integer.parseInt(String.valueOf(map.get(field.getName()))));
                    }
                } else if (field.getType().getName().equals("java.lang.Long")) {
                    if (null != map.get((field.getName()))){
                        field.setLong(t, Long.parseLong(String.valueOf(map.get(field.getName()))));
                    }
                } else { // 其他类型
                    if (null != map.get((field.getName()))) {
                        field.set(t, map.get(field.getName()));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw new RuntimeException("", ex);
        }
        return t;
    }


    /**
     * 将 Map对象转化为JavaBean
     * @param map
     * @param T
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T convertMap2Bean(Map map, Class T) throws Exception {
        if(map==null || map.size()==0){
            return null;
        }
        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        T bean = (T)T.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            String upperPropertyName = propertyName.toUpperCase();
            if (map.containsKey(upperPropertyName)) {
                Object value = map.get(upperPropertyName);
                //这个方法不会报参数类型不匹配的错误。
                BeanUtils.copyProperty(bean, propertyName, value);
                //用这个方法对int等类型会报参数类型不匹配错误，需要我们手动判断类型进行转换，比较麻烦。
                //descriptor.getWriteMethod().invoke(bean, value);
                //用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
                //BeanUtils.setProperty(bean, propertyName, value);
            }
        }
        return bean;
    }

    /**
     * 将 JavaBean对象转化为 Map   此方法未测试
     * @param bean 要转化的类型
     * @return Map对象
     */
    public static Map convertBean2Map(Object bean) throws Exception {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * 将 List<Map>对象转化为List<JavaBean>
     * @param listMap
     * @param T
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> convertListMap2ListBean(List<Map<String,Object>> listMap, Class T) throws Exception {
        List<T> beanList = new ArrayList<T>();
        for(int i=0, n=listMap.size(); i<n; i++){
            Map<String,Object> map = listMap.get(i);
            T bean = convertMap2Bean(map,T);
            beanList.add(bean);
        }
        return beanList;
    }




        /**
         * 将 List<JavaBean>对象转化为List<Map>
         * @param beanList
         * @return
         * @throws Exception
         */
    public static List<Map<String,Object>> convertListBean2ListMap(List<Object> beanList) throws Exception {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (int i = 0, n = beanList.size(); i < n; i++) {
            Object bean = beanList.get(i);
            Map map = convertBean2Map(bean);
            mapList.add(map);
        }
        return mapList;

     }
    }

