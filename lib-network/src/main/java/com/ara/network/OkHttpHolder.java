package com.ara.network;

import com.ara.network.certificate.HTTPSCerUtils;
import com.ara.network.interceptor.GlobalRequestInterceptor;
import com.blankj.utilcode.util.Utils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by XieXin on 2018/12/10.
 * OkHttp持有人
 */
public class OkHttpHolder {
    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    private static OkHttpClient instance;
    private static OkHttpClient instanceSSL;

    private OkHttpHolder() {
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .addInterceptor(new GlobalRequestInterceptor())
                    //timeout
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    // 可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                    .connectionPool(new ConnectionPool(8, TIMEOUT_CONNECTION, TimeUnit.SECONDS))
                    //打印请求log
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(getCookieJar())
                    .retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
                    .build();
        }
        return instance;
    }

    public static OkHttpClient getInstanceSSL() {
        if (instanceSSL == null) {
//            try {
//                InputStream[] certificateStreams = new InputStream[1];
//                certificateStreams[0] = NetworkUtils.context.getResources().openRawResource(R.raw.ca1);
//                CertificateManage.SSLParams sslParams = CertificateManage.getSslSocketFactory(certificateStreams, null, null);
//                instanceSSL = new OkHttpClient.Builder()
//                        .addInterceptor(new GlobalRequestInterceptor())
//                        //timeout
//                        .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
//                        .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
//                        // 可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
//                        .connectionPool(new ConnectionPool(8, TIMEOUT_CONNECTION, TimeUnit.SECONDS))
//                        //打印请求log
//                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                        .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                        .hostnameVerifier(new HostnameVerifier() {
//                            @Override
//                            public boolean verify(String hostname, SSLSession session) {
//                                return true;
//                            }
//                        })
//                        .build();
//            } catch (IOException e) {
//                LoggerUtils.e(e.getMessage());
//            }
            instanceSSL = HTTPSCerUtils.setTrustAllCertificate(new OkHttpClient.Builder()
                    .addInterceptor(new GlobalRequestInterceptor())
                    //timeout
                    .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    // 可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                    .connectionPool(new ConnectionPool(8, TIMEOUT_CONNECTION, TimeUnit.SECONDS))
                    //打印请求log
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
                    .cookieJar(getCookieJar())
            ).build();

        }
        return instanceSSL;
    }

    /**
     * @param timeOut 超时时间(s)
     * @return
     */
    public static OkHttpClient getInstance(int timeOut) {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .addInterceptor(new GlobalRequestInterceptor())
                    //打印请求log
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    //timeout
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    // 可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                    .connectionPool(new ConnectionPool(8, timeOut, TimeUnit.SECONDS))
                    .cookieJar(getCookieJar())
                    .build();
        }
        return instance;
    }

    private static ClearableCookieJar cookieJar;
    private static SharedPrefsCookiePersistor sharedPrefsCookiePersistor;

    public static ClearableCookieJar getCookieJar() {
        if (cookieJar == null) {
            if (sharedPrefsCookiePersistor == null)
                sharedPrefsCookiePersistor = new SharedPrefsCookiePersistor(Utils.getApp());
            cookieJar = new PersistentCookieJar(new SetCookieCache(), sharedPrefsCookiePersistor);
        }
        return cookieJar;
    }

    public static void clearCookie(){
        getCookieJar().clear();
    }

    public static void clearSessionCookie(){
        getCookieJar().clearSession();
    }

    /**
     * 重置
     */
    public static void reset() {
        OkHttpHolder.instance = null;
        OkHttpHolder.instanceSSL = null;
    }
}
