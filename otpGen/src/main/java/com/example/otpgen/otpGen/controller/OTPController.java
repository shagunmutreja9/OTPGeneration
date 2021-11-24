package com.example.otpgen.otpGen.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.otpgen.otpGen.dto.RequestDTO;
import com.example.otpgen.otpGen.dto.ResponseDTO;
import com.example.otpgen.otpGen.entities.OTPEntity;
import com.example.otpgen.otpGen.exception.ApplicationResponseEntity;
import com.example.otpgen.otpGen.service.OTPServiceInterface;

import io.swagger.annotations.ApiOperation;


@RestController
public class OTPController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OTPServiceInterface otpService;
	
	
	@PostMapping("/OTPEntity")
	@ApiOperation(value="Click to generate OTP", notes="Enter data")
	public ApplicationResponseEntity<ResponseDTO> generatedOTP(@RequestBody RequestDTO requestDTO){
		try {
			//DTO to entity
			OTPEntity otpEntity = modelMapper.map(requestDTO, OTPEntity.class);
			otpService.generatedOTP(otpEntity);
			//Entity to DTO
			return new ApplicationResponseEntity<>("200", "Success", modelMapper.map(otpEntity, ResponseDTO.class));
		}catch(Exception e) {
			return new ApplicationResponseEntity<>("444", e.toString(),null);
		}
	}
	
	@GetMapping("/validate/{uid}/{otp}")
	@ApiOperation(value="Click to validate OTP", notes="Enter User Id and OTP")
	public ApplicationResponseEntity<ResponseDTO> validateOtp(@PathVariable String uid, @PathVariable String otp){
		try {
			OTPEntity otpEntity=otpService.validateOTP(uid, otp);
			return new ApplicationResponseEntity<>("200", "Success", modelMapper.map(otpEntity, ResponseDTO.class));
		}catch(Exception e) {
			return new ApplicationResponseEntity<>("444", e.toString(), null);
		}
	}
	/*
	 * @PostMapping("/OTPBean") public OTPEntity generatedOtp(@RequestBody OTPEntity
	 * otpentity) throws NoResourceException, OTPTime, EmailException,
	 * PhoneNoDigitException, PhoneNoLengthException, InvalidChannelException{
	 * LOGGER.info("Entered generate otp section"); return
	 * this.otpService.generatedOTP(otpentity); }
	 * 
	 * @GetMapping("/validate/{uid}/{otp}") public
	 * ResponseEntity<OTPEntity>validateOtp(@PathVariable String uid,@PathVariable
	 * String otp) throws NoResourceException, OTPInvalidException,
	 * TimestampException, InvalidUserException {
	 * 
	 * LOGGER.info("Entered validate eotp section"); return
	 * this.otpService.validateOTP(uid,otp);
	 * 
	 * }
	 */
	
}
