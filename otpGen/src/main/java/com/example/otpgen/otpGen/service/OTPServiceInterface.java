package com.example.otpgen.otpGen.service;

import com.example.otpgen.otpGen.entities.OTPEntity;
import com.example.otpgen.otpGen.exception.EmailException;
import com.example.otpgen.otpGen.exception.InvalidChannelException;
import com.example.otpgen.otpGen.exception.InvalidUserException;
import com.example.otpgen.otpGen.exception.NoResourceException;
import com.example.otpgen.otpGen.exception.OTPInvalidException;
import com.example.otpgen.otpGen.exception.OTPTime;
import com.example.otpgen.otpGen.exception.PhoneNoDigitException;
import com.example.otpgen.otpGen.exception.PhoneNoLengthException;
import com.example.otpgen.otpGen.exception.TimestampException;

public interface OTPServiceInterface {

	public String generateOTP();

	public OTPEntity generatedOTP(OTPEntity otpentity) throws NoResourceException, OTPTime, EmailException, PhoneNoDigitException, PhoneNoLengthException, InvalidChannelException;

	public OTPEntity validateOTP(String uid, String otp) throws OTPInvalidException,TimestampException, InvalidUserException, NoResourceException;

	public void sendEmail(String toEmail, String body, String subject) ;


}
