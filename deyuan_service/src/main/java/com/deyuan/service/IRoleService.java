package com.deyuan.service;

import com.deyuan.pojo.Permission;
import com.deyuan.pojo.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll();

    void save(Role role);

    List<Permission> findByRoleIdOtherPermission(String id);

    void addPermissionToRole(String roleId, String[] permissionIds);
}
