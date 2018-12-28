/*
 * Copyright (c) 2018 Anzen Soluciones S.A. de C.V.
 * Mexico D.F.
 * All rights reserved.
 *
 * THIS SOFTWARE IS  CONFIDENTIAL INFORMATION PROPIETARY OF ANZEN SOLUCIONES.
 * THIS INFORMATION SHOULD NOT BE DISCLOSED AND MAY ONLY BE USED IN ACCORDANCE THE TERMS DETERMINED BY THE COMPANY ITSELF.
 */
package mx.com.anzen.app.abanking.fake.user.service.redis.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import mx.com.anzen.app.abanking.fake.user.util.RedisUtils;



/**
 * <p>TODO [Add comments of the class]</p>
 *
 * @version abanking-fake-user
 * @since abanking-fake-user
 */
public abstract class AbstractRedisSimpleCrudRepository<T, KEY extends Serializable> implements 
	RedisSimpleCrudRepository<T, KEY> {
	
	@Autowired
	private RedisTemplate<String, T> entitiesRedisTemplate;
	private Class<T> entityClass;
	
	/**
	 * 
	 */
	public AbstractRedisSimpleCrudRepository() {
		this.entityClass = getEntityClass();
	}

	/**
	 * {@inheritDoc} Save a given entity in Redis.
	 * @param entity .- {@link T}
	 * @return &lt;{@link S} {@code extends} {@link T}&gt;
	 */
	@Override
	public <S extends T> S save(S entity) {
		entitiesRedisTemplate.opsForValue().set(getCompositeKey(entity), entity);
		return entity;
	}

	/**
	 * {@inheritDoc} Reuse the {@code save(S entity)} method.
	 * @param entities .- {@link Iterable}&lt;{@link S}&gt;
	 * @return &lt;{@link S} {@code extends} {@link T}&gt; {@inheritDoc}
	 */
	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		for (S s : entities) {
			save(s);
		}
		return entities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T findOne(KEY key) {
		return (T) entitiesRedisTemplate.opsForValue().get(getCompositeKey(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(KEY key) {
		return entitiesRedisTemplate.hasKey(getCompositeKey(key));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> findAll() {
		return getAll(RedisUtils.WILDCARD);
	}

	/**
	 * {@inheritDoc}
	 * @param keys .- {@link Iterable}&lt;{@link KEY}&gt;
	 * @return entities .- {@link Iterable}&lt;{@link S} {@code extends} {@link T}&gt;
	 */
	@Override
	public Iterable<T> findAll(Iterable<KEY> keys) {
		List<String> stringKeys = new ArrayList<String>();
		for(KEY key : keys){
			stringKeys.add(getCompositeKey(key));
		}
		return entitiesRedisTemplate.opsForValue().multiGet(stringKeys);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(KEY key) {
		entitiesRedisTemplate.delete(getCompositeKey(key));

	}

	/**
	 * {@inheritDoc}
	 * @param entity .- {@link T}
	 */
	@Override
	public void delete(T entity) {
		delete(getKey(entity));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Iterable<? extends T> entities) {
		for (T t : entities) {
			delete(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAll() {
		Iterable<T> entities = findAll();
		for (T t : entities) {
			delete(t);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		// TODO: Can be better.
		Long result = new Long(0);
		for(T element : findAll()){
			result++;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <S extends T> S save(S entity, Long timeout) {
		entitiesRedisTemplate.opsForValue().set(getCompositeKey(entity), entity, timeout, TimeUnit.SECONDS);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean timeout(KEY key, Long timeout) {
		return entitiesRedisTemplate.expire(getCompositeKey(key), timeout, TimeUnit.SECONDS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean timeout(T t, Long timeout) {
		return entitiesRedisTemplate.expire(getCompositeKey(t), timeout, TimeUnit.SECONDS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterable<T> find(String pattern) {
		return getAll(pattern);
	}

	/**
	 * Return a {@link RedisTemplate} object.
	 * @return {@link RedisTemplate}&lt;{@link String}, {@link T}&gt;
	 */
	public RedisTemplate<String, T> getEntitiesRedisTemplate() {
		return entitiesRedisTemplate;
	}

	/**
	 * Set a {@link RedisTemplate} object.
	 * @param {@link RedisTemplate}&lt;{@link String}, {@link T}&gt; entitiesRedisTemplate
	 */
	public void setEntitiesRedisTemplate(RedisTemplate<String, T> entitiesRedisTemplate) {
		this.entitiesRedisTemplate = entitiesRedisTemplate;
	}

	/**
	 * Return the class name as String.
	 * @return {@link String}
	 */
	protected String getClassName() {
		return getEntityClass().getName();
	}

	protected Class<T> getEntityClass() {
		if (!isEntityClassSet()) {
			try {
				this.entityClass = resolveReturnedClassFromGernericType();
			} catch (Exception e) {
				throw new InvalidDataAccessApiUsageException(
					"Unable to resolve EntityClass. Please use according setter!", e);
			}
		}
		return entityClass;
	}

	protected final void setEntityClass(Class<T> entityClass) {
		Assert.notNull(entityClass, "EntityClass must not be null.");
		this.entityClass = entityClass;
	}

	protected boolean isEntityClassSet() {
		return entityClass != null;
	}

	@SuppressWarnings("unchecked")
	private Class<T> resolveReturnedClassFromGernericType() {
		ParameterizedType parameterizedType = resolveReturnedClassFromGernericType(getClass());
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	private ParameterizedType resolveReturnedClassFromGernericType(Class<?> clazz) {
		Object genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Type rawtype = parameterizedType.getRawType();
			if (AbstractRedisSimpleCrudRepository.class.equals(rawtype)) {
				return parameterizedType;
			}
		}
		return resolveReturnedClassFromGernericType(clazz.getSuperclass());
	}

	protected String getCompositeKey(T entity) {
		return getCompositeKey(getKey(entity));
	}

	protected Iterable<T> getAll(String pattern) {
		Set<String> keys = entitiesRedisTemplate.keys(pattern);
		return entitiesRedisTemplate.opsForValue().multiGet(keys);
	}

	protected String getCompositeKey(KEY key) {
		String k = (key != null) ? key.toString() : "";
		return getNamespace().concat(RedisUtils.FIELD_SEPARATOR).concat(k);
	}

	public String getNamespace() {
		return getClassName().toString();
	}

	protected Iterable<String> getCompositeKeys(Set<KEY> keys) {
		List<String> result =new ArrayList<String>();
		for(KEY key : keys){
			result.add(getCompositeKey(key));
		}
		return result;
	}

	/**
	 * This method has to be implemente in order to get the right key.
	 * @param t
	 * @return
	 */
	public abstract KEY getKey(T t);
}
