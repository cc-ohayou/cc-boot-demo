package com.cc.ccbootdemo.core.common.config.db.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.cc.ccbootdemo.core.common.properties.resource.TestLoadResource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ConditionalOnBean(TestLoadResource.class)
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = ClusterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "clusterSqlSessionFactory")
public class ClusterDataSourceConfig {

    // 精确到 cluster 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cc.ccbootdemo.core.mapper.cluster";
    private static final String MAPPER_LOCATION = "classpath:mapper/cluster/*.xml";



    @Bean(name = "clusterDataSource")
    public DataSource clusterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        Properties property = TestLoadResource.propertyJdbc;
        if (property.isEmpty()) {
            TestLoadResource.loadProperties();
        }
        property=TestLoadResource.propertyJdbc;
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
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("clusterDataSource") DataSource clusterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(clusterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ClusterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}