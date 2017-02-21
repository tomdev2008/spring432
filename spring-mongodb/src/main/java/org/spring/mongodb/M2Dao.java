package org.spring.mongodb;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface M2Dao extends MongoRepository<JpaBean, ObjectId>{

	List<Object> findByName(String string);
}
