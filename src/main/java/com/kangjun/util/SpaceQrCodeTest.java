package com.kangjun.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class SpaceQrCodeTest {
    public static void main(String[] args) throws Exception{

        String path = SpaceQrCodeTest.class.getClassLoader().getResource("space.json").getPath();
        JSONArray jsonArray = null;
        try {
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            jsonArray = JSONArray.parseArray(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //封装参数
        JSONObject jsonObject = new JSONObject();
        JSONObject criteria = new JSONObject();

        criteria.put("id", "Pj3406020001");  //新城环境
        JSONArray types = new JSONArray();
        types.add("Sp");
        criteria.put("type", types);
        jsonObject.put("criteria", criteria);
        jsonObject.put("projectId", "Pj3406020001");
        String requestUrl = "http://212.64.46.74:9909/data-platform-3/object/subset_query";
        String queryResult = httpPostRequestDataPlatform(requestUrl, jsonObject.toJSONString());
        JSONObject queryJson = JSONObject.parseObject(queryResult);
        JSONArray spaceIds = new JSONArray();
        JSONArray spaceIdName = new JSONArray();
        if("success".equals(queryJson.getString("Result"))) {
            JSONArray queryContents = queryJson.getJSONArray("Content");
            if(queryContents != null && queryContents.size() > 0) {
                for (Object content : queryContents) {
                    JSONObject obj = (JSONObject) content;
                    //infos
                    JSONObject infos = obj.getJSONObject("infos");
//                    if(!infos.containsKey("RoomQRCode")){
//                        spaceIdss.add(infos.getString("RoomID"));
//                    }
                    if(null == infos.getString("RoomQRCode") || infos.getString("RoomQRCode").length() == 0){
//                        if(jsonArray.contains(infos.getString("RoomID"))){
//                            spaceIds.add(infos.getString("RoomLocalName"));
//                        }
                        spaceIds.add(infos.getString("RoomID"));
                    }

                }
            }
        }
        String s = spaceIds.toJSONString();
//        String ss = spaceIdss.toJSONString();
        System.out.println("S" + s);
//        System.out.println("SS" + ss);
        System.out.println("success");

    }

    public static String httpPostRequestDataPlatform(String url, String jsonStr) throws Exception {


        String respContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
        httpPost.setEntity(new StringEntity(jsonStr, "utf-8"));
        // 执行请求
        respContent = executeHttpRequest(httpclient, httpPost);
        if(null != respContent && respContent.contains("failure")) {
            System.out.println(respContent);
        }
        return respContent;
    }

    /**
     * 执行http请求
     * @param httpClient
     * @param httpRequest
     * @return
     */
    private static String executeHttpRequest(CloseableHttpClient httpClient, HttpUriRequest httpRequest){
        CloseableHttpResponse response;
        String respContent = null;
        try {
            response = httpClient.execute(httpRequest);
            respContent = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println("HttpClient close failed!");
            }

            if(respContent == null || respContent.startsWith("<")){
                respContent = "请求异常";
            }
        }
        return respContent;
    }


}
