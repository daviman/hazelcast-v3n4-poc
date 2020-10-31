package com.sorintlab.demo;

import com.sorintlab.demo.v3_12.VersionLoaderV3;
import com.sorintlab.demo.v4.VersionLoaderV4;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class HzClientDemoV4 {

    private static final String V4JarPath = "/Users/mdavid/projects/binaries/hazelcast/hazelcast-4.1-BETA-1/lib/hazelcast-all-4.1-BETA-1.jar";

    public static void main(String[] args) {


        JarClassLoader versionLoaderV4 = null;

        try {
            versionLoaderV4 = new JarClassLoader(V4JarPath);

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        try {
            Class<?> hzClientClass_4 = versionLoaderV4.loadClass("com.hazelcast.client.HazelcastClient");
            Class<?> hzInstanceClass_4 = versionLoaderV4.loadClass("com.hazelcast.core.HazelcastInstance");
            Class<?> hzClientConfigClass_4 = versionLoaderV4.loadClass("com.hazelcast.client.config.ClientConfig");
            Class<?> hzXmlClientConfigClass_4 = versionLoaderV4.loadClass("com.hazelcast.client.config.ClientClasspathXmlConfig");
            Constructor constructor = hzXmlClientConfigClass_4.getConstructor(String.class);

            Object xmlConfigInstance = constructor.newInstance("hazelcast-client-4.x.xml");

            Method method =
                hzClientClass_4.getMethod("newHazelcastClient", new Class[] { hzClientConfigClass_4 });

            Object hzInstanceObject = method.invoke(null, xmlConfigInstance);

            Method getQueue = hzInstanceClass_4.getMethod("getQueue", String.class);
            BlockingQueue<String> queue = (BlockingQueue<String>) getQueue.invoke(hzInstanceObject, "queue");

            Method getMap = hzInstanceClass_4.getMethod("getMap", String.class);

            Map<String, String> hzMap = (Map<String, String>) getMap.invoke(hzInstanceObject, "my-map");

            for(int i = 0; i < 10; i++) {
                Thread.sleep(5000);
                queue.put("hello: " + i);
                hzMap.put("key_" + i, "value_" + i);
            }

            Method shutdownMethod = hzInstanceClass_4.getMethod("shutdown", null);
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
