http://docs.spring.io/spring-data/jpa/docs/1.8.0.RELEASE/reference/html/

主张
单实体开发:orm只是负责映射，别管理关联关系

1)开发维护复杂
2)查询没有处理好，简单的查询也多表关联，有时还open session in view
3)移植性差
4)表层面的数据结构不友好
5)优化控制无从下手

目标:快速开发的时候兼容性能，可读性和可维护性
同时兼容行好，mysql,mongodb间可以轻松切换

告别jsp,dbcp,c3p0等工具吧


#任何一个主流的工具，常用的工具，我建议你都必须足够的了解
http://velocity.apache.org/engine/releases/velocity-1.7/developer-guide.html
  
  
  
 
#通过解析方法名创建查询
find、findBy、read、readBy、get、getBy


#约定大于配置
框架在进行方法名解析时，会先把方法名多余的前缀截取掉，比如 find、findBy、
read、readBy、get、getBy，然后对剩下部分进行解析。并且如果方法的最
后一个参数是 Sort 或者 Pageable 类型，也会提取相关的信息，以便按规则
进行排序或者分页查询。

#异常处理
读者可以明确在属性之间加上 "_" 以显式表达意图


#关键字
And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
NotNull --- 与 IsNotNull 等价；
Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
    
#注解查询
#HQL语句
@Query("select a from AccountInfo a where a.balance > ?1")


#修改
@Modifying 将查询标识为修改查询
    
    
##适合简单CRUD
##无性能，无复杂查询统一的，传统XXX管理系统这样
  