package com.tdl.dubbomesh.registry;

import java.net.InetAddress;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:32
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:32
* @UpdateRemark:   
* @Version:        1.0
*/
public class IpHelper {

    public static String getHostIp() throws Exception {

        String ip = InetAddress.getLocalHost().getHostAddress();
//        String ip = "127.0.0.1";
        return ip;
    }
}
