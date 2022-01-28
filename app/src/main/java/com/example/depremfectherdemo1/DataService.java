package com.example.depremfectherdemo1;

import android.provider.ContactsContract;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataService {

    ObjectMapper objectMapper ;

    public DataService(){
        this. objectMapper = new ObjectMapper();
    }

    public List<Map<String,String>> getQuakesFromOrhanApiAndKandilli() throws IOException {
        CloseableHttpClient httpclient= HttpClients.createDefault() ;
        HttpGet httpget = new HttpGet("https://api.orhanaydogdu.com.tr/deprem/live.php?limit=100");
        HttpResponse httpresponse = httpclient.execute(httpget);


        return getBodyAsString(httpresponse);

    }

    private List getBodyAsString(HttpResponse response) throws IOException {
        Map map = objectMapper.readValue(EntityUtils.toString(response.getEntity()), Map.class);

        return (List) map.get("result");

    }



}
