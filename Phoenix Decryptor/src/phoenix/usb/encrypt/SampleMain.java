package phoenix.usb.encrypt;

import java.io.File;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import net.lingala.zip4j.core.ZipFile;

public class SampleMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SampleMain main = new SampleMain();
		
		//To Encrypt the Document 
		//main.fileEncrypt(); //- PLEASE RUN THIS LINE
		                    //   OR
		//To Decrypt the Document
		//main.fileDeCrypt(); //- RUN THIS LINE AT AN ONCE

	}

	public Boolean fileEncrypt(String filePath, String expireDate) {
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

	public Boolean fileDeCrypt(String ashFilePath, String phoenixFilePath) {

		String ashFile = ashFilePath;
		String phenixFilepath = phoenixFilePath;

		File ff = new File(phenixFilepath);
		Decryptor decrypt = new Decryptor();
		try {
			decrypt.loadSecretKey(ashFile);
			decrypt.doDecrypt(ff.getAbsolutePath());
			
			String originalExtension = "." + decrypt.ex;
			String fileName = FilenameUtils.getName(phoenixFilePath).split("\\.")[0]; 
			String decryptedFilePath = phenixFilepath.replace(".phoenix", originalExtension);
			
			System.out.println("decrypted file path: " + decryptedFilePath);
			
			File decryptedFile = new File(decryptedFilePath);
			
			//find system temp file and set phoenix directory
			String tempDirectory = System.getProperty("java.io.tmpdir");
			File outputDir = new File(tempDirectory + "/phoenix");
			String outputDirectory = outputDir.toString();
			
			FileUtils.moveFileToDirectory(decryptedFile, outputDir, true);
			
			//if its a zip file decompress it
			if(decrypt.ex.equals("zip") || decrypt.ex.equals("rar"))
			{
				String zipFilePath = outputDirectory + "/" + fileName + "." + decrypt.ex;
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
