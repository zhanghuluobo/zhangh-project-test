package com.zhangh.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * JdbcRealm 测试
 * 
 * Author zhangh
 * Date 2018/5/3 22:19
 * 
 */

 
public class JdbcRealmTest {

    private DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/shiro_test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm(){

        //1、创建JdbcRealm
        JdbcRealm jdbcRealm = new JdbcRealm();

        //2、设置数据源
        jdbcRealm.setDataSource(dataSource);

        //认证权限时，需开启权限认证
        jdbcRealm.setPermissionsLookupEnabled(true);

        //3、创建Security Manage
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        //4、在Security Manage中配置JdbcRealm
        defaultSecurityManager.setRealm(jdbcRealm);

        //5、创建主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //6、模拟登录
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123456");


        String userSql = "SELECT USER_PASSWORD FROM USER WHERE USER_NAME = ?";
        jdbcRealm.setAuthenticationQuery(userSql);

        String roleSql = "SELECT ROLE_NAME FROM ROLE WHERE USER_NAME = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String perSql = "SELECT PER_PERMISSION FROM PERMISSIONS WHERE ROLE_NAME = ?";
        jdbcRealm.setPermissionsQuery(perSql);

        subject.login(token);

        System.out.println("是否认证："+subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user_query");


    }
}
