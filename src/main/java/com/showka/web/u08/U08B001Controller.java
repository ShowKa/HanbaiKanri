package com.showka.web.u08;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.entity.TFirmBankFurikomi;
import com.showka.repository.i.TFirmBankFurikomiRepository;
import com.showka.system.io.FileManager;
import com.showka.system.io.CsvReader;
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
	public ResponseEntity<?> loadFB(@ModelAttribute U08B001Form form, ModelAndViewExtended model) {
		// read csv
		String filePath = form.getFilePath();
		File file = fileManager.get(filePath);
		List<U08B001Csv> csv = CsvReader.read(U08B001Csv.class, file);
		// get entities
		List<TFirmBankFurikomi> entities = csv.parallelStream().map(c -> {
			TFirmBankFurikomi e = c.toFirmBankFurikomiEntity();
			String id = UUID.randomUUID().toString();
			e.setRecordId(id);
			return e;
		}).collect(Collectors.toList());
		// save
		entities.forEach(tFirmBankFurikomiRepository::save);
		// return
		form.setSuccessMessage("FBファイル取込成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
