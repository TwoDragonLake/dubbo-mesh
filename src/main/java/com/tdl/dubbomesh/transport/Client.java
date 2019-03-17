package com.tdl.dubbomesh.transport;

import com.tdl.dubbomesh.rpc.Endpoint;

/**
* @Description:    点对点的通信
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:32
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:32
* @UpdateRemark:
* @Version:        1.0
*/
public interface Client {

    void init();

    MeshChannel getMeshChannel();

    MeshChannel getMeshChannel(Endpoint endpoint);

}
