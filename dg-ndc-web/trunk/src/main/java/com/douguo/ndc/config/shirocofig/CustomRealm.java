package com.douguo.ndc.config.shirocofig;

import com.douguo.ndc.model.User;
import com.douguo.ndc.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lcyanxi on 2018/8/12.
 */
public class CustomRealm extends AuthorizingRealm{
    @Autowired
    private  UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String uid = (String) principalCollection.getPrimaryPrincipal();

        //保存授权信息

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();


        //从数据库中获取当前登录用户的 角色列表 和 权限列表
       // authorizationInfo.addRoles(roleService.listRoleNameBySId(staffSId));
        authorizationInfo.addStringPermissions(userService.selectMenus(uid));
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //实际上这个userName是从LoginController里面currentUser.login(token)传过来的
        String userName = (String) authenticationToken.getPrincipal();

        User user = userService.selectByIdentify(userName);
        //如果没有该用户
        if (user == null) {
            System.out.print("认证失败");
            throw new UnknownAccountException();
        }
        //判断该账号是否被激活
/*        if (paperStaff.getSMark() < 0) {

            throw new LockedAccountException();
        }*/

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(
                user.getUid(),
                user.getPass(),
                getName()
        );
    }
}
