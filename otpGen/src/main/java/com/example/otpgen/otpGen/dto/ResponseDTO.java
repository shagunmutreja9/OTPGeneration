package com.example.otpgen.otpGen.dto;

import lombok.Data;

@Data
public class ResponseDTO {
	private String uid;
	private String oid;
	private String channelName;
	private String otp;
	private long timestamp;

}
