package com.asiainfo.socket.future;

import java.util.concurrent.TimeUnit;
import net.sf.json.JSONObject;

/**
 * 请求结果
 * @author grucee（sundedong)
 */
public interface OperationFuture {
    /**
     * listener
     */
    void addListener(OperationListener listener);
    
    /**
     * 操作是否成功
     * @return 
     */
    boolean isSuccess();
    
    /**
     * 无超时等待回结果(尽量不用此方法）
     * @return 
     */
    JSONObject getResult() throws InterruptedException;

    /**
     * 获取异常
     * @return 
     */
    Throwable getCause();

    JSONObject getResult(long timeout, TimeUnit unit) throws Exception;

    /**
     * 超时等待
     * @param timeoutMillis
     * @return
     * @throws InterruptedException
     */
    JSONObject getResult(long timeoutMillis) throws Exception;

    /**
     * 是否完成
     * @return 
     */
    boolean isDone();

    /**
     * 失败原因
     * @param cause
     * @return 
     */
    boolean setFailure(Throwable cause);

    /**
     * 设置结果
     * @param result
     * @return 
     */
    boolean setResult(JSONObject result);

    /**
     * 附加元素
     * @return 
     */
    Object getAttachment();
}
