package iosco.app.utils;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by usuario on 18/02/2016.
 */
public class CustomImageDownaloder extends BaseImageDownloader {

    public CustomImageDownaloder(Context context) {
        super(context);
    }

    public CustomImageDownaloder(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection conn = super.createConnection(url, extra);
        //Map<String, String> headers = (Map<String, String>) extra;

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",Global.getUserToken(context));
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        return conn;
    }
}