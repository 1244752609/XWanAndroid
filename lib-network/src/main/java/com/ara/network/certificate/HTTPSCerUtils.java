package com.ara.network.certificate;

import android.content.Context;

import com.ara.common.util.LoggerUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by XieXin on 2020/6/18.
 * Https 证书工具类
 */
public class HTTPSCerUtils {

    /**
     * 信任所有证书
     *
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setTrustAllCertificate(OkHttpClient.Builder okHttpClientBuilder) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            X509TrustManager trustAllManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sc.getSocketFactory(), trustAllManager);
            //如果需要兼容安卓5.0以下，可以使用这句
            //okHttpClientBuilder.sslSocketFactory(new TLSSocketFactory(), trustAllManager);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }

    /**
     * 只信任指定证书（传入字符串）
     *
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @param cerStr              证书字符串
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setCertificate(OkHttpClient.Builder okHttpClientBuilder, String cerStr) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cerStr.getBytes());
            Certificate ca = certificateFactory.generateCertificate(byteArrayInputStream);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            byteArrayInputStream.close();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }

    /**
     * 只信任指定证书（传入raw资源ID）
     *
     * @param context             上下文
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @param cerResId            证书资源Id
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setCertificate(Context context, OkHttpClient.Builder okHttpClientBuilder, int cerResId) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            InputStream inputStream = context.getResources().openRawResource(cerResId);
            Certificate ca = certificateFactory.generateCertificate(inputStream);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            inputStream.close();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }

    /**
     * 批量信任证书（传入raw资源Id数组）
     *
     * @param context             上下文
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @param cerResIds           证书资源Ids
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setCertificates(Context context, OkHttpClient.Builder okHttpClientBuilder, int... cerResIds) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (int i = 0; i < cerResIds.length; i++) {
                Certificate ca = certificateFactory.generateCertificate(context.getResources().openRawResource(cerResIds[i]));
                keyStore.setCertificateEntry("ca" + i, ca);
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }

    /**
     * 只信任指定证书（传入InputStream）
     *
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @param is                  InputStream
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setCertificate(OkHttpClient.Builder okHttpClientBuilder, InputStream is) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate ca = certificateFactory.generateCertificate(is);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            is.close();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }

    /**
     * 批量信任证书（传入InputStream数组）
     *
     * @param okHttpClientBuilder OkHttpClient.Builder
     * @param iss                 InputStream列表
     * @return OkHttpClient.Builder
     */
    public static OkHttpClient.Builder setCertificates(OkHttpClient.Builder okHttpClientBuilder, InputStream[] iss) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (int i = 0; i < iss.length; i++) {
                Certificate ca = certificateFactory.generateCertificate(iss[i]);
                keyStore.setCertificateEntry("ca" + i, ca);
            }

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
            okHttpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0]);
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
            LoggerUtils.i("ca" + Arrays.toString(((X509TrustManager) tmf.getTrustManagers()[0]).getAcceptedIssuers()));
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
        }
        return okHttpClientBuilder;
    }
}
