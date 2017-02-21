package springtest.viewresolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * 实现文件下载服务:
 * 视图解析器和视图都可以自己实现
 * 这特别方便扩展
 */
public class MyFileView extends AbstractView{

	public static final String FILE_VIEW_PREFIX="file:"; 
	
	private String viewName;
	
	public MyFileView(String viewName){
		this.viewName=viewName;
	}
	
	@Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		File file = getOutputFile(); 
		downloadFile(request, response, file); 
    }
	
	private File getOutputFile() throws Exception{ 
		
		Integer beginIndex = viewName.indexOf(FILE_VIEW_PREFIX) + FILE_VIEW_PREFIX.length(); 
	
		String filePath = viewName.substring(beginIndex).trim(); 
	
		File file = new File(filePath); 
	
		if(file.exists()){ 
			return file; 
		} 
		
		throw new Exception("下载的文件不存在："+ filePath); 
	}

	private void downloadFile(HttpServletRequest request, 
		HttpServletResponse response, File file) throws IOException{ 
		response.setHeader("Content-Disposition", "attachment; filename="+file.getName());  
		response.setContentType("application/octet-stream; charset=utf-8");
		FileInputStream in=new FileInputStream(file);
		ServletOutputStream out = response.getOutputStream();
		byte [] buf=new byte[2048];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		in.close();
		out.flush();
		out.close();
	} 

}
