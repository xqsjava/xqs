package com.deyuan.dao;


import com.deyuan.pojo.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {


    @Select("select * from permission where id in(select permissionid from role_permission where roleid = #{roleid})")
    public List<Permission> findByRoleId(String id);

    @Select("select * from permission")
    List<Permission> findAll();

    @Insert("insert into permission(id,permissionName,url)values(role_seq.nextval,#{permissionName},#{url})")
    void save(Permission permission);
}
