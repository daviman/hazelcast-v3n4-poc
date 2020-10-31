package com.sorintlab.demo;

public class HzConfiguration {

    private String clientClassName;
    private String instanceClassName;
    private String configClassName;
    private String xmlConfigClassName;

    private String xmlJarPathName;

    private Class<?> clientClass;
    private Class<?> instanceClass;
    private Class<?> configClass;
    private Class<?> xmlConfigClass;

    private JarClassLoader jarClassLoader;

    public HzConfiguration(String clientClassName,
                           String instanceClassName,
                           String configClassName,
                           String xmlConfigClassName,
                           String xmlJarPathName)
            throws Exception {
        this.clientClassName = clientClassName;
        this.instanceClassName = instanceClassName;
        this.configClassName = configClassName;
        this.xmlConfigClassName = xmlConfigClassName;
        this.xmlJarPathName = xmlJarPathName;

        initialize();
    }

    private void initialize() throws Exception {
        this.jarClassLoader = new JarClassLoader(xmlJarPathName);
        this.clientClass = jarClassLoader.loadClass(clientClassName);
        this.instanceClass = jarClassLoader.loadClass(instanceClassName);
        this.configClass = jarClassLoader.loadClass(configClassName);
        this.xmlConfigClass = jarClassLoader.loadClass(xmlConfigClassName);
    }

    public Class<?> getClientClass() {
        return clientClass;
    }

    public Class<?> getInstanceClass() {
        return instanceClass;
    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public Class<?> getXmlConfigClass() {
        return xmlConfigClass;
    }

    public String getXmlJarPathName() {
        return xmlJarPathName;
    }
}
