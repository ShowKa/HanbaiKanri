package com.showka.system.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.showka.system.exception.SystemException;

public class CsvReader {

	/**
	 * CSVを指定した型にパース.
	 * 
	 * @param type
	 *            型
	 * @param filePath
	 *            ファイルパス
	 * @return 型で指定したオブジェクトのリスト
	 * @throws IOException
	 */
	public static <T> List<T> read(Class<T> type, String filePath) {
		File file = new File(filePath);
		return read(type, file);
	}

	/**
	 * CSVを指定した型にパース.
	 * 
	 * @param type
	 *            型
	 * @param file
	 *            CSVファイル
	 * @return 型で指定したオブジェクトのリスト
	 * @throws IOException
	 */
	public static <T> List<T> read(Class<T> type, File file) {
		MappingIterator<T> mi = getMappingIterator(type, file);
		try {
			return mi.readAll();
		} catch (IOException e) {
			throw new SystemException("全データ同時読み込みに失敗しました。", e);
		}
	}

	/**
	 * CSVを指定した型にパース.
	 * 
	 * <pre>
	 * 読み込み可能なもののみ読み込む。エラーデータは無視。
	 * </pre>
	 * 
	 * @param type
	 *            型
	 * @param file
	 *            CSVファイル
	 * @throws IOException
	 */
	public static <T> List<T> readOnlyReadeble(Class<T> type, File file) {
		MappingIterator<T> readValues = getMappingIterator(type, file);
		List<T> result = new ArrayList<T>();
		while (readValues.hasNext()) {
			T value;
			try {
				value = readValues.nextValue();
			} catch (IOException e) {
				continue;
			}
			result.add(value);
		}
		return result;
	}

	/**
	 * 指定した型へのキャストエラーを取得する.
	 * 
	 * @param type
	 *            型
	 * @param file
	 *            CSVファイル
	 * @throws IOException
	 */
	public static <T> List<CsvError> getError(Class<T> type, File file) {
		MappingIterator<T> readValues = getMappingIterator(type, file);
		List<CsvError> errorList = new ArrayList<CsvError>();
		while (readValues.hasNext()) {
			try {
				readValues.nextValue();
			} catch (IOException e) {
				JsonLocation current = readValues.getCurrentLocation();
				int lineNumber = current.getLineNr();
				CsvError error = new CsvError();
				error.setLineNumber(lineNumber);
				error.setCause(e);
				errorList.add(error);
				continue;
			}
		}
		return errorList;
	}

	private static <T> MappingIterator<T> getMappingIterator(Class<T> type, File file) {
		ObjectReader reader = getReader(type);
		try {
			return reader.readValues(file);
		} catch (IOException e) {
			throw new SystemException("ファイル読み込み失敗", e);
		}
	}

	private static <T> ObjectReader getReader(Class<T> type) {
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		return mapper.readerFor(type).with(schema);
	}
}
