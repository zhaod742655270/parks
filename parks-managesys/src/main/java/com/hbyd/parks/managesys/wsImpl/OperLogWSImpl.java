package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.util.ValHelper;
import com.hbyd.parks.domain.managesys.OperLog;
import com.hbyd.parks.dto.managesys.OperLogDTO;
import com.hbyd.parks.ws.managesys.OperLogWS;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OperLogWSImpl extends BaseWSImpl<OperLogDTO, OperLog> implements OperLogWS {
    @Resource
    private SessionFactory sessionFactory;

    @Override
    public OperLogDTO save(OperLogDTO dto) {
        OperLog target;
        String id = dto.getId();
        ValHelper.idNotExist(id);
            target = dozerMapper.map(dto, OperLog.class);
            sessionFactory.getCurrentSession().persist(target);//转为持久态

        Object r = sessionFactory.getCurrentSession().get(OperLog.class, target.getId());
        return dozerMapper.map(r, OperLogDTO.class);
    }
}
