#稍微高级点的特性
http://redis.majunwei.com/topics/sentinel.html

Redis2.8和Redis3.0附带稳定的哨兵版本。他们是Redis的两个最新稳定版本


#任何东西，只有适合场景下才有用:金子不是放哪里都会发光的!


(1) Master最好不要做任何持久化工作，如RDB内存快照和AOF日志文件
(2) 如果数据比较重要，某个Slave开启AOF备份数据，策略设置为每秒同步一次
(3) 为了主从复制的速度和连接的稳定性，Master和Slave最好在同一个局域网内
(4) 尽量避免在压力很大的主库上增加从库
(5) 主从复制不要用图状结构，用单向链表结构更为稳定，即：Master <- Slave1 <- Slave2 <- Slave3…
这样的结构方便解决单点故障问题，实现Slave对Master的替换。如果Master挂了，可以立刻启用Slave1做Master，其他不变


volatile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰
volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
allkeys-lru：从数据集（server.db[i].dict）中挑选最近最少使用的数据淘汰
allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
no-enviction（驱逐）：禁止驱逐数据


#include /path/to/local.conf