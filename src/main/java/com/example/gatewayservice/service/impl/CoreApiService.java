package com.example.gatewayservice.service.impl;

import com.example.gatewayservice.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoreApiService extends ApiService {

    @Value("${api.core.header}")
    private String header;
    @Value("${api.core.url}")
    private String url;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Object postApi(String uri, Object object, Class tclass) {
        return restTemplate.postForObject(url.concat(uri),object,tclass);
    }

    @Override
    public Object postApiHeader(String uri, Object object, Class tclass) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,header);
        HttpEntity<Object> request = new HttpEntity<>(object,headers);
        return restTemplate.postForObject(url.concat(uri),request,tclass);
    }

    @Override
    public Object postApiHeaderSignature(String url, Object object, Class tclass) {
        return null;
    }
}
