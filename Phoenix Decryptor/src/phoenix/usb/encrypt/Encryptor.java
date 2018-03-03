package phoenix.usb.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONException;
import org.json.JSONObject;

public class Encryptor {

	private static int kbFileSixe =1000;
	
	private static final int initEndByte = 1000 * kbFileSixe;
	private final String ALGORITHM = "AES";
	private final String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
	private byte[] secKey;
	private String fileNameList;

	public Encryptor(String strInputFile) {
		try {
			secKey = this.genSecretKey(strInputFile);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doEncrypt(int encryptMode, String strInputFile, String expiredate) {
		doCrypto(strInputFile, expiredate);
	}

	private void doCrypto(String inputFile, String expiredate) {
		try {

			Key secretKey = new SecretKeySpec(secKey, ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			ArrayList<File> filesList = new ArrayList<File>();

			List<File> fuel = this.makeFuelFile(cipher, inputFile);
			File heat = this.makeHeatFile(cipher, inputFile, expiredate);

			filesList.add(heat);
			filesList.addAll(fuel);

			FileHandler zip = new FileHandler();
			zip.zipFile(this.getFileName(inputFile) + ".phoenix", filesList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFileName(String inputFile) {

		int extPlace = inputFile.split("\\.").length;
		String extStr = "." + inputFile.split("\\.")[extPlace - 1];

		String cryptFile = inputFile.replace(extStr, "");
		return cryptFile;
	}

	private List<File> makeFuelFile(Cipher cipher, String strInputFile)
			throws IOException, IllegalBlockSizeException, BadPaddingException {

		File inputFile = new File(strInputFile);

		FileInputStream inputStream = new FileInputStream(inputFile);
		
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);

		int byteSize = inputBytes.length;

		int endByte = initEndByte;

		byte[] outputBytes = null;
		
		List<File> outputFileList = new ArrayList<File>();
		
		int fileIterate = 1;
		this.fileNameList= "";
		
		for (int startByte = 0; startByte < byteSize; startByte = startByte + initEndByte) {
			
			String strOutputFile = fileIterate + ".fuel";
			File outputFile = new File(strOutputFile);
			FileOutputStream outputStream = new FileOutputStream(outputFile);

			byte[] slice = null;

			if (byteSize < endByte) {
				slice = Arrays.copyOfRange(inputBytes, startByte, byteSize);
			} else {
				slice = Arrays.copyOfRange(inputBytes, startByte, endByte);
			}
			endByte = endByte + initEndByte;
			outputBytes = cipher.doFinal(slice);
			outputStream.write(outputBytes);

			outputStream.close();
			outputFileList.add(outputFile);
			System.out.println(outputFile);
			
			fileNameList = fileNameList + "," + fileIterate;
			fileIterate ++;
		}

		inputStream.close();

		return outputFileList;

	}

	private File makeHeatFile(Cipher cipher, String inputFile, String expiredate)
			throws IllegalBlockSizeException, BadPaddingException, IOException, JSONException {

		String outputFile = getFileName(inputFile) + ".heat";
		JSONObject json = new JSONObject();
		String[] extension = inputFile.split("\\.");
		json.put("ext", extension[extension.length - 1]);
		json.put("xprdate", expiredate);
		json.put("fileNameList", this.fileNameList.subSequence(1, this.fileNameList.length()));
		byte[] outputBytes = cipher.doFinal(json.toString().getBytes("utf-8"));

		FileOutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(outputBytes);
		outputStream.close();

		return new File(outputFile);
	}

	private byte[] genSecretKey(String strInputFile) throws NoSuchAlgorithmException {

		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128); // The AES key size in number of bits
		byte[] key = generator.generateKey().getEncoded();

		try {
			int extPlace = strInputFile.split("\\.").length;
			String extStr = "." + strInputFile.split("\\.")[extPlace - 1];

			File outputFile = new File(strInputFile.replace(extStr, ".ash"));
			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(key);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return key;

	}

}
