##论坛项目

##本地网站地址
[论坛地址](http://localhost:8887)<br>
[本地Gitlab](http://192.168.31.174/)<br>
[本地GitlabAPI地址](http://192.168.31.174/help/api/oauth2.md)<br>

##资料网站
[maven仓库](https://mvnrepository.com/)<br>
[镜像资源](https://mirrors.tuna.tsinghua.edu.cn/)<br>

##项目使用工具
[springboot](https://spring.io/)<br>
[git](https://git-scm.com/)<br>
[bootstrap](https://www.bootcss.com/)<br>
[maven](http://maven.apache.org/)<br>
[mysql](https://www.mysql.com/)<br>
[flyway](https://flywaydb.org/)<br>

##遇到的问题
springMVC中RequestParam注解中name和value两个属性的区别：<br>
源码中name的别名是value，value的别名是name,所以说name和value两个属性基本是等价的,都是获取从前台传入的参数<br>

用flyway工具运行sql文件：<br>
mvn flyway:migrate<br>

thymeleaf中:<br>
input type="text"需要用 th:value<br>
textarea需要用 th:text<br>
