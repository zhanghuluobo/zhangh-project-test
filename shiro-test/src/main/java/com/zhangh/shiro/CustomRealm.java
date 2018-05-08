package com.zhangh.shiro;

import cn.hutool.core.util.StrUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    private Map<String, String> map = new HashMap<String, String>();
    {
//        map.put("zhangsan", "123456");
        map.put("zhangsan", "9bad41710724cf6511abde2a52416881");
        super.setName("customRealm");
    }

    //鉴权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        //模拟数据库通过用户名获取角色和资源
        Set<String> roleSet = this.getRolesByUsername(userName);
        Set<String> resourceSet = this.getResourceByUsername(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleSet);
        simpleAuthorizationInfo.addStringPermissions(resourceSet);
        return simpleAuthorizationInfo;
    }

    private Set<String> getResourceByUsername(String userName) {
        HashSet<String> resourceSet = new HashSet<String>();
        resourceSet.add("user:insert");
        resourceSet.add("user:update");
        return resourceSet;
    }

    private Set<String> getRolesByUsername(String userName) {
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("user");
        return roleSet;
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1、从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        //2、通过用户名到数据库中获取凭证
        String password = this.getPasswordByUserName(userName);
        if(StrUtil.isEmpty(password)){
            return null;
        }
        SimpleAuthenticationInfo realm = new SimpleAuthenticationInfo(userName, password, "customRealm");
        //放盐
        realm.setCredentialsSalt(ByteSource.Util.bytes("zhangsan"));
        return realm;
    }

    /**
     * 模拟数据库
     * @param userName 用户名
     * @return 密码
     */
    private String getPasswordByUserName(String userName) {
        return map.get(userName);
    }

    @Test
    public void md5Test(){
        Md5Hash md5Hash = new Md5Hash("123456","zhangsan");
        System.out.print(md5Hash.toString());
    }
}
