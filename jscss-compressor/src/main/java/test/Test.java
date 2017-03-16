package test;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Exception {
		// 指定临时文件位置
		File jsAllFile=new File("E:/all.js");
		File cssAllFile=new File("E:/all.css");
		
		JsCssZip jc = new JsCssZip();
		
		// 合并js
		jc.mergeFile(jsAllFile,projectDir,jsFile);
		
		// 压缩 js
		jc.toJsZip(jsAllFile, projectDir,"js/rcp-pc-front");

		// 合并css
		jc.mergeFile(cssAllFile,projectDir,cssFile);
		
		// 压缩css
		jc.toCssZip(cssAllFile,projectDir, "css/rcp-pc-front");
		
		// 干掉临时文件
		jsAllFile.delete();
		cssAllFile.delete();
	}
	
	/*项目目录*/
	private final static String  projectDir="E:/mvw-workspace/rcp-ui/pc/manage/";
	
	/*css文件列表*/
	private final static String [] cssFile={
			    "css/opinionCollection.css",
				"css/personInformation.css",
				"css/addNoticePage.css",
				"css/releaseNotice.css",
				"css/addTeacher.css",
				"css/addSubject.css",
				"css/commonRight.css",
				"css/commonLeft.css",
				"css/homeContent.css",
				"css/common.css",
				"css/header.css",
				"css/departmentsMatch.css",
				"css/_00departmentsCommon.css",
				"css/_departmentsMain.css",
				"css/_05departmentsMenu.css",
				"css/_03departmentsLeftMenuOperation.css",
				"css/_04departmentsRightMenuOperation.css",
				"css/_01departmentsContent.css",
				"css/_043departmentsRightMenuDataContent.css",
				"css/_042departmentsRightMenuDataEdit.css",
				"css/_02departmentsAddOther.css",
				"css/addDepartmentsForMatch.css",
				"css/rotateScheduling.css"
	};
	
	/*js文件列表*/
	private final static String [] jsFile={
				"js/logic/commonLogic.js",
				"js/data/user.js",
				"js/data/staticData.js",
				"js/ui/setCycle.js",
				"js/ui/addArticlePage.js",
				"js/ui/articleManagement.js",
				"js/ui/columnManagement.js",
				"js/ui/opinionCollection.js",
				"js/ui/commonDetailedPage.js",
				"js/ui/updatePassword.js",
				"js/ui/personalInformation.js",
				"js/ui/addResourcePage.js",
				"js/ui/releaseResource.js",
				"js/ui/addNoticePage.js",
				"js/ui/releaseNotice_hospitalNotice.js",
				"js/ui/releaseNotice_allNotice.js",
				"js/ui/releaseNotice.js",
				"js/ui/addStudent.js",
				"js/ui/addTeacher.js",
				"js/ui/addSubject.js",
				"js/ui/commonRight.js",
				"js/ui/commonLeft.js",
				"js/ui/homeContent.js",
				"js/ui/header.js",
				"js/ui/common.js",
				"js/importFile/importFile.js",
				"js/ui/departmentsMatch.js",
				"js/ui/_00departmentsCommon.js",
				"js/ui/_departmentsMain.js",
				"js/ui/_05departmentsMenu.js",
				"js/ui/_03departmentsLeftMenuOperation.js",
				"js/ui/_04departmentsRightMenuOperation.js",
				"js/ui/_01departmentsContent.js",
				"js/ui/_043departmentsRightMenuDataContent.js",
				"js/ui/_042departmentsRightMenuDataEdit.js",
				"js/ui/_02departmentsAddOther.js",
				"js/ui/addDepartmentsForMatch.js",
				"js/ui/rs/rs_common.js",
				"js/ui/rs/rs_select_utils.js",
				"js/ui/rs/rotateScheduling.js",
				"js/ui/rs/modifyRotateScheduling.js",
				"js/ui/rs/modifyRotateScheduling_2.js",
				"js/ui/rs/rs_data_access.js",
				"js/ui/rs/schedulingWorkers.js"
		};
}
