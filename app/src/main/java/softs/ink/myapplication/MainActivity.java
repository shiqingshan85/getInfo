package softs.ink.myapplication;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String url="http://www.imooc.com/api/teacher?type=2&page=1";
    private static final String TAG = "TAG";
    private ArrayList<String> groups;
    private ArrayList<ArrayList<IMoocBean.DataBean>> childs;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: " + msg.obj);
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    parserJSON((String) msg.obj);
adapter.notifyDataSetChanged();
                    break;
            }
        }

        private void parserJSON(String obj) {
            groups.clear();
            childs.clear();
            if (obj != null) {
                Gson gson = new Gson();
                IMoocBean iMoocBean = gson.fromJson(obj, IMoocBean.class);
                for (int i=0;i<iMoocBean.getData().size();i++){
                    groups.add(iMoocBean.getData().get(i).getName());
                    ArrayList<IMoocBean.DataBean> dataBeans=new ArrayList<>();
                    dataBeans.add(iMoocBean.getData().get(i));
                    childs.add(dataBeans);
                }

            }
        }
    };
    private ExPanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groups=new ArrayList<>();
        childs=new ArrayList<>();

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Http http = new Http(url,handler);
                http.get();*/
                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder().url(url).build();
                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            if(response!=null){
                                String jsonString=response.body().string();
                                Log.e("Tag",jsonString);
                                try {
                                    JSONObject jsonObject=new JSONObject(jsonString);
                                    String dataString=jsonObject.getString("data");
                                    Log.e("dataString",dataString);
                                    Gson gosn=new Gson();
                                    List<IMoocBean> lists=gosn.fromJson(dataString,new TypeToken<List<IMoocBean>>(){}.getType());
                                    /*for(int i=0;i<lists.size();i++){
                                        IMoocBean iMoocBean=lists.get(i);
                                        Log.d("Imoocbean",iMoocBean.toString());
                                    }*/

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                    }
                });

            }
        });
        ExpandableListView expandableListView= (ExpandableListView) findViewById(R.id.expn);
        adapter = new ExPanAdapter(groups,childs,MainActivity.this);
        expandableListView.setAdapter(adapter);

    }
}
