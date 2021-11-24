package com.example.otpgen.otpGen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.otpgen.otpGen.entities.OTPEntity;
import com.example.otpgen.otpGen.exception.EmailException;
import com.example.otpgen.otpGen.exception.InvalidChannelException;
import com.example.otpgen.otpGen.exception.InvalidUserException;
import com.example.otpgen.otpGen.exception.OTPInvalidException;
import com.example.otpgen.otpGen.exception.OTPTime;
import com.example.otpgen.otpGen.exception.PhoneNoDigitException;
import com.example.otpgen.otpGen.exception.PhoneNoLengthException;
import com.example.otpgen.otpGen.exception.TimestampException;
import com.example.otpgen.otpGen.service.OTPService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OtpServiceTest {


	@Autowired
	OTPService otpService;
	
	
	OTPEntity otpTest1=new OTPEntity("email","12349","shagun@gmail.com");
	OTPEntity otpTest2=new OTPEntity("email","23491","mutreja@gmail.com");
	OTPEntity otpTest3=new OTPEntity("email","34912","minit@gmail.com");
	OTPEntity otpTest4=new OTPEntity("email","49123","shagunmutreja@gmail.com");
	OTPEntity otpTest5=new OTPEntity("email","91234","minitmutreja@gmail.com");
	OTPEntity otpTest6=new OTPEntity("sms","66666","82870643s7");
	OTPEntity otpTest7=new OTPEntity("sms","55555","999988877");
	OTPEntity otpTest8=new OTPEntity("jg","22222","22335557673");
	
	
	
	
	@Test
	void GenerateOtp() {
		String otp=otpService.generateOTP();
		assertTrue(otp.length()==6);
	}
	
	
	@Test
	void testGenerateOtpService() {
		
		try {
//			assertEquals(otpTest1.getUid(),otpService.generatedOTP(otpTest1));
//			Assertions.assertThrows(OTPTime.class, ()->{
//				otpService.generatedOTP(otpTest2);
//			});
//			Assertions.assertThrows(EmailException.class,()->{
//				otpService.generatedOTP(otpTest3);
//			});
//
//			assertEquals(otpTest5.getUid(),otpService.generatedOTP(otpTest5));
//			Assertions.assertThrows(PhoneNoDigitException.class,()->{
//				otpService.generatedOTP(otpTest6);
//			});
//			Assertions.assertThrows(PhoneNoLengthException.class,()->{
//				otpService.generatedOTP(otpTest7);
//			});
			Assertions.assertThrows(InvalidChannelException.class, ()->{
				otpService.generatedOTP(otpTest8);
			});
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	void testValidateOtpService() {

		OTPService otpService=new OTPService();
		try {
			otpService.generatedOTP(otpTest7);
			assertEquals(true,otpService.validateOTP(otpTest7.getUid(),otpTest7.getOtp()));
			otpTest1.setOtp("1234");
			assertEquals(false,otpService.validateOTP(otpTest1.getUid(),otpTest1.getOtp()));
			Assertions.assertThrows(InvalidUserException.class,()->{
				otpService.validateOTP(otpTest7.getUid(),otpTest7.getOtp());
			});
			Assertions.assertThrows(OTPInvalidException.class,()->{
				otpService.validateOTP(otpTest7.getUid(),otpTest7.getOtp());
			});
			Assertions.assertThrows(TimestampException.class,()->{
				otpService.validateOTP(otpTest7.getUid(),otpTest7.getOtp());
			});
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
}
