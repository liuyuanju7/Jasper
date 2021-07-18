# Linux 常用指令
---
## Find
- **-name 按文件名查找**

		find /dir -name filename  在/dir目录及其子目录下面查找名字为filename的文件

		find . -name "*.c" 在当前目录及其子目录（用“.”表示）中查找任何扩展名为“c”的文件

- **按类型 和关键字查找**

	find . -name "*.vm" -type f | xargs grep "keyword"  找到对应文件

- **按时间查找**

   [acm]time  计量单位是天，即24H
   ​	
   ​	[acm]min    计量单位是分钟
   ​	

   	find ./ -mtime 0  #查找一天内修改的文件
   	
   	find ./ -mtime -2 #查找2天内修改的文件，多了一个减号
   	
   	find ./ -mmin  -10  #查找距离现在10分钟内修改的文件

## du
- **-h** 
	显示文件或目录大小 单位 M G T
- **-s** 
  只显示总计

  ```shell
  du -sh *   :以总计的形式显示当前目录下所有文件或目录的大小
  du -sh attach ：显示attach目录大小
  du -sh * | sort -n 统计当前文件夹(目录)大小，并按文件大小排序
  du -sk filename 查看指定文件大小
  ```

#### ls

    ```
ls -lR|grep "^-"|wc -l		--查看某文件夹下文件的个数，包括子文件夹里的
ls -lR|grep "^d"|wc -l		--查看某文件夹下文件夹的个数，包括子文件夹里的
find . -type f | wc -l
​    ```

## scp

1.上传文件
scp /tmp/test.tar root@192.168.1.190:/home/test.tar

2.下载文件
scp root@192.168.1.190:/home/test.tar /tmp/test.tar

3.上传整个目录
scp -r /tmp/testdir root@192.168.1.190:home

4.下载整个目录
scp -r root@192.168.1.190:home/testdir /tmp

5.断点续传
rsync -P --rsh=ssh /tmp/test.tar 192.168.1.190:/home/test.tar

6. 在后台运行,  退出shell进程不会退出

nohup  /tmp/test.tar root@192.168.1.190:/home/test.tar

Ctrl+Z



**系统**

```
# uname -a # 查看内核/操作系统/CPU信息
# head -n 1 /etc/issue # 查看操作系统版本
# cat /proc/cpuinfo # 查看CPU信息
# hostname # 查看计算机名
# lspci -tv # 列出所有PCI设备
# lsusb -tv # 列出所有USB设备
# lsmod # 列出加载的内核模块
# env # 查看环境变量
```

**资源**

```
# free -m # 查看内存使用量和交换区使用量
# df -h # 查看各分区使用情况
# du -sh <目录名> # 查看指定目录的大小
# grep MemTotal /proc/meminfo # 查看内存总量
# grep MemFree /proc/meminfo # 查看空闲内存量
# uptime # 查看系统运行时间、用户数、负载
# cat /proc/loadavg # 查看系统负载
```

**磁盘和分区**

```
# mount | column -t # 查看挂接的分区状态
# fdisk -l # 查看所有分区
# swapon -s # 查看所有交换分区
# hdparm -i /dev/hda # 查看磁盘参数(仅适用于IDE设备)
# dmesg | grep IDE # 查看启动时IDE设备检测状况
```

**网络**

```
# ifconfig # 查看所有网络接口的属性
# iptables -L # 查看防火墙设置
# route -n # 查看路由表
# netstat -lntp # 查看所有监听端口
# netstat -antp # 查看所有已经建立的连接
# netstat -s # 查看网络统计信息
```

**进程**

```
# ps -ef # 查看所有进程
# top # 实时显示进程状态
# ps aux 查看所有进程信息
# ps axo cmd,pid,%cpu,%mem k %cpu # 查看所有进程的命令，进程id,cpu占用，内存占用信息 ，并按照cpu占用排序
```

##### ps 详细参数

```
参数：
    a：列出终端相关的，如果想列出所有进程需要配合x
    u：显示面向用户的格式
    x：列出终端先关的，如果列出所有进程需要配合a
    f ：显示进程树，这个显示效果跟pstree的效果差远啦
    k ：根据指定指标去排序
    --sort： 根据指定指标去排序，%cpu以cpu占用排序，-%cpu反序排
    o ：设置显示的列
    L ：显示支持的属性列表
    -C ：指定命令，多个命令用，分割
    -L ：显示线程
    -u ： 指定有效用户ID或名称
    -U ： 指定真正的用户ID或名称
    -p ： 显示指定pid进程
    --pid ：显示属于pid的子进程
    --forest:树型显示
列：
    USER :用户
    PID:进程id
    %CPU:cpu占用率
    %MEM:内存占用率
    VSZ:虚拟内存大小
    RSS:真实内存大小
    TTY:终端
    STAT:状态
    START:开始时间
    TIME:占用cpu时间
    COMMAND:命令
    PRI:优先级
    RTPRIO:实时优先级
    PSR:当前进程对应的处理器
    NI:进程的nice值
进程状态：
    D:不可终端睡眠
    R:运行或者在运行队列中
    S:终端睡眠（等待一个时间完成）
    T:被作业控制信号停止
    t:被调试追踪停止
    W:内存换页中
    X：死亡了
    Z:蜘蛛进程
    <:高优先级的
    N:第优先级的
    L:有页面锁定在内存
    s:是一个会话的领导者
    l:是一个多线程
    +：是一个前台进程
```



**用户**

```
# w # 查看活动用户
# id <用户名> # 查看指定用户信息
# last # 查看用户登录日志
# cut -d: -f1 /etc/passwd # 查看系统所有用户
# cut -d: -f1 /etc/group # 查看系统所有组
# crontab -l # 查看当前用户的计划任务
```

**服务**

```
# chkconfig --list # 列出所有系统服务
# chkconfig --list | grep on # 列出所有启动的系统服务
```

**程序**

```
# rpm -qa # 查看所有安装的软件包
```

##### Vim

```
全选（高亮显示）：按esc后，然后ggvG或者ggVG

全部复制：按esc后，然后ggyG

全部删除：按esc后，然后dG

gg：是让光标移到首行，在vim才有效，vi中无效 

v ： 是进入Visual(可视）模式 

G ：光标移到最后一行 

选中内容以后就可以其他的操作了，比如： 
d  删除选中内容 
y  复制选中内容到0号寄存器 
"+y  复制选中内容到＋寄存器，也就是系统的剪贴板，供其他程序用 
```

