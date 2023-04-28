package com.capstone.employee.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.employee.entity.Employee;
import com.capstone.employee.exception.InvalidEmployeeException;
import com.capstone.employee.repository.IEmployeeRepository;
import com.capstone.employee.vo.EmployeeVO;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	IEmployeeRepository employeeRepository;

	private static final String SECRET_KEY = "MySecretKey";
	private static final String SALTVALUE = "abcdefg";

	@Override
	public EmployeeVO getEmployeeById(int ID) throws Exception {

		Employee employee = employeeRepository.findById(ID).orElse(null);
		if (employee != null) {
			String encrypted=EmployeeService.encrypt(employee.getDateOfBirth().toString());
			String decrpted=EmployeeService.decrypt(encrypted);
			
			System.out.println("Encrypted = "+encrypted);
			System.out.println("Decrypted = "+decrpted);
			
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO.setEmployeeName(employee.getEmployeeName());
			employeeVO.setEmployeeID(employee.getEmployeeID());
			employeeVO.setDateOfBirth(encrypted);
			
			
			
			return employeeVO;

		} else
			throw new InvalidEmployeeException("Invalid EmployeeID");
	}

	public static String encrypt(String strToEncrypt) throws Exception {

		/* Declare a byte array. */
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		/* Create factory for secret keys. */
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		/* PBEKeySpec class implements KeySpec interface. */
		KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
		/* Retruns encrypted value. */
		return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));

	}

	public static String decrypt(String strToDecrypt) throws Exception {

		/* Declare a byte array. */
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		/* Create factory for secret keys. */
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		/* PBEKeySpec class implements KeySpec interface. */
		KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALTVALUE.getBytes(), 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
		/* Retruns decrypted value. */
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	}

	@Override
	public List<Employee> getEmployees() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

}
