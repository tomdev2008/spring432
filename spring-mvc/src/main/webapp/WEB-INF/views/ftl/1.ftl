<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
ftl

<!--

<#if condition>
  ...
<#elseif condition2>
  ...
<#elseif condition3>
  ...
<#else>
  ...
</#if>


<#list sequence as item>
    Part repeated for each item
</#list>

*其中有2个内置对象
    item_index (deprecated by item?index): The index (0-based number) 
    		    of the current item in the loop.
    item_has_next (deprecated by item?has_next): Boolean value that tells 
    			   if the current item is the last in the sequence or not.
    			   
<#include> 			   
 被引用的ftl内容会在引用的ftl中重新被渲染最终输出。一般用于页面拆分，便于页面重用
 
<#import>
 其含义是将标签中指定的模板中的已定义的宏、函数等导入到当前模板中，并在当前文档中指定一个变量作为该模板命名空间
 但是当前文档不会将import的模板输出插入到import标签的位置
 
 在两个模版中都分别存在变量名都相同的变量的时候，include包含进来，会进行覆盖，include只时候将其公共的静态文件进行包含，
 而里面不涉及到内部函数以及变量声明之类的，当涉及到这种问题，我们就要用import进行导入
 
 
 
 1.<#include> directive
该标签的作用是将便签中指定的路径的ftl文件导入到使用标签的ftl文件中，包括macro\funtion\variable等所有被引用的ftl内容。
被引用的ftl内容会在引用的ftl中重新被渲染最终输出。一般用于页面拆分，便于页面重用，如将header和footer分别抽取出来独自成模板，
这样在所有返回给前端的page里都可以include这两个模板了。

<#include "../../header.ftl"> 将相对路径中的header.ftl文件加载到当前文件中。如header.ftl中定义了宏、函数等，
在当前文件中可以不加命名空间前缀直接使用。如在header.ftl中
定义了<#marco getBranch></macro>,可以在当前文件中直接使用:<@getBranch>...</@getBranch>.


2.<#import> directive
该标签的字面意义和include差不多，经常会混淆使用。其含义是将标签中指定的模板中的已定义的宏、函数等导入到当前模板中，
并在当前文档中指定一个变量作为该模板命名空间，以便当前文档引用。与include的区别是该指令不会讲import指定的
模板内容渲染到引用的模板的输出中。

如：<#import  ”../../service.ftl as service>.其作用是将service.ftl中的定义的各宏、函数、变量、
自定义、设置等内容用指定的命名空间名称加以引用。但是当前文档不会将import的模板输出插入到import标签的位置。
和<#include>标签一样可以使用相对路径和绝对路径引用外部模板。

如：service.ftl中定义的宏如下：<#macro branchService></#macro>,在当前文档中可以这样导入
<#import "../../service.ftl" as service> ,service变量作为该文档中使用service中服务的命名空间，
调用时应该这样:<@service.branchService >....</@service.branchService>.
-->

</body>
</html>