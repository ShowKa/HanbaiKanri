package com.showka.web.u08;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showka.web.ControllerBase;

@RestController
@RequestMapping("api/u08b001")
public class U08B001Controller extends ControllerBase {

	// how to request
	// $user = "user01"
	// $pass = "pass"
	// $secp= ConvertTo-SecureString $PASS -AsPlainText -Force
	// $cred = New-Object System.Management.Automation.PSCredential($user,$secp)
	// $uri = "http://localhost:8080/api/u08b001?filePath=abc/def.txt"
	// Invoke-RestMethod -Uri $uri -Credential $cred

	// 通常「@PathVariable」を使う
	// しかしパラメータにスラッシュ（FileSeparator）を含むとうまく動かないので、@RequestParamにしてる。
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> loadNyukin(@RequestParam("filePath") String filePath) {
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("param", filePath);
		m.put("result", "success!");
		return ResponseEntity.ok(m);
	}
}
