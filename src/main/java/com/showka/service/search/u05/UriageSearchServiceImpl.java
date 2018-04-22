package com.showka.service.search.u05;

import java.util.List;

import org.springframework.stereotype.Service;

import com.showka.domain.Uriage;
import com.showka.service.search.u05.i.UriageSearchService;

@Service
public class UriageSearchServiceImpl implements UriageSearchService {

	@Override
	public List<Uriage> search(UriageSearchCriteria criteria) {
		return null;
	}
}
