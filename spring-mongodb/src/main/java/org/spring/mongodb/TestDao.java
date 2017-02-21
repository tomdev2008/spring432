package org.spring.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao {

	@Autowired
	private  MongoTemplate mongoTemplate;
	
	
	public void add(Person p){
		/*
		 *添加元素
		 *save是save or update,insert就是insert 
		 */
		mongoTemplate.save(p);
		//mongoTemplate.insert(p); //批量增加或放入指定集合
		
	}
	
	/*
		MongoOperations mongoOps = new MongoTemplate(new Mongo(), "database");
		mongoOps.insert(new Person("Joe", 34));
		log.info(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));
		mongoOps.dropCollection("person"); 
	 */
	
	public void up(Person p){
		Query query = new Query();
		Criteria criteria = Criteria.where("id").gte(123);//大于等于
		
		query.addCriteria(criteria);
		Update update=new Update();
		//age值加2
		update.inc("age", p.getAge());
		update.set("name",p.getName());
		//mongoTemplate.upsert(query, update, Person.class);
		mongoTemplate.updateFirst(query, update, Person.class);
	}
	
	public Person getById(Object id){
		//mongoTemplate.findById(id, entityClass, collectionName);
		return mongoTemplate.findById(id, Person.class);
	}
	
	public List<Person> getKV2(String key,Object value){
		Query query = new Query();
		Criteria criteria = Criteria.where(key).gte(value);//大于等于
		query.addCriteria(criteria);
		return mongoTemplate.find(query, Person.class);
	}
	
	
	public List<Person> getKV(String key,Object value){
		//查询主要用到Query和Criteria两个对象
		Query query = new Query();
		Criteria criteria = Criteria.where(key).is(value);//等于
		// criteria.and("name").is("cuichongfei");等于
		// criteria.and("interest").in(interests);   in查询
		// criteria.and("home.address").is("henan"); 内嵌文档查询
		// criteria.and("").exists(false);           列存在
		// criteria.and("").lte();                   小于等于
		// criteria.and("").regex("");               正则表达式
		// criteria.and("").ne("");                  不等于
		query.addCriteria(criteria);
		
		/*
		 *  排序
			//排序查询sort方法,按照age降序排列
			// query.sort().on("age", Order.DESCENDING);
		 */
		
		/*
		 * 查询指定字段
		     // 指定字段查询,只查询age和name两个字段
			 // query.fields().include("age").include("name");
			    query.fields().include(key);
		 */
		
		/*
		 分页查询
		// query.skip(2).limit(3);
		 * query.skip(5).limit(5);
		 */
		
		
		/*
		 * 统计个数
		 * // System.out.println(mongoTemplate.count(query, User.class));
		 */
		return mongoTemplate.find(query, Person.class);
	}
	
	public List<Person> getAll(){
		return mongoTemplate.findAll(Person.class);
	}
	
	public void del(Person p){
		Query query = new Query();
		Criteria criteria = Criteria.where("age").gte(1);//大于等于
		query.addCriteria(criteria);
		mongoTemplate.remove(query, Person.class);
	}
	
	public void up(){
		/*
		 *  //update(query,update,class)
			//Query query:需要更新哪些用户,查询参数
			//Update update:操作符,需要对数据做什么更新
			//Class class:实体类
			
			//更新age大于24的用户信息
			Query query = new Query();
			query.addCriteria(where("age").gt(24));
			
			Update update = new Update();
			//age值加2
			update.inc("age", 2);
			// update.set("name", "zhangsan"); 直接赋值
			// update.unset("name");           删去字段   
			// update.push("interest", "java"); 把java追加到interest里面,interest一定得是数组
			// update.pushAll("interest", new String[]{".net","mq"}) 用法同push,只是pushAll一定可以追加多个值到一个数组字段内
			// update.pull("interest", "study"); 作用和push相反,从interest字段中删除一个等于value的值
			// update.pullAll("interest", new String[]{"sing","dota"})作用和pushAll相反
			// update.addToSet("interest", "study") 把一个值添加到数组字段中,而且只有当这个值不在数组内的时候才增加
			// update.rename("oldName", "newName") 字段重命名
			
			//只更新第一条记录,age加2,name值更新为zhangsan
			mongoTemplate.updateFirst(query, new Update().inc("age", 2).set("name", "zhangsan"), User.class);
			
			//批量更新,更新所有查询到的数据
			mongoTemplate.updateMulti(query, update, User.class);
		 */
	}
	
	/*
			MongoTemplate(Mongo, String)
			MongoTemplate(Mongo, String, UserCredentials)
			MongoTemplate(MongoDbFactory)
			MongoTemplate(MongoDbFactory, MongoConverter)
			
			setWriteResultChecking(WriteResultChecking)
			setWriteConcern(WriteConcern)
			setWriteConcernResolver(WriteConcernResolver)
			setReadPreference(ReadPreference)
			setApplicationContext(ApplicationContext)
			prepareIndexCreator(ApplicationContext)
			getConverter()
			
			getCollectionName(Class<?>)
			executeCommand(String)
			executeCommand(DBObject)
			executeCommand(DBObject, int)
			logCommandExecutionError(DBObject, CommandResult)
			executeQuery(Query, String, DocumentCallbackHandler)
			executeQuery(Query, String, DocumentCallbackHandler, CursorPreparer)
			execute(DbCallback<T>)
			execute(Class<?>, CollectionCallback<T>)
			execute(String, CollectionCallback<T>)
			executeInSession(DbCallback<T>)
			createCollection(Class<T>)
			createCollection(Class<T>, CollectionOptions)
			createCollection(String)
			createCollection(String, CollectionOptions)
			getCollection(String)
			collectionExists(Class<T>)
			collectionExists(String)
			dropCollection(Class<T>)
			dropCollection(String)
			indexOps(String)
			indexOps(Class<?>)
			findOne(Query, Class<T>)
			findOne(Query, Class<T>, String)
			exists(Query, Class<?>)
			exists(Query, String)
			exists(Query, Class<?>, String)
			find(Query, Class<T>)
			find(Query, Class<T>, String)
			findById(Object, Class<T>)
			findById(Object, Class<T>, String)
			geoNear(NearQuery, Class<T>)
			geoNear(NearQuery, Class<T>, String)
			findAndModify(Query, Update, Class<T>)
			findAndModify(Query, Update, Class<T>, String)
			findAndModify(Query, Update, FindAndModifyOptions, Class<T>)
			findAndModify(Query, Update, FindAndModifyOptions, Class<T>, String)
			findAndRemove(Query, Class<T>)
			findAndRemove(Query, Class<T>, String)
			count(Query, Class<?>)
			count(Query, String)
			count(Query, Class<?>, String)
			insert(Object)
			insert(Object, String)
			ensureNotIterable(Object)
			prepareCollection(DBCollection)
			prepareWriteConcern(MongoAction)
			doInsert(String, T, MongoWriter<T>)
			toDbObject(T, MongoWriter<T>)
			initializeVersionProperty(Object)
			insert(Collection<? extends Object>, Class<?>)
			insert(Collection<? extends Object>, String)
			insertAll(Collection<? extends Object>)
			doInsertAll(Collection<? extends T>, MongoWriter<T>)
			doInsertBatch(String, Collection<? extends T>, MongoWriter<T>)
			save(Object)
			save(Object, String)
			doSaveVersioned(T, MongoPersistentEntity<?>, String)
			doSave(String, T, MongoWriter<T>)
			insertDBObject(String, DBObject, Class<?>)
			insertDBObjectList(String, List<DBObject>)
			saveDBObject(String, DBObject, Class<?>)
			upsert(Query, Update, Class<?>)
			upsert(Query, Update, String)
			upsert(Query, Update, Class<?>, String)
			updateFirst(Query, Update, Class<?>)
			updateFirst(Query, Update, String)
			updateFirst(Query, Update, Class<?>, String)
			updateMulti(Query, Update, Class<?>)
			updateMulti(Query, Update, String)
			updateMulti(Query, Update, Class<?>, String)
			doUpdate(String, Query, Update, Class<?>, boolean, boolean)
			increaseVersionForUpdateIfNecessary(MongoPersistentEntity<?>, Update)
			dbObjectContainsVersionProperty(DBObject, MongoPersistentEntity<?>)
			remove(Object)
			remove(Object, String)
			extractIdPropertyAndValue(Object)
			getIdQueryFor(Object)
			getIdInQueryFor(Collection<?>)
			assertUpdateableIdIfNotSet(Object)
			remove(Query, String)
			remove(Query, Class<?>)
			remove(Query, Class<?>, String)
			doRemove(String, Query, Class<T>)
			findAll(Class<T>)
			findAll(Class<T>, String)
			mapReduce(String, String, String, Class<T>)
			mapReduce(String, String, String, MapReduceOptions, Class<T>)
			mapReduce(Query, String, String, String, Class<T>)
			mapReduce(Query, String, String, String, MapReduceOptions, Class<T>)
			group(String, GroupBy, Class<T>)
			group(Criteria, String, GroupBy, Class<T>)
			aggregate(TypedAggregation<?>, Class<O>)
			aggregate(TypedAggregation<?>, String, Class<O>)
			aggregate(Aggregation, Class<?>, Class<O>)
			aggregate(Aggregation, String, Class<O>)
			findAllAndRemove(Query, String)
			findAllAndRemove(Query, Class<T>)
			findAllAndRemove(Query, Class<T>, String)
			doFindAndDelete(String, Query, Class<T>)
			aggregate(Aggregation, String, Class<O>, AggregationOperationContext)
			replaceWithResourceIfNecessary(String)
			copyQuery(Query, DBObject)
			copyMapReduceOptions(MapReduceOptions, MapReduceCommand)
			getCollectionNames()
			getDb()
			maybeEmitEvent(MongoMappingEvent<T>)
			doCreateCollection(String, DBObject)
			doFindOne(String, DBObject, DBObject, Class<T>)
			doFind(String, DBObject, DBObject, Class<T>)
			doFind(String, DBObject, DBObject, Class<T>, CursorPreparer)
			doFind(String, DBObject, DBObject, Class<S>, CursorPreparer, DbObjectCallback<T>)
			convertToDbObject(CollectionOptions)
			doFindAndRemove(String, DBObject, DBObject, DBObject, Class<T>)
			doFindAndModify(String, DBObject, DBObject, DBObject, Class<T>, Update, FindAndModifyOptions)
			populateIdIfNecessary(Object, Object)
			getAndPrepareCollection(DB, String)
			executeFindOneInternal(CollectionCallback<DBObject>, DbObjectCallback<T>, String)
			executeFindMultiInternal(CollectionCallback<DBCursor>, CursorPreparer, DbObjectCallback<T>, String)
			executeQueryInternal(CollectionCallback<DBCursor>, CursorPreparer, DocumentCallbackHandler, String)
			getPersistentEntity(Class<?>)
			getIdPropertyFor(Class<?>)
			determineEntityCollectionName(T)
			determineCollectionName(Class<?>)
			handleAnyWriteResultErrors(WriteResult, DBObject, MongoActionOperation)
			potentiallyConvertRuntimeException(RuntimeException)
			handleCommandError(CommandResult, DBObject)
			getDefaultMongoConverter(MongoDbFactory)
			getMappedSortObject(Query, Class<?>)
			FindOneCallback
			FindCallback
			FindAndRemoveCallback
			FindAndModifyCallback
			DbObjectCallback<T>
			ReadDbObjectCallback<T>
			UnwrapAndReadDbObjectCallback<T>
			DefaultWriteConcernResolver
			QueryCursorPreparer
			GeoNearResultDbObjectCallback<T>
	 */
}
