package com.zhangh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * shiro授权测试
 *
 * Author zhangh
 * Date 2018/5/2 21:56
 *
 */

public class AuthorizerTest {

    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Test
    public void testAuthorizer(){
        //1、创建Security Manage
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //3、将realm设置入Security Manage环境中
        securityManager.setRealm(simpleAccountRealm);
        
        //2、主体提交授权请求
        //将SecurityManage放入SecurityUtils中
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //4、模拟登录请求
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");

        subject.login(token);

        //授权检验
        subject.checkRole("role1");
        subject.checkRoles("role1","role2");
    }


    /**
     * 模拟添加用户
     *
     * Author zhangh
     * Date 2018/5/2 22:13
     *
     */


    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("zhangsan","123456","role1","role2");
    }
}
