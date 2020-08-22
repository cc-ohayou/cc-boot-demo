package com.qunhe.toilet.core.common.config.db.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.qunhe.toilet.core.common.properties.resource.TestLoadResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author bupo
 * @DATE 2020/8/20 16:57
 * @Description
 */
@Slf4j
@Configuration
@ConditionalOnBean(TestLoadResource.class)
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    static final String PACKAGE = "com.qunhe.toilet.core.mapper.master";
    private static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

   //加载配置文件application.properties中的  mybatis.configuration属性,否则无法生效
   @Bean
   @ConfigurationProperties(prefix = "mybatis.configuration")
   public org.apache.ibatis.session.Configuration globalConfiguration() {
       org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
       //启用缓存
       conf.setCacheEnabled(true);
       //驼峰命名
       conf.setMapUnderscoreToCamelCase(true);
       //允许JDBC支持生成主键，如果设置为true的话，这个键强制被使用
       conf.setUseGeneratedKeys(true);
       return conf;
   }
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        Properties p = TestLoadResource.propertyJdbc;
        if (p.isEmpty()) {
            TestLoadResource.loadProperties();
        }


        p=TestLoadResource.propertyJdbc;
        log.info("Properties="+p);
        dataSource.setDriverClassName(p.getProperty("masterDriver"));
        dataSource.setUrl(p.getProperty("masterUrl"));
        dataSource.setUsername(p.getProperty("masterName"));
        dataSource.setPassword(p.getProperty("masterPwd"));

        return dataSource;
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource,
                                                     org.apache.ibatis.session.Configuration config)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        //加载配置文件中的  mybatis.configuration属性
        sessionFactory.setConfiguration(config);
        return sessionFactory.getObject();
    }
}