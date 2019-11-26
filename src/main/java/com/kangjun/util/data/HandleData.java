package com.kangjun.util.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Author: zhang guo shuai
 * @Date: 2019/8/16 14:03
 * @Version 1.0
 */
public class HandleData {

    public static void main(String[] args) {
        HandleData hd = new  HandleData();
        JSONArray jsonArray = hd.createdData();
        Map<String, ContentResult> resultMap = hd.hadleData(jsonArray);
        List<ContentResult> resultList = new ArrayList<>();
        resultList.addAll(resultMap.values());
    }

    public JSONArray createdData(){
        List<Content> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Content content = new Content();
            content.setCalender_id("objId" + i % 5);
            content.setCalender_name("objname" + i);
            content.setIs_related(i % 2 == 0);
            content.setObj_id(i + "");
            content.setObj_name("objname" + i);
            data.add(content);
        }

        return JSONArray.parseArray(JSON.toJSONString(data));
    }

    public Map<String,ContentResult> hadleData(JSONArray jsonArray){
        Map<String,ContentResult> map = new HashMap<>();
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String calender_id = object.getString("calender_id");
            if(map.containsKey(calender_id)){
                ContentResult contentResult = map.get(calender_id);
                contentResult.addRelatedObj(object.getString("obj_id"),object.getString("obj_name"));
                map.put(calender_id,contentResult);
            }else{
                ContentResult contentResult = new ContentResult();
                contentResult.setCalender_id(calender_id);
                contentResult.setCalender_name(object.getString("calender_name"));
                contentResult.setIs_project_calender(object.getBoolean("is_related"));
                contentResult.addRelatedObj(object.getString("obj_id"),object.getString("obj_name"));
                map.put(calender_id,contentResult);
            }
        }
        return map;
    }
}
