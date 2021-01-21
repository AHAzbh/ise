package com.rabbitmqconsumer.demo.dao;


import com.rabbitmqconsumer.demo.pojo.U2G;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface U2GDao extends JpaRepository<U2G,String> {

}
