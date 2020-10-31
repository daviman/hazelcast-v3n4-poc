package com.sorintlab.demo;

import com.sorintlab.demo.v3_12.VersionLoaderV3;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class HzClientDemoV3 {

    private final static String V3JarPath = "/Users/mdavid/projects/binaries/hazelcast/hazelcast-3.12.5/lib/hazelcast-all-3.12.5.jar";

    public static void main(String[] args) {


        JarClassLoader versionLoaderV33_12 = null;

        try {
            versionLoaderV33_12 = new JarClassLoader(V3JarPath);

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        try {
            Class<?> hzClientClass_3_12 = versionLoaderV33_12.loadClass("com.hazelcast.client.HazelcastClient");
            Class<?> hzInstanceClass_3_12 = versionLoaderV33_12.loadClass("com.hazelcast.core.HazelcastInstance");
            Class<?> hzClientConfigClass_3_12 = versionLoaderV33_12.loadClass("com.hazelcast.client.config.ClientConfig");
            Class<?> hzXmlClientConfigClass_3_12 = versionLoaderV33_12.loadClass("com.hazelcast.client.config.ClientClasspathXmlConfig");
            Constructor constructor = hzXmlClientConfigClass_3_12.getConstructor(java.lang.String.class);

            Object xmlConfigInstance = constructor.newInstance("hazelcast-client-3.x.xml");

            Method method =
                hzClientClass_3_12.getMethod("newHazelcastClient", new Class[] { hzClientConfigClass_3_12 });

            Object hzInstanceObject = method.invoke(null, xmlConfigInstance);

            Method getQueue = hzInstanceClass_3_12.getMethod("getQueue", String.class);
            BlockingQueue<String> queue = (BlockingQueue<String>) getQueue.invoke(hzInstanceObject, "queue");

            Method getMap = hzInstanceClass_3_12.getMethod("getMap", String.class);

            Map<String, String> hzMap = (Map<String, String>) getMap.invoke(hzInstanceObject, "my-map");

            for(int i = 0; i < 10; i++) {
                Thread.sleep(5000);
                queue.put("hello: " + i);
                hzMap.put("key_" + i, "value_" + i);
            }

            Method shutdownMethod = hzInstanceClass_3_12.getMethod("shutdown", null);
            shutdownMethod.invoke(hzInstanceObject, null);

        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
        } catch(Exception otherExceptions) {
            otherExceptions.printStackTrace();
        }
    }
}
