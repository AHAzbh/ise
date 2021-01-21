package com.ise.demo.dao;

import com.ise.demo.pojo.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodDao extends JpaRepository<Good,String> {

}
