package com.sorintlab.demo;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;

public class JarClassLoaderTest {

    @Test
    public void testSuccessLoad_V3_Jar() {
        String xmlJarPathName = "./ext-lib/hazelcast-all-3.12.5.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        JarClassLoader jarClassLoader = null;
        try {
            jarClassLoader = new JarClassLoader(xmlJarPathName);
        } catch (MalformedURLException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        try {
            Class<?> clz = jarClassLoader.loadClass(clientClassName);
            Assert.assertNotNull(clz);
        } catch (ClassNotFoundException classNotFoundException) {
            Assert.assertNull(clientClassName);
        }

    }

    @Test
    public void testFailLoad_V3_Jar() {
        String xmlJarPathName = "./ext-lib/fail-hazelcast-all-3.12.5.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        JarClassLoader jarClassLoader = null;
        try {
            jarClassLoader = new JarClassLoader(xmlJarPathName);
        } catch (MalformedURLException e) {
            Assert.assertNotNull(e);
        }
        try {
            jarClassLoader.loadClass(clientClassName);
            Assert.assertNotNull(null);
        } catch (ClassNotFoundException classNotFoundException) {
            Assert.assertNotNull(clientClassName);
        }
    }

    @Test
    public void testSuccessLoad_V4_Jar() {
        String xmlJarPathName = "./ext-lib/hazelcast-all-4.1-BETA-1.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        JarClassLoader jarClassLoader = null;
        try {
            jarClassLoader = new JarClassLoader(xmlJarPathName);
        } catch (MalformedURLException e) {
            Assert.fail(e.getLocalizedMessage());
        }
        try {
            Class<?> clz = jarClassLoader.loadClass(clientClassName);
            Assert.assertNotNull(clz);
        } catch (ClassNotFoundException classNotFoundException) {
            Assert.assertNull(clientClassName);
        }

    }

    @Test
    public void testFailLoad_V4_Jar() {
        String xmlJarPathName = "./ext-lib/fail-hazelcast-all-4.1-BETA-1.jar";
        String clientClassName = "com.hazelcast.client.HazelcastClient";
        JarClassLoader jarClassLoader = null;
        try {
            jarClassLoader = new JarClassLoader(xmlJarPathName);
        } catch (MalformedURLException e) {
            Assert.assertNotNull(e);
        }
        try {
            jarClassLoader.loadClass(clientClassName);
            Assert.assertNotNull(null);
        } catch (ClassNotFoundException classNotFoundException) {
            Assert.assertNotNull(clientClassName);
        }
    }
}
