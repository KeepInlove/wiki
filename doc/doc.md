 ## 一,deploy.sh放到服务器:
 1.vim deploy.sh
 2.:set ff=unix
 3.:wq
##  二,执行
 1.sh deploy.sh
 2.ps -ef | grep java,
 查看 ps -ef | grep deploy.sh
    sh deploy.sh stop 
##  三,安装nginxdocker
1.yum install nginx
2.改配置:/etc/nginx/nginx.conf  改user root;
3.启动:service nginx start,停止service nginx stop
4. web配置:目录/etc/nginx/conf.d/web.conf
6.server配置:/etc/nginx/conf.d/server.conf
5.重启nginx -s reload
##  四,开启
systemctl start docker
### 启动RabbitMQ,默认guest用户，密码也是guest
docker run --name rabbit --restart=always -p 15672:15672 -p 5672:5672  -d  rabbitmq:management