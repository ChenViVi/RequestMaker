package com.chenyuwei.requestmaker.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private TextView name;
    private TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);

        new RequestMaker<List<Person>>(this,"tag") {
            @Override
            protected Call<BaseEntity<List<Person>>> onRequest(RetrofitService service,Map<String, String> map) {
                map.put("id", "123");
                map.put("name", "gesanri");
                return service.getUsers(map);
            }

            @Override
            protected void onSuccess(List<Person> response) {
                for (int i = 0; i < response.size(); i++) {
                    Person person = response.get(i);
                    if (i == 0) {
                        name.setText("姓名：" + person.getName());
                        age.setText("年龄：" + person.getAge());
                    }
                }
            }
        };
    }
}
