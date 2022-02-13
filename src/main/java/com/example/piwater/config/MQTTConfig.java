package com.example.piwater.config;

public abstract class MQTTConfig {

    protected static final String BROKER = "192.168.1.114";
    protected static final int QOS = 0;
    protected boolean hasSSL = false; /* By default SSL is disabled */
    protected Integer port = 1883; /* Default port */
    protected static final String USERNAME = "testUserName";
    protected static final String PASSWORD = "demoPassword";
    protected static final String TCP = "tcp://";
    protected static final String SSL = "ssl://";

    /**
     * Custom Configuration
     *
     * @param broker
     * @param port
     * @param ssl
     * @param withUserNamePass
     */
    protected abstract void config(String broker, Integer port, boolean ssl, boolean withUserNamePass);

    /**
     * Default Configuration
     */
    protected abstract void config();
}
