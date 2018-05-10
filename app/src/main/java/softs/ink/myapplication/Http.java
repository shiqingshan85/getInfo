package softs.ink.myapplication;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qq230 on 2018/5/9.
 */

public class Http {


    private OkHttpClient client=new OkHttpClient();

    private final Request request;

    private Handler handler;
    public Http(String url, Handler handler) {
        this.handler=handler;
        request = new Request.Builder().url(url).build();
    }

    public void get(){
        try {
            Response execute = client.newCall(request).execute();
            if (execute.isSuccessful()){
                String str=execute.body().string();
                Message message=new Message();
                message.obj=str;
                message.what=1;
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
