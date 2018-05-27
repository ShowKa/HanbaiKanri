package com.showka.system.io;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.showka.common.SimpleTestCase;
import com.showka.system.io.FileReader;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FileReaderTest extends SimpleTestCase {

	@Test
	public void test01_read() throws Exception {
		String path = "/data/system/FileReaderTest.csv";
		File file = new ClassPathResource(path).getFile();
		String absoluteFilePath = file.getAbsolutePath();
		List<DTO> result = FileReader.read(DTO.class, absoluteFilePath);
		assertEquals("ABC", result.get(1).getNamae());
	}

	@Test
	public void test02_read() throws Exception {
		String path = "/data/system/FileReaderTest.csv";
		File file = new ClassPathResource(path).getFile();
		List<DTO> result = FileReader.read(DTO.class, file);
		assertEquals("ABC", result.get(1).getNamae());
	}

	@NoArgsConstructor
	@Getter
	@Setter
	static public class DTO {
		@JsonProperty("name")
		String namae;
		@JsonProperty("id")
		String id;
	}
}
