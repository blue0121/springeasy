# springeasy
[![Build](https://github.com/blue0121/springeasy/actions/workflows/maven.yml/badge.svg)](https://github.com/blue0121/springeasy/actions/workflows/maven.yml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=blue0121_springeasy&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=blue0121_springeasy) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=blue0121_springeasy&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=blue0121_springeasy) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=blue0121_springeasy&metric=coverage)](https://sonarcloud.io/summary/new_code?id=blue0121_springeasy)

简化SpringBoot3开发，集成常用3pp，当前最新版本`1.2.0-104`

## 功能/特性:
- cache: 缓存接口, 并使用`Caffeine`实现内存缓存
- codec: 提供16进制, Base32编解码, 常用数据类型和2进制之间的编解码
- collection: 提供`Stack`, `MultiMap`, `ConcurrentHashSet`数据结构
- http: 使用jdk自带的`java.net.http.HttpClient`简化Http请求, 支持`GET`,`POST`,`PATCH`,`PUT`,`DELETE`等操作, 并支持文件上传和下载
- id: 主键生成器, 支持`Snowflake`, `String20`, `UUIDv7`等算法
- io: 抽象存储操作接口`StorageService`, 打包接口`PackService`, 扫描文件系统或Jar包`ResourceScannerFacade`
- schedule: 定时任务实现, 支持`Cron表达式`, 也可以在定时任务中使用`Mutex`互斥锁
- util: 常用工具类
- validation: 使用`JSR-349: Bean Validation`验证数据, 也可以自定义验证: `@Mobile`, `@Phone`, `@Vin`, `@EnumIn`等

## 模块列表:
- springeasy-core: 常用工具, 不依赖SpringBoot
- springeasy-spring: 用SpringBoot的自动装配定义常用配置, 包含对`funthin-core`的自动装配
- springeasy-mybatis: 使用`MyBatis`访问数据库
- springeasy-redis: 简化使用`Jedis`和`RedisTemplate`访问`Redis`, 集成`spring-cache`, `RedisPubSub`, 分布式锁

## 如何使用
### 拉取最新代码并安装到本地仓库:
```bash
git clone git@github.com/blue0121/springeasy.git
cd springeasy
mvn clean install -Drevision=1.2.0-104
```

### 在`Maven`中添加依赖:
```xml
<dependencies>
	<!-- 先导入 -->
	<dependency>
		<groupId>io.jutil</groupId>
		<artifactId>springeasy-parent</artifactId>
		<version>1.2.0-104</version>
		<type>pom</type>
		<scope>import</scope>
	</dependency>
    
	<!-- 再按模块引用 -->
	<dependency>
		<groupId>io.jutil.springeasy</groupId>
		<artifactId>springeasy-core</artifactId>
	</dependency>
	<dependency>
		<groupId>io.jutil.springeasy</groupId>
		<artifactId>springeasy-spring</artifactId>
	</dependency>
	<dependency>
		<groupId>io.jutil.springeasy</groupId>
		<artifactId>springeasy-mybatis</artifactId>
	</dependency>
	<dependency>
		<groupId>io.jutil.springeasy</groupId>
		<artifactId>springeasy-redis</artifactId>
	</dependency>
</dependencies>
```

