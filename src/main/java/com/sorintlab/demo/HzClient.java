package com.sorintlab.demo;

import com.sorintlab.demo.JarClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class HzClient {

    private HzConfiguration configuration = null;

    private Object hazelcastInstance = null;

    public HzClient(HzConfiguration configuration) {
        this.configuration = configuration;
    }

    public HzClient connect(String xmlConfigPathName) throws Exception {
        Constructor constructor = configuration.getXmlConfigClass().getConstructor(java.lang.String.class);

        Object xmlConfigInstance = constructor.newInstance(xmlConfigPathName);

        Method method = configuration.getClientClass()
                .getMethod("newHazelcastClient", new Class[] { configuration.getConfigClass() });

        this.hazelcastInstance = method.invoke(null, xmlConfigInstance);
        return this;
    }

    public Object getDistributedObject(DistributedObjectType type, String name) {
        Object result = null;

        try {
            switch (type) {
                case Map:
                case Queue:
                case ReplicatedMap:
                    Method getMethod = hazelcastInstance.getClass().getMethod(type.getAccessorName(), String.class);
                    result = getMethod.invoke(hazelcastInstance, name);
                    break;
            }
        }catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        } catch(Exception other) {
            other.printStackTrace();
        }
        return result;
    }

    public void shutdown() throws Exception {
        Method shutdownMethod = hazelcastInstance.getClass().getMethod("shutdown", null);
        shutdownMethod.invoke(hazelcastInstance, null);
    }

}
