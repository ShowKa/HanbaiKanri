package com.showka.web.u08;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.entity.TFirmBankFurikomi;
import com.showka.repository.i.TFirmBankFurikomiRepository;
import com.showka.system.io.CsvError;
import com.showka.system.io.CsvReader;
import com.showka.system.io.CsvWriter;
import com.showka.system.io.FileManager;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

// how to request
// $user = "user01"
// $pass = "pass"
// $secp= ConvertTo-SecureString $PASS -AsPlainText -Force
// $cred = New-Object System.Management.Automation.PSCredential($user,$secp)
// $uri = "http://localhost:8080/api/u08b001?filePath=abc/def.txt"
// Invoke-RestMethod -Uri $uri -Credential $cred

@Controller
@RequestMapping("api/u08b001")
public class U08B001Controller extends ControllerBase {

	@Autowired
	private FileManager fileManager;

	@Autowired
	private TFirmBankFurikomiRepository tFirmBankFurikomiRepository;

	/**
	 * FirmBankデータ取込.
	 * 
	 * <pre>
	 * ファイル：1行でも型不整合 => エラー
	 * データ：同一キーのデータが登録済 => エラー
	 * </pre>
	 * 
	 * @param filePath
	 *            FBデータパス
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> loadFB(@ModelAttribute U08B001Form form, ModelAndViewExtended model) {
		// FB振込ファイル 取得
		String filePath = form.getFilePath();
		File file = fileManager.get(filePath);
		// 読込不可能データリストを読込
		List<CsvError> unreadableList = CsvReader.getError(U08B001Csv.class, file);
		// 読込不可能データリストを FB振込読込不可能データリスト Fileとして出力
		File errorFile = fileManager.create("/output/u08b001_unreadable.csv");
		CsvWriter.write(unreadableList, errorFile);
		// 読込可能行を FB振込リスト として読込
		List<U08B001Csv> fbFurikomiList = CsvReader.readOnlyReadable(U08B001Csv.class, file);
		// FB振込リスト をFB振込Table に登録
		List<TFirmBankFurikomi> entities = fbFurikomiList.parallelStream()
				.map(U08B001Csv::toFirmBankFurikomiEntity)
				.collect(Collectors.toList());
		entities.forEach(tFirmBankFurikomiRepository::save);
		// return
		form.setSuccessMessage("FBファイル取込成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
