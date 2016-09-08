package com.jifenke.lepluslive.shiro.config;

import com.jifenke.lepluslive.management.domain.entities.Realm;
import com.jifenke.lepluslive.management.domain.entities.Role_realm;
import com.jifenke.lepluslive.management.domain.entities.User_role;
import com.jifenke.lepluslive.management.repository.RealmRepository;
import com.jifenke.lepluslive.management.repository.Role_realmRepository;
import com.jifenke.lepluslive.management.repository.UserRoleRepository;
import com.jifenke.lepluslive.user.domain.entities.ManageUser;
import com.jifenke.lepluslive.user.repository.ManageUserRepository;

import net.sf.ehcache.CacheManager;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;


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
      RealmRepository
          realmRepository =
          (RealmRepository) applicationContext.getBean("realmRepository");
      List<Realm> realmIdList = realmRepository.findAll();
      List<String> rolePercodes = new ArrayList<String>();
      for (Realm realmId : realmIdList) {
        rolePercodes.add(realmId.getRolePercode());
      }
      permissions.addAll(rolePercodes);
      permissions.add("management:query");
    } else {
      ManageUserRepository manageUserRepository =
          (ManageUserRepository) applicationContext.getBean("manageUserRepository");
      UserRoleRepository
          userRoleRepository =
          (UserRoleRepository) applicationContext.getBean("userRoleRepository");
      Role_realmRepository
          role_realmRepository =
          (Role_realmRepository) applicationContext.getBean("role_realmRepository");
      RealmRepository
          realmRepository =
          (RealmRepository) applicationContext.getBean("realmRepository");
      ManageUser manageUser = manageUserRepository.findUserByName(userName);

//获得角色ID
      List<User_role> user_roleList = userRoleRepository.findUserRoleByUserId(manageUser.getId());
      List<Long> roleIdList = new ArrayList<Long>();

      for (int i = 0; i < user_roleList.size(); i++) {
        roleIdList.add(user_roleList.get(i).getRoleId());
      }
//获得权限ID
      List<Long> realmIdList = new ArrayList<Long>();

      for (Long roleId : roleIdList) {
        List<Role_realm> realmIdList2 = role_realmRepository.findRealmIdByRoleId(roleId);
        for (Role_realm realm : realmIdList2) {
          realmIdList.add(realm.getRealmId());
        }
      }

//获得权限rolePercode

      List<String> rolePercodes = new ArrayList<String>();
      for (Long realmId : realmIdList) {
        rolePercodes.add(realmRepository.findOne(realmId).getRolePercode());
      }
      permissions.addAll(rolePercodes);
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
