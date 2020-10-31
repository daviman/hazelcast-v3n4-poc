package com.sorintlab.demo.v4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class VersionLoaderV4 {

    private static VersionLoaderV4 instance = null;

    private ClassLoader customClassLoader = null;

    private VersionLoaderV4() throws MalformedURLException {
        initialize();
    }

    public static VersionLoaderV4 getInstance() throws MalformedURLException {
        if(instance == null) {
            synchronized (VersionLoaderV4.class) {
                instance = new VersionLoaderV4();
            }
        }
        return instance;
    }

    private void initialize() throws MalformedURLException {
//        try {
            URL url = (new File("/Users/mdavid/projects/binaries/hazelcast/hazelcast-4.1-BETA-1/lib/hazelcast-all-4.1-BETA-1.jar")
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
