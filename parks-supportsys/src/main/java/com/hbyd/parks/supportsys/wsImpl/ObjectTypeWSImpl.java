package com.hbyd.parks.supportsys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.dao.supportsys.ObjectTypeDao;
import com.hbyd.parks.dao.supportsys.PredefObjectDao;
import com.hbyd.parks.domain.supportsys.ObjectType;
import com.hbyd.parks.dto.supportsys.ObjectTypeDTO;
import com.hbyd.parks.ws.supportsys.ObjectTypeWS;

import javax.annotation.Resource;

import static com.hbyd.parks.common.util.ValHelper.idNotExist;
import static com.hbyd.parks.common.util.ValHelper.notNull;

/**
 * 对象类型服务的实现
 */
public class ObjectTypeWSImpl extends BaseWSImpl<ObjectTypeDTO, ObjectType> implements ObjectTypeWS {

    @Resource
    private ObjectTypeDao objectTypeDao;

    @Resource
    private PredefObjectDao predefObjectDao;

    @Override
    public void update(ObjectTypeDTO dto) {
        String id = dto.getId();
        notNull(id, "更新操作接收的 ID 不能为 NULL");

//      提取关联属性
        String objectFK = dto.getObjectFK();
        dto.setObjectFK(null);
        dto.setObjectName(null);

        ObjectType target = objectTypeDao.getById(id);

//      填充普通属性
        dozerMapper.map(dto, target);

//      填充关联属性
        target.setPreObj(predefObjectDao.getById(objectFK));

        objectTypeDao.update(target);
    }

    @Override
    public ObjectTypeDTO save(ObjectTypeDTO dto) {
        idNotExist(dto.getId());

//      提取关联属性
        String objectFK = dto.getObjectFK();
        dto.setObjectFK(null);
        dto.setObjectName(null);

//      填充普通属性
        ObjectType target = dozerMapper.map(dto, ObjectType.class);

//      填充关联属性
        target.setPreObj(predefObjectDao.getById(objectFK));

        ObjectType saved = objectTypeDao.save(target);

        return dozerMapper.map(saved, dClass);
    }
}
