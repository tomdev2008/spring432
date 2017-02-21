package org.spring.mongodb;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 1.@Id - 文档的唯一标识，在mongodb中为ObjectId，它是唯一的，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。
 * 2.@Document - 把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
 * 3.@Indexed - 声明该字段需要索引，建索引可以大大的提高查询效率。
 * 4.@Transient - 映射忽略的字段，该字段不会保存到MongoDB
 * 5.@CompoundIndex - 复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
 * 6.@PersistenceConstructor - 声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据。
 */
@Document(collection = "persons")
public class Person	implements Serializable {

	/**   */
    private static final long serialVersionUID = 932487033750484879L;

    /**
     * 注意这个有点特殊！！！
     * 
     * org.bson.types.ObjectId
     */
	@Id
	private ObjectId id;
	
	private String name;
	
	private Integer age;

	
    public Person() {
	    super();
    }

	public Person(String name, Integer age) {
	    super();
	    this.name = name;
	    this.age = age;
    }


	public Object getId() {
    	return id;
    }

	
    public void setId(ObjectId id) {
    	this.id = id;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public Integer getAge() {
    	return age;
    }

	
    public void setAge(Integer age) {
    	this.age = age;
    }


	@Override
    public String toString() {
	    return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
