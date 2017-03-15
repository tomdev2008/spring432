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


<!--
freemarker语法介绍：
1. FreeMarker模板文件主要由如下4个部分组成

文本：直接输出的部分
注释：<#– … –> 格式部分，不会输出
插值：即 ${…} 或 #{…} 格式的部分，将使用数据模型中的部分替代输出
指令：FreeMarker 指定，和 HTML 标记类似，名字前加 # 予以区分，不会输出

<html>
<head>
    <title>Welcome!</title>
</head>
<body>
    <#-- 注释部分 -->
    <#-- 下面使用插值 -->
    <h1>Welcome ${username} !</h1>

    <p>We have these animals:</p>
    <u1>
        <!-- 使用FTL指令 -->
        <#list animals as animal>
        <li>${animal.name} for ${animal.price} Euros</li>
        </#list>
    </u1>
</body>
</html

    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19

    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19

2.控制语句

<#if condition> 
    ... 
<#elseif condition2> 
    ... 
<#elseif condition3> 
    ... 
<#else>
<#switch value> 
    <#case refValue1> 
        ... 
        <#break> 
    <#case refValue2> 
        ... 
        <#break> 
    <#case refValueN> 
        ... 
        <#break> 
    <#default> 
        ... 
</#switch>

    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19
    20

    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19
    20

3判断变量是否存在

<#if readonly??></#if>

    1

    1

4.防止空指针报错

变量名后用 ! 加默认值：${foo!”Default”}，如果 foo 为 null 则输出 Default
5.普通的变量

这是最简单的情况，直接变量名称，如{name}

需要注意的是有的变量是需要转义的如双引号
6..遍历List集合

<#list ["克里斯埃文斯", "斯嘉丽约翰逊", "小罗伯特唐尼"]  as x>  
${x}  
</#list>  

    1
    2
    3
    4

    1
    2
    3
    4

此外,迭代集合对象时,还包含两个特殊的循环变量:
item_index:当前变量的索引值
item_has_next:是否存在下一个对象
也可以使用<#break>指令跳出迭代
7.运算符

FreeMarker表达式中完全支持算术运算,FreeMarker支持的算术运算符包括:+, - , * , / , %
比较运算符

表达式中支持的比较运算符有如下几个:
1,=或者==:判断两个值是否相等.
2,!=:判断两个值是否不等.
3,>或者gt:判断左边值是否大于右边值
4,>=或者gte:判断左边值是否大于等于右边值
5,<或者lt:判断左边值是否小于右边值
6,<=或者lte:判断左边值是否小于等于右边值

注意:=和!=可以用于字符串,数值和日期来比较是否相等,但=和!=两边必须是相同类型的值,否则会产生错误,而且FreeMarker是精确比较,”x”,”x “,”X”是不等的.其它的运行符可以作用于数字和日期,但不能作用于字符串,大部分的时候,使用gt等字母运算符代替>会有更好的效果,因为FreeMarker会把>解释成FTL标签的结束字符,当然,也可以使用括号来避免这种情况,如:<#if (x>y)>

逻辑运算符

和普通程序一样，freemarker也有&&，|| ，!三种
8.变量的声明

<#assign num=0/>
9.include指令

include指令的作用类似于JSP的包含指令,用于包含指定页.include指令的语法格式如下:
<#include filename [options]>
在上面的语法格式中,两个参数的解释如下:
filename:该参数指定被包含的模板文件
options:该参数可以省略,指定包含时的选项,包含encoding和parse两个选项,其中encoding指定包含页面时所用的解码集,而parse指定被包含文件是否作为FTL文件来解析,如果省略了parse选项值,则该选项默认是true.
10.import指令

该指令用于导入FreeMarker模板中的所有变量,并将该变量放置在指定的Map对象中,import指令的语法格式如下:
<#import “/lib/common.ftl” as com>
上面的代码将导入/lib/common.ftl模板文件中的所有变量,交将这些变量放置在一个名为com的Map对象中.
11 macro的使用

这个可以用来实现自定义指令，一般用来做公共组件，例如分页条
最后说下list中含有map的遍历，这种情况可以使用点语法或方括号语法.假如有下面的数据模型:
Map root = new HashMap();
Book book = new Book();
Author author = new Author();
author.setName(“annlee”);
author.setAddress(“gz”);
book.setName(“struts2”);
book.setAuthor(author);
root.put(“info”,”struts”);
root.put(“book”, book);

为了访问数据模型中名为struts2的书的作者的名字,可以使用如下语法:
book.author.name //全部使用点语法
book[“author”].name
book.author[“name”] //混合使用点语法和方括号语法
book[“author”][“name”] //全部使用方括号语法
-->
</body>
</html>