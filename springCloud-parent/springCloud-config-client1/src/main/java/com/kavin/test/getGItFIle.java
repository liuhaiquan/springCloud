package com.kavin.test;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope  //实现配置文件自动刷新  不能修饰final类     当配置更改时，标有@RefreshScope的Bean将得到特殊处理来生效配置。
			   //在客户端执行/refresh或者调用总线的/bus/refresh的时候就会更新此类下面的变量值。

public class getGItFIle {

	
	//读取gitee仓库中的配置文件的某个属性注入到password 中来 
	//client 和client1读取的远程仓库的配置文件不一样.在bootstrap.yml中的spring.config.profile 配置了具体的哪个文件
    @Value("${com.kavin.password}")
    private   String  password;

    
    //读取从gitee 远程仓库读取过来的属性值
    @RequestMapping("/getGitFile")
    public String getValue(){
        return  this.password;
    }
    
    /**
     * actuator提供的refresh接口来实现配置的动态更新  http://localhost:7076/refresh
     * 
     * 当项目采用了消息总线的时候，就可以使用actuator提供的  http://localhost:7076/bus/refresh 实现动态刷新了。
     * 每个引入spring-cloud-starter-bus-amqp的config-client服务都会通过MQ广播获取到最新远程仓库的信息
     * 
     * 通过访问该controller实现配置文件刷新，获取到gitee上最新的文件内容
     * @return
     */
    
    //当属性文件发生变化时候先访问此controller  然后在访问上面的controller，才能获取最新的属性信息
    @RequestMapping("/refreshGit")
    public String refresh() {
    		CloseableHttpClient client = HttpClients.createDefault();
    		HttpPost post = new HttpPost("http://localhost:7076/bus/refresh");
    		HttpResponse response;
			try {
				response = client.execute(post);
	    		System.out.println(EntityUtils.toString(response.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
			}
    		return "success";
    }
}
