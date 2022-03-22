package com.ara.project_common.data.bean;

/**
 * Created by XieXin on 2018/12/10.
 * app 更新实体类
 * <update>
 *     <!--  版本号  -->
 *     <version>2</version>
 *     <!--  版本名  -->
 *     <versionName>1.2</versionName>
 *     <name>tnxl_v1.2.apk</name>
 *     <!--  apk下载url  -->
 *     <url>http://cdn1.daihing.yn-ce.com/dhb_dev_v1.2.1.apk</url>
 *     <!--  更新内容  -->
 *     <content>垫</content>
 *     <!-- 是否强制更新 -->
 *     <status>false</status>
 * </update>
 */
public class UpgradeBean {
    /*** 版本号 */
    private long version = 0;
    /*** 版本名 */
    private String versionName;
    /*** 文件名称 */
    private String name;
    /*** apk下载url */
    private String url;
    /*** 更新内容 */
    private String content;
    /*** 是否强制更新 */
    private boolean status;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getVersionName() {
        if (versionName == null) setVersionName("");
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getName() {
        if (name == null) setName("");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        if (url == null) setUrl("");
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        if (content == null) setContent("");
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
