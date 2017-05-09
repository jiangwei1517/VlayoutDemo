package com.jiangwei.vlayoutdemo.bean;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * author:  jiangwei18 on 17/5/8 16:24
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
public class TianMao {
    // 广告栏
    public LoopDatas loopData;
    // 热点
    @JsonField(name = "hotPoint")
    public List<HotPoint> hotPoints;

    @JsonField(name = "todayHot")
    public List<TodayHot> todayHots;

    public List<History> history;

    @JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
    public static class LoopDatas {
        public List<LoopData> items;
    }

    @JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
    public static class LoopData {
        public String id;
        public String descText;
        public String imgUrl;
        public String link;
    }

    @JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
    public static class HotPoint {
        public String imgUrl;
        public String name;
    }

    @JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
    public static class TodayHot {
        public String imgUrlBig;
        public String nameBig;
        public String imgUrlSmall;
        public String nameSmall;
        public String imgUrlSmall2;
        public String nameSmall2;
    }

    @JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
    public static class History {
        public String imgUrl;
        public String name;
        public int width;
        public int height;
    }


}
