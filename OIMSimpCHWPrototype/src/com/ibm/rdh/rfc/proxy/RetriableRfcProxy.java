package com.ibm.rdh.rfc.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.pprds.epimshw.PropertyKeys;
import com.ibm.pprds.epimshw.util.ConfigManager;
import com.ibm.pprds.epimshw.util.RfcLogger;
import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;



public class RetriableRfcProxy implements InvocationHandler {
	private RfcLogger rfcLogger;
	private Object targetObject;
	private RfcProxy rfcProxy;
	private final int MAX_TRY_COUNT = ConfigManager.getConfigManager().getInt(PropertyKeys.KEY_RFC_MAX_TRY_COUNT, 3);
	private final long RETRY_WAIT_TIME = Long.valueOf(ConfigManager.getConfigManager().getInt(PropertyKeys.KEY_RFC_RETRY_WAIT_TIME, 1000));
	
	private static List<String> lockedCallers = new ArrayList<>();
	
	static {
		lockedCallers.add("r129");
		lockedCallers.add("r126");
		lockedCallers.add("r153");
		lockedCallers.add("r152");
		lockedCallers.add("r177");
		lockedCallers.add("r176");
		lockedCallers.add("r138");
	}

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
				String callerName = method.getName();
				if (lockedCallers.contains(callerName)) { // lock the caller
					// Lock file
					rfcLogger.info("Caller " + callerName + " need to lock");
					String fileName = "./locks/" + getLockFileName(callerName, args) + ".lock";
					File file = new File(fileName);
					new File(file.getParent()).mkdirs();	
					try (FileOutputStream fos = new FileOutputStream(file); FileChannel fileChannel = fos.getChannel()) {
						while (true) {
							try {
								FileLock fileLock = fileChannel.tryLock();
								if (fileLock != null) {
									rfcLogger.info(callerName + " get lock, lock file " + fileName);
									
									return method.invoke(targetObject, args);
								} else {
									rfcLogger.info(callerName + " fileLock == null");
									Thread.sleep(5000);
								}
							} catch (OverlappingFileLockException e1) {
								rfcLogger.info(callerName + " other caller is running " + callerName);
								Thread.sleep(5000);
							}
						}			
					}
				} else {
					return method.invoke(targetObject, args);
				}				 
			} catch(Exception e) {
				rfcLogger.info("RetriableRfcProxy exception " + e.getCause().getMessage() + " try " + (i + 1) + "/" + MAX_TRY_COUNT + " wait " + RETRY_WAIT_TIME + "ms");
				exception = e;
				Thread.sleep(RETRY_WAIT_TIME);
			}
		}
		throw exception.getCause();
	}
	
	private String getLockFileName(String callerName, Object[] args) {
		String fileName = "";
		switch(callerName) {
			case "r129" :
			case "r126" :
				TypeFeature tf = (TypeFeature)args[0];
				fileName = "CHARMAINT" + tf.getType() + tf.getFeature();
				break;
			case "r153" :
			case "r152" :
				TypeFeature tf1 = (TypeFeature)args[0];
				fileName = "CLASSIFIMAINT" + tf1.getType() + tf1.getFeature();
				break;
			case "r177" :
			case "r176" :
				String type = args[0] == null ? "" : (String)args[0];
				String range = args[1] == null ? "" : (String)args[1];
				fileName = "CLASSIFIMAINT" + type + range;
				break;				
			case "r138" :
				if (args[0] instanceof TypeModelUPGGeo) {
					TypeModelUPGGeo tfu = (TypeModelUPGGeo)args[0];
					String fromToType = args[3] == null ? "" : (String)args[3];
					if (fromToType.equals("MTCFROMTYPE")) {
						fileName = "CLASSIFIMAINT" + tfu.getFromType() + "RPQ";
					} else {
						fileName = "CLASSIFIMAINT" + tfu.getType() + "RPQ";
					}
				} else if (args[0] instanceof TypeFeature) {
					fileName = "CLASSIFIMAINT" + ((TypeFeature)args[0]).getType() + "RPQ";
				} else {
					fileName = callerName;
				}				
				break;
			default :
				fileName = callerName;
		}
		return fileName;
	}

	public RfcProxy getRfcProxy() {
		return rfcProxy;
	}
	
}
