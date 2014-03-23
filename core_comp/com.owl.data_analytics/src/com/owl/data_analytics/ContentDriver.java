package com.owl.data_analytics;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.owl.data_analytics.util.TRECReader;
import com.owl.data_analytics.util.TRECReader.TRECRecord;
import com.owl.model.Entity;

public class ContentDriver {

	private File dataDir;

	public ContentDriver(File inputDir, File indexDir) {
		dataDir = inputDir;
		ContentStoreBuilder.setIndexDir(indexDir);
	}

	public Entity createEntity(TRECRecord record) {
		return new Entity(record.name, record.address, record.category, record.review, record.rating);	
	}

	private Entity next(String fileName) {
		TRECReader trecReader = new TRECReader(fileName);
		if (trecReader.processTREC())
			return createEntity(trecReader.getCurrentRecord());
		return null;
	}

	public static void main(String[] args) {
		String indexDirPath = "";
		String inputDirPath = "";

		if(inputDirPath.trim().isEmpty() || indexDirPath.trim().isEmpty()) {
			System.err.println("Usage: ContentDriver inputDirPath indexDirPath");
			System.exit(1);
		}
		else {
			File inputDir = new File(inputDirPath);
			File indexDir = new File(inputDirPath);

			ContentDriver contentDriver = new ContentDriver(inputDir, indexDir);

			if (contentDriver.dataDir.exists() && contentDriver.dataDir.isDirectory()) {
				Iterator it = FileUtils.iterateFiles(contentDriver.dataDir, null, false);
				while(it.hasNext()){
					System.out.println(((File) it.next()).getName());
				}
			}
			else {
				System.err.println(contentDriver.dataDir.getAbsolutePath() + " is not a directory.");
			}
		}

	}
}
