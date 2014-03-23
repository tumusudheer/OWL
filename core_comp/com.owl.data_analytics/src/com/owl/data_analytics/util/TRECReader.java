package com.owl.data_analytics.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TRECReader {
	
	public class TRECRecord{
		TRECRecord(String dN, String n, String a, String c, float r, String rV){
			docNo = dN;
			name = n;
			address = a;
			category = c;
			rating = r;
			review = rV;
		}

		public String docNo;
		public String name;
		public String address;
		public String category;
		public float rating;
		public String review;

	}
	
	public TRECReader(String path){
		try {
			reader = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TRECReader(BufferedReader br) {
		reader = br;
	}
	
	public boolean processTREC(){
		String line;
		currentRecord = null;
		try {
			line = reader.readLine();
			String docNo = "";
			String name = "";
			String address = "";
			String category = "";
			float rating = 0.0f;
			
			StringBuffer reviewBuffer = new StringBuffer();
			String status = "";
			while(line != null){
				if(line.startsWith("<DOC>")){
				}
				else if(line.startsWith("<DOCNO>")){
					int beginIndex = "<DOCNO>".length();
					int endIndex = line.indexOf("</DOCNO>");
					docNo = line.substring(beginIndex, endIndex);
				}
				else if(line.startsWith("<NAME>")){
					int beginIndex = "<NAME>".length();
					int endIndex = line.indexOf("</NAME>");
					name = line.substring(beginIndex, endIndex);
				}
				else if(line.startsWith("<ADDRESS>")){
					int beginIndex = "<ADDRESS>".length();
					int endIndex = line.indexOf("</ADDRESS>");
					address = line.substring(beginIndex, endIndex);
				}
				else if(line.startsWith("<CATEGORY>")){
					int beginIndex = "<CATEGORY>".length();
					int endIndex = line.indexOf("</CATEGORY>");
					category = line.substring(beginIndex, endIndex);
				}
				else if(line.startsWith("<RATING>")){
					int beginIndex = "<RATING>".length();
					int endIndex = line.indexOf("</RATING>");
					rating = Float.parseFloat(line.substring(beginIndex, endIndex));
				}
				else if(line.startsWith("<REVIEW>")){
					status = "REVIEW";
				}
				else if(line.startsWith("</DOC>")){
					currentRecord = new TRECRecord(docNo, name, address, category, rating, reviewBuffer.toString());
					break;
				}
				else{
					if(status.equals("REVIEW")){
						reviewBuffer.append(line + "\n");
					}
				}
				line = reader.readLine();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentRecord != null;
	}
	
	
	void close(){
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public TRECRecord getCurrentRecord(){
		return currentRecord;
	}
	
	BufferedReader reader ;
	TRECRecord currentRecord;

	
	public static void main(String[] args){
		TRECReader reader = new TRECReader("/home/czhai/jinghe/local/data/wt2g/Wt11/B09");
		
		while(reader.processTREC()){
		TRECRecord record = reader.getCurrentRecord();
		System.out.println("===========================================");
		}
		
	}
}
