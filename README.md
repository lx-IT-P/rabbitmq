# rabbitmq
rabbitmq学习总结
RabbitMQ五种交换机类型，六种队列模式

一、哪五种交换机？

1、Direct exchange.

       a、会根据routingKey 完全匹配成功后才会消费。比如：如果生产一条消息 “我是中国人”，发送到交换机的时候绑定了路由键是：“中国”，则如果要消费的话只有匹配了路由键是“中国”的才能消费。（可以比喻为交换机是 “地球”，路由键是“国家—中国”，消息是“人”，这个消息的身份证是哪个国家的“路由键”那就是只能在这个国家享有权益。）

        b、如果都消费同一个routingKey的话，多个消费者谁先消费到就是谁的

2、Topic exchange.

        a、该模式不仅仅需要exchange和queue绑定还需要和路由键routingKey关联，

        b、模糊匹配模式，比如：两个路由键 animal.dog ， animal.dog.eat。如果该队列不仅仅对“dog”的消息感兴趣，同时还对与“dog”相关的消息感兴趣就可以使用topic模式，animal.dog.# 。（支持# 0或多词模糊匹配，*一个词匹配）

        应用：订阅任务，信息分类更新业务

3、Fanout exchange.

        a、该模式不需要路由键routingKey

        b、该模式只需要将queue和exchange绑定就好。一个exchange可以绑定N多个queue，每一个queue都会得到同样的消息

        c、一个queue可以和多个exchange绑定，消费来自不同的exchange的消息

        d、转发消息最快

        应用：群聊功能、全网消息推送功能

4、Headers exchange.

            a、无路由键routingKey的概念

            b、是以 header和message中的消息匹配上才能消费

5、System exchange——其实就是系统默认和direct模式没区别，只不过不需要定义exchange名字而已。

二、六种队列模式

1、hello word 模式（单发送，单接收模式）

2、work模式（工厂模式）

    一个发送端，多个接收端，支持持久化durable，公平消费原则basicQos，消息的可靠性ack=true，false

    a、消息队列durable——true持久化

    b、在消费的时候，由channel.basicAck()在消息处理完成后发送消息false确认单条或true批量确认。

    c、使用了channel.basicQos(1)保证在接收端一个消息没有处理完时不会接收另一个消息，即接收端发送了ack后才会接收下一个消息。在这种情况下发送端会尝试把消息发送给下一个空闲的的接收端。

3、Publish/Subscribe

    一个生产者发送消息到多个消费者

4、routing模式

    发送消息到交换机并且要指定路由key ，消费者需要匹配对路由key才能消费

5、topic模式

    发送消息到交换机并和路由key进行绑定，但该路由key支持模糊匹配，是指成为“一类”消息

6、RPC模式

    此种方式比较高端，我暂时没每学会



    一下附上 arguments 的参数解释
    代码：
   简单队列发送端
    @Component
public class HelloSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(0));//24小时制
        String context = "hello " + date;
        System.out.println("Sender : " + context);
        for (int i = 0; i < 100; i++) {
        	 //简单对列的情况下routingKey即为Q名
            this.rabbitTemplate.convertAndSend("q_hello", context);
        }
		}

交换机发送端
@Component
public class WorkQueue {
	 @Autowired
	    private AmqpTemplate rabbitTemplate;
	 public void send() {
	        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(0));//24小时制
	        String context = "工作模式 " + date;
	        System.out.println("Sender : " + context);
	        for (int i = 0; i < 100; i++) {
	        	//交换机的情况
		        this.rabbitTemplate.convertAndSend("message1","direct", context);
			}
	        
	    }
}
接收端代码
@Component
public class HelloReceiver {
	/**
	 * 简单队列
	 * 
	 * @funciton
	 * @param hello
	 * @time 2019年12月13日 下午3:34:32
	 */
	
	  @RabbitListener(queues = "q_hello")
	  @RabbitHandler public void process(String hello) {
		  i++;
	  System.out.println("第"+i+"Receiver  : " + hello); }
	  @RabbitListener(queues = "q_hello")
	  @RabbitHandler public void process1(String hello) {
		  j++;
	  System.out.println("第"+j+"Receiver1  : " + hello); }
	  int i= 0;	
	  int j =0;
	 /**
		 * 广播模式
		 */
	
	  @RabbitListener(bindings = @QueueBinding(value =
	  
	  @Queue(value = "ly-work-queue", durable = "true"), exchange
	  = @Exchange(value= "message", ignoreDeclarationExceptions = "true", type
	  =ExchangeTypes.FANOUT))) public void fanout(String work) { j++;
	  System.out.println("第"+j+"WorkReceiver  : " + work); }
	  
	  @RabbitListener(bindings = @QueueBinding(value =
	  
	  @Queue(value = "ly-work-queue1", durable = "true"), exchange
	  = @Exchange(value = "message", ignoreDeclarationExceptions = "true", type =
	  ExchangeTypes.FANOUT))) public void fanout1(String work) { i++;
	  System.out.println("第"+i+"WorkReceiver1  : " + work);
	  
	  }
	 
			  /**
				 * head模式
				 */
					  @RabbitListener(bindings = @QueueBinding(value =
					  @Queue(value = "ly-heads-queue", durable = "true"), 
					  exchange = @Exchange(value= "message1", ignoreDeclarationExceptions = "true", 
					  type =ExchangeTypes.DIRECT),
			            key = "direct")) 
					  public void work(String work) 
					  {
						  j++;
						  System.out.println("第"+j+"HEADRDSeceiver  : " + work); 
						  }
					  @RabbitListener(bindings = @QueueBinding(value =
							  @Queue(value = "ly-heads-queue1", durable = "true"), 
							  exchange = @Exchange(value = "message1", ignoreDeclarationExceptions = "true", 
							  type = ExchangeTypes.DIRECT),
					            key = "direct")) 
					  public void work1(String work) 
					  {
						  i++;
						  System.out.println("第"+i+"HEADRDSeceiver1  : " + work); 
						  	
					  }

}
pom文件

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.snbc</groupId>
  <artifactId>rabbitmq</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>rabbitmq</name>
  <url>http://maven.apache.org</url>
<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.4.RELEASE</version>
	</parent>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
  </dependency>
  <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    
  </dependencies>
</project>
