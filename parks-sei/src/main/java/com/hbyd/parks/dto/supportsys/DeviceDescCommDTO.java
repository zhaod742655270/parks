package com.hbyd.parks.dto.supportsys;

import com.hbyd.parks.common.base.BaseDTO;

/**
 * 设备的通信属性：地址、端口、用户名、密码和网关
 */
public class DeviceDescCommDTO extends BaseDTO {
    private String ip;
    private int port;
    private String gateway;
    private String username;
    private String password;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
