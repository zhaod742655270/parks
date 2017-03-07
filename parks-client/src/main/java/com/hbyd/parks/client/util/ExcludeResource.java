package com.hbyd.parks.client.util;

import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Len on 14-9-28.
 */
public class ExcludeResource {
    public static Collection<ResAppDTO> excludeApp(Collection<ResAppDTO> appDTOs) {
        if (appDTOs == null) {
            return null;
        }
        List<ResAppDTO> temp = new ArrayList<>();
        for (ResAppDTO app : appDTOs) {
            if (!app.getIsVisible()) {
                temp.add(app);
            }
        }
        for (ResAppDTO app : temp) {
            appDTOs.remove(app);
        }
        return appDTOs;
    }

    public static Collection<ResMenuDTO> excludeMenu(Collection<ResMenuDTO> menuDTOs) {
        if (menuDTOs == null) {
            return null;
        }
        List<ResMenuDTO> temp = new ArrayList<>();
        for (ResMenuDTO menu : menuDTOs) {
            if (!menu.getIsVisible()) {
                temp.add(menu);
            }
        }
        for (ResMenuDTO menu : temp) {
            menuDTOs.remove(menu);
        }
        return menuDTOs;
    }

    public static Collection<ResBtnDTO> excludeBtn(Collection<ResBtnDTO> btnDTOs) {
        if (btnDTOs == null) {
            return null;
        }
        List<ResBtnDTO> temp = new ArrayList<>();
        for (ResBtnDTO btn : btnDTOs) {
            if (!btn.getIsVisible()) {
                temp.add(btn);
            }
        }
        for (ResBtnDTO btn : temp) {
            btnDTOs.remove(btn);
        }
        return btnDTOs;
    }
}
