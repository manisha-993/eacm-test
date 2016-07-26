package com.ibm.rdh.rfc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.RfcLogger;



public class RetriableRfcProxy implements InvocationHandler {
	private RfcLogger rfcLogger;
	private Object targetObject;
	private RfcProxy rfcProxy;
	private final int MAX_TRY_COUNT = ConfigManager.getConfigManager().getInt(PropertyKeys.KEY_RFC_MAX_TRY_COUNT, 3);
	private final long RETRY_WAIT_TIME = Long.valueOf(ConfigManager.getConfigManager().getInt(PropertyKeys.KEY_RFC_RETRY_WAIT_TIME, 1000));

    public  RetriableRfcProxy(Object targetObject, RfcLogger rfcLogger) {  
        this.targetObject = targetObject;
        this.rfcLogger = rfcLogger;
        rfcProxy = (RfcProxy)Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
        rfcLogger.info("RetriableRfcProxy MAX_TRY_COUNT " + MAX_TRY_COUNT + " RETRY_WAIT_TIME " + RETRY_WAIT_TIME + "ms");
    }

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Exception exception = null;
		for (int i = 0; i < MAX_TRY_COUNT; i++) {
			try {
				return method.invoke(targetObject, args); 
			} catch(Exception e) {
				rfcLogger.info("RetriableRfcProxy exception " + e.getCause().getMessage() + " try " + (i + 1) + "/" + MAX_TRY_COUNT + " wait " + RETRY_WAIT_TIME + "ms");
				exception = e;
				Thread.sleep(RETRY_WAIT_TIME);
			}
		}
		throw exception.getCause();
	}

	public RfcProxy getRfcProxy() {
		return rfcProxy;
	}
	
}
