package com.khtm.circuitbreaker;

import com.google.gson.GsonBuilder;
import com.khtm.eureka.impl.EurekaService;
import com.khtm.eureka.model.Instance;
import lombok.Builder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.apache.http.HttpHeaders.USER_AGENT;

@Builder
public class Feign {

    private EurekaService eurekaService;
    private List<Instance> instances;
    private String applicationName;
    private Object fallBackResponse;
    private Map<String, String> parameters;
    private String url;
    private String method;
    private String body;
    private boolean isThereAppOnTomcatStandalone;

    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_PUT = "PUT";
    public static final String REQUEST_METHOD_DELETE = "DELETE";

    public Object callService() {
        try {
            // get all instance of this service
            this.instances = eurekaService.getServiceInfo(this.applicationName);
            String url = null;
            if (instances.size() == 0) return fallBackResponse;
            if (instances.size() == 1) {
                if (!this.isThereAppOnTomcatStandalone) {
                    url = String.format("http://%s:%s", instances.get(0).getIpAddr(), instances.get(0).getPort().getValue());
                } else {
                    url = String.format("http://%s:%s/%s", instances.get(0).getIpAddr(), instances.get(0).getPort().getValue(),
                            this.applicationName.toLowerCase());
                }
            } else {
                int instanceSize = instances.size();
                Random random = new Random();
                int instanceIndex = random.nextInt(instanceSize);
                if (!this.isThereAppOnTomcatStandalone) {
                    url = String.format("http://%s:%s", instances.get(instanceIndex).getIpAddr(), instances.get(instanceIndex).getPort().getValue());
                } else {
                    url = String.format("http://%s:%s/%s", instances.get(instanceIndex).getIpAddr(), instances.get(instanceIndex).getPort().getValue(),
                            this.applicationName.toLowerCase());
                }
            }

            com.khtm.eureka.model.HttpResponse response = this.sendRequest(url + this.url, this.parameters,
                    this.body, this.method);
            if (Integer.parseInt((response.getResponseCode() + "").split("|")[0]) == 2) {
                return new GsonBuilder().create().fromJson(response.getResult(), fallBackResponse.getClass());
            }
            return fallBackResponse;
        }catch (IOException | JAXBException e){
            return fallBackResponse;
        }
    }

    private String addRequestParameterToUrl(String url, Map<String, String> parameters){
        String strRequest = url + "?";
        for(String key : parameters.keySet()){
            strRequest += String.format("%s=%s&", key, parameters.get(key));
        }
        if(strRequest.endsWith("&")) strRequest = strRequest.substring(0, strRequest.lastIndexOf("&"));
        return strRequest;
    }

    private com.khtm.eureka.model.HttpResponse sendRequest(
            String url, Map<String, String> parameters, String body, String requestMethod) throws IOException {
        HttpRequestBase request = null;
        // create http client
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String strRequest = null;
        if(parameters != null) {
            // create url request
            strRequest = addRequestParameterToUrl(url, parameters);
        }else {
            strRequest = url;
        }
        switch (requestMethod){
            case REQUEST_METHOD_GET:
                request = new HttpGet(strRequest);
                break;
            case REQUEST_METHOD_PUT:
                request = new HttpPut(strRequest);
                if (body != null) {
                    StringEntity requestEntity = new StringEntity(body, "application/json", "UTF-8");
                    ((HttpPut) request).setEntity(requestEntity);
                }
                break;
            case REQUEST_METHOD_DELETE:
                request = new HttpDelete(strRequest);
                break;
        }
        request.addHeader("User-Agent", USER_AGENT);
        // receive http response
        assert client != null;
        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();
        if(response.getEntity() != null) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = bfr.readLine()) != null) sb.append(line);
            // close client
            client.close();
            // return result to
            return com.khtm.eureka.model.HttpResponse.builder().responseCode(responseCode).result(sb.toString()).build();
        }else{
            // close client
            client.close();
            // return result to
            return com.khtm.eureka.model.HttpResponse.builder().responseCode(responseCode).result(null).build();
        }
    }


}
