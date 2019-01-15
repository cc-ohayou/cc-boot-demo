package com.cc.ccbootdemo.core.common.config.db.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.cc.ccbootdemo.core.common.properties.resource.TestLoadResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;
@Slf4j
@Configuration
@ConditionalOnBean(TestLoadResource.class)
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cc.ccbootdemo.core.mapper.cluster";
    private static final String MAPPER_LOCATION = "classpath:mapper/cluster/*.xml";

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

    @Bean(name = "clusterDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        Properties property = TestLoadResource.propertyJdbc;
        if (property.isEmpty()) {
            TestLoadResource.loadProperties();
        }
        property=TestLoadResource.propertyJdbc;
        log.info("Properties="+property);
        dataSource.setDriverClassName(property.getProperty("clusterDriver"));
        dataSource.setUrl(property.getProperty("clusterUrl"));
        dataSource.setUsername(property.getProperty("clusterName"));
        dataSource.setPassword(property.getProperty("clusterPwd"));

        return dataSource;
    }

    @Bean(name = "clusterTransactionManager")
    public DataSourceTransactionManager clusterTransactionManager() {
        return new DataSourceTransactionManager(clusterDataSource());
    }

    @Bean(name = "clusterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource,
                                                      org.apache.ibatis.session.Configuration config)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfiguration(config);

        return sessionFactory.getObject();
    }
}