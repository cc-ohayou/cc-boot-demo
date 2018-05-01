package com.cc.ccbootdemo.core.common.config.db.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.cc.ccbootdemo.core.common.properties.resource.TestLoadResource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnBean(TestLoadResource.class)

// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.cc.ccbootdemo.core.mapper.master";
    private static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";

   /* @Value("${baseResourceProperties.masterUrl}")
    private String url;

    @Value("${baseResourceProperties.masterName}")
    private String user;

    @Value("${baseResourceProperties.masterPassword}")
    private String password;

    @Value("${baseResourceProperties.masterDriver}")
    private String driverClass;
*/
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(TestLoadResource.property.getProperty("masterDriver"));
        dataSource.setUrl(TestLoadResource.property.getProperty("masterUrl"));
        dataSource.setUsername(TestLoadResource.property.getProperty("masterName"));
        dataSource.setPassword(TestLoadResource.property.getProperty("masterPwd"));
        return dataSource;
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}