package com.coins.tradecoin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@MapperScan(value = "com.coins.tradecoin.dao",sqlSessionFactoryRef="commonSessionFactory")
public class MybatisConfig {


    @Value("${spring.datasource.huobi.url}")
    private String webUrl;
    @Value("${spring.datasource.huobi.username}")
    private String webUserName;
    @Value("${spring.datasource.huobi.password}")
    private String webPassword;

    /** 构建数据源 **/
    @Bean("commonDataSource")
    @ConfigurationProperties(prefix="spring.datasource.huobi")
    public DataSource commonDataSource() {
        //通过DataSourceBuilder构建数据源
        return DataSourceBuilder.create()
                .url(webUrl)
                .username(webUserName)
                .password(webPassword)
                .type(DruidDataSource.class)
                .build();
    }


    /** 注入数据连接 **/
    @Bean("commonSessionFactory")
    public SqlSessionFactory commonSessionFactory(DataSource commonDataSource) throws Exception {
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        pageHelper.setProperties(properties);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);//驼峰


        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(commonDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));
        bean.setPlugins(new Interceptor[]{pageHelper});
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    /** 注入事务管理 **/
    @Bean("commonTransactionManager")
    public PlatformTransactionManager snsTransactionManager(DataSource commonDataSource) {
        return new DataSourceTransactionManager(commonDataSource);
    }

}
