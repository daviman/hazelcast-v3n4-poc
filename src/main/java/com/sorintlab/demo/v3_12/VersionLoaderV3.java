package com.sorintlab.demo.v3_12;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class VersionLoaderV3 {

    private static String JarPath = null;

    private static VersionLoaderV3 instance = null;

    private final static Object lock = new Object();

    private ClassLoader customClassLoader = null;

    private VersionLoaderV3() throws MalformedURLException {
        initialize();
    }

    public static VersionLoaderV3 getInstance(String jarPath) throws MalformedURLException {
        if(instance == null) {
            synchronized (lock) {
                if(instance == null) {
                    instance = new VersionLoaderV3();
                }
            }
        }
        return instance;
    }

    private void initialize() throws MalformedURLException {
//        try {
            URL url = (new File("/Users/mdavid/projects/binaries/hazelcast/hazelcast-3.12.5/lib/hazelcast-all-3.12.5.jar")
                    .toURI()
                    .toURL());

            this.customClassLoader = new URLClassLoader(new URL[] { url });

//        } catch (MalformedURLException malformedURLException) {
//            malformedURLException.printStackTrace();
//        }


    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return customClassLoader.loadClass(className);
    }
}
