##论坛项目<br>

##本地网站地址<br>
[论坛地址](http://localhost:8887)<br>
[本地Gitlab](http://192.168.31.174/)<br>
[本地GitlabAPI地址](http://192.168.31.174/help/api/oauth2.md)<br>
[Gitlab用户信息](http://192.168.31.174/api/v4/user?access_token=jCEq22FxbCc2Jsr7uySM)<br>
[GithubAPI地址](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)<br>

##资料网站<br>
[maven仓库](https://mvnrepository.com/)<br>
[镜像资源](https://mirrors.tuna.tsinghua.edu.cn/)<br>
[springboot文档](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/)<br>
[Spring-boot 异常处理](https://www.jianshu.com/p/332f42fbabe2)<br>
[HTTP网页错误代码大全](https://blog.csdn.net/admin_8888/article/details/75270112)<br>
[springMVC官方文档](https://docs.spring.io/spring/docs/5.0.1.RELEASE/spring-framework-reference/web.html#spring-web)<br>
[图标素材库](https://www.iconfont.cn/)<br>
[服务器安装mysql帮助文档](https://help.aliyun.com/document_detail/116727.html?spm=5176.11065259.1996646101.searchclickresult.1c5c3a56sW5Vbi&aly_as=RG3yoaGF)<br>

##项目使用工具<br>
[springboot](https://spring.io/)<br>
[git](https://git-scm.com/)<br>
[bootstrap](https://www.bootcss.com/)<br>
[markdown editor富文本编工具](http://editor.md.ipandao.com/)<br>
[maven](http://maven.apache.org/)<br>
[mysql](https://www.mysql.com/)<br>
[flyway](https://flywaydb.org/)<br>
[lombok](https://projectlombok.org/)(有一些注解:@data-可以给实体类进行get、set|@Slf4j-用于指定方法的日志)<br>
[commons-lang3](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3)(方法StringUtils.isBlank(Object):用于判断为空返回true)<br>
[mybatis-generator](http://mybatis.org/generator/running/running.html)<br>

##遇到的问题<br>
一个知识点，用于把一个对象的属性值给另一个对象所相同的属性值<br>
BeanUtils.copyProperties(question,questionDTO)<br>

springMVC中RequestParam注解中name和value两个属性的区别：<br>
源码中name的别名是value，value的别名是name,所以说name和value两个属性基本是等价的,都是获取从前台传入的参数<br>

thymeleaf中:<br>
input type="text"需要用 th:value<br>
textarea需要用 th:text<br>

一个页面的应用<br>
创建navigation.html<br>

    编写
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    <div th:fragment="nav">
    引用
    <div th:insert="~{navigation::nav}">

地址编写<br>
@{'/revert/'+${question.id}}<br>
@{./(page=${paginationDTO.page +1})}<br>

css不加载问题
是由于浏览器中把css进行了缓存，可以把缓存删除再试

目前缺少<br>
登录有一个功能没有完善<br>
自动部署（热部署）没有添加<br>
拦截器没有编写<br>
mybatis generator的集成<br>
使用 ControlerAdvice 和 ExceptionHandler 通用处理异常<br>

当mysql中不同数据库存在相同表时，Mybatis generator 生成实体类与指定数据库表不一致问题解决<br>
[解决链接](https://blog.csdn.net/weixin_41809435/article/details/85207563)<br>

    <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
        connectionURL="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;serverTimezone=UTC"
        userId="root"
        password="root">
        <property name="nullCatalogMeansCurrent" value="true"/>
    </jdbcConnection>
    首先数据库 要指定对！
    其次带参数必然的
    然后加上一行<property name="nullCatalogMeansCurrent" value="true"/>

用flyway工具运行sql文件：<br>
mvn flyway:migrate<br>

Mybatis generator执行命令<br>
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate<br>


SpringBoot 全局异常处理进阶：使用 @ControllerAdvice 对不同的 Controller 分别捕获异常并处理<br>
[解决链接](https://blog.csdn.net/Colton_Null/article/details/88574923)

    用@ControllerAdvice可以控制指定controller类
    @ControllerAdvice(basePackages = {"RevertController.class"})
    basePackages控制包里的所有类及调用的所有类
    basePackageClasses控制类及其调用类
    assignableTypes只控制该类

获取正在访问的地址<br>

    ${#httpServletRequest.getRequestURL()}