package com.showka.web.u08;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.showka.domain.MatchedFBFurikomi;
import com.showka.service.crud.u08.i.NyukinKeshikomiCrudService;
import com.showka.service.search.u08.i.MatchedFBFurikomiSearchService;
import com.showka.value.TheDate;
import com.showka.web.ControllerBase;
import com.showka.web.ModelAndViewExtended;

@Controller
@RequestMapping("api/u08b005")
public class U08B005Controller extends ControllerBase {

	@Autowired
	private MatchedFBFurikomiSearchService searchService;

	@Autowired
	private NyukinKeshikomiCrudService keshikomiCrudService;

	/**
	 * FB振込による入金で売掛の消込を行う。
	 * 
	 * <pre>
	 * - 入力:伝送日付
	 * - データ更新方針:特記事項なし
	 * - トランザクション単位:一括
	 * </pre>
	 */
	@RequestMapping(value = "keshikomi", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> keshikomi(@ModelAttribute U08B005Form form, ModelAndViewExtended model) {
		// FB振込Tableから$マッチング済FB振込を検索
		TheDate transmissionDate = new TheDate(form.getDate());
		List<MatchedFBFurikomi> matchedFbFurikomiList = searchService.search(transmissionDate);
		// 消込テーブル登録
		matchedFbFurikomiList.forEach(keshikomiCrudService::save);
		// return
		form.setSuccessMessage("マッチング済FB振込消込登録処理成功");
		model.addForm(form);
		return ResponseEntity.ok(model);
	}
}