package com.jifenke.lepluslive.shiro.config;

import com.jifenke.lepluslive.management.domain.entities.User_role;
import com.jifenke.lepluslive.management.repository.RoleRealmRepository;
import com.jifenke.lepluslive.management.repository.RoleRepository;
import com.jifenke.lepluslive.management.repository.UserRoleRepository;
import com.jifenke.lepluslive.user.domain.entities.ManageUser;
import com.jifenke.lepluslive.user.repository.ManageUserRepository;
import com.jifenke.lepluslive.user.service.ManageUserService;

import net.sf.ehcache.CacheManager;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/31.
 */
public class MyShiroRealm extends AuthorizingRealm implements ApplicationContextAware {


  private ApplicationContext applicationContext;


  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    List<String> permissions = new ArrayList<String>();
    String userName = (String) principalCollection.getPrimaryPrincipal();
    if (userName.equals("root")) {
      permissions.add("index:query");
      permissions.add("product:query");
      permissions.add("merchant:query");
      permissions.add("merchant:edit");
      permissions.add("order:query");
      permissions.add("onLineOrder:query");
      permissions.add("offLineOrder:query");
      permissions.add("financial:query");
      permissions.add("financial:transfer");
      permissions.add("share:query");
      permissions.add("topic:query");
      permissions.add("lj_user:query");
      permissions.add("weixin:query");
      permissions.add("partner:query");
      permissions.add("SalesStaff:query");
      permissions.add("management:query");
    } else {
      ManageUserRepository manageUserRepository =
          (ManageUserRepository) applicationContext.getBean("manageUserRepository");
      ManageUser manageUser = manageUserRepository.findUserByName(userName);
      UserRoleRepository
          userRoleRepository =
          (UserRoleRepository) applicationContext.getBean("userRoleRepository");
      RoleRealmRepository
          roleRealmRepository =
          (RoleRealmRepository) applicationContext.getBean("roleRealmRepository");

      List<User_role> user_roleList = userRoleRepository.findUserRoleByUserId(manageUser.getId());
      List<Long> roleIdList = new ArrayList<Long>();

      for (int i = 0; i < user_roleList.size(); i++) {
        roleIdList.add(user_roleList.get(i).getRoleID());
      }

      for (int i = 0; i < roleIdList.size(); i++) {
        permissions.addAll(roleRealmRepository.findRealmByID(roleIdList.get(i)));
      }
    }
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    simpleAuthorizationInfo.addStringPermissions(permissions);
    return simpleAuthorizationInfo;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    ManageUserRepository manageUserRepository =
        (ManageUserRepository) applicationContext.getBean("manageUserRepository");
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    ManageUser manageUser = manageUserRepository.findUserByName(token.getUsername());
    if (manageUser != null) {
      return new SimpleAuthenticationInfo(manageUser.getName(), manageUser.getPassword(),
                                          getName());
    }

    return null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public void clearCached() {
    EhCacheManager ehCacheManager =
        (EhCacheManager) applicationContext.getBean("ehCacheManager");
    CacheManager cacheManager = ehCacheManager.getCacheManager();
    cacheManager.clearAll();
  }
}
