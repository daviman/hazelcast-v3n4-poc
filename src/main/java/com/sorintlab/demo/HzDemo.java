package com.sorintlab.demo;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class HzDemo {

    public static void main(String[] args) {
        HzDemo demo = new HzDemo();
        demo.testMovement();
    }

    public static void startBoth() {

        new Thread() {
            public void run() {
                try {
                    new HzDemo().runV3();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            public void run() {
                try {
                    new HzDemo().runV4();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void testMovement() {

        HzClient clientV3 = null;
        HzClient clientV4 = null;
        try {
            clientV3 = getClientV3();
            clientV4 = getClientV4();

            String mapName = "my-test";
            Map<Integer, String> seededMap = seedMap(clientV3, mapName);
            int count = storeMap(clientV4, mapName, seededMap);

            System.out.println("Source map size: " + seededMap.size());
            System.out.println("Target map size: " + count);
        } finally {
            try {
                clientV3.shutdown();
                clientV4.shutdown();
            } catch (Exception doNothing) {}
        }
    }

    private int storeMap(HzClient targetClient, String mapName, Map<Integer, String> sourceMap) {
        Map<Integer, String> targetMap = (Map<Integer, String>)
                targetClient.getDistributedObject(DistributedObjectType.Map, mapName);

        final AtomicInteger count = new AtomicInteger(0);
        sourceMap.keySet().forEach(key -> {
            targetMap.put(key, sourceMap.get(key));
            count.incrementAndGet();
        });
        return count.get();

    }

    private Map<Integer, String> seedMap(HzClient client, String mapName) {
        Map<Integer, String> hzMap = (Map<Integer, String>)
                client.getDistributedObject(DistributedObjectType.Map, mapName);

        Random random = new Random();
        IntStream.range(0, 1000).forEach(i ->
                {
                    Integer key = random.nextInt();
                    hzMap.put(key, "value-"+key);
                }
            );
        return hzMap;
    }


    private void runV3() throws Exception {
        HzClient client = getClientV3();
        BlockingQueue<String> queue = (BlockingQueue<String>)
                client.getDistributedObject(DistributedObjectType.Queue, "queue");

        Map<String, String> hzMap = (Map<String, String>)
                client.getDistributedObject(DistributedObjectType.Map, "my-map");

        for(int i = 0; i < 10; i++) {
            Thread.sleep(5000);
            queue.put("hello: " + i);
            hzMap.put("key_" + i, "value_" + i);
        }
        client.shutdown();
    }

    private void runV4() throws Exception {
        HzClient client = getClientV4();
        BlockingQueue<String> queue = (BlockingQueue<String>)
                client.getDistributedObject(DistributedObjectType.Queue, "queue");

        Map<String, String> hzMap = (Map<String, String>)
                client.getDistributedObject(DistributedObjectType.Map, "my-map");

        for(int i = 0; i < 10; i++) {
            Thread.sleep(5000);
            queue.put("hello: " + i);
            hzMap.put("key_" + i, "value_" + i);
        }
        client.shutdown();
    }

    private HzClient getClientV3() {
        String xmlJarPathName = "./ext-lib/hazelcast-all-3.12.5.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        String instanceClassName = "com.hazelcast.core.HazelcastInstance";
        String clientConfigClassName = "com.hazelcast.client.config.ClientConfig";
        String xmlConfigClassName = "com.hazelcast.client.config.ClientClasspathXmlConfig";

        String xmlConfigFile = "hazelcast-client-3.x.xml";

        try {
            HzConfiguration configuration = new HzConfiguration( clientClassName,
                    instanceClassName,
                    clientConfigClassName,
                    xmlConfigClassName,
                    xmlJarPathName);

            HzClient client = new HzClient(configuration);
            return client.connect(xmlConfigFile);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private HzClient getClientV4() {
        String xmlJarPathName = "./ext-lib/hazelcast-all-4.1-BETA-1.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        String instanceClassName = "com.hazelcast.core.HazelcastInstance";
        String clientConfigClassName = "com.hazelcast.client.config.ClientConfig";
        String xmlConfigClassName = "com.hazelcast.client.config.ClientClasspathXmlConfig";
        String xmlConfigFile = "hazelcast-client-4.x.xml";

        try {
            HzConfiguration configuration = new HzConfiguration( clientClassName,
                    instanceClassName,
                    clientConfigClassName,
                    xmlConfigClassName,
                    xmlJarPathName);

            HzClient client = new HzClient(configuration);
            return client.connect(xmlConfigFile);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
