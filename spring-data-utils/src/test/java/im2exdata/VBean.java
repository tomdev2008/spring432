package im2exdata;


public class VBean {
	private String cId;
	private String objId;

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public VBean(String cId, String objId) {
		this.cId = cId;
		this.objId = objId;
	}

	public VBean() {
	}
}
