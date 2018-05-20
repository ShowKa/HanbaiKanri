package com.showka.web.u08;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.showka.web.ControllerBase;

@RestController
@RequestMapping("api/u08b001")
public class U08B001Controller extends ControllerBase {

	@RequestMapping(method = RequestMethod.GET, value = "{filePath}")
	public ResponseEntity<?> loadNyukin(@PathVariable("filePath") String filePath) {
		return ResponseEntity.ok(new Object());
	}
}
