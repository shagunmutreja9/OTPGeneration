package com.example.otpgen.otpGen.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.otpgen.otpGen.entities.OTPEntity;

public interface OTPDao extends JpaRepository<OTPEntity,String>{

}
