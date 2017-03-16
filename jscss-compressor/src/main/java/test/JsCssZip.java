package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class JsCssZip {
	
	/**
	 * 合并文件
	 * 
	 * @param toFile 目标文件
	 * @param files	   源文件列表
	 * @throws IOException
	 * 
	 * 2016年4月21日
	 */
	public void mergeFile(File toFile,String dir, String ... files )throws IOException {
		
		FileOutputStream fos = new FileOutputStream(toFile, true);
		
		for (String f : files) {
			File file = new File(dir+f);
			FileInputStream fis = new FileInputStream(file);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1){
				fos.write(buffer, 0, len);
			}
			fos.write("\r\n".getBytes());
			fis.close();
		}
		fos.close();
	}
	
	/**
	 * 压缩文件
	 * 
	 * @param formFile	源文件
	 * @param toFile	目标文件前缀地址
	 * @throws Exception
	 * 
	 * 2016年4月21日
	 */
	public void toJsZip(File formFile,String dir,String prefix) throws Exception {

		File minFile = new File(getJsFileName(dir+prefix));
		
		Reader reader = new InputStreamReader(new FileInputStream(formFile),"UTF-8");
		Writer writer = new OutputStreamWriter(new FileOutputStream(minFile),"UTF-8");
		
		JavaScriptCompressor compressor = new JavaScriptCompressor(reader,getErrorReporter());
		compressor.compress(writer, -1, true, false, false, false);
		
		reader.close();
		writer.close();
	}
	
	/**
	 * 压缩文件
	 * 
	 * @param formFile	源文件
	 * @param toFile	目标文件前缀地址
	 * @throws Exception
	 * 
	 * 2016年4月21日
	 */
	public void toCssZip(File formFile,String dir,String prefix) throws Exception {

		File minFile = new File(getCssFileName(dir+prefix));
		
		Reader reader = new InputStreamReader(new FileInputStream(formFile),"UTF-8");
		Writer writer = new OutputStreamWriter(new FileOutputStream(minFile),"UTF-8");
		
		CssCompressor compressor = new CssCompressor(reader);
		compressor.compress(writer, -1);
		
		reader.close();
		writer.close();
	}
	
	private String getJsFileName(String prefix) {
		return prefix+"-"+(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))+".min.js";
	}
	
	private String getCssFileName(String prefix) {
		return prefix+"-"+(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()))+".min.css";
	}

	//警告与错误信息处理
	private ErrorReporter getErrorReporter() {
		
		return new ErrorReporter() {

			public void warning(String message, String sourceName,int line, String lineSource, int lineOffset) {
				if (line < 0) {
					System.err.println("\n[WARNING] " + message);
				} else {
					System.err.println("\n[WARNING] " + line + ':' + lineOffset + ':' + message);
				}
			}

			public void error(String message, String sourceName,int line, String lineSource, int lineOffset) {
				if (line < 0) {
					System.err.println("\n[ERROR] " + message);
				} else {
					System.err.println("\n[ERROR] " + line + ':'+ lineOffset + ':' + message);
				}
			}

			public EvaluatorException runtimeError(String message,String sourceName, int line, String lineSource,int lineOffset) {
				error(message, sourceName, line, lineSource,lineOffset);
				return new EvaluatorException(message);
			}
		};
	}
}
