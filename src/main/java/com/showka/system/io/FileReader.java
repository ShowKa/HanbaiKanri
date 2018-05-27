package com.showka.system.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.showka.system.exception.SystemException;

public class FileReader {

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
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();
		try {
			MappingIterator<T> readValues = mapper.readerFor(type).with(schema).readValues(file);
			return readValues.readAll();
		} catch (IOException e) {
			throw new SystemException("ファイル読み込み失敗", e);
		}
	}
}
