package com.kangjun.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  工作历工具类
 */
public class WorkCalendarUtil {

    //获取CorpAccessToken URL
    private String URL = "https://open.fxiaoke.com/cgi/corpAccessToken/get/V2";

    /**
     *  调用外网接口，返回当前月的全部假期
     *  URL: http://timor.tech/api/holiday/year/2019-10
     * @param currentMonth
     * @return JSONArray [
     *       {
     *         "date": "2019-08-01",
     *         "week": 1,
     *         "lunarCalendar": "八月十五",
     *         "holiday": true,
     *         "solarTerm": "大寒"
     *      }
     *        ]
     */
    public JSONArray getMonthCalendarDetails(Integer currentYear,Integer currentMonth) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM-dd");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        //0. 外网获取当年当月节假日换休列表
        String remoteUrl = URL + currentYear + "-" + currentMonth;
        String queryResult = httpGetRequest(remoteUrl);
        JSONObject queryJson = JSONObject.parseObject(queryResult);
        Map<String,Object> holidays = (Map<String,Object>)queryJson.get("holiday");
        if (holidays == null && holidays.size() == 0) {
            holidays = new HashMap<>();
        }

        //1. 获取每月总天数，每天属性
        LocalDate localdate = LocalDate.of(currentYear, currentMonth, 1);
        //当前年月每天LocalDate对象
        //LocalDate dateInMonth = null;
        //周几week 1 2 3 4 5 6 7
        int week = 0;
        //日期20190801
        String date = "";
        //日期09-13 用于过滤节假日信息
        String monthDay = "";
        //农历日期
        String lunarCalendar = "";
        //节气
        String solarTerm = "";
        //holiday
        Boolean holiday = false;
        JSONArray jsonArray = new JSONArray();

        //每月总天数 data
        int dayOfMonth = localdate.lengthOfMonth();
        for (int i = 1; i <= dayOfMonth; i++) {
            LocalDate dateInMonth = LocalDate.of(currentYear,currentMonth,i);
            week = dateInMonth.getDayOfWeek().getValue();
            date = dateInMonth.format(formatter);         //获取农历 节气
            monthDay = dateInMonth.format(formatter2);    //获取休息，工作

            if(holidays.containsKey(monthDay)){
                Map<String,Object> properties = (Map<String,Object>)holidays.get(monthDay);
                holiday = Boolean.valueOf(properties.get("holiday").toString());
            }else {
                holiday = false;
            }
            Date simpleDate = dateformat.parse(date);
            Calendar today = Calendar.getInstance();
            today.setTime(simpleDate);
            Lunar lunar = new Lunar(today);
            lunarCalendar = lunar.toString();
            solarTerm = Lunar.getSolarTerms(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date",date);
            jsonObject.put("week",week);
            jsonObject.put("lunarCalendar",lunarCalendar);
            jsonObject.put("solarTerm",solarTerm);
            jsonObject.put("holiday",holiday);
            jsonArray.add(jsonObject);
        }
        return jsonArray;

    }

    /**
     * GET 请求
     * @param url
     * @return
     * @throws Exception
     */
    public String httpGetRequest(String url) throws Exception {
        String respContent = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        // 执行请求
        respContent = executeHttpRequest(httpclient, httpget);
        if(httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException e) {
            }
        }
        return respContent;
    }

    /**
     * 执行http请求
     * @param httpClient
     * @param httpRequest
     * @return
     */
    private String executeHttpRequest(CloseableHttpClient httpClient, HttpUriRequest httpRequest){
        CloseableHttpResponse response = null;
        String respContent = null;
        try {
            response = httpClient.execute(httpRequest);
            respContent = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
            }
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return respContent;
    }

}