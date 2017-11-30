package com.lxf.bigdata.dao.redis.utils;

import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtil implements Serializable {

    public RedisTemplate<String,Object> redisTemplate = SpringContextUtil.appContext.getBean(RedisTemplate.class);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key    缓存的键值
     * @param value    缓存的值
     * @return        缓存的对象
     */
    public void setCacheObject(String key, Object value)
    {
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获得缓存的基本对象。
     * @param key        缓存键值
     * @return            缓存键值对应的数据
     */
    public Object getCacheObject(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存List数据
     * @param key        缓存的键值
     * @param dataList    待缓存的List数据
     * @return            缓存的对象
     */
    public Object setCacheList(String key, List<Object> dataList)
    {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        if(null != dataList)
        {
            int size = dataList.size();
            for(int i = 0; i < size ; i ++)
            {
                listOperation.rightPush(key,dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     * @param key    缓存的键值
     * @return        缓存键值对应的数据
     */
    public List<Object> getCacheList(String key)
    {
        List<Object> dataList = new ArrayList<Object>();
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for(int i = 0 ; i < size ; i ++)
        {
            dataList.add(listOperation.leftPop(key));
        }
        return dataList;
    }

    /**
     * 获得缓存的list对象
     * @Title: range
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param key
     * @param @param start
     * @param @param end
     * @param @return
     * @return List<T>    返回类型
     * @throws
     */
    public List<Object> range(String key, long start, long end)
    {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        return listOperation.range(key, start, end);
    }

    /**
     * list集合长度
     * @param key
     * @return
     */
    public Long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 覆盖操作,将覆盖List中指定位置的值
     * @param key
     * @param index index 位置
     * @param obj
     *            value 值
     * @return 状态码
     * */
    public void listSet(String key, int index, Object obj) {
        redisTemplate.opsForList().set(key, index, obj);
    }

    /**
     * 向List尾部追加记录
     *
     * @param key
     * @param obj
     * @return 记录总数
     * */
    public long leftPush(String key, Object obj) {
        return redisTemplate.opsForList().leftPush(key, obj);
    }

    /**
     * 向List头部追加记录
     *
     * @param key
     * @param obj
     * @return 记录总数
     * */
    public long rightPush(String key, Object obj) {
        return redisTemplate.opsForList().rightPush(key, obj);
    }

    /**
     * 算是删除吧，只保留start与end之间的记录
     *
     * @param key
     * @param start 记录的开始位置(0表示第一条记录)
     * @param end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     * */
    public void trim(String key, int start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 删除List中c条记录，被删除的记录值为value
     *
     * @param key
     * @param i 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param obj
     *            obj 要匹配的值
     * @return 删除后的List中的记录数
     * */
    public long remove(String key, long i, Object obj) {
        return redisTemplate.opsForList().remove(key, i, obj);
    }

    /**
     * 缓存Set
     * @param key        缓存键值
     * @param dataSet    缓存的数据
     * @return            缓存数据的对象
     */
    public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet)
    {
        BoundSetOperations<String, Object> setOperation = redisTemplate.boundSetOps(key);

        Iterator<Object> it = dataSet.iterator();
        while(it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 缓存Set
     * @param key        缓存键值
     * @param data    缓存的数据
     * @return            缓存数据的对象
     */
    public Long setCacheSet(String key, Object data)
    {
        return redisTemplate.opsForSet().add(key, data);
    }

    /**
     * 是不是set集合中的一员
     * @param key        缓存键值
     * @param data    缓存的数据
     * @return            缓存数据的对象
     */
    public Boolean isCacheSetMember(String key, Object data)
    {
        return redisTemplate.opsForSet().isMember(key, data);
    }

    /**
     * 删除set集合中的一员
     * @param key        缓存键值
     * @param data    缓存的数据
     * @return            缓存数据的对象
     */
    public Long removeCacheSetMember(String key, Object data)
    {
        return redisTemplate.opsForSet().remove(key, data);
    }

    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public Set<Object> getCacheSet(String key)
    {
        Set<Object> dataSet = new HashSet<Object>();
        BoundSetOperations<String,Object> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();
        for(int i = 0 ; i < size ; i++)
        {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public int setCacheMap(String key,Map<String, Object> dataMap)
    {
        if(null != dataMap)
        {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                if(hashOperations != null){
                    hashOperations.put(key,entry.getKey(),entry.getValue());
                } else{
                    return 0;
                }
            }
        } else{
            return 0;
        }
        return dataMap.size();
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public Map<Object, Object> getCacheMap(String key)
    {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 获得缓存的Map数据
     * @param key
     * @return
     */
    public Object getCacheMap(String key,Object hkey)
    {
        return redisTemplate.opsForHash().get(key, hkey);
    }

    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public void setCacheIntegerMap(String key,Map<Integer, Object> dataMap)
    {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        if(null != dataMap)
        {
            for (Map.Entry<Integer, Object> entry : dataMap.entrySet()) {
                hashOperations.put(key,entry.getKey(),entry.getValue());
            }

        }
    }

    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public Map<Object, Object> getCacheIntegerMap(String key)
    {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 从hash中删除指定的存储
     *
     * @param key
     * @return 状态码，1成功，0失败
     * */
    public long deleteMap(String key) {
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate.opsForHash().delete(key);
    }



    /**
     * 设置过期时间
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    /**
     * increment
     * @param key
     * @param step
     * @return
     */
    public long increment(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * increment
     * @param key
     * @param step
     * @return
     */
    public Double increment(String key, Double step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * 增长hashmap的数值   整型
     * hIncrementBy
     * @param key
     * @param step
     * @return
     */
    public Double hIncrementBy(String key,String hkey, Double step) {
        return redisTemplate.opsForHash().increment(key,hkey,step);
    }
    /**
     * 增长hashmap的数值 浮型
     * hIncrementBy
     * @param key
     * @param step
     * @return
     */
    public long hIncrementBy(String key,String hkey, long step) {
        return redisTemplate.opsForHash().increment(key,hkey,step);
    }

    /**
     * 判断是否存在某个key
     * @param key
     * @return
     */
    public Boolean hasKey(String key){
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean;
    }

}
