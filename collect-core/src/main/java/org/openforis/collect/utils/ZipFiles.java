package org.openforis.collect.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author S. Ricci
 *
 */
public class ZipFiles {

	public static void writeFile(ZipOutputStream zipOutputStream, File file, String zipEntryName) {
		try {
			zipOutputStream.putNextEntry(new ZipEntry(zipEntryName));
			byte[] metadataContent = FileUtils.readFileToByteArray(file);
			IOUtils.write(metadataContent, zipOutputStream);
			zipOutputStream.closeEntry();
		} catch (IOException e) {
			throw new RuntimeException("Error writing data to zip file", e);
		}
	}
	
	public static void extract(File file, File destinationFolder) throws IOException {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
			extract(zipFile, destinationFolder);
		} finally {
			IOUtils.closeQuietly(zipFile);
		}
	}
	
	public static void extract(ZipFile zipFile, File destinationFolder) throws IOException {
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = entries.nextElement();
			if (! zipEntry.isDirectory()) {
				String entryName = zipEntry.getName();
				File folder = getOrCreateEntryFolder(destinationFolder, entryName);
				String fileName = Files.extractFileName(entryName);
				File newFile = new File(folder, fileName);
				newFile.createNewFile();
				InputStream is = zipFile.getInputStream(zipEntry);
				FileUtils.copyInputStreamToFile(is, newFile);
			}
		}
	}
	
	public File extractEntry(File parentDestinationFolder, String entryName) {
		return extractEntry(parentDestinationFolder, entryName, true);
	}
	
	public static File extractEntry(File parentDestinationFolder, String entryName, boolean required) {
		File folder = getOrCreateEntryFolder(parentDestinationFolder, entryName);
		String fileName = Files.extractFileName(entryName);
		File result = new File(folder, fileName);
		return result.exists() ? result: null;
	}

	public static File getOrCreateEntryFolder(File parentDestinationFolder, String entryName) {
		String path = FilenameUtils.getPathNoEndSeparator(entryName);
		return Files.getOrCreateFolder(parentDestinationFolder, path);
	}

	public static List<String> getEntryNames(ZipFile file) {
		List<String> result = new ArrayList<String>();
		Enumeration<? extends ZipEntry> zipEntries = file.entries();
		while ( zipEntries.hasMoreElements() ) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			result.add(name);
		}
		return result;
	}

	
}
