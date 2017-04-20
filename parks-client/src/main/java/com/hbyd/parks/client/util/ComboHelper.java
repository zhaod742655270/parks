package com.hbyd.parks.client.util;

import com.google.common.base.Strings;
import com.hbyd.parks.common.model.Combobox;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.dto.officesys.ContractGatheringDTO;
import com.hbyd.parks.dto.officesys.ProjectRecordDTO;
import com.hbyd.parks.dto.officesys.WarehouseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Author:Zhang_F
 * Data:2016/3/20
 */
public class ComboHelper {
    public static List<Combobox> getContractNameCombobox(List<ContractGatheringDTO> list) {
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContractGatheringDTO dto = list.get(i);
                Combobox node = new Combobox();
                node.setId(dto.getId());
            if(dto.getProjectType().equals("零星项目")){
                node.setText(dto.getContractName()+"#"+dto.getContractNo());
            }else {
                node.setText(dto.getContractName());
            }
                nodes.add(node);
        }
        return nodes;
    }


    public static List<Combobox> getContractSnCombobox(List<ContractGatheringDTO> list) {
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContractGatheringDTO dto = list.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getContractNo());
            nodes.add(node);
        }
        return nodes;
    }



    public static List<Combobox> getPurchaserNameCombobox(List<UserDTO> list) {
        ArrayList<Combobox> nodes = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            UserDTO dto = list.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getUserName());
            nodes.add(node);
        }
        return nodes;
    }

    public static List<Combobox> getNicknameCombobox(List<UserDTO> list) {
        ArrayList<Combobox> nodes = new ArrayList<>();
        Combobox nodeNull = new Combobox();
        //添加空白的选项
        nodeNull.setId(null);
        nodeNull.setText("");
        nodes.add(nodeNull);

        for (int i = 0; i < list.size(); i++) {
            Combobox node = new Combobox();
            UserDTO dto = list.get(i);
            node.setId(dto.getId());
            if(!Strings.isNullOrEmpty(dto.getNickname())) {
                node.setText(dto.getNickname());
                nodes.add(node);
            }
        }

        return nodes;
    }

    public static List<Combobox> getProjectNameCombobox(List<ProjectRecordDTO> list){
        ArrayList<Combobox> nodes = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            ProjectRecordDTO dto = list.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getId());
            node.setText(dto.getName());
            nodes.add(node);
        }
        return nodes;
    }

    public static List<Combobox> getProductNameCombobox(List<WarehouseDTO> list){
        ArrayList<Combobox> nodes = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            WarehouseDTO dto = list.get(i);
            Combobox node = new Combobox();
            node.setId(dto.getProductId());
            node.setText(dto.getName());
            nodes.add(node);
        }
        return nodes;
    }
}
