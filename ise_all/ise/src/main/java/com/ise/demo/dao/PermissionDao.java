package com.ise.demo.dao;

import com.ise.demo.pojo.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao extends JpaRepository<Permission,String> {

    @Query(value = "select a from Permission a where a.utype=?1")
    List<Permission> findAllByType(String type);

    @Query(value = "select a from Permission a where a.utype=?1 and a.pertype=?2")
    List<Permission> findByUserAndPerType(String utype,String pertype);

}
