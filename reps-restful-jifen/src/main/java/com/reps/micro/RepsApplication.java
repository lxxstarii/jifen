package com.reps.micro;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.boot.ApplicationPid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.reps.core.boot.RepsBootConstant;


@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.reps")
@ServletComponentScan(basePackages = "com.reps")
@EntityScan(basePackages = "com.reps")
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableHystrix
public class RepsApplication extends WebMvcConfigurerAdapter
{
    public static void main(String[] args)
    {
        SpringApplication application = new SpringApplication(RepsApplication.class); 
        application.run(args);
    }
    
    @PostConstruct
    private void handlePid() throws IOException {
        File file = new File("rrt.pid");
        new ApplicationPid().write(file);
        file.deleteOnExit();
    }
    
    @Bean(name =RepsBootConstant.DATASOURSE_PRIMARY)
    @Primary
    @ConfigurationProperties(prefix="datasource.primary")
    public DataSource primaryDataSource(){
      return DataSourceBuilder.create().build();
    }
    
    @Bean(name = RepsBootConstant.DATASOURSE_DICTIONARY) 
    @ConfigurationProperties(prefix="datasource.dictionary")
    public DataSource dictionaryDataSource(){
      return DataSourceBuilder.create().build();
    }
    
}
