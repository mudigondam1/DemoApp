package com.manasa.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manasa.demo.model.PncModel;

public interface PncRepo extends JpaRepository<PncModel, Long>{

}
