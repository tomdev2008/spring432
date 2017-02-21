package model;


public class MyModel{

	private Integer id;

	private String time;

	private String name;

	private String content;

	private Integer score;
	
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
    public Integer getStatus() {
    	return status;
    }

	
    public void setStatus(Integer status) {
    	this.status = status;
    }

	@Override
    public String toString() {
	    return "JpaBean [id=" + id + ", time=" + time + ", name=" + name + ", content=" + content + ", score=" + score + "]";
    }
	
}
