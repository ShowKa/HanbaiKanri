package com.showka.service.specification.u08.i;

import com.showka.domain.MatchedFBFurikomi;
import com.showka.domain.NyukinKeshikomi;

public interface NyukinKeshikomiBuildService {
	public NyukinKeshikomi build(MatchedFBFurikomi matchedFBFurikomi);
}
