java客户端使用指南
java版本兼容性

Tair的java客户端需要JDK 1.5或与其兼容的环境。我们使用Sun公司的JDK 1.5在 Linux和Windows上测试过。
依赖

Tair客户端使用mina（  http://mina.apache.org/ ）通信框架与Tair server通信，所以使用Tair java客户端需要确保运行环境中包含mina的jar包以及其依赖的库，mina请使用1.1.x的版本。
配置java客户端
支持的配置项
配置项名称 	类型 	是否必选 	默认值 	说明
configServerList 	List<String> 	是 	无 	configserver地址列表，ip:port格式
groupName 	String 	是 	无 	group的name，一个configserver服务可以管理多个group，所以需要使用groupName加以区分
charset 	String 	否 	UTF-8 	字符集，这个选项在将String对象序列化时使用
compressionThreshold 	int 	否 	8192 	压缩阀值，单位为字节。当数据的大小超过这个阀值时，客户端将自动采用zip压缩算法压缩数据，并在接受到数据时自动解压。
maxWaitThread 	int 	否 	100 	最大等待线程数，当超过这个数量的线程在等待时，新的请求将直接返回超时
timeout 	int 	否 	2000 	请求的超时时间，单位为毫秒
初始化Java客户端

// 创建config server列表
List<String> confServers = new ArrayList<String>();
confServers.add("CONFIG_SERVER_ADDREEE:PORT");
confServers.add("CONFIG_SERVER_ADDREEE_2:PORT"); // 可选

// 创建客户端实例
DefaultTairManager tairManager = new DefaultTairManager();
tairManager.setConfigServerList(confServers);

// 设置组名
tairManager.setGroupName("GROUP_NAME");
// 初始化客户端
tairManager.init();

如果你的系统使用spring，那么可以使用类似下面的bean配置：

<bean id="tairManager" class="com.taobao.tair.impl.DefaultTairManager" init-method="init">
        <property name="configServerList">
                <list>
                        <value>CONFIG_SERVER_ADDREEE:PORT</value>
                        <value>CONFIG_SERVER_ADDREEE_2:PORT</value> <!-- 可选 -->
                </list>
        </property>
        <property name="groupName">
                <value>GROUP_NAME</value>
        </property>
</bean>

接口介绍
预备知识

由于Tair中的value除了用户的数据外，好包括version等元信息。所以返回的用户数据将和元数据一起封装在DataEntry对象中。

Tair客户端的所有接口都不抛出异常，操作的结果状态使用ResultCode表示。如果接口会返回数据，则返回的数据和ResultCode一起封装在Result中。

Result和ResultCode都包含有isSuccess方法，如果该方法返回true，则表示请求成功（当get的数据不存在时，该方法也返回 true）。
基本接口

    get接口 

get接口用于获取单个数据，要获取的数据由namespace和key指定。

当数据存在时，返回成功，数据存放在DataEntry对象中；

当数据不存在时，返回成功，ResultCode为ResultCode.DATANOTEXSITS，value为null。

示例：

Result<DataEntry> result = tairManager.get(namespace, key);
if (result.isSuccess()) {
    DataEntry entry = result.getValue();
    if(entry != null) {
        // 数据存在
    } else {
        // 数据不存在
    }
} else {
    // 异常处理
}

    mget接口 

mget接口用于批量获取同一个namespace中的多个key对应的数据集合。mget在客户端将key列表根据key所在的服务器分组，然后将分组后的key列表发送到对应的服务器上，发送到多个服务器这个步骤是异步的，所以需要的时间不是线性的。

当得到返回结果时

    如果返回的个数为0，ResultCode为ResultCode.DATANOTEXSITS
    如果返回的个数小于请求的个数，ResultCode为ResultCode.PARTSUCC
    全部返回，则返回成功 

当有数据返回时，Result对象中的value是一个List<DataEntry>，这个List包含了所有取到的数据，每个 DataEntry都会包括请求的key，返回的value和version信息。

    put接口 

put接口有3个签名，分别为：

ResultCode put(int namespace, Serializable key, Serializable value); // version为0，即不关心版本；expireTime为0，即不失效
ResultCode put(int namespace, Serializable key, Serializable value, int version); // expireTime为0，即不失效
ResultCode put(int namespace, Serializable key, Serializable value, int version, int expireTime);

示例：

ResultCode rc = tairManager.put(namespace, key, value);
if (rc.isSuccess()) {
    // put成功
} else if (ResultCode.VERERROR.equals(rc) {
    // 版本错误的处理代码
} else {
    // 其他失败的处理代码
}

// version应该从get返回的DataEntry对象中获取
// 出给使用0强制更新，否则不推荐随意指定版本
rc = tairManager.put(namespace, key, value, version);

// 使用全参数版本的put
rc = tairManager.put(namespace, key, value, version, expireTime);

    delete接口 

delete接口用于删除有namespac和key指定的value。如果请求删除的key不存在，tair也将返回成功。

示例：

// 使用删除接口
ResultCode rc = tairManager.delete(namespace, key);
if (rc.isSuccess()) {
    // 删除成功
} else {
    // 删除失败
}

    mdelete接口 

mdelete接口用于批量删除数据，该接口只能删除同一个namespace中的多条数据。其工作原理和mget接口类似。

示例：

// 使用批量删除接口
ResultCode rc = tairManager.mdelete(namespace, keys);
if (rc.isSuccess()) {
    // 删除成功
} else {
    // 部分成功处理代码
}

    incr接口 

incr和decr接口配合使用可以提供计数器的功能。使用get和put接口也能实现计数器的功能，但由于两个操作不是原子的，很多情况下这不能满足需求。所以我们提供了incr和decr接口，通过这两个接口提供原子的计数器操作。

incr接口的方法签名为： Result<Integer> incr(int namespace, Serializable key, int value, int defaultValue, int expireTime);

接口参数的含义为：
参数名 	含义
namespace 	计数器所在的namespace
key 	计数器的key
value 	本次增加的数量
defaultValue 	当计数器不存在时的初始化值
expireTime 	失效时间，单位为秒

示例：

Result<Integer> result = tairManager.incr(namespace, key, 1, 0);
if (result.isSuccess()) {
    int rv = result.getValue(); // 这里是1
} else {
    // 错误处理
}

// 将返回4
result = tairManager.incr(namespace, key, 3, 0);

// 将返回2
result = tairManager.decr(namespace, key, 2, 0);

    item接口 

item接口是对原有key/value接口的扩充，item接口将value视为一个数组，配合服务器端的支持，可以完成以下操作：

    向item末尾添加新的items
    获取指定范围的items
    删除指定范围的items
    获取并删除指定范围的items
    获取item的个数 

Item接口内部使用json格式，只支持基本类型，包括：

    String
    Long
    Integer
    Double 

同一个Value中的类型需要保持一致，否则将返回序列化错误。

每个item可以指定maxcount，当item的条数操作指定的maxcount是，服务器将自动删除最早插入的item。

List<Integer> intList = new ArrayList<Integer>();
intList.add(1);
intList.add(2);

// 添加新的itmes
ResultCode rc = tairManager.addItems(1, key, intList, 50, 0, 0);

// 获取item的总条数
Result<Integer> ic = tairManager.getItemCount(1, key);
int totalCount = ic.getValue();

// 获取所有items
Result<DataEntry> rets = tairManager.getItems(1, key, 0, totalCount);

// 获取第一个item，并将其从系统中删除
rets = tairManager.getAndRemove(1, key, 0, 1);


#统计类型:TairConstant
public static final int Q_AREA_CAPACITY  = 1;
public static final int Q_MIG_INFO  = 2;
public static final int Q_DATA_SEVER_INFO  = 3;
public static final int Q_GROUP_INFO  = 4;
public static final int Q_STAT_INFO  = 5;
    