# Dorado
简单、快速、轻量级的http restful server实现，基于Netty4和JDK1.8+

## Features

* HTTP/1.1 and HTTP/1.0协议支持
* 内置JSON/Protobuf序列化支持
* Http路由支持


## Maven

```xml
<dependency>
    <groupId>ai.houyi</groupId>
    <artifactId>dorado</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Quick start

* 最简单的Dorado rest server

```java
public class Application {

	public static void main(String[] args) throws Exception {
		// create simple rest server
		DoradoServerBuilder.forPort(18888).build().start();
	}
}
```

* 更多定制化参数服务器

```java

public class Application {

	public static void main(String[] args) throws Exception {
		DoradoServerBuilder.forPort(18888).acceptors(2).ioWorkers(4)
				.minWorkers(10).maxWorkers(100)
				.backlog(1024)
				.maxConnection(500).maxPendingRequest(10000)
				.maxIdleTime(120).sendBuffer(256 * 1024)
				.recvBuffer(256 * 1024)
				.scanPackages("com.rtbstack.demo.controller",
						"com.rtbstack.demo.controller1")
		       .build().start();
	}
}
```

* Rest Controller

```java
@Controller
@Path("/campaign")
public class CampaignController {

	@Path("/{id:[0-9]+}")
	@GET
	public Campaign newCampaign(int id) {
		Campaign campaign = new Campaign();
		campaign.setId(id);
		campaign.setName("test campaign");

		return campaign;
	}

	@Path("/name")
	public String campaignName() {
		return String.format("hello_campaign", "");
	}

	@POST
	public Campaign save(Campaign campaign) {
		System.out.println(campaign);
		return campaign;
	}

	@Path("/{id}")
	@DELETE
	public void deleteCampaign(int id) {
		System.out.println("delete campaign, id: " + id);
	}

	@GET
	@Path("/{id}")
	public Campaign getCampaign(int id) {
		return Campaign.builder().withId(12)
				.withName("网易考拉推广计划")
				.build();
	}
}
```
* More examples 

Please visit https://github.com/javagossip/dorado-examples

## 注解说明
### 类注解

| 注解类型  | 描述  | 
|:-------------: |:---------------:|
| Controller    | 控制器 | 
| Path      | 控制器访问Path|

### 方法注解

| 注解类型  | 描述  | 
|:-------------: |:---------------:|
| Path      | 资源访问路径，实际访问path为：controllerPath+methodPath |
|GET|方法仅支持Http GET请求|
|POST|方法仅支持Http POST请求|
|PUT|方法仅支持HTTP PUT请求|
|DELETE|方法仅支持HTTP DELETE请求|
|Consume | 方法参数支持的MediaType, 如：application/json|
|Produce | 方法响应MediaType, 如：application/json|

### 方法参数注解

| 注解类型  | 描述  |参数支持数据类型|
|:-------------: |:---------------:|:------------:|
|RequestParam|Query or Form parameter|Primitive type&wrapper class,String|
|PathVariable|Uri path variable, example: {var}|Primitive type&wrapper class,String|
|HeaderParam|Request header value|Primitive type&wrapper class,String|
|CookieParam|Request cookie value|Primitive type&wrapper class,String|
|RequestBody|Http request body|String, byte[],InputStream or any serializable type|

## 内置服务

* Get Server configuration: **[http://{ip}:{port}/config]()**
* Get Server status: **[http://{ip}:{port}/status]()**
* List All services: **[http://{ip}:{port}/services]()**

## 最佳实践

## 性能

