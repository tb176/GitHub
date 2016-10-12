/**
 *  File: 
 *  Description:此类封装一些dao的基本操作
 *  Copyright 2011-2011 99Bill Corporation. All rights reserved.
 *  Date            Author      Changes
 *  2011-2-12		王亮   		JTAO
**/
package com.mo9.data.cashmere.base;

import java.util.List;
import java.util.Map;

import org.hibernate.LockOptions;

import com.mo9.webUtils.Page;

/**
 * 
 * @author thomas
 *
 * @param <T>
 */
public interface IBaseDao<T> {
    
    /**
     * 删除指定的持久化对象
     * @param entity 待删除的持久化对象
     */
    public void delete(T entity);  
    
    
    /**
     * 删除指定ID的持久化对象
     * @param id 待删除的ID
     */
    public void deleteById(long id);
    
    /**
     * 删除指定的多个持久化对象
     * @param ids 待删除的主键列表，比如id ="1,2,3"，必须一逗号分隔
     */
    public void deleteByIds(String ids);  
    
    /**
     * 
     * @Title: findById
     * @Description: 通过id查询实体
     * @param ids
     * @param booleans 是否使用缓存 如果查询
     * @return: T  范型对象
     * @throws  IllegalArgumentException()  当不定参数的长度大于1的时候
     */
    public T findById(long ids);
    

    
    /**
     * 使用悲观锁的方式去查询对象
     * @Title: findById
     * @Description: 通过id查询对象
     * @param id  对象id
     * @param lockOptions  lock的模式 见hibernate的注释
     * @return: T  范型
     */
    public T findById(long id,LockOptions lockOptions);
    
    
    /**
     * 查询所有的的对象
     * @Title: findAll
     * @Description: 查询对象对应的所有持久化对象列表。
     * @param  booleans 是否使用缓存 
     * @return: List<T>
     * @throws  IllegalArgumentException()  当不定参数的长度大于1的时候
     */
    public List<T> findAll(boolean ...usecha);
    
    /**
     * 查询对象
     * @param ids
     * @param isUseCaches
     * @return
     */
	public List<T> findByIds(List<Long> ids, boolean... isUseCaches);
    
    /**
     * 
     * @Title: findAll
     * @Description:  分页查询持久化对象列表
     * @param startNum 开始页码
     * @param maxNum 每页最大记录条数
     * @return: 返回指定分页的持久化对象列表。
     * @throws  IllegalArgumentException()  当不定参数的长度大于1的时候
     */
    public List<T> findAll(int startNum, int maxNumb,boolean... isUseCache);
    
    /**
     * 查询指定ID集合的持久化对象
     * @param ids 待查询的ID列表，比如id ="1,2,3"，必须一逗号分隔
     * @return 返回指定的持久化对象列表
     */
    public List<T> findByIds(String ids,boolean... isUseCaches);
    
    /**
     * 拼合指定的持久化对象
     * @param entity 待拼合的持久化对象
     * @return 返回拼合后的持久化对象
     */
    public long save(T entity);
    
    /**
     * 
     * @param entity
     * @param charset
     * @param entityTblName
     * @param fieldNamesToHex fields to use HEX() instead of plain string
     * @param valuesToHex hex coverted from UTF8 bytes, using org.apache.shiro.codec.Hex.encodeToString(bytes[])
     * @return
     */
    public long saveTWithCharset(T entity, String charset, final String entityTblName, final String[] fieldNamesToHex, final String[] valuesToHex);
    
    /**
     * 拼合指定的持久化对象
     * @param entity 待拼合的持久化对象
     * @return 返回拼合后的持久化对象
     */
    public T merge(T entity);
    
    
    /**
     * 根据属性查询持久化对象。
     * @param map 待查询的属性列表，map的key为指定的属性名，value为对应的属性值
     * @return 返回符合条件的持久化对象列表。
     */
    public List<T> queryByParameter(Map<String,Object> map,boolean... isUseCache);
    
    /**
     * 根据属性查询持久化对象。
     * @param map 待查询的属性列表，map的key为指定的属性名，value为对应的属性值
     * @param maxResults 最大数量
     * @param isUseCache 是否使用缓存
     * @return 返回符合条件的持久化对象列表。
     * @throws  IllegalArgumentException()  当不定参数的长度大于1的时候
     */
    
    public List<T> queryByParameter(String hql,Map<String,Object> map,int maxResults,boolean... isUseCache);
    /**
     * 根据属性分页查询持久化对象。
     * @param map 待查询的属性列表，map的key为指定的属性名，value为对应的属性值
     * @param startNum 开始页码
     * @param maxNum 每页最大记录条数
     * @return 返回符合条件的持久化对象列表。
     */
    public List<T> queryByParameter(Map<String,Object> map,int startNum,int maxNum,boolean... isUseCache);
    

	/**@Description: 根据参数查询对象
	 * @param hql 
	 * @param map 参数
	 * @param startNum 查询开始的位置
	 * @param maxNum 查询的最多的对象个数
	 * eg:hql 为"from User where name =:name1"
	 * Map map = new HashMap();
	 * map.put("name1","dsds");
	 */
    public List<T> queryByParam(String hql, Map<String, Object> map, int startNum, int maxNum,boolean... isUseCache);
    
    
    
    /**@Description: 根据参数查询对象
	 * @param hql 
	 * @param map 参数
	 */
    public List<T> queryByParameter(String hql, Map<String, Object> map,boolean... isUseCache);
    
    /**
	 * 
	 * @Title: queryByParamUnique
	 * @Description: 根据参数得到唯一的实体对象
	 * @param hql
	 * @param map 要传递的参数
	 * @return
	 */
    public T queryByParamUnique(String hql, Map<String, Object> map);
    
    
    
    /**
	 * SQL查询.
	 * 
	 * @param <T>
	 * @param hql
	 * @param params
	 * @return
	 */
	public <T> List<T> queryBySQL(String hql, Map<String, Object> params);

	/**
	 * 
	 * @Title: queryBySQL
	 * @Description: 根据SQL查询实体对象列表
	 * @param hql
	 * @param params
	 * @param limit
	 * @return: List<T>
	 */
	public <T> List<T> queryBySQL(String hql, Map<String, Object> params, Integer limit);
	
	
	/**
	 * 分页查询. queryPage:
	 * 
	 * @param page
	 * @param sql
	 * @param params
	 */
	public void queryPage(Page<Map<String, Object>> page, String sql, Map<String, Object> params);
	
	
	
	/**
	 * 根据指定hql查询出结果总数
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @author thomas
	 */
	public long countHqlResult(final String hql, final Map<String, Object> values);
	public long countHqlResultWithGroupBy(String hql, Map<String, Object> values);

	/**
	 * 根据指定sql查询出结果总数
	 * 
	 * @param sql
	 * @param values
	 * @return
	 * @author thomas
	 */
	public long countSqlResult(final String sql, final Map<String, Object> values);
	public long countSqlResultWithGroupBy(String hql, Map<String, Object> values);
	
	
	
	
	/**
	 * 
	 * @Title: findPage
	 * @Description: 翻页查询
	 * @param page
	 * @param hql
	 * @param values
	 * @return
	 * @return: Page<T>
	 */
	public <T> Page<T> findPage(final Page<T> page, final String hql, final Map<String, Object> values);
	public <T> Page<T> findPageWithGroupBy(final Page<T> page, final String hql, final Map<String, Object> values);

	/**
	 * 
	 * @Title: findPageSQL
	 * @Description: 通过原生sql查询翻页数据
	 * @param page
	 * @param sql
	 * @param values
	 * @return: Page
	 */
	public Page findPageSQL(final Page page, final String sql, final Map<String, Object> values);
	public Page findPageSQLWithGroupBy(final Page page, final String sql, final Map<String, Object> values);
	
	
	
	/**
	 * 查询出唯一值
	 * 
	 * @param <T>
	 * @param hql
	 * @param values
	 * @return
	 * @author thomas
	 */
	public <T> T findUnique(final String hql, final Map<String, Object> values);
	public <T> T findUniqueByLimit(String hql, Map<String, Object> values);
	
	

	/**
	 * 查询指定ID的持久化对象
	 * 
	 * @param ids
	 *            待查询的ID
	 * @return 如果指定ID的持久化对象存在，则返回对应的对象，如果不存在则返回null。
	 */
	public <T> T findById(Class<T> type, long ids);
    
	/**
	 * 批量执行语句，如update
	 * 
	 * @param hql
	 * @param map
	 * @return
	 */
	public int batchExcute(String hql, Map<String, Object> map);
	
	public int batchExcuteSql(String sql, Map<String, Object> map);
	
	public T queryByParamUniqueByMax(String hql, Map<String, Object> map);
	
	/**
	 * 保存
	 * @param o
	 * @return
	 */
	public Object mergeObject(Object o);
	public void flush();
	
    
}
