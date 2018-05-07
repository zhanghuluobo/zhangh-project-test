package com.zhangh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * IniRealm测试
 *
 * Author zhangh
 * Date 2018/5/2 22:51
 *
 */


public class IniRealmTest {

    @Test
    public void testIniRealm(){

        IniRealm iniRealm = new IniRealm("classpath:test.ini");

        //1、创建Security Manage
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、设置realm至Security Manage
        securityManager.setRealm(iniRealm);

        //3、创建主体
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");

        //登录
        subject.login(token);
        //角色
        subject.checkRole("admin");
        //权限
        subject.checkPermissions("user:update","user:update");


    }
}
