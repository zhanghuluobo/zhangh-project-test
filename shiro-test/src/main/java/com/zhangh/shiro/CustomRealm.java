package com.zhangh.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

public class CustomRealm extends AuthorizingRealm {
    private Map<String, String> map = new HashMap<String, String>();
    {
        map.put("zhangsan", "123456");
    }

    //鉴权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        //2、通过用户名到数据库中获取凭证
        String password = this.getPasswordByUserName(userName);
        return null;
    }

    /**
     * 模拟数据库
     * @param userName 用户名
     * @return 密码
     */
    private String getPasswordByUserName(String userName) {
        return map.get(userName);
    }
}
