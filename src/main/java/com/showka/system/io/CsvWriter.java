package com.showka.system.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.showka.system.exception.SystemException;

public class CsvWriter {

	/**
	 * CSV書込
	 * 
	 * <pre>
	 * データリスト0件の場合は何もせず処理終了。
	 * encode = UTF8
	 * </pre>
	 * 
	 * @param dataList
	 *            データリスト
	 * @param file
	 *            書き込み先ファイル
	 */
	public static <T> void write(List<T> dataList, File file) {
		if (dataList.size() == 0) {
			return;
		}
		try (FileOutputStream tempFileOutputStream = new FileOutputStream(file);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
				OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");) {
			CsvMapper mapper = new CsvMapper();
			Class<? extends Object> type = dataList.get(0).getClass();
			CsvSchema schema = mapper.schemaFor(type).withHeader();
			ObjectWriter writer = mapper.writer(schema);
			writer.writeValue(writerOutputStream, dataList);
		} catch (FileNotFoundException e) {
			throw new SystemException("存在しないファイルが指定されました。", e);
		} catch (UnsupportedEncodingException e) {
			throw new SystemException("サポート対象外エンコードが指定されました。", e);
		} catch (IOException e) {
			throw new SystemException("書き込み失敗。", e);
		}
	}
}
