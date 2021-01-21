package com.rabbitmqconsumer.demo.dao;

import com.rabbitmqconsumer.demo.pojo.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodDao extends JpaRepository<Good,String> {

}