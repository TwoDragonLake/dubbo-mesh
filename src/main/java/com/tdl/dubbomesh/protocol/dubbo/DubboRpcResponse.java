package com.tdl.dubbomesh.protocol.dubbo;

/**
* @Description:    
* @Author:         ceaserWang
* @CreateDate:     2019/3/17 16:31
* @UpdateUser:     yc
* @UpdateDate:     2019/3/17 16:31
* @UpdateRemark:   
* @Version:        1.0
*/
public class DubboRpcResponse {

    private long requestId;
    private byte[] bytes;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
