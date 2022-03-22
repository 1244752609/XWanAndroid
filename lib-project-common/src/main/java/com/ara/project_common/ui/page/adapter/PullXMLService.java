package com.ara.project_common.ui.page.adapter;

import android.util.Xml;

import com.ara.common.util.LoggerUtils;
import com.ara.project_common.data.bean.UpgradeBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by XieXin on 2018/12/10.
 * Pull解析XML
 */
public class PullXMLService {
    private PullXMLService() {
    }

    /**
     * 获取app更新数据
     *
     * @param inputStream InputStream
     * @return AppUpdateBean
     */
    public static UpgradeBean getUpdate(InputStream inputStream) {
        if (inputStream == null) return new UpgradeBean();
        UpgradeBean bean = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "UTF-8");

            //触发了第一个事件，根据XML的语法，也就是从他开始了解文档
            int event = parser.getEventType();
            //如果获得的事件码如果是文档的结束，那么解析结束
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT: {
                        //开始解析的时候我们一般做一些初始化的操作
                        bean = new UpgradeBean();
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        //判断当前的元素是否是需要检索的元素
                        if (bean == null) bean = new UpgradeBean();
                        if ("version".equals(parser.getName())) {
                            bean.setVersion(Integer.valueOf(parser.nextText()));
                        } else if ("versionName".equals(parser.getName())) {
                            bean.setVersionName(parser.nextText());
                        } else if ("name".equals(parser.getName())) {
                            bean.setName(parser.nextText());
                        } else if ("url".equals(parser.getName())) {
                            bean.setUrl(parser.nextText());
                        } else if ("content".equals(parser.getName())) {
                            bean.setContent(parser.nextText());
                        } else if ("status".equals(parser.getName())) {
                            bean.setStatus(Boolean.parseBoolean(parser.nextText()));
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        break;
                    }
                }
                //这一步很重要，该方法返回一个事件码，也是触发下一个事件的方法
                event = parser.next();
            }
        } catch (XmlPullParserException e) {
            LoggerUtils.e(e.getMessage());
        } catch (IOException e) {
            LoggerUtils.e(e.getMessage());
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return bean;
    }
}
