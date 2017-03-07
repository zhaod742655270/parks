package com.hbyd.parks.dao.supportsys;

import com.hbyd.parks.common.base.BaseDao;
import com.hbyd.parks.domain.supportsys.Card;

/**
 * 卡
 */
public interface CardDao extends BaseDao<Card, String> {
    /**通过卡号查询卡信息
     * @param cardNo 卡号
     * @return 卡信息
     */
    Card getByCardNo(String cardNo);
}
