package phoenix.usb.encrypt;

import java.io.File;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import net.lingala.zip4j.core.ZipFile;

public class SampleMain {

	public String decryptedOriginalFile;
	
	public Boolean fileEncrypt(String filePath, String expireDate) 
	{
		String filepath = filePath;
		String expiredate = expireDate; 
		
		
		Encryptor encry;
		try {
			encry = new Encryptor(filepath);
			File ff = new File(filepath);
			encry.doEncrypt(Cipher.ENCRYPT_MODE, ff.getAbsolutePath(), expiredate);
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public Boolean fileDeCrypt(String ashFilePath, String phoenixFilePath) 
	{
		String ashFile = ashFilePath;
		String phenixFilepath = phoenixFilePath;
		String fileName = FilenameUtils.getName(phoenixFilePath).split("\\.")[0]; 
		String filePath = FilenameUtils.getPath(phoenixFilePath);
		
		File ff = new File(phenixFilepath);
		Decryptor decrypt = new Decryptor();
		try {
			decrypt.loadSecretKey(ashFile);
			decrypt.doDecrypt(ff.getAbsolutePath());
			
			File decryFile = new File("/"+filePath + fileName + "." +decrypt.extension);
			decryptedOriginalFile = decryFile.toString();
			
			String tempDirectory = System.getProperty("java.io.tmpdir");
			File outputDir = new File(tempDirectory + "/phoenix");
			String outputDirectory = outputDir.toString();
			
			FileUtils.moveFileToDirectory(decryFile, outputDir, true);
			
			//if its a zip file decompress it
			if(decrypt.extension.equals("zip") || decrypt.extension.equals("rar"))
			{
				String zipFilePath = outputDirectory + "/" + fileName + "." + decrypt.extension;
				ZipFile zipFile = new ZipFile(zipFilePath);
				zipFile.extractAll(outputDirectory);
				File tmpZipFile = new File(zipFilePath);
				tmpZipFile.delete();
			}
			
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
