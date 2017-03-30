package com.android.record.list;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SwipeCardBean {
    private int position;
    private String url;
    private String name;
    private String currentTime;
    public String getCurrentTime() {
        return this.currentTime;
    }
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    @Generated(hash = 1730026456)
    public SwipeCardBean(int position, String url, String name, String currentTime) {
        this.position = position;
        this.url = url;
        this.name = name;
        this.currentTime = currentTime;
    }
    @Generated(hash = 1327921346)
    public SwipeCardBean() {
    }

    public static List<SwipeCardBean> initDatas() {
        List<SwipeCardBean> datas = new ArrayList<>();
        int i = 1;
        datas.add(new SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_03/2016_03_25/201603259771458878793312_origin.jpg", "张", "2017"));
        datas.add(new SwipeCardBean(i++, "http://p14.go007.com/2014_11_02_05/a03541088cce31b8_1.jpg", "旭童", "2017"));
        datas.add(new SwipeCardBean(i++, "http://news.k618.cn/tech/201604/W020160407281077548026.jpg", "多种type", "2017"));
        datas.add(new SwipeCardBean(i++, "http://www.kejik.com/image/1460343965520.jpg", "多种type", "2017"));
        datas.add(new SwipeCardBean(i++, "http://cn.chinadaily.com.cn/img/attachement/jpg/site1/20160318/eca86bd77be61855f1b81c.jpg", "多种type", "2017"));
        datas.add(new SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_12/201604124411460430531500.jpg", "多种type", "2017"));
        datas.add(new SwipeCardBean(i++, "http://imgs.ebrun.com/resources/2016_04/2016_04_24/201604244971461460826484_origin.jpeg", "多种type", "2017"));
        datas.add(new SwipeCardBean(i++, "http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg", "多种type", "2017"));
        return datas;
    }
}
