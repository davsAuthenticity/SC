package com.davs.dashboard.controller;

import java.math.BigInteger;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;


@RestController
@RequestMapping("/api")
public class Dashboard {

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(10000000000L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4500000L);
	public static final String contractAddress = "0xe37dda2d0fbb22a3db6a5791a905b5a5008b4d85";

	@RequestMapping(value = "/addIfNotVerifyQr", method = RequestMethod.GET)
	public String addIfNotVerifyQr(@RequestParam(value="value",required=false) String value) {

		try {
			if(value!=null) {
				int returnVal = verify(getHash(value));
				String str = returnVal == 0 ? "absent":"present";
				if(returnVal == 0) {
					return addQr(value);
				}else {
					JSONObject result = new JSONObject();
					result.put("result", str);
					return result.toString();
				}
			}else {
				throw new Exception();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("result", "Error: Invalid Request");
			return result.toString();
		}

	}

	@RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
	public String sendEmail(@RequestParam(value="name",required=false) String name, @RequestParam(value="email",required=false) String email, @RequestParam(value="find-us",required=false) String findUs, @RequestParam(value="message",required=false) String message) {
		try {
			String actualName = "";
			String actualEmail = "";
			String actualMessage = "";
			if(name!=null)
				actualName = name;
			if(email!=null)
				actualEmail = email;
			if(message!=null)
				actualMessage = message;
			String content = "<head>\n" + 
					"  <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\">\n" + 
					"</head>\n" + 
					"<body style=\"font-family: 'Montserrat', sans-serif;\">\n" + 
					"\n" + 
					"  <p>\n" + 
					"    <h3 style=\"text-decoration: underline; letter-spacing: 1px;\">Subscription Details</h3> \n" + 
					"\n" + 
					"    <ul style=\"list-style-type: none\">\n" + 
					"      <li style=\"margin-top: 10px\">\n" + 
					"        <strong>Name: </strong> "+actualName+"\n" + 
					"      </li>\n" + 
					"      <li style=\"margin-top: 20px\">\n" + 
					"        <strong>Email : </strong> "+actualEmail+"\n" + 
					"      </li>\n" + 
					"      <li style=\"margin-top: 20px\">\n" + 
					"        <strong>Message : </strong> "+actualMessage+"\n" + 
					"      </li>\n" + 
					"    </ul>\n" + 
					"  </p>\n" + 
					"</body>";
			new EmailService(content).start();
			return "Email sent successfully. Thank you for contacting us";

		}catch(Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("result", "Error: Invalid Request");
			return result.toString();
		}

	}

	@RequestMapping(value = "/addQr", method = RequestMethod.GET)
	public String addQr(@RequestParam(value="value",required=false) String value) {

		try {
			if(value!=null) {
				String returnVal = add(getHash(value));
				if(returnVal == null) {
					throw new Exception();
				}
				JSONObject result = new JSONObject();
				result.put("result", returnVal);
				return result.toString();
			}else {
				throw new Exception();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("result", "Error: Invalid Request");
			return result.toString();
		}

	}

	@RequestMapping(value = "/verifyQr", method = RequestMethod.GET)
	public String verifyQr(@RequestParam(value="value",required=false) String value) {

		try {
			if(value!=null) {
				int returnVal = verify(getHash(value));
				String str = returnVal == 0 ? "absent":"present";
				JSONObject result = new JSONObject();
				result.put("result", str);
				return result.toString();
			}else {
				throw new Exception();
			}

		}catch(Exception e) {
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("result", "Error: Invalid Request");
			return result.toString();
		}

	}

	// hash
	public  byte[] getHash(String content) {
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
		return digestSHA3.digest(content.getBytes());
	}

	// add qr
	public String add(byte[] qr) {
		try {
			Admin web3j = Admin.build(new HttpService("http://13.71.161.208:8545/"));
			Credentials credentials = WalletUtils.loadCredentials("admin", "UTC--2018-11-02T23-20-06.523567778Z--9876bb9716093298a926d9544ede4f2fab094022");

			Davs_sol_davs contract = Davs_sol_davs.load(
					contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);

			TransactionReceipt transactionReceipt = contract.addQR(qr).send();
			return transactionReceipt.getTransactionHash();

		}catch(Exception e) {
			return null;
		}
	}

	// verify qr
	public int verify(byte[] qr) {
		try {
			Admin web3j = Admin.build(new HttpService("http://13.71.161.208:8545/"));
			Credentials credentials = WalletUtils.loadCredentials("admin", "UTC--2018-11-02T23-20-06.523567778Z--9876bb9716093298a926d9544ede4f2fab094022");

			Davs_sol_davs contract = Davs_sol_davs.load(
					contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);

			BigInteger value = contract.verify(qr).send();
			System.out.println("Value is "+value);
			return value.intValue();

		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}



