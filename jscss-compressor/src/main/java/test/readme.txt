 * 1.配置js,css文件目录[一次]
 * 2.访问人家提供的网页把合并后的内容贴进去[多次]
 * 3.点压缩再把压缩后的内容复制[多次]
 * 4.建立文件或重命名文件把内容贴进去，还得看现在时间把文件名写正确[多次]
 * 
 * 一个项目的开发中js和css各要执行上述步骤N次
 * 而且还因为忘记清除浏览器缓存时常搞个无效的压缩
 * 
 * 有点痛苦了啊!你想不想改变一下???
 * 
 * 可以用yuicompressor自己封装下
 * 搞个工具，轻轻一点，js,css压缩都搞定
 * 大型web还可以用类似的工具搞个代码动态分发
 * 
 * 示范例子，请查阅Test.java(实用请自己完善)
 
 1.只压缩js/css
 2.只压缩摸个项目
 3.输出压缩步骤和压缩比例
 4.运行方式   xxx.jar  配置文件
 5.workspace 工作空间
 
 
 ####
 现在配置还是太麻烦，可以解析debug文件
 将符合过滤条件的东西压缩!
<script src="js/common/Global.js"></script>
<script src="js/common/DateTimeUtil.js"></script>

<link href="../libs/elf/css/frame201601251139.css" rel="stylesheet" />
<link href="../libs/elf/plugin/radialProgress/elf_radialProgress.css" rel="stylesheet" />   
 
