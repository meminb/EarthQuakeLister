package com.example.depremfectherdemo1;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class QuakeRecyclerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void orhanaydogduKandilliApiTest() throws IOException, JSONException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("https://api.orhanaydogdu.com.tr/deprem/live.php?limit=100");
        HttpResponse httpresponse = httpclient.execute(httpget);


        Map<String,String> map = objectMapper.readValue(EntityUtils.toString(httpresponse.getEntity()), Map.class);

        System.out.println("... "+ map.toString());
        for (Map.Entry  entry : map.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
        }


    }


}