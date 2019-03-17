package com.tdl.dubbomesh.cluster.loadbalance;

import com.tdl.dubbomesh.rpc.Endpoint;

import java.util.List;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:33
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:33
* @UpdateRemark:   
* @Version:        1.0
*/
public interface LoadBalance {

    Endpoint select();

    void onRefresh(List<Endpoint> endpoints);

}
