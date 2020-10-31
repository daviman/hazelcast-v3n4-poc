package com.sorintlab.demo;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class HzDemo {



    public static void main(String[] args) {

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


    private void runV3() throws Exception {
        String xmlJarPathName = "./ext-lib/hazelcast-all-3.12.5.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        String instanceClassName = "com.hazelcast.core.HazelcastInstance";
        String clientConfigClassName = "com.hazelcast.client.config.ClientConfig";
        String xmlConfigClassName = "com.hazelcast.client.config.ClientClasspathXmlConfig";

        String xmlConfigFile = "hazelcast-client-3.x.xml";

        HzConfiguration configuration = new HzConfiguration( clientClassName,
                 instanceClassName,
                clientConfigClassName,
                 xmlConfigClassName,
                 xmlJarPathName);

        HzClient client = new HzClient(configuration);
        client.connect(xmlConfigFile);

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
        String xmlJarPathName = "./ext-lib/hazelcast-all-4.1-BETA-1.jar";

        String clientClassName = "com.hazelcast.client.HazelcastClient";
        String instanceClassName = "com.hazelcast.core.HazelcastInstance";
        String clientConfigClassName = "com.hazelcast.client.config.ClientConfig";
        String xmlConfigClassName = "com.hazelcast.client.config.ClientClasspathXmlConfig";

        String xmlConfigFile = "hazelcast-client-4.x.xml";

        HzConfiguration configuration = new HzConfiguration( clientClassName,
                instanceClassName,
                clientConfigClassName,
                xmlConfigClassName,
                xmlJarPathName);

        HzClient client = new HzClient(configuration);
        client.connect(xmlConfigFile);

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
}
