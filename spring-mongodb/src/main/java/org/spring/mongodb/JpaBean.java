package org.spring.mongodb;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jpas_2")
public class JpaBean implements Serializable{

    private static final long serialVersionUID = -3315184292982842282L;

	@Id
	private ObjectId id;

	private String time;

	private String name;

	private String content;

	private Integer score;

	
    public ObjectId getId() {
    	return id;
    }

	
    public void setId(ObjectId id) {
    	this.id = id;
    }

	
    public String getTime() {
    	return time;
    }

	
    public void setTime(String time) {
    	this.time = time;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public String getContent() {
    	return content;
    }

	
    public void setContent(String content) {
    	this.content = content;
    }

	
    public Integer getScore() {
    	return score;
    }

	
    public void setScore(Integer score) {
    	this.score = score;
    }


	@Override
    public String toString() {
	    return "JpaBean [id=" + id + ", time=" + time + ", name=" + name + ", content=" + content + ", score=" + score + "]";
    }
}
