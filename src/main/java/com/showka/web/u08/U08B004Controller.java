package com.showka.web.u08;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.service.persistence.u08.i.NyukinFBFurikomiPersistence;
import com.showka.service.query.u08.i.MatchedFBFurikomiQuery;
import com.showka.value.TheDate;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b004")
public class U08B004Controller extends ControllerBase {

	@Autowired
	private MatchedFBFurikomiQuery query;

	@Autowired
	private NyukinFBFurikomiPersistence persistence;

	/**
	 * マッチング済FB振込を入金テーブルに登録する.
	 * 
	 * <pre>
	 * 入力:伝送日付
	 * データ更新方針:特記事項なし
	 * トランザクション単位:一括
	 * </pre>
	 */
	@RequestMapping(value = "saveNyukin", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> saveNyukin(@ModelAttribute U08B004Form form, ModelAndViewExtended model) {
		// FB振込Tableから$マッチング済FB振込を検索
		TheDate transmissionDate = new TheDate(form.getDate());
		List<MatchedFBFurikomi> matchedFbFurikomi = query.get(transmissionDate);
		// $マッチング済FB振込を入金登録
		matchedFbFurikomi.forEach(persistence::save);
		// return
		form.setSuccessMessage("マッチング済FB振込入金登録処理成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}
