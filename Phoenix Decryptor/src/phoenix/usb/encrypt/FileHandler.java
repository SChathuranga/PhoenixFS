package phoenix.usb.encrypt;

import java.io.File;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class FileHandler {

	String password = "Hephaestus was the god of fire, metalworking, stone masonry, forges and the art of sculpture";
	String FileName;

	public void zipFile(String zipfile, ArrayList<File> filesList) {

		try {
			ZipFile zipFile = new ZipFile(zipfile);
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

			parameters.setPassword(password);
			zipFile.addFiles(filesList, parameters);
			this.deleteTempFiles(filesList);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	private void deleteTempFiles(ArrayList<File> filesList) {
		for (File files : filesList) {
			files.delete();
		}
	}

	public void unzipFile(String file) {

		try {
			ZipFile zipFile = new ZipFile(file);
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			System.out.println("Extract Content Files....");
			zipFile.extractAll(file.replace(zipFile.getFile().getName(),"")+"temp");
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	public File generateSourceFile(String srcPath)
	{
		String srcFileExtension = FilenameUtils.getExtension(srcPath);
		String encrySrc = srcPath.replaceAll(srcFileExtension, "phoenix");
		File srcFile = new File(encrySrc);
		FileName = srcFile.getName();
		System.out.println(srcFile.toString());
		return srcFile;
	}
	
	public File generateDestFile(String destDirectory)
	{ 
		String destinationFilePath = destDirectory;
		File destFile = new File(destinationFilePath);
		System.out.println(destFile.toString());
		return destFile;
	}

}
