package com.jifenke.lepluslive.management.controller;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.management.domain.entities.ManageRole;
import com.jifenke.lepluslive.management.domain.entities.Role_realm;
import com.jifenke.lepluslive.management.domain.entities.User_role;
import com.jifenke.lepluslive.management.service.ManagementUserService;
import com.jifenke.lepluslive.management.service.RoleService;
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

  @RequestMapping("/roleCreatePage")
  public ModelAndView roleCreatePage() {
    return MvUtil.go("/management/createRolePage");
  }

  @RequestMapping("/userCreatePage")
  public ModelAndView userCreatePage(Model model) {
    List<ManageUser> manageUsers = managementUserService.findAllManagementUser();
    List<String> userNames = new ArrayList<String>();

    for (ManageUser manageUser : manageUsers) {
      userNames.add(manageUser.getName());
    }
    model.addAttribute("userNames", userNames);
    return MvUtil.go("/management/createUserPage");
  }

  @RequestMapping("/roleList")
  public ModelAndView management(Model model) {
    List<ManageRole> roleList = roleService.findAllRole();
    Map roleMap = new HashMap();
    roleMap.put("list", roleList);
    model.addAttribute("roleMap", roleMap);
    return MvUtil.go("/management/roleList");
  }


  @RequestMapping("/managementUserList")
  public ModelAndView managementUserList(Model model) {
    List<ManageUser> managementUserList = managementUserService.findAllManagementUser();
    managementUserList.remove(0);
    Map managementUserMap = new HashMap();
    managementUserMap.put("managementUserList", managementUserList);
    model.addAttribute("managementUserMap", managementUserMap);
    return MvUtil.go("/management/managementUserList");
  }

  @RequestMapping("/addRole")
  public ModelAndView addRole(String roleName, String[] realm, Model model) {
    ManageRole role = new ManageRole();
    role.setRoleName(roleName);
    roleService.saveRole(role);
    Long roleId = roleService.serchId(roleName);
    List<Role_realm> list = new ArrayList<Role_realm>();
    for (int i = 0; i < realm.length; i++) {
      Role_realm role_realm = new Role_realm();
      role_realm.setRoleID(roleId);
      role_realm.setRolePercode(realm[i]);
      list.add(role_realm);
    }
    if (realm != null) {
      roleService.saveRoleRealm(list);
    }
    return new ModelAndView("redirect:/manage/roleList");
  }

  @RequestMapping("/roleManange")
  public ModelAndView roleManange(Model model, String id) {
    List<String> roleRealmList = roleService.findRoleRealmByID(Long.parseLong(id));
    model.addAttribute("roleRealmList", roleRealmList);
    return MvUtil.go("/management/roleManagementList");
  }

  @RequestMapping("/deleteRole")
  public ModelAndView deleteRole(Model model, String id) {
    roleService.deleteRoleMenuByRoleID(Long.parseLong(id));
    roleService.deleteRole(Long.parseLong(id));
    List<User_role> user_roleList = userRoleService.findUserRoleByRoleId(Long.parseLong(id));
    userRoleService.deleteUserRole(user_roleList);

    return new ModelAndView("redirect:/manage/roleList");
  }

  @RequestMapping("/deleteUser")
  public ModelAndView deleteUser(Model model, String id) {

    managementUserService.deleteUser(Long.parseLong(id));
    return new ModelAndView("redirect:/manage/managementUserList");
  }


  @RequestMapping("/serchUserRole")
  public ModelAndView serchUserRole(Model model, String id) {
    ManageUser managementUser = managementUserService.findManagementUserByID(Long.parseLong(id));
    List<ManageRole> roleList = new ArrayList<ManageRole>();
    List<User_role> userRoleList = userRoleService.findUserRoleByUserId(Long.parseLong(id));
    for (int i = 0; i < userRoleList.size(); i++) {
      roleList.add(roleService.findRoleById(userRoleList.get(i).getRoleID()));
    }
    model.addAttribute("roleList", roleList);
    model.addAttribute("managementUser", managementUser);
    return MvUtil.go("/management/serchUserRole");
  }


  @RequestMapping("/editUserRolePage")
  public ModelAndView editUserRole(Model model, String id) {
    ManageUser managementUser = managementUserService.findManagementUserByID(Long.parseLong(id));
    List<ManageRole> manageRoleList = roleService.findAllRole();
    model.addAttribute("managementUser", managementUser);
    model.addAttribute("manageRoleList", manageRoleList);
    return MvUtil.go("/management/editUserRole");
  }


  @RequestMapping("/editUserRole")
  public ModelAndView updateUserRole(Model model, String id, String[] roleIdArray) {
    for (int i = 0; i < roleIdArray.length; i++) {
      userRoleService.deleteUserRole(userRoleService.findUserRoleByUserId(Long.parseLong(id)));
      User_role user_role = new User_role();
      user_role.setUserID(Long.parseLong(id));
      user_role.setRoleID(Long.parseLong(roleIdArray[i]));
      userRoleService.saveUser_role(user_role);
      userRoleService.clearCache();
    }
    return new ModelAndView("redirect:/manage/managementUserList");
  }

  @RequestMapping("/addUser")
  public ModelAndView addUser(Model model, String userName, String userPassword) {
    ManageUser managementUser = new ManageUser();
    managementUser.setName(userName);
    managementUser.setPassword(MD5Util.MD5Encode(userPassword, "UTF-8"));
    managementUserService.saveManagementUser(managementUser);
    return new ModelAndView("redirect:/manage/managementUserList");
  }

}
