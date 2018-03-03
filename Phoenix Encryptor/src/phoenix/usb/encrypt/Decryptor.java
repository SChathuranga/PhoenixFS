package phoenix.usb.encrypt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Decryptor {

	public String extension = "";

	private final String ALGORITHM = "AES";
	private final String TRANSFORMATION = "AES/ECB/PKCS5PADDING";
	private byte[] secKey;

	private String xDate;

	private String fileNameList;

	public void loadSecretKey(String keyFilePath) throws IOException {
		RandomAccessFile f = new RandomAccessFile(keyFilePath, "r");
		this.secKey = new byte[(int) f.length()];
		f.readFully(secKey);
		f.close();
	}

	public void doDecrypt(String strInputFile) {
		try {
			Key secretKey = new SecretKeySpec(secKey, ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);

			FileHandler fh = new FileHandler();
			fh.unzipFile(strInputFile);

			String[] arrFilePath = strInputFile.split("\\\\");
			String fileName = arrFilePath[arrFilePath.length - 1].replace(".phoenix", "");

			this.resolveHeat(cipher, strInputFile, fileName);

			if (this.isXpired()) {
				this.bufferFuelRead(cipher, strInputFile, fileName);
			} else {
				System.err.println("Expired");
			}

			this.cleanPlace(strInputFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bufferFuelRead(Cipher cipher, String inputFile, String fileName)
			throws IllegalBlockSizeException, BadPaddingException, IOException, JSONException {

		String outputFile = getFileName(inputFile) + "." + this.extension;

		File temp = new File(new File(inputFile).getParentFile() + File.separator + "temp");

		String[] fileList = this.fileNameList.split(",");

		OutputStream out = new FileOutputStream(outputFile);

		for (int i = 0; i < fileList.length; i++) {
			
			File encryptFile = new File(temp.getAbsoluteFile() + File.separator + fileList[i]+ ".fuel");
			System.out.println("Decrypting File - " + fileList[i]);

			byte[] buf = new byte[(int) encryptFile.length()];
			InputStream in = new FileInputStream(encryptFile);
			int b = 0;
			while ((b = in.read(buf)) >= 0) {
				out.write(cipher.doFinal(buf, 0, b));
				out.flush();
			}
			in.close();
		}
		out.close();
		/*
		 * FileInputStream inputStream = new FileInputStream(encryptFile);
		 * byte[] inputBytes = new byte[(int) encryptFile.length()];
		 * inputStream.read(inputBytes);
		 * 
		 * byte[] tempOutputBytes = cipher.doFinal(inputBytes);
		 * 
		 * newOutbyteSteam.write(tempOutputBytes);
		 * 
		 * inputBytes = null; tempOutputBytes = null; inputStream.close(); }
		 * 
		 * byte concateByteList[] = newOutbyteSteam.toByteArray();
		 * 
		 * FileOutputStream outputStream = new FileOutputStream(outputFile);
		 * outputStream.write(concateByteList); outputStream.flush();
		 * outputStream.close();
		 */
	}

	@SuppressWarnings({ "resource", "unused" })
	private void bufferWrite(String fileName, String fileContent) throws IOException {
		File file = new File(fileName);

		BufferedWriter bw = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
		bw.write(fileContent);
		System.out.println("File written Successfully");
	}

	private void cleanPlace(String strInputFile) {
		String fileName = getFileName(strInputFile);

		File zipFile = new File(fileName + ".phoenix");
		File ashFile = new File(fileName + ".ash");
		File tempFile = new File(zipFile.getParent() + File.separator + "temp");
		try {
			zipFile.delete();
			FileUtils.forceDelete(tempFile);
			FileDeleteStrategy.FORCE.delete(ashFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isXpired() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date xpireDate = sdf.parse(xDate);

		Date todaDate = new Date();

		if (xpireDate.compareTo(todaDate) < 0) {
			return false;
		}

		return true;
	}

	private void resolveHeat(Cipher cipher, String inputFile, String fileName)
			throws IOException, IllegalBlockSizeException, BadPaddingException, JSONException {
		File heatFile = new File(
				new File(inputFile).getParentFile() + File.separator + "temp" + File.separator + fileName + ".heat");

		FileInputStream inputStream = new FileInputStream(heatFile);
		byte[] inputBytes = new byte[(int) heatFile.length()];
		inputStream.read(inputBytes);

		byte[] outputBytes = cipher.doFinal(inputBytes);

		JSONObject json = new JSONObject(new String(outputBytes, "UTF-8"));
		this.extension = json.get("ext").toString();
		this.xDate = json.get("xprdate").toString();
		this.fileNameList = json.get("fileNameList").toString();

		inputStream.close();
	}

	private String getFileName(String inputFile) {
		int extPlace = inputFile.split("\\.").length;
		String extStr = "." + inputFile.split("\\.")[extPlace - 1];

		String cryptFile = inputFile.replace(extStr, "");
		return cryptFile;
	}

}
