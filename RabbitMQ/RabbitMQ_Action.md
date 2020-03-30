### RabbitMQ 安装与使用
Reference： https://www.jianshu.com/p/c85ac0063dbf
#### mac os 安装
```
brew installl rabbitmq
```
```
If you need to have openssl first in your PATH run:
  echo 'export PATH="/usr/local/opt/openssl/bin:$PATH"' >> ~/.bash_profile

For compilers to find openssl you may need to set:
  export LDFLAGS="-L/usr/local/opt/openssl/lib"
  export CPPFLAGS="-I/usr/local/opt/openssl/include"
```
#### 启动
```
cd /usr/local/Cellar/rabbitmq/3.7.14/sbin
./rabbitmq-server
```
打开浏览器并访问：http://localhost:15672/，并使用默认用户guest登录，密码也为guest,进入管理端
点击Admin标签，在这里可以进行用户的管理
- 后台启动
如果想以守护程序的方式后台运行，启东时加上 `-detached` 参数
```
rabbitmq-server -detached
```

```sh

```