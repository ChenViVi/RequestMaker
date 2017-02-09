# RequestMaker

对Volley框架的二次封装。

## 优点

代码更精简，5行代码完成一个请求

自带调试模式，再也不用纠结到底是写接口的人错了还是你错了

自动抓取错误码和错误信息，并可在此时做出指定动作

## 注意

本项目只适用于如下返回格式的接口，若格式不同，需要自行改造

请求成功
```
{
    "state": 1,
    "data": [
        {
            "id": "2",
            "area_name": "北京"
        },
        {
            "id": "22",
            "area_name": "天津"
        },
        {
            "id": "46",
            "area_name": "河北"
        },
        {
            "id": "486",
            "area_name": "辽宁"
        }
    ]
}
```

请求失败
```
{
    "state": 0,
    "code": 9,
    "error_msg": "参数不全"
}
```

字段解释

| 名称 | 解释 |
| --- | --- |
| state | 状态码。state=1时，请求成功，执行onSuccess(String response)方法。state=0时，请求失败，执行onError(int code, String message)
| data | 返回数据的主体内容，在onSuccess(String response)中以response对象来储存
| code | 错误码，错误信息的唯一标识，在onError(int code, String message)中以code对象来储存
| error_msg | 错误信息描述，在onError(int code, String message)中以message对象来储存


## 用法示例


详细用法[https://github.com/ChenViVi/RequestMaker/blob/master/app/src/main/java/com/chenyuwei/requestmaker/MainActivity.java](https://github.com/ChenViVi/RequestMaker/blob/master/app/src/main/java/com/chenyuwei/requestmaker/MainActivity.java)


首先需要在App入口初始化
```
RequestMaker.init(this);
```

设置BASE_URl（可选）
```
RequestMaker.BASE_URl = "http://www.mocky.io/v2/";
```

请求成功
```
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
```
Logcat中如下图所示
![img](http://p1.bqimg.com/4851/0b060d059f1337ec.png)


显示稍等dialog

```
/**
 * 设置最后一个参数为true来让请求开始时弹出一个等待dialog，当请求成功或失败时此dialog自动消失。
 * */
new RequestMaker(this, RequestMaker.Method.POST, "589be3b61000002f1b66e61b",true) {
    @Override
    protected void onSuccess(String response) throws JSONException {
        toast(response);
    }
};
```

请求失败

```
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
```

请求错误
```
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
```
