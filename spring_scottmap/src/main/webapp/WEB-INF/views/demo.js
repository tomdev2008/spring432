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

setInterval() �����᲻ͣ�ص��ú�����ֱ�� clearInterval() 
�����û򴰿ڱ��رա��� setInterval() ���ص� ID ֵ������
clearInterval() �����Ĳ���
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
 �հ�:�հ������ܹ���ȡ���������ڲ������ĺ���(�ж��壬�����òŹ�����)
 1.����������,�����ڲ�����ֱ�Ӷ�ȡȫ�ֱ���,�ⲿ��Ȼ�޷���ȡ�����ڵľֲ�����(��ʽ������)
 2.����:�����ڲ�����������ʱ��һ��Ҫʹ��var���������õĻ�����ʵ����������һ��ȫ�ֱ�����  
  
 
 �õ������ڵľֲ�����:Ӧ�þ��Ǳհ���װ���ܣ�ֻ���������
function f1(){
������var��n=999;

��������function f2(){
������������alert(n);
��������}

��������return f2;

����}
����var result=f1();
����result(); // 999

Ӧ��:һ����ǰ���ᵽ�Ŀ��Զ�ȡ�����ڲ��ı�����
          ��һ����������Щ������ֵʼ�ձ������ڴ��У��ڴ�������գ�
          
���ڲ����� �ڶ������������� ���ⲿ ������ʱ,�ʹ����˸��ڲ������ıհ� ,
����ڲ�����������λ���ⲿ�����ı���,���ⲿ����������Ϻ�,��Щ����
���ڴ治�ᱻ �ͷ�,��Ϊ�հ���Ҫ����  
 */



/*
  ��function Cat(name,color){
��������this.name = name;
��������this.color = color;
��������this.type = "è�ƶ���";
��������this.eat = function(){alert("������");};
����} 
  
  function Cat(name,color){
��������this.name = name;
��������this.color = color;
����}
����Cat.prototype.type = "è�ƶ���";
����Cat.prototype.eat = function(){alert("������")}; 

   //����ķ�ʽ����������ķ�ʽ˽��
 */
/*�½�:*/var obj = new Object(); �� var obj = {}; 
/*����:*/obj[key] = value; (keyΪstring) 
/*ɾ��:*/delete obj[key]; 
/*����:*/for ( var key in obj ) obj[key];