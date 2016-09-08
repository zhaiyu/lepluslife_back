package com.jifenke.lepluslive.management.controller;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.management.domain.entities.Realm;
import com.jifenke.lepluslive.management.domain.entities.Role;
import com.jifenke.lepluslive.management.domain.entities.Role_realm;
import com.jifenke.lepluslive.management.domain.entities.User_role;
import com.jifenke.lepluslive.management.service.ManagementUserService;
import com.jifenke.lepluslive.management.service.RealmService;
import com.jifenke.lepluslive.management.service.RoleService;
import com.jifenke.lepluslive.management.service.Role_realmService;
import com.jifenke.lepluslive.management.service.UserRoleService;
import com.jifenke.lepluslive.user.domain.entities.ManageUser;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.util.*;

/**
 * Created by lss on 2016/7/26.
 */
@RestController
@RequestMapping("/manage")
public class ManageController {

  @Inject
  private RoleService roleService;
  @Inject
  private ManagementUserService managementUserService;
  @Inject
  private UserRoleService userRoleService;
  @Inject
  private Role_realmService role_realmService;
  @Inject
  private RealmService realmService;


  @RequestMapping("/serchUserRole")
  public ModelAndView serchUserRole(Model model, String id) {
    ManageUser managementUser = managementUserService.findManagementUserByID(Long.parseLong(id));
    List<Role> roleList = new ArrayList<Role>();
    List<User_role> userRoleList = userRoleService.findUserRoleByUserId(Long.parseLong(id));
    for (int i = 0; i < userRoleList.size(); i++) {
      roleList.add(roleService.findRoleById(userRoleList.get(i).getRoleId()));
    }
    model.addAttribute("roleList", roleList);
    model.addAttribute("managementUser", managementUser);
    return MvUtil.go("/management/serchUserRole");
  }

  @RequestMapping("/deleteRole")
  public ModelAndView deleteRole(Model model, String id) {
    roleService.deleteRoleMenuByRoleID(Long.parseLong(id));
    roleService.deleteRole(Long.parseLong(id));
    List<User_role> user_roleList = userRoleService.findUserRoleByRoleId(Long.parseLong(id));
    userRoleService.deleteUserRole(user_roleList);

    return new ModelAndView("redirect:/manage/roleList");
  }

  @RequestMapping("/userCreatePage")
  public ModelAndView userCreatePage(Model model) {
    List<ManageUser> manageUsers = managementUserService.findAllManagementUser();
    List<String> userNames = new ArrayList<String>();

    for (ManageUser manageUser : manageUsers) {
      userNames.add(manageUser.getName());
    }
    model.addAttribute("userNames", userNames);
    List<ManageUser> manageUserList = managementUserService.findAllManagementUser();
    model.addAttribute("manageUserList", manageUserList);
    return MvUtil.go("/management/createUserPage");
  }

  @RequestMapping("/managementUserList")
  public ModelAndView managementUserList(Model model) {
    List<ManageUser> managementUserList = managementUserService.findAllManagementUser();
    managementUserList.remove(0);
    model.addAttribute("managementUserList", managementUserList);
    return MvUtil.go("/management/managementList");
  }

  @RequestMapping("/addUser")
  public ModelAndView addUser(Model model, String userName, String userPassword) {
    ManageUser managementUser = new ManageUser();
    managementUser.setName(userName);
    managementUser.setPassword(MD5Util.MD5Encode(userPassword, "UTF-8"));
    managementUserService.saveManagementUser(managementUser);
    return new ModelAndView("redirect:/manage/managementUserList");
  }


  @RequestMapping("/realmList")
  public ModelAndView realmList(Model model) {
    List<Realm> realmList = realmService.findAll();
    model.addAttribute("realmList", realmList);
    return MvUtil.go("/management/managementList");
  }

  @RequestMapping("/addRealmPage")
  public ModelAndView addRealmPage(Model model) {
    List<Realm> realmList = realmService.findAll();
    model.addAttribute("realmList", realmList);
    return MvUtil.go("/management/creatRealmPage");
  }

  @RequestMapping("/addRealm")
  public ModelAndView addRealm(String name, String rolePercode) {
    Realm realm = new Realm();
    realm.setName(name);
    realm.setRolePercode(rolePercode);
    realmService.save(realm);
    return new ModelAndView("redirect:/manage/realmList");
  }

  @RequestMapping("/deletRealm")
  public ModelAndView deletRealm(Long id) {
    List<Role_realm> role_realmList = role_realmService.findByRealmId(id);
    role_realmService.deleteRole_realmList(role_realmList);
    Realm realm = realmService.findRealmById(id);
    realmService.deleteRealm(realm);
    return new ModelAndView("redirect:/manage/realmList");
  }

  @RequestMapping("/roleList")
  public ModelAndView management(Model model) {
    List<Role> roleList = roleService.findAllRole();
    model.addAttribute("roleList", roleList);
    return MvUtil.go("/management/managementList");
  }

  @RequestMapping("/roleCreatePage")
  public ModelAndView roleCreatePage(Model model) {
    List<Realm> realmList = realmService.findAll();
    List<Role> roleList = roleService.findAllRole();
    model.addAttribute("roleList", roleList);
    model.addAttribute("realmList", realmList);
    return MvUtil.go("/management/createRolePage");
  }

  @RequestMapping("/addRole")
  public ModelAndView addRole(String roleName, String[] realmIdArray, Model model) {
    Role role = new Role();
    role.setRoleName(roleName);
    roleService.saveRole(role);
    Long roleId = roleService.serchId(roleName);
    List<Role_realm> list = new ArrayList<Role_realm>();
    for (int i = 0; i < realmIdArray.length; i++) {
      Role_realm role_realm = new Role_realm();
      role_realm.setRoleID(roleId);
      role_realm.setRealmId(Long.parseLong(realmIdArray[i]));
      list.add(role_realm);
    }
    if (realmIdArray != null) {
      roleService.saveRoleRealm(list);
    }
    return new ModelAndView("redirect:/manage/roleList");
  }


  @RequestMapping("/editRealmPage")
  public ModelAndView editRealmPage(Model model, Long id) {
    Realm realm = realmService.findRealmById(id);
    model.addAttribute("realm", realm);
    return MvUtil.go("/management/editRealmPage");
  }


  @RequestMapping("/editRealm")
  public ModelAndView editRealm(Long id, String name, String rolePercode) {
    Realm realm = realmService.findRealmById(id);
    realm.setName(name);
    realm.setRolePercode(rolePercode);
    realmService.save(realm);
    return new ModelAndView("redirect:/manage/realmList");
  }

  @RequestMapping("/serchRealmPage")
  public ModelAndView serchRealmPage(Model model, Long id) {
    Realm realm = realmService.findRealmById(id);
    model.addAttribute("realm", realm);
    return MvUtil.go("/management/serchRealm");
  }

  @RequestMapping("/roleRealm")
  public ModelAndView roleRealm(Model model, Long id) {
    List<Role_realm> role_realmlist = role_realmService.findByRoleId(id);
    List<Realm> realmList = new ArrayList<Realm>();
    for (Role_realm role_realm : role_realmlist) {
      Realm realm = realmService.findRealmById(role_realm.getRealmId());
      realmList.add(realm);
    }
    model.addAttribute("realmList", realmList);
    return MvUtil.go("/management/roleManagementList");
  }

  @RequestMapping("/editUserRolePage")
  public ModelAndView editUserRole(Model model, Long id) {
    ManageUser manageUser = managementUserService.findManagementUserByID(id);
    List<Role> roleList = roleService.findAllRole();
    model.addAttribute("manageUser", manageUser);
    model.addAttribute("roleList", roleList);
    return MvUtil.go("/management/editUserRolePage");
  }

  @RequestMapping("/editUserRole")
  public ModelAndView updateUserRole(Model model, String manageUserId, String[] roleIdArray) {
    List<User_role> user_roleList = new ArrayList<User_role>();
    userRoleService
        .deleteUserRole(userRoleService.findUserRoleByUserId(Long.parseLong(manageUserId)));
    for (int i = 0; i < roleIdArray.length; i++) {
      userRoleService
          .deleteUserRole(userRoleService.findUserRoleByUserId(Long.parseLong(manageUserId)));
      User_role user_role = new User_role();
      user_role.setUserId(Long.parseLong(manageUserId));
      user_role.setRoleId(Long.parseLong(roleIdArray[i]));
      user_roleList.add(user_role);

      userRoleService.clearCache();
    }
    userRoleService.saveUser_role(user_roleList);
    return new ModelAndView("redirect:/manage/managementUserList");
  }


  @RequestMapping("/deletManageUser")
  public ModelAndView deletManageUser(Long id) {
    List<User_role> user_roleList = userRoleService.findUserRoleByUserId(id);
    userRoleService.deleteUserRole(user_roleList);

    ManageUser manageUser = managementUserService.findManagementUserByID(id);
    managementUserService.deleteUser(id);
    return new ModelAndView("redirect:/manage/managementUserList");
  }


}
