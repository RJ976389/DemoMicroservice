package com.sigma.om.cs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.sigma.om", exclude = { KafkaAutoConfiguration.class })
public class OmDemoCloudServiceSpring{
  public static final Logger LOGGER= LoggerFactory.getLogger(OmDemoCloudServiceSpring.class);
    public static void main(String[] args)
{
LOGGER.info("1111111111111111111111111111111111111111");
    SpringApplication app = new SpringApplication(OmDemoCloudServiceSpring.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
}
}