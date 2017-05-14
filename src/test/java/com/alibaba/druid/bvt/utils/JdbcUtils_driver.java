package com.alibaba.druid.bvt.utils;

import java.sql.Driver;

import org.junit.Assert;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.JdbcUtils;

import junit.framework.TestCase;

public class JdbcUtils_driver extends TestCase {
    
    public void test_null() throws Exception {
        Assert.assertNull(JdbcUtils.getDriverClassName(null));
    }

    public void test_driver() throws Exception {
        String url = "jdbc:odps:xxx";
        String className = JdbcUtils.getDriverClassName(url);
        Class<?> clazz = Class.forName(className);
        Assert.assertNotNull(clazz);
        Driver driver = (Driver) clazz.newInstance();
        Assert.assertNotNull(driver);

        Assert.assertEquals(0, driver.getMajorVersion());
        Assert.assertEquals(1, driver.getMinorVersion());
        
        Assert.assertEquals(JdbcConstants.ODPS, JdbcUtils.getDbType(url, className));
    }

    public void test_log4jdbc_mysql() {
        String jdbcUrl = "jdbc:log4jdbc:mysql://localhost:8066/test";
        String dbType = JdbcUtils.getDbType(jdbcUrl, null);
        assertEquals("not support log4jdbc mysql, url like jdbc:log4jdbc:mysql:...", JdbcConstants.MYSQL, dbType);
    }

    public void test_log4jdbc_mysql2() throws Exception {
        String jdbcUrl = "jdbc:log4jdbc:mysql://localhost:8066/test";
        String dbType = JdbcUtils.getDbType(jdbcUrl, "net.sf.log4jdbc.DriverSpy");
        assertEquals("not support log4jdbc mysql, url like jdbc:log4jdbc:mysql:...", JdbcConstants.MYSQL, dbType);
    }
}
