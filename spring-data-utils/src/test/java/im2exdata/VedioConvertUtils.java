//package im2exdata;
//
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//
///**
// * 视频重转换工具类
// * 
// * @author <a href="mailto:tingping@ssreader.cn">tingping</a>
// * @version 2014-10-20
// */
//public class VedioConvertUtils {
//	
//	public static void main(String[] args) throws Exception {
//		//先把所有obejctid查询出来
//       //read
//	   
//	   //按照课程更新
//		//update();
//	}
//	
//	private static void read() throws Exception {
//		//读取excel
//				String path="E:/duxiuWeekSpace/mp4.xls";
//				int index=0;//指定读取的索引，从0开始
//				List<String> list=redExcel(path,index);
//				
//			   //查询obejctId
//			   List<VBean> objList=getObjectIdList(list);	
//				
//			   //保存
//			   saveObjectId(objList);
//	}
//	
//	private static void update() throws Exception {
//		   List<String> list=new ArrayList<String>();
//		   updateTask(list,11);	
//	}
//	
//	private static void updateTask(List<String> list,int flag) {
//		try{
//			if(list!=null){
//				for(String cid:list){
//					System.out.println("正在处理="+cid);
//					updateTaskByCid(cid,flag);
//				}
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static void updateTaskByCid(String cid, int flag) throws Exception{
//		//更具课程查询objectId
//		List<String> list=getObjId(cid);
//		
//		//更新task表
//		updateTaskByObjId(list,flag);
//		
//		//更新临时表状态
//		updateStatus(cid);
//	}
//
//	private static void updateStatus(String cid) throws Exception{
//		Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
//		String sql="UPDATE t_mobile_mp4 SET isconvert='1' WHERE cid=?";
//		PreparedStatement pst = connection.prepareStatement(sql);
//		pst.setString(1,cid);
//		pst.executeUpdate();
//		connection.close();
//	}
//
//	private static void updateTaskByObjId(List<String> list,int flag) throws Exception{
//		Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
//		String sql="update t_video_task set status='0',processor_id='0',flag=? where STATUS='2' AND object_id=?";
//		PreparedStatement pst = connection.prepareStatement(sql);
//		for(String objId:list){
//			pst.setString(1, flag+"");
//			pst.setString(2, objId);
//			pst.executeUpdate();
//		}
//		connection.close();
//	}
//
//	private static List<String> getObjId(String cid) throws Exception{
//		Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
//		String sql="select objectid from t_mobile_mp4 where cid=?";
//		PreparedStatement pst = connection.prepareStatement(sql);
//		pst.setString(1, cid);
//		ResultSet rs = pst.executeQuery();
//		List<String> list=new ArrayList<String>();
//		while(rs.next()){
//			list.add(rs.getString("objectid"));
//		}
//		rs.close();
//		connection.close();
//		return list;
//	}
//
//	private static void saveObjectId(List<VBean> objList) {
//		try{
//			Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
//			String sql="INSERT INTO t_mobile_mp4 (objectid,cid) VALUES(?,?)";
//			PreparedStatement pst = connection.prepareStatement(sql);
//			for(VBean obj:objList){
//				pst.setString(1, obj.getObjId());
//				pst.setString(2, obj.getcId());
//				pst.executeUpdate();
//			}
//			connection.close();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	//查询objectId
//	private static List<VBean> getObjectIdList(List<String> list) {
//		try{
//			String sql="SELECT object_id FROM t_video_task"+
//					" WHERE object_id IN("+
//					" SELECT objectId FROM iclass_star.attachment WHERE DTYPE='VideoAttachment' AND id IN (SELECT attachment_id"+
//					" FROM iclass_star.attachment_knowledge WHERE knowledge_id IN(SELECT id FROM iclass_star.knowledge"+
//					" WHERE course_id=?))) AND status=2";
//			List<VBean> objList=new ArrayList<VBean>();
//			Connection connection = DataSourceFactory.newInstance().getConnection("mysql");
//			PreparedStatement pst = connection.prepareStatement(sql);
//			for(String ar:list){
//				    if(ar!=null && !"".equals(ar)){
//				    	System.out.println("正在查询id="+ar);
//						pst.setString(1, ar);
//						ResultSet rs = pst.executeQuery();
//						while(rs.next()){
//							VBean vBean=new VBean(ar,rs.getString("object_id"));
//							objList.add(vBean);
//						}
//						rs.close();
//				    }
//			}
//			pst.close();
//			connection.close();	
//			return objList;
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	/**
//	 * 读取文件
//	 * 
//	 * @param path
//	 * @param index
//	 * @return
//	 * @throws FileNotFoundException
//	 * @throws IOException
//	 */
//	private static List<String> redExcel(String path,int index) throws FileNotFoundException, IOException {
//		List<String> list=new ArrayList<String>();
//		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
//		HSSFSheet sheet = workbook.getSheetAt(0);
//		int rows=sheet.getLastRowNum();
//		for(int i = 1;i<=rows;i++){
//			HSSFRow row = sheet.getRow(i);
//			String objId=redRow(row,index);
//			if(objId!=null && !"".equals(objId)){
//				list.add(objId);
//			}
//		}
//		return list;
//	}
//	
//	/**
//	 * 根据索引读取一行
//	 * 
//	 * @param row
//	 * @param index
//	 * @return
//	 */
//	private static String redRow(HSSFRow row, int index) {
//			HSSFCell cell = row.getCell(index);
//			if(cell!=null){
//				cell.setCellType(Cell.CELL_TYPE_STRING);
//				return cell.getStringCellValue();
//			}
//		    return null;
//	}
//}
