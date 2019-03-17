package com.tdl.dubbomesh.transport;

import com.tdl.dubbomesh.rpc.Endpoint;
import io.netty.channel.Channel;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:32
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:32
* @UpdateRemark:   
* @Version:        1.0
*/
public class MeshChannel {
    private Channel channel;
    private Endpoint endpoint;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
}
