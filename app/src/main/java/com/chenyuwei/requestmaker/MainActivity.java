package com.chenyuwei.requestmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestMaker.init(this);
        RequestMaker.BASE_URl = "http://www.mocky.io/v2/";

        findViewById(R.id.btnGet).setOnClickListener(this);
        findViewById(R.id.btnPost).setOnClickListener(this);
        findViewById(R.id.btnDialog).setOnClickListener(this);
        findViewById(R.id.btnFail).setOnClickListener(this);
        findViewById(R.id.btnError).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGet:
                /**
                 * 请求的全路径为：http://www.mocky.io/v2/589be3b61000002f1b66e61b
                 * 第二个参数是请求方式，有GET和POST可选
                 * 第三个参数是partUrl，真正的请求路径是BaseUrl+partUrl，这里的请求的全路径为：http://www.mocky.io/v2/589be3b61000002f1b66e61b
                 * 第四个参数是调试Log的Tag，当不设置此参数时，没有调试信息，当设置此参数是，会在Logcat中打印出本次请求的调试信息
                 * */
                new RequestMaker(this, RequestMaker.Method.GET, "589be3b61000002f1b66e61b","city/list") {
                    @Override
                    protected void onSuccess(String response) throws JSONException {
                        //response是请求成功后返回的信息
                        toast(response);
                    }

                    @Override
                    protected void setParams(HashMap<String, String> map) {
                        /**
                         * 这里用于设置请求参数
                         * 这里相当于设置了两个请求参数,http://www.mocky.io/v2/589be3b61000002f1b66e61b?user_id=7&user_name=vivi
                         * */
                        map.put("user_id","7");
                        map.put("user_name","vivi");
                    }
                };
                break;
            case R.id.btnPost:
                //同上
                new RequestMaker(this, RequestMaker.Method.POST, "589be3b61000002f1b66e61b") {
                    @Override
                    protected void onSuccess(String response) throws JSONException {
                        toast(response);
                    }
                    @Override
                    protected void setParams(HashMap<String, String> map) {
                        map.put("id","7");
                        map.put("token","dnOJ4BJk8VXUY1OYH9");
                    }
                };
                break;
            case R.id.btnDialog:
                /**
                 * 设置最后一个参数为true来让请求开始时弹出一个等待dialog，当请求成功或失败时此dialog自动消失。
                 * */
                new RequestMaker(this, RequestMaker.Method.POST, "589be3b61000002f1b66e61b",true) {
                    @Override
                    protected void onSuccess(String response) throws JSONException {
                        toast(response);
                    }
                };
                break;
            case R.id.btnFail:
                new RequestMaker(this, RequestMaker.Method.POST, "233333") {
                    @Override
                    protected void onSuccess(String response) throws JSONException {
                        toast(response);
                    }

                    @Override
                    protected void onFail() {
                        //super.onFail();中会自动弹出一个信息为【网络请求失败】的Toast，可以重写onFail()来单独定制此时的动作
                        //super.onFail();
                        toast("网络请求失败了呢╮(￣▽￣)╭");
                    }
                };
                break;
            case R.id.btnError:
                new RequestMaker(this, RequestMaker.Method.POST, "589be3501000001e1b66e61a") {
                    @Override
                    protected void onSuccess(String response) throws JSONException {
                        toast(response);
                    }

                    @Override
                    protected void onError(int code, String message) {
                        //super.onFail();中会自动弹出一个信息为【message】的Toast，可以重写onError()来单独定制此时的动作
                        //super.onError(code, message);
                        toast("网络请求错误，code=" + code + "  message=" + message);
                    }
                };
                break;
        }
    }

    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
