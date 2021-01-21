package com.ise.demo.dao;

import com.ise.demo.pojo.U2G;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface U2GDao extends JpaRepository<U2G,String> {

    @Query(value = "select a from U2G a where a.uname=?1")
    List<U2G> findByUname(String uname);

    @Query(value = "select a from U2G a where a.gid=?1")
    List<U2G> findByGid(String gid);

}
