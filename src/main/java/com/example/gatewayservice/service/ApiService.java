package com.example.gatewayservice.service;

public abstract class ApiService<T, V> {
    public abstract <T> T postApi(String url,V object, Class tclass);
    public abstract <T> T postApiHeader(String url, V object,Class tclass);

    public abstract <T> T postApiHeaderSignature(String url, V object, Class tclass);
}
