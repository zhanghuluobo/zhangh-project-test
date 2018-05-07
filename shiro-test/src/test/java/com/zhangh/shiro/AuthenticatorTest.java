package com.zhangh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * shiro认证器测试
 *
 * Author zhangh
 * Date 2018/4/21 23:21
 */


public class AuthenticatorTest {

    private SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Test
    public void testAuthenticator() {
        //1、创建SecurityManage
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //3、将realm设置入SecurityManage环境中
        defaultSecurityManager.setRealm(simpleAccountRealm);

        //2、主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //4、创建模拟登录用户
        UsernamePasswordToken token = new UsernamePasswordToken("zhangh", "123456");
        //登录
        subject.login(token);
        System.out.println("是否认证"+subject.isAuthenticated());
        //登出
        subject.logout();
        System.out.println("是否认证"+subject.isAuthenticated());
    }


    /**
     * 模拟添加用户
     */
    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("zhangh","123456");
    }


}
