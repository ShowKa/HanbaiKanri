package com.showka.system.io;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.showka.system.exception.SystemException;

@Component
public class FileManager {

	@Value("${hanbaikanri.file.root}")
	private String root;

	public File get(String pathFromRoot) {
		String _path = pathFromRoot.startsWith("/") ? pathFromRoot : "/" + pathFromRoot;
		if (root == null || root.length() == 0) {
			try {
				return new ClassPathResource(_path).getFile();
			} catch (IOException e) {
				throw new SystemException("ファイル読み込み失敗", e);
			}
		} else {
			root = root.replaceAll("/$", "");
			String absolutePath = root + _path;
			File file = new File(absolutePath);
			if (!file.exists()) {
				throw new SystemException("ファイルが存在しません。 : " + absolutePath);
			}
			return file;
		}
	}
}
