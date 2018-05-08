package com.zhangh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 自定义Realm测试
 * <p>
 * Author zhangh
 * Date 2018/5/7 23:06
 */


public class CustomRealmTest {

    @Test
    public void testCustomRealm() {
        //1、创建自定义Realm
        CustomRealm customRealm = new CustomRealm();

        //2、创建Security Manage
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        //shiro加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //加密方式
        matcher.setHashAlgorithmName("md5");
        //加密次数
        matcher.setHashIterations(1);
        //设置加密对象
        customRealm.setCredentialsMatcher(matcher);

        //3、创建主体
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");
        subject.login(token);
        System.out.println("是否鉴权："+subject.isAuthenticated());
        subject.checkRoles("admin","user");
        subject.checkPermissions("user:insert","user:update");
    }
}
