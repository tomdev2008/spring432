package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/*根据配置文件压缩*/
public class Test2 {

	public static void main(String[] args) throws Exception {

		File f=new File(Test2.class.getResource("config.json").getPath());
		String json=readFile(f);
		JSONObject conf = JSONObject.parseObject(json);
		
		String dir=conf.getString("dir");
		
		//压缩任务
		JSONArray tasks = conf.getJSONArray("task");
		
		JsCssZip jc = new JsCssZip();
		File jsAllFile=new File("E:/test/all.js");
		File cssAllFile=new File("E:/test/all.css");
		
		int pSize = tasks.size();
		for(int i=0;i<pSize;i++){
			
			JSONObject t = tasks.getJSONObject(i);
			
			//解析
			String type = t.getString("type");
			String min = t.getString("min");
			JSONArray files = t.getJSONArray("file");
			
			//执行压缩
			if("js".equals(type)){
				jc.mergeFile(jsAllFile,dir,getFileArray(files));
				jc.toJsZip(jsAllFile,dir,min);
			}else if("css".equals(type)){
				jc.mergeFile(cssAllFile,dir,getFileArray(files));
				jc.toCssZip(cssAllFile,dir,min);
			}
		}
		
		// 干掉临时文件
		jsAllFile.delete();
		cssAllFile.delete();
	}

	private static String[] getFileArray(JSONArray files) {
		
		int pSize = files.size();
		String [] fs=new String[pSize];
		
		for(int i=0;i<pSize;i++){
			fs[i]=files.getString(i);
		}
		
		return fs;
	}

	public static String readFile(File f) throws IOException{
		
		FileInputStream in=new FileInputStream(f);
		
		StringBuilder str=new StringBuilder();
		
		byte [] buf=new byte[1024];
		int len = 0;
		while ((len = in.read(buf)) != -1){
			str.append(new String(buf,0,len));
		}
		in.close();
		
		return str.toString();	
	}
}
