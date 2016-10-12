package com.mo9.data.cashmere.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.mo9.webUtils.Page;

/**
 * 泛型类的实现
 * @author thomas
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public abstract  class BaseDaoImpl<T> extends HibernateDaoSupport implements
		IBaseDao<T> {

	// 实体类的类型
	protected Class entityClass;

		
	// 通过反射机制获取泛型对应的实体类的类型
	protected BaseDaoImpl() {
				Type genType = getClass().getGenericSuperclass();
				Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
				entityClass = (Class) params[0];
	}
	
	/**
	 * 
	 * @Title: 根据用户传递的不定参数，来确定是否使用缓存
	 * @Description: TODO
	 * @param useCaches
	 * @return
	 * @return: boolean
	 */
	private boolean getUseCache(boolean[] useCaches) {
		if (useCaches == null || useCaches.length == 0) {
			return false;
		}
		if (useCaches.length > 1) {
			throw new IllegalArgumentException("use cache 参数只能传递一个");
		}
		return useCaches[0];
	}

	

	/**
	 * 
	 * @Title: findById
	 * @Description: 通过id查询实体
	 * @param ids
	 * @param booleans
	 *            是否使用缓存 如果查询
	 * @return: T 范型对象
	 * @throws IllegalArgumentException
	 *             () 当不定参数的长度大于1的时候
	 */
	@Override
	public T findById(long id) {
		return (T) this.getSession().get(entityClass, id);
	}

	/**
	 * @Description: 新建或修改对象
	 * @param entity
	 *            要保存的对象
	 */
	@Override
	public long save(T entity) {
		return (Long) this.getHibernateTemplate().save(entity);
	}

	/**
	 * @Description: 新建或修改对象
	 * @param entity
	 *            要保存的对象
	 */
	@Override
	public long saveTWithCharset(final T entity, final String charset, final String entityTblName, 
			final String[] fieldsToHex, final String[] valuesToHex) {
		final HibernateTemplate template = this.getHibernateTemplate();
		
		return (Long) template.executeWithNativeSession(new HibernateCallback<Serializable>() {
			public Serializable doInHibernate(Session session) throws HibernateException {
				session.createSQLQuery("SET NAMES " + charset).executeUpdate();
				checkWriteOperationAllowed(template, session);
				Serializable ret = template.save(entity);
				if(ret != null && fieldsToHex != null && fieldsToHex.length > 0){
					StringBuilder sql = new StringBuilder("UPDATE " + entityTblName + " SET ");
					for(int i = 0;i < fieldsToHex.length;i++){
						if(i >= 1){
						   sql.append(",");
						}
						String field = fieldsToHex[i];
						sql.append( field + " = UNHEX('" + valuesToHex[i] + "') ");
					}
					sql.append(" WHERE id=" + ret);
					session.createSQLQuery(sql.toString()).executeUpdate();						
				}			
				return ret;
			}
		});
	}

	/**
	 * @Description: 新建或修改对象
	 * @param entity
	 *            要保存的对象
	 */
	@Override
	public T merge(T entity) {
		entity = (T) this.getSession().merge(entity);
		return entity;
	}

	/**
	 * @Description: 删除对象
	 * @param entity
	 *            要删除的对象
	 */
	@Override
	public void delete(T entity) {
		this.getSession().delete(entity);
	}

	/**
	 * @Description: 删除对象
	 * @param entity
	 *            要删除的对象的类型
	 * @param entity
	 *            要删除的对象的主键
	 */
	@Override
	public void deleteById(long id) {
		delete(this.findById(id));
	}

	/**
	 * @Description: 删除对象
	 * @param entity
	 *            要删除的对象的类型
	 * @param entity
	 *            要删除的对象的主键的集合 比如id ="1,2,3"，必须一逗号分隔
	 */
	@Override
	public void deleteByIds(String ids) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("delete from ").append(entityClass.getName())
				.append(" where id in (:ids)");
		Query query = this.getSession().createQuery(stringBuilder.toString());
		query.setParameter("ids", ids.split(","));
		query.executeUpdate();
	}

	/**
	 * @Description: 查询对象
	 * @param entity
	 *            要查询的对象的类型
	 */
	@Override
	public List<T> findAll(boolean... isUseCaches) {
		Criteria criteria = this.getSession().createCriteria(entityClass);

		boolean useCache = getUseCache(isUseCaches);
		criteria.setCacheable(useCache);
		return criteria.list();
	}

	/**
	 * @Description: 查询对象
	 * @param entity
	 *            要查询的对象的类型
	 * @param entity
	 *            要查询的对象的主键的集合 eg:id ="1,2,3"，必须一逗号分隔
	 */
	@Override
	public List<T> findByIds(String ids, boolean... isUseCaches) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		String[] idArray = ids.split(",");
		Long[] idLong = new Long[idArray.length];
		for (int i = 0; i < idArray.length; i++) {
			idLong[i] = Long.parseLong(idArray[i]);
		}
		criteria.add(Restrictions.in("id", idLong));

		boolean useCache = getUseCache(isUseCaches);
		criteria.setCacheable(useCache);
		return criteria.list();
	}
	
	/**
	 * @Description: 查询对象
	 * @param entity
	 *            要查询的对象的类型
	 * @param entity
	 *            要查询的对象的主键的集合 eg:id ="1,2,3"，必须一逗号分隔
	 */
	@Override
	public List<T> findByIds(List<Long> ids, boolean... isUseCaches) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.in("id", ids));

		boolean useCache = getUseCache(isUseCaches);
		criteria.setCacheable(useCache);
		return criteria.list();
	}

	/**
	 * @Description: 查询对象
	 * @param startNum
	 *            查询开始的位置
	 * @param maxNum
	 *            查询的最多的对象个数
	 */
	@Override
	public List<T> findAll(int startNum, int maxNum, boolean... isUseCaches) {
		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.setFirstResult(startNum);
		criteria.setMaxResults(maxNum);

		boolean useCache = getUseCache(isUseCaches);
		criteria.setCacheable(useCache);
		return criteria.list();
	}

	/**
	 * @Description: 根据参数查询对象
	 * @param hql
	 * @param map
	 *            参数
	 */
	@Override
	public List<T> queryByParameter(Map<String, Object> map,
			boolean... isUseCaches) {

		Query query = this.createQuery(map);

		boolean useCache = getUseCache(isUseCaches);
		query.setCacheable(useCache);
		return query.list();
	}

	/**
	 * @Description: 根据参数查询对象
	 * @param hql
	 * @param map
	 *            参数
	 * @param startNum
	 *            查询开始的位置
	 * @param maxNum
	 *            查询的最多的对象个数 eg:hql 为"from User where name =:name1" Map map =
	 *            new HashMap(); map.put("name1","dsds");
	 */
	@Override
	public List<T> queryByParameter(Map<String, Object> map, int startNum,
			int maxNum, boolean... isUseCaches) {
		Query query = this.createQuery(map);
		query.setFirstResult(startNum);
		query.setMaxResults(maxNum);

		boolean useCache = getUseCache(isUseCaches);
		query.setCacheable(useCache);
		return query.list();
	}

	/**
	 * 
	 * @Title: createQuery
	 * @Description: 根据map拼接hql
	 * @param map
	 *            key为对象的属性名 value为对象的值
	 * @return
	 */
	protected Query createQuery(Map<String, Object> map) {

		StringBuffer hql = new StringBuffer("FROM "
				+ entityClass.getSimpleName());
		if (map != null && !map.isEmpty()) {// 如果参数约束不为空，则拼接HTML约束。
			hql.append("  t WHERE ");
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String attr = it.next();
				if (it.hasNext()) {// 如果 还有后续节点，则添加And
					hql.append(" t." + attr + "=:" + attr + " AND ");
				} else {// 如果没有后续节点，则取消And
					hql.append(" t." + attr + "=:" + attr);
				}
			}
		}
		Query query = this.getSession().createQuery(hql.toString());
		// Iterator<String> keys = map.keySet().iterator();
		// while(keys.hasNext()){
		// String key = keys.next();
		// Object values = map.get(key);
		// query.setParameter(key, values);
		// }
		if (map != null) {
			query.setProperties(map);
		}
		return query;
	}

	/**
	 * @Description: 根据参数查询对象
	 * @param hql
	 * @param map
	 *            参数
	 * @param startNum
	 *            查询开始的位置
	 * @param maxNum
	 *            查询的最多的对象个数 eg:hql 为"from User where name =:name1" Map map =
	 *            new HashMap(); map.put("name1","dsds");
	 */
	@Override
	public List<T> queryByParam(String hql, Map<String, Object> map,
			int startNum, int maxNum, boolean... isUseCaches) {
		Query query = this.createQuery(hql, map);
		query.setFirstResult(startNum);
		query.setMaxResults(maxNum);

		boolean useCache = getUseCache(isUseCaches);
		query.setCacheable(useCache);
		return query.list();
	}

	/**
	 * @Description: 根据参数查询对象
	 * @param hql
	 * @param map
	 *            参数
	 */
	@Override
	public List<T> queryByParameter(String hql, Map<String, Object> map,
			boolean... isUseCaches) {
		Query query = this.createQuery(hql, map);
		boolean useCache = getUseCache(isUseCaches);
		query.setCacheable(useCache);
		return query.list();
	}

	/**
	 * 
	 * @Title: createQuery
	 * @Description: 组装以及传递参数
	 * @param hql
	 * @param map
	 *            要传递的参数
	 * @return
	 */
	protected Query createQuery(String hql, Map<String, Object> map) {
		Query query = this.getSession().createQuery(hql);
		if (map == null) {
			return query;
		}
		// Iterator<String> keys = map.keySet().iterator();
		// while (keys.hasNext()) {
		// String key = keys.next();
		// Object values = map.get(key);
		// query.setParameter(key, values);
		// }

		if (map != null) {
			query.setProperties(map);
		}
		return query;
	}

	/**
	 * 
	 * @Title: queryByParamUnique
	 * @Description: 根据参数得到唯一的实体对象
	 * @param hql
	 * @param map
	 *            要传递的参数
	 * @return
	 */
	@Override
	public T queryByParamUniqueByMax(String hql, Map<String, Object> map) {
		Query query = this.createQuery(hql, map);
		query.setMaxResults(1);
		return (T) query.uniqueResult();
	}
	
	@Override
	public T queryByParamUnique(String hql, Map<String, Object> map) {
		Query query = this.createQuery(hql, map);
		return (T) query.uniqueResult();
	}

	/**
	 * 
	 * @Title: queryByParameter
	 * @Description: 根据
	 * @param hql
	 *            where以前的hql
	 * @param map
	 *            要传递的参数
	 * @param 返回的最大数
	 * @return
	 */
	@Override
	public List<T> queryByParameter(String hql, Map<String, Object> map,
			int maxResults, boolean... isUseCaches) {
		Query query = this.createQuery(hql, map).setMaxResults(maxResults);

		boolean useCache = getUseCache(isUseCaches);
		query.setCacheable(useCache);
		return query.list();
	}


	/**
	 * 
	 * @Title: findById
	 * @Description: 根据id得到实体对象
	 * @param id
	 *            主键
	 * @param lockOptions
	 *            锁的
	 * @return
	 */
	@Override
	public T findById(long id, LockOptions lockOptions) {
		return (T) this.getSession().get(entityClass, id, lockOptions);
	}

	/**
	 * SQL查询.
	 * 
	 * @param <T>
	 * @param hql
	 * @param params
	 * @return
	 */
	@Override
	public <T> List<T> queryBySQL(String sql, Map<String, Object> params) {
		return queryBySQL(sql, params, null);
	}

	/**
	 * 
	 * @Title: queryBySQL
	 * @Description: 根据SQL查询实体对象列表
	 * @param hql
	 * @param params
	 * @param limit
	 * @return: List<T>
	 */
	@Override
	public <T> List<T> queryBySQL(String sql, Map<String, Object> params,
			Integer limit) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (limit != null && limit > 0) {
			query.setMaxResults(limit);
		}
		return query.setProperties(params).list();
	}

	/**
	 * 分页查询. queryPage:
	 * 
	 * @param page
	 * @param sql
	 * @param params
	 */
	@Override
	public void queryPage(Page<Map<String, Object>> page, String sql,
			Map<String, Object> params) {

		if (page.isAutoCount()) {
			/** 统计记录总条数 */
			int index = sql.indexOf("FROM") > 0 ? sql.indexOf("FROM") : sql
					.indexOf("from");
			String countSql = "SELECT COUNT(*) " + sql.substring(index);
			List<Long> count = this.queryBySQL(countSql, params);
			page.setTotalCount(count.get(0));
		}
		// 查询分页信息.

		List<Object[]> result = this.getSession().createSQLQuery(sql)
				.setProperties(params).setMaxResults(page.getPageSize())
				.setFirstResult(page.getFirst() - 1).list();
		if (result != null && !result.isEmpty()) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> res = null;
			for (Object[] row : result) {
				res = new HashMap<String, Object>();
				for (int i = 0; i < row.length; i++) {
					res.put("field_" + i, row[i]);
				}
				list.add(res);
			}
			page.setResult(list);
		}

	}

	/**
	 * 根据指定hql查询出结果总数
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @author thomas
	 */
	@Override
	public long countHqlResult(String hql, Map<String, Object> values) {

		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = findUnique(countHql, values);
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}
	
	@Override
	public long countHqlResultWithGroupBy(String hql, Map<String, Object> values) {

		String fromHql = hql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			Long count = 0L;
			List ql = queryByParameter(countHql, values);
			if(ql != null && ql.size() > 0){
			   for(Object q : ql){
				   count += (Long)q;
			   }
			}
			return count;
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:"
					+ countHql, e);
		}
	}

	/**
	 * 根据指定sql查询出结果总数
	 * 
	 * @param sql
	 * @param values
	 * @return
	 * @author thomas
	 */
	@Override
	public long countSqlResult(String sql, Map<String, Object> values) {

		String fromSql = sql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");

		String countSql = "select count(*) " + fromSql;

		try {
			Query q = createQuerySQL(countSql, values);
			BigInteger decimal = (BigInteger) q.uniqueResult();
			Long count = decimal == null ? 0 : decimal.longValue();
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
	}
	
	@Override
	public long countSqlResultWithGroupBy(String sql, Map<String, Object> values) {

		String fromSql = sql;
		// select子句与order by子句会影响count查询,进行简单的排除.
		fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "order by");

		String countSql = "select count(*) " + fromSql;

		try {
			Long count = 0L;
			List ql = queryBySQL(countSql, values);
			if(ql != null && ql.size() > 0){
			   for(Object q : ql){
				   count += (Long)q;
			   }
			}
			return count;
		} catch (Exception e) {
			throw new RuntimeException("sql can't be auto count, hql is:"
					+ countSql, e);
		}
	}

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
	@Override
	public <T> Page<T> findPage(Page<T> page, String hql,
			Map<String, Object> values) {

		Assert.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	@Override
	public <T> Page<T> findPageWithGroupBy(Page<T> page, String hql, Map<String, Object> values) {

		Assert.notNull(page, "page不能为空");
		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResultWithGroupBy(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 
	 * @Title: findPageSQL
	 * @Description: 通过原生sql查询翻页数据
	 * @param page
	 * @param sql
	 * @param values
	 * @return: Page
	 */
	@Override
	public Page findPageSQL(Page page, String sql, Map<String, Object> values) {

		Assert.notNull(page, "page不能为空");
		SQLQuery q = createQuerySQL(sql, values);

		if (page.isAutoCount()) {
			Long totalCount = countSqlResult(sql, values);
			page.setTotalCount(totalCount);
		}
		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	@Override
	public Page findPageSQLWithGroupBy(Page page, String sql, Map<String, Object> values) {

		Assert.notNull(page, "page不能为空");
		SQLQuery q = createQuerySQL(sql, values);

		if (page.isAutoCount()) {
			Long totalCount = countSqlResultWithGroupBy(sql, values);
			page.setTotalCount(totalCount);
		}
		setPageParameter(q, page);
		List result = q.list();
		page.setResult(result);
		return page;
	}

	/**
	 * 查询出唯一值
	 * 
	 * @param <T>
	 * @param hql
	 * @param values
	 * @return
	 * @author thomas
	 */
	@Override
	public <T> T findUnique(String hql, Map<String, Object> values) {
        Query q = createQuery(hql, values);
		return (T) q.uniqueResult();
	}

	/**
	 * 可能出现不唯一的情况，查询出唯一值by setMaxResults(1)
	 * 
	 * @param <T>
	 * @param hql
	 * @param values
	 * @return
	 * @author thomas
	 */
	@Override
	public <T> T findUniqueByLimit(String hql, Map<String, Object> values) {
        Query q = createQuery(hql, values);
        q.setMaxResults(1);
		List<T> l = q.list();
		if(l != null && l.size() > 0){
			return l.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * @Title: createQuerySQL
	 * @Description: 创建SQLQuery对像
	 * @param sql
	 * @param map
	 * @return: SQLQuery
	 */
	private SQLQuery createQuerySQL(String sql, Map<String, Object> map) {

		SQLQuery query = this.getSession().createSQLQuery(sql);
		if (map == null) {
			return query;
		}
		if (map != null) {
			query.setProperties(map);
		}
		return query;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected <T> Query setPageParameter(final Query q, final Page<T> page) {
		// hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 查询指定ID的持久化对象
	 * 
	 * @param ids
	 *            待查询的ID
	 * @return 如果指定ID的持久化对象存在，则返回对应的对象，如果不存在则返回null。
	 */
	@Override
	public <T> T findById(Class<T> type, long ids) {

		return (T) getHibernateTemplate().get(type, ids);
	}

	@Override
	public int batchExcute(String hql, Map<String, Object> map) {
		return createQuery(hql, map).executeUpdate();
		}

	public int batchExcuteSql(String sql, Map<String, Object> map) {
		SQLQuery query = getSession().createSQLQuery(sql);
		if (map != null) {
			query.setProperties(map);
		}
		return query.executeUpdate();
	}
	
	public Object mergeObject(Object o){
		return this.getSession().merge(o);
	}
	public void flush(){
		getSession().flush();
	}
	

	/** 执行存储过程，不返回值 eg: "{ call procedure_name(?,?,?,?)}" */
	public Object executeStoreProcedure(String name, Map<Integer, Object> map) {
		Object resultObjct;
		List list = new ArrayList();
		Session session = getSession();
		Connection conn = session.connection();
		CallableStatement cs = null;
		try {
			logger.info("excute:" + name);
			cs = conn.prepareCall(name);
			for (int i = 0; i < map.size(); i++) {
				Object param =map.get(i + 1);
				if (param instanceof String){
					cs.setString(i + 1, (String)param);
				}else if (param instanceof java.util.Date){
					java.util.Date uDate = (java.util.Date)param;
					Date date =new Date(uDate.getTime());
					cs.setDate(i + 1, date);
				}
			}
			boolean hadResults = cs.execute();
			if (hadResults) {
				resultObjct = list;
			} else {
				resultObjct = true;
			}
			while (hadResults) {
				ResultSet rs = cs.getResultSet();

				if (rs != null) {
					ResultSetMetaData tsmt = rs.getMetaData();
					int count = tsmt.getColumnCount();
					while (rs.next()) {
						Object[] array = new Object[count];
//						Object result = entityClass.newInstance();
						for (int i = 1; i <= count; i++) {
							String propertyName = column2PropertyName(tsmt.getColumnName(1));
							Object val=null;
//							if (result != null) {
								if (Types.DATE == tsmt.getColumnType(i)) {
									Date value = rs.getDate(i);
									val = value == null ? null : new java.util.Date(value.getTime());
								} else {
									val = rs.getObject(i);
								}
								array[i-1] =val;
//							} else {
//								result = rs.getObject(i);
//							}
						}
						list.add(array);
					}

					rs.close();
				}
				hadResults = cs.getMoreResults(); // 检查是否存在更多结果集
			}
		} catch (Exception e) {
			logger.error("executeStoreProcedure error", e);
			throw new RuntimeException(e);
		} finally {
			try {
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e2) {
				logger.error("executeStoreProcedure error", e2);
				throw new RuntimeException(e2);
			}
		}
		return resultObjct;
	}

	/**
	 * 执行函数 <br/>
	 * executeStoreProcedure("{?= call fn_procedure_name(?,?,?)}",
	 * map,FunctionVo.class);
	 * 
	 */
	public Object executeFunction(String name, Map<Integer, ? extends Object> map, Class entityClass) {

		Object resultObjct;
		List list = new ArrayList();
		Connection connection = getSession().connection();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		logger.info("excute:" + name);
		try {
			callableStatement = connection.prepareCall(name);
			if ( entityClass == null ) {
				callableStatement.registerOutParameter(1, Types.NUMERIC);
			} else if ( entityClass == String.class ) {
				callableStatement.registerOutParameter(1, Types.VARCHAR);
			} else {
				callableStatement.registerOutParameter(1, Types.REF);
			}
			for ( int i = 1; i <= map.size(); i++ ) {
				Object value = map.get(i);
				if ( value instanceof String ) {
					callableStatement.setString(i + 1, (String) value);
				} else if ( value instanceof Date ) {
					callableStatement.setDate(i + 1, (Date) value);
				} else if ( value instanceof Integer ) {
					callableStatement.setInt(i + 1, (Integer) value);
				} else {
					if ( value == null ) {
						callableStatement.setString(i + 1, null);
					} else {
						callableStatement.setObject(i + 1, value);
					}
				}
				// callableStatement.setObject(i + 1, (String) value);
			}
			callableStatement.execute();
			if ( entityClass == null ) {
				resultObjct = callableStatement.getObject(1);
			} else if ( entityClass == String.class ) {
				resultObjct = callableStatement.getObject(1);
			} else {
				resultObjct = list;
				rs = (ResultSet) callableStatement.getObject(1);
			}
			if ( rs != null ) {
				ResultSetMetaData tsmt = rs.getMetaData();
				int count = tsmt.getColumnCount();
				while (rs.next()) {
					Object result = entityClass.newInstance();
					for ( int i = 1; i <= count; i++ ) {
						String propertyName = column2PropertyName(tsmt.getColumnName(i));
						if ( result != null ) {
							if ( Types.DATE == tsmt.getColumnType(i) ) {
								Date value = rs.getDate(i);
								java.util.Date value1 = value == null ? null : new java.util.Date(value.getTime());
								PropertyUtils.setProperty(result, propertyName, value1);
								// setValue2Entity(result, propertyName,
								// value1);
							} else if ( Types.NUMERIC == tsmt.getColumnType(i) ) {
								Object value = rs.getObject(i);
								PropertyUtils.setProperty(result, propertyName, value);
								// setValue2Entity(result, propertyName, value);
							} else if ( Types.VARCHAR == tsmt.getColumnType(i) || Types.CHAR == tsmt.getColumnType(i) ) {
								Object value = rs.getObject(i);
								// setValue2Entity(result, propertyName, value);
								PropertyUtils.setProperty(result, propertyName, value);
							}
						} else {
							result = rs.getObject(i);
						}
					}
					list.add(result);
				}

				rs.close();
			}
			// 测试结果:不影响程序
			if ( callableStatement != null ) {
				callableStatement.close();
			}
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {

		}
		return resultObjct;
	}

	private String column2PropertyName(String columnName) {

		StringBuffer propertyName = new StringBuffer();
		String[] str = columnName.split("_");
		propertyName.append(StringUtils.lowerCase(str[0]));
		for ( int i = 1; i < str.length; i++ ) {
			propertyName.append(firstCharToUpperCase(StringUtils.lowerCase(str[i])));
		}
		return propertyName.toString();
	}

	private String firstCharToUpperCase(String str) {

		String result = StringUtils.upperCase(StringUtils.substring(str, 0, 1)) + StringUtils.substring(str, 1);
		return result;
	}
	
	/**
	 * Check whether write operations are allowed on the given Session.
	 * <p>Default implementation throws an InvalidDataAccessApiUsageException in
	 * case of <code>FlushMode.MANUAL</code>. Can be overridden in subclasses.
	 * @param session current Hibernate Session
	 * @throws InvalidDataAccessApiUsageException if write operations are not allowed
	 * @see #setCheckWriteOperations
	 * @see #getFlushMode()
	 * @see #FLUSH_EAGER
	 * @see org.hibernate.Session#getFlushMode()
	 * @see org.hibernate.FlushMode#MANUAL
	 */
	private void checkWriteOperationAllowed(HibernateTemplate template, Session session) throws InvalidDataAccessApiUsageException {
		if (template.isCheckWriteOperations() && template.getFlushMode() != HibernateTemplate.FLUSH_EAGER &&
				session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.MANUAL): "+
					"Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}
}

	
