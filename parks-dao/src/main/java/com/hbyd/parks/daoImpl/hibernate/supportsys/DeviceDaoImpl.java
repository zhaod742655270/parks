package com.hbyd.parks.daoImpl.hibernate.supportsys;

import com.hbyd.parks.common.base.BaseDaoImpl;
import com.hbyd.parks.dao.supportsys.DeviceDao;
import com.hbyd.parks.domain.supportsys.Device;
import org.springframework.stereotype.Repository;

/**
 * 设备
 */
@Repository
public class DeviceDaoImpl extends BaseDaoImpl<Device, String> implements DeviceDao {
}
