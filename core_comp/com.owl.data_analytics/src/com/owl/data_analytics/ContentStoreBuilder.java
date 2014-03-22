package com.owl.data_analytics;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.FieldInfo.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;

import com.owl.model.Entity;

public class ContentStoreBuilder {
	
	private static String dataDir;
	private static String indexDir;
	
	private static IndexWriter indexWriter;
	

	private static FieldType getDocFreqPosFieldType() {
		FieldType f = new FieldType();
		f.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
		f.setTokenized(true);
		f.setIndexed(true);
		f.setStored(false);
		return f;
	}
	
	
	public static void addToIndex(Entity entity) throws IOException {
		
		Document doc = new Document();
		doc.add(new StoredField(ContentStoreConstants.FIELD_NAME, entity.getName()));
		doc.add(new StoredField(ContentStoreConstants.FIELD_ADDRESS, entity.getAddress()));
		doc.add(new StoredField(ContentStoreConstants.FIELD_CATEGORY, entity.getCategory()));
		doc.add(new StoredField(ContentStoreConstants.FIELD_REVIEW, entity.getReview()));
		doc.add(new StoredField(ContentStoreConstants.FIELD_RATING, entity.getRating()));
		
		
		/* Adding indexedName field */
		Analyzer nameAnalyzer = new KeywordAnalyzer();
		TokenStream nameTokenStream = nameAnalyzer.tokenStream(ContentStoreConstants.INDEXED_FIELD_NAME, new StringReader(entity.getName()));
		CharTermAttribute charTermAttribute = nameTokenStream.addAttribute(CharTermAttribute.class);
		nameTokenStream.reset();
		doc.add(new Field(ContentStoreConstants.INDEXED_FIELD_NAME, nameTokenStream, getDocFreqPosFieldType()));
		nameTokenStream.close();
		nameAnalyzer.close();
		
		/* Adding indexedAddress field */
		Analyzer addressAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_47);
		TokenStream addressTokenStream = addressAnalyzer.tokenStream(ContentStoreConstants.INDEXED_FIELD_ADDRESS, new StringReader(entity.getAddress()));
		charTermAttribute = addressTokenStream.addAttribute(CharTermAttribute.class);
		addressTokenStream.reset();
		doc.add(new Field(ContentStoreConstants.INDEXED_FIELD_ADDRESS, addressTokenStream, getDocFreqPosFieldType()));
		addressTokenStream.close();
		addressAnalyzer.close();
		
		/* Adding indexedCategory field */
		String[] categories = entity.getCategory().split(ContentStoreConstants.STANDARD_SEPARATOR);
		if (categories != null && !categories[0].isEmpty()) {
			Analyzer categoryAnalyzer = new KeywordAnalyzer();
			for (String category: categories) {
				TokenStream categoryTokenStream = categoryAnalyzer.tokenStream(ContentStoreConstants.INDEXED_FIELD_CATEGORY, new StringReader(category.trim()));
				charTermAttribute = categoryTokenStream.addAttribute(CharTermAttribute.class);
				categoryTokenStream.reset();
				doc.add(new Field(ContentStoreConstants.INDEXED_FIELD_CATEGORY, categoryTokenStream, getDocFreqPosFieldType()));
				categoryTokenStream.close();
			}
			categoryAnalyzer.close();
		}
		
		/* Adding indexedRating field */
		doc.add(new FloatField(ContentStoreConstants.INDEXED_FIELD_RATING, entity.getRating(), getDocFreqPosFieldType()));
		
		/* Adding indexedReview field */
		String[] reviews = entity.getCategory().split(ContentStoreConstants.SPECIAL_SEPARATOR);
		if (reviews != null && !reviews[0].isEmpty()) {
			Analyzer reviewAnalyzer = new StandardAnalyzer(Version.LUCENE_47);
			for (String review: reviews) {
				TokenStream reviewTokenStream = reviewAnalyzer.tokenStream(ContentStoreConstants.INDEXED_FIELD_REVIEW, new StringReader(review.trim()));
				charTermAttribute = reviewTokenStream.addAttribute(CharTermAttribute.class);
				reviewTokenStream.reset();
				doc.add(new Field(ContentStoreConstants.INDEXED_FIELD_CATEGORY, reviewTokenStream, getDocFreqPosFieldType()));
				reviewTokenStream.close();
			}
			reviewAnalyzer.close();
		}
		
		
		indexWriter.addDocument(doc);
	}


	public static String getDataDir() {
		return dataDir;
	}


	public static void setDataDir(String dataDir) {
		ContentStoreBuilder.dataDir = dataDir;
	}


	public static String getIndexDir() {
		return indexDir;
	}


	public static void setIndexDir(String indexDir) {
		ContentStoreBuilder.indexDir = indexDir;
	}


	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}


	public static void setIndexWriter(IndexWriter indexWriter) {
		ContentStoreBuilder.indexWriter = indexWriter;
	}
	
	
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Usage: ContentStoreBuilder dataDir indexDir");
		}
		
		
	}
}
