package com.example.depremfectherdemo1;

import static org.junit.Assert.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataServiceTest {

    DataService dataService;

    @Before
    public void setup(){
        dataService= new DataService();
    }
    @Test
    public void orhanaydogduKandilliApiTest() throws IOException, JSONException {

        List l = dataService.getQuakesFromOrhanApiAndKandilli();

        System.out.println(l.toString());

    }

}