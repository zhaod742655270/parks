package com.hbyd.parks.domain.supportsys;

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 设备的通信属性：地址、端口、用户名、密码和网关
 */
@Entity
@Table(name = "base_device_desc_comm", schema = "dbo", catalog = "parks")
public class DeviceDescComm extends BaseEntity {
    private String ip;
    private int port;
    private String gateway;
    private String username;
    private String password;

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Basic
    @Column(name = "gateway")
    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
