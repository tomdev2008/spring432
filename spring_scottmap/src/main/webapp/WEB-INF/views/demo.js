//http://lbs.amap.com/api/javascript-api/example/mouse-operate-map/mouse-draw-overlayers/

(function() {
	sendGetData("http://www.w3school.com.cn/jquery/ajax_get.asp", "123")
})();

/*
$.ajax({
		url:url,
		type:GET/POST,
		data:data,
		success:success,
		error:error
});

setInterval() 方法会不停地调用函数，直到 clearInterval() 
被调用或窗口被关闭。由 setInterval() 返回的 ID 值可用作
clearInterval() 方法的参数
*/

function sendGetData(url,data){
	$.ajax({
		url:url,
		type:GET/POST,
		data:data,
		success:success,
		error:error
	});
}

/*
 闭包:闭包就是能够读取其他函数内部变量的函数(有定义，有引用才构成了)
 1.变量作用域,函数内部可以直接读取全局变量,外部自然无法读取函数内的局部变量(链式作用域)
 2.特殊:函数内部声明变量的时候，一定要使用var命令。如果不用的话，你实际上声明了一个全局变量！  
  
 
 得到函数内的局部变量:应用就是闭包封装功能，只将结果返回
function f1(){
　　　var　n=999;

　　　　function f2(){
　　　　　　alert(n);
　　　　}

　　　　return f2;

　　}
　　var result=f1();
　　result(); // 999

应用:一个是前面提到的可以读取函数内部的变量，
          另一个就是让这些变量的值始终保持在内存中（内存溢出风险）
          
当内部函数 在定义它的作用域 的外部 被引用时,就创建了该内部函数的闭包 ,
如果内部函数引用了位于外部函数的变量,当外部函数调用完毕后,这些变量
在内存不会被 释放,因为闭包需要它们  
 */



/*
  　function Cat(name,color){
　　　　this.name = name;
　　　　this.color = color;
　　　　this.type = "猫科动物";
　　　　this.eat = function(){alert("吃老鼠");};
　　} 
  
  function Cat(name,color){
　　　　this.name = name;
　　　　this.color = color;
　　}
　　Cat.prototype.type = "猫科动物";
　　Cat.prototype.eat = function(){alert("吃老鼠")}; 

   //下面的方式，共享，上面的方式私有
 */
/*新建:*/var obj = new Object(); 或 var obj = {}; 
/*增加:*/obj[key] = value; (key为string) 
/*删除:*/delete obj[key]; 
/*遍历:*/for ( var key in obj ) obj[key];