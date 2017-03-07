package com.hbyd.parks.attendancesys.wsImpl;

import com.hbyd.parks.common.base.BaseWSImpl;
import com.hbyd.parks.common.enums.ShiftType;
import com.hbyd.parks.dao.attendancesys.RegularShiftDao;
import com.hbyd.parks.dao.attendancesys.ShiftBindingDao;
import com.hbyd.parks.dao.attendancesys.ShiftDao;
import com.hbyd.parks.domain.attendancesys.RegularShift;
import com.hbyd.parks.domain.attendancesys.Shift;
import com.hbyd.parks.domain.attendancesys.ShiftBinding;
import com.hbyd.parks.dto.attendancesys.RegularShiftDTO;
import com.hbyd.parks.dto.attendancesys.ShiftBindingDTO;
import com.hbyd.parks.ws.attendancesys.RegularShiftWS;
import org.dozer.DozerBeanMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.hbyd.parks.common.util.ValHelper.*;

/**
 * 规律班次服务
 */
public class RegularShiftWSImpl extends BaseWSImpl<RegularShiftDTO, RegularShift> implements RegularShiftWS {

    @Resource
    private RegularShiftDao regularShiftDao;

    @Resource
    private ShiftBindingDao shiftBindingDao;

    @Resource
    private ShiftDao shiftDao;

    @Resource
    private DozerBeanMapper dozerMapper;


    @Override
    public void update(RegularShiftDTO dto) {
        String id = dto.getId();
        notNull(id, "更新目标没有ID, 无法更新");

        RegularShift old = regularShiftDao.getById(id);
        List<ShiftBinding> bindings = old.getBindings();

//      1. 更新普通属性
        List<ShiftBindingDTO> dtoBinds = dto.getBindings();
        dto.setBindings(null);//防止拷贝
        dozerMapper.map(dto, old);

//      2. 删除之前的班次绑定
        if(bindings != null && bindings.size()>0){
            for (ShiftBinding binding : bindings) {
                shiftBindingDao.delete(binding);
            }
        }

//      3. 添加新的班次绑定
        List<ShiftBinding> binds = new ArrayList<>(dtoBinds.size());

        if(dtoBinds != null && dtoBinds.size() > 0){
            for (ShiftBindingDTO sdto : dtoBinds) {
                ShiftBinding b = new ShiftBinding();

//              普通属性
                b.setIdx(sdto.getIdx());
                b.setDayToBound(sdto.getDayToBound());

//              关联属性
                b.setShift(getShift(sdto));
                b.setRegularShift(old);

                shiftBindingDao.save(b);
                binds.add(b);
            }
        }

        old.setBindings(binds);
        regularShiftDao.update(old);
    }

    @Override
    public RegularShiftDTO save(RegularShiftDTO dto) {
        String id = dto.getId();
        idNotExist(id);

        List<ShiftBindingDTO> dtoBinds = dto.getBindings();
        dto.setBindings(null);//防止拷贝
//      1. 普通属性
        RegularShift rs = dozerMapper.map(dto, RegularShift.class);
        regularShiftDao.save(rs);

//      2. 关联属性
        List<ShiftBinding> binds = new ArrayList<>(dtoBinds.size());

        if(dtoBinds != null && dtoBinds.size() > 0){
            for (ShiftBindingDTO sbDTO : dtoBinds) {
                ShiftBinding b = new ShiftBinding();

//              普通属性
                b.setIdx(sbDTO.getIdx());
                b.setDayToBound(sbDTO.getDayToBound());

//              关联属性
                b.setShift(getShift(sbDTO));//特殊班次的绑定可能每次都不同，因此只能在每次迭代时查询
                b.setRegularShift(rs);

                shiftBindingDao.save(b);
                binds.add(b);
            }
        }

        rs.setBindings(binds);
        regularShiftDao.update(rs);
        return dozerMapper.map(rs, RegularShiftDTO.class);
    }

    /**获取绑定的班次
     * @param dto 班次绑定 DTO
     * @return 班次实体
     */
    private Shift getShift(ShiftBindingDTO dto) {
        Shift shift;

        String shiftFK = dto.getShiftFK();
        ShiftType shiftType = dto.getShiftType();

        notNull(shiftType, "绑定班次时：班次的 shiftType 不能为 null");
        
        if(ShiftType.NORMAL.equals(shiftType)){
            notNullOrEmpty(shiftFK);
            shift = shiftDao.getById(shiftFK);
        }else{
            shift = shiftDao.getByType(shiftType).get(0);
        }
        notNull(shift, "绑定的目标班次不存在，shiftFK: " + shiftFK + ", shiftType: " + shiftType);

        return shift;
    }

    @Override
    public void delByID(String id) {
        RegularShift byId = regularShiftDao.getById(id);
        List<ShiftBinding> bindings = byId.getBindings();

//      1. 删除规律班次下的所有班次绑定
        if(bindings != null && bindings.size()>0){
            for (ShiftBinding binding : bindings) {
                shiftBindingDao.delete(binding);
            }
        }
//      2. 删除规律班次
        regularShiftDao.delete(byId);
    }

    /**返回规律班次的列表，出于速度考虑，不加载所含的数据绑定列表
     * @return 规律班次列表，其中只包含普通属性，不包含集合属性
     */
    @Override
    public List<RegularShiftDTO> findAll() {
        List<RegularShift> eList = regularShiftDao.findAll();
        List<RegularShiftDTO> dList = new ArrayList<>(eList.size());

        for (RegularShift rs : eList) {
//          置空集合属性，防止触发懒加载
            rs.setBindings(null);

            dList.add(dozerMapper.map(rs, RegularShiftDTO.class));
        }
        return dList;
    }

//SELECT this_.id             AS id9_2_,
//       this_.daytobound     AS dayToBound9_2_,
//       this_.idx            AS idx9_2_,
//       this_.regularshiftfk AS regularS4_9_2_,
//       this_.shiftfk        AS shiftFK9_2_,
//       regularshi2_.id      AS id7_0_,
//       regularshi2_.NAME    AS name7_0_,
//       regularshi2_.unit    AS unit7_0_,
//       regularshi2_.unitcnt AS unitCnt7_0_,
//       shift3_.id           AS id8_1_,
//       shift3_.description  AS descript2_8_1_,
//       shift3_.NAME         AS name8_1_,
//       shift3_.shifttype    AS shiftType8_1_
//FROM   attend_shift_binding this_
//       LEFT OUTER JOIN attend_regular_shift regularshi2_
//                    ON this_.regularshiftfk = regularshi2_.id
//       LEFT OUTER JOIN attend_shift shift3_
//                    ON this_.shiftfk = shift3_.id
//WHERE  this_.regularshiftfk = ?
//ORDER  BY this_.idx ASC,
//          this_.daytobound ASC

    @Override
    public List<ShiftBindingDTO> getShiftBindings(String id){
        List<ShiftBinding> binds = shiftBindingDao.getShiftBindings(id);
        List<ShiftBindingDTO> dbinds = new ArrayList<>(binds.size());

        for (ShiftBinding b : binds) {
            dbinds.add(dozerMapper.map(b, ShiftBindingDTO.class));
        }

        return dbinds;
    }
}
