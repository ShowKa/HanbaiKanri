package com.showka.system.io;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.showka.common.SimpleTestCase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CsvReaderTest extends SimpleTestCase {

	@Test
	public void test01_read() throws Exception {
		String path = "/data/system/FileReaderTest1.csv";
		File file = new ClassPathResource(path).getFile();
		String absoluteFilePath = file.getAbsolutePath();
		List<DTO> result = CsvReader.read(DTO.class, absoluteFilePath);
		assertEquals("ABC", result.get(1).getNamae());
	}

	@Test
	public void test02_read() throws Exception {
		String path = "/data/system/FileReaderTest1.csv";
		File file = new ClassPathResource(path).getFile();
		List<DTO> result = CsvReader.read(DTO.class, file);
		assertEquals("ABC", result.get(1).getNamae());
	}

	@Test
	public void test01_readOnlyReadable() throws Exception {
		String path = "/data/system/FileReaderTest2.csv";
		File file = new ClassPathResource(path).getFile();
		List<DTO> result = CsvReader.readOnlyReadeble(DTO.class, file);
		assertEquals(2, result.size());
		assertEquals("ABC", result.get(1).getNamae());
	}

	@Test
	public void test01_getError() throws Exception {
		String path = "/data/system/FileReaderTest2.csv";
		File file = new ClassPathResource(path).getFile();
		List<CsvError> actual = CsvReader.getError(DTO.class, file);
		assertEquals(1, actual.size());
	}

	@NoArgsConstructor
	@Getter
	@Setter
	static public class DTO {
		@JsonProperty("name")
		String namae;
		@JsonProperty("id")
		String id;
		@JsonProperty("number")
		Integer number;
	}

}
