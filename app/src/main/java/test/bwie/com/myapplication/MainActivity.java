package test.bwie.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String url = "http://v.juhe.cn/toutiao/index?type=top&key=55657a83e68652aa6db8e5117482ee91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OkHttputils httpUtils = OkHttputils.getHttpUtils();
        httpUtils.loadDataFromNet(url, Bean.class, new OkHttputils.CallBackListener<Bean>() {
            @Override
            public void onSuccess(Bean result) {
                final List<Bean.ResultBean.DataBean> data = result.result.data;
                MyAdapter myAdapter = new MyAdapter(data, MainActivity.this);
                recyclerView.setAdapter(myAdapter);
                myAdapter.setRecyonItemclick(new MyAdapter.RecyonItemclick() {
                    @Override
                    public void recyonitemclik(int pos) {

                        Toast.makeText(MainActivity.this, data.get(pos).title, Toast.LENGTH_SHORT).show();


                    }
                });

            }

            @Override
            public void onFail() {

            }
        });


    }
}
