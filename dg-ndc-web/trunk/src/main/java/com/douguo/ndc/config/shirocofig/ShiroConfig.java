package com.douguo.ndc.config.shirocofig;

import com.douguo.ndc.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lcyanxi on 2018/8/12.
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private UserService userService;

    /**
     * 自定义的Realm
     */
    @Bean(name = "customRealm")
    public CustomRealm myShiroRealm(){
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCachingEnabled(false);
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);

        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return customRealm;
    }

    /**
     * cookie对象;
     * @return
     * */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(60*60*24);
        return simpleCookie;
    }

    /**
     * cookie管理器;
     * @return
     */

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }


    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中：
     * 1、安全管理器：securityManager
     * 可见securityManager是整个shiro的核心；
     * @return
     */
/*    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return cacheManager;
    }*/

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //注入缓存管理器
      //  securityManager.setCacheManager(ehCacheManager());
        //注入记住我管理器;
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setRealm(customRealm);
        return securityManager;
    }




    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        //获取要拦截的路径
        Set<String> list=userService.queryPermission();

        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/subLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/index/403");

        Map<String,String> filerChainDefinitionMap=new LinkedHashMap<>();


        for (String url:list){
            filerChainDefinitionMap.put(url,"authc,perms["+url+"]");
        }

        //大屏幕显示页面不拦截
        filerChainDefinitionMap.put("/datashow/**","anon");
        //菜谱推荐不拦截
        filerChainDefinitionMap.put("/recommend/**","anon");

        filerChainDefinitionMap.put("/bootstrap/**","anon");
        filerChainDefinitionMap.put("/echarts2-2-7/**","anon");
        filerChainDefinitionMap.put("/echarts3-7/**","anon");
        filerChainDefinitionMap.put("/logout","logout");
        filerChainDefinitionMap.put("/loginPage","anon");
        filerChainDefinitionMap.put("/login","anon");
        filerChainDefinitionMap.put("/success","anon");


        filerChainDefinitionMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filerChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
