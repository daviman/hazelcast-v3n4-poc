package com.sorintlab.demo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoader {

    private ClassLoader customClassLoader = null;

    private String jarPath = null;

    public JarClassLoader(String jarPath) throws MalformedURLException {
        this.jarPath = jarPath;
        initialize();
    }

    private void initialize()  throws MalformedURLException {
        URL url = (new File(jarPath).toURI().toURL());
        this.customClassLoader = new URLClassLoader(new URL[] { url });
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return customClassLoader.loadClass(className);
    }

}
