package com.kangjun.util.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhang guo shuai
 * @Date: 2019/8/16 14:05
 * @Version 1.0
 */
public class ContentResult {

    private boolean is_project_calender;
    private String calender_id;
    private String calender_name;
    private List<RelatedObj> related_Obj= new ArrayList<>();

    class RelatedObj{
        private String obj_id;
        private String obj_name;

        public RelatedObj(String obj_id, String obj_name) {
            this.obj_id = obj_id;
            this.obj_name = obj_name;
        }

        public String getObj_id() {
            return obj_id;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public String getObj_name() {
            return obj_name;
        }

        public void setObj_name(String obj_name) {
            this.obj_name = obj_name;
        }
    }

    public void addRelatedObj(String obj_id,String obj_name) {
        related_Obj.add(new RelatedObj(obj_id,obj_name));
    }

    public boolean isIs_project_calender() {
        return is_project_calender;
    }

    public void setIs_project_calender(boolean is_project_calender) {
        this.is_project_calender = is_project_calender;
    }

    public String getCalender_id() {
        return calender_id;
    }

    public void setCalender_id(String calender_id) {
        this.calender_id = calender_id;
    }

    public String getCalender_name() {
        return calender_name;
    }

    public void setCalender_name(String calender_name) {
        this.calender_name = calender_name;
    }

    public List<RelatedObj> getRelated_Obj() {
        return related_Obj;
    }

    public void setRelated_Obj(List<RelatedObj> related_Obj) {
        this.related_Obj = related_Obj;
    }
}

