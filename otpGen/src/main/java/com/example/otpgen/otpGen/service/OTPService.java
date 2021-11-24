package com.example.otpgen.otpGen.service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.otpgen.otpGen.controller.OTPController;
import com.example.otpgen.otpGen.dao.OTPDao;
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

@Service
public class OTPService implements OTPServiceInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

	@Value("${app.gen}")
	public int generateOtp;

	@Value("${app.val}")
	public int validateOtp;

	@Autowired
	JavaMailSender sender;

	@Override
	public String generateOTP() {
		long otp;
		Random random = new Random();
		otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	public boolean verifyEmail(OTPEntity otpEntity) {
		Pattern emailPattern = Pattern.compile(
				"[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
		Matcher m = emailPattern.matcher(otpEntity.getUid());
		if (!m.matches()) {
			return false;
		} else {
//			sendEmail(otpEntity.getUid(), "Hello your otp is: " + otpEntity.getOtp(), "OTP");
			return true;
		}
	}

	@Autowired
	private OTPDao otpDao;

	public OTPEntity generatedOTP(OTPEntity otpEntity) throws NoResourceException, OTPTime, EmailException,
			PhoneNoDigitException, PhoneNoLengthException, InvalidChannelException {
		otpEntity.setOtp(generateOTP());
		OTPEntity otp = otpDao.findById(otpEntity.getUid()).orElse(null);
		if (otp != null) {
			if (((System.currentTimeMillis() - otp.getTimestamp()) / 60000) < generateOtp)
				LOGGER.error("OTP Request Time Out"); 
				throw new OTPTime();
		}
		if (otpEntity.getChannelName().equalsIgnoreCase("email")) {
			LOGGER.info("Channel name valid-email");
			if(!verifyEmail(otpEntity)) {
				LOGGER.error("Email not valid");
			}else {
				LOGGER.info("Email Sent");
				sendEmail(otpEntity.getUid(), "Your otp is: " + otpEntity.getOtp(), " . Valid till 2 mins.");
			}

		} else if (otpEntity.getChannelName().equalsIgnoreCase("sms")) {
			if (!otpEntity.getUid().matches("[0-9]+")) {
				LOGGER.error("Enter digit only");
				throw new PhoneNoDigitException();
				}
			if (otpEntity.getUid().length() != 10) {
				LOGGER.error("Enter valid length of phone number");
				throw new PhoneNoLengthException();
			}
		} else {
			LOGGER.error("Enter email or sms only");
			throw new InvalidChannelException();
		}

//		System.out.println(otpEntity.getOtp());
		otpEntity.setTimestamp();
		this.otpDao.save(otpEntity);
		return otpEntity;
	}

	@Override
	public OTPEntity validateOTP(String uid, String otp)
			throws OTPInvalidException, TimestampException, InvalidUserException, NoResourceException {
		long timestamp = System.currentTimeMillis();
		OTPEntity otpBean = otpDao.findById(uid).orElseThrow(() -> new InvalidUserException());

		long timestampStored = otpBean.getTimestamp();
		if ((timestamp - timestampStored) / 60000 < validateOtp) {
			String otpStored = otpBean.getOtp();
			if (otpStored.equals(otp)) {
				return otpBean;
			} else {
				LOGGER.error("Invalid OTP");
				throw new OTPInvalidException();
				// return ResponseEntity.badRequest().body(otpBean);
			}
		} else {
			LOGGER.error("OTP Request Time Out");
			throw new TimestampException();
			// return ResponseEntity.badRequest().body(otpBean);}
		}
	}

	@Override
	public void sendEmail(String toEmail, String body, String subject) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("csecec.1802200@gmail.com");
		simpleMessage.setTo(toEmail);
		simpleMessage.setText(body);
		simpleMessage.setSubject(subject);
		sender.send(simpleMessage);
		System.out.println("mail sent");

	}
}
