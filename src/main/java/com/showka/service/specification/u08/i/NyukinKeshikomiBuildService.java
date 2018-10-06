package com.showka.service.specification.u08.i;

import com.showka.domain.u08.MatchedFBFurikomi;
import com.showka.domain.u08.NyukinKeshikomi;

public interface NyukinKeshikomiBuildService {
	public NyukinKeshikomi build(MatchedFBFurikomi matchedFBFurikomi);
}
