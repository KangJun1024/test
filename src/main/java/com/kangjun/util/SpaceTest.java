package com.kangjun.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SpaceTest {
    public static void main(String[] args) throws Exception{

        //封装参数
        JSONObject jsonObject = new JSONObject();
        JSONObject criteria = new JSONObject();

        criteria.put("id", "Pj3207210001");  //新城环境
        JSONArray types = new JSONArray();
        types.add("Sp");
        criteria.put("type", types);
        jsonObject.put("criteria", criteria);
        jsonObject.put("projectId", "Pj3207210001");
        String requestUrl = "http://212.64.46.74:9909/data-platform-3/object/subset_query";
        String queryResult = httpPostRequestDataPlatform(requestUrl, jsonObject.toJSONString());
        JSONObject queryJson = JSONObject.parseObject(queryResult);
        JSONArray spaceIds = new JSONArray();
        if("success".equals(queryJson.getString("Result"))) {
            JSONArray queryContents = queryJson.getJSONArray("Content");
            if(queryContents != null && queryContents.size() > 0) {
                for (Object content : queryContents) {
                    JSONObject obj = (JSONObject) content;
                    //判断location是否有值
                    if (null != obj.getJSONObject("location") && obj.getJSONObject("location").size() == 0) {
                        spaceIds.add(obj.getString("id"));
                    }
                }
            }
        }
        JSONArray moveCriterias = new JSONArray();
        for (Object spaceId : spaceIds) {
            //绑定关系
            String id = (String) spaceId;
            JSONObject param = new JSONObject();
            param.put("moveId", id);
            param.put("toId", "Bd32072100015e16b5b846cc11e9a1d5334dc7b3c56c"); //建筑ID
            moveCriterias.add(param);
        }

        JSONObject param = new JSONObject();
        param.put("projectId", "Pj3207210001");
        param.put("criterias", moveCriterias);

        requestUrl = "http://212.64.46.74:9909/data-platform-3/object/move";
        queryResult = httpPostRequestDataPlatform(requestUrl, param.toJSONString());

        JSONObject resultJson = JSONObject.parseObject(queryResult);
        if ("failure".equals(resultJson.getString("Result"))) {
            System.out.println("falure");
        }

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
