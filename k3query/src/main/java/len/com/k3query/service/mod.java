package len.com.k3query.service;

import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class mod {
    String ok;
    String s1;

    public  String get(String uid,String city, String sakuid,String vendid,String cat){

       AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        String url="https://c0.3.cn/stock?skuId="+sakuid+"&venderId="+vendid+"&cat="+cat+"&area="+city+"&buyNum=1&extraParam={%22originid%22:%221%22}&ch=1&pduid="+uid;
        Log.e("uuuuuuuuuu",url);
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {

            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                String s ;
                try {
                    s = new String(bytes,"gbk");
                    SimpleDateFormat formatter    =   new    SimpleDateFormat    ("HH:mm:ss:  SSS ");
                    Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
                    String    str    =    formatter.format(curDate);
                    if (s.contains("有货")||s.contains("现货")){
                        System.err.println("有货");

                        if (s.length()>800){
                            ok=str+"  有货";
                        }else {
                            ok=str+"  异常";
                        }
                    }else {
                        ok=str+"  无货";
                    }
                } catch (UnsupportedEncodingException e) {
                }
            }
            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return  ok+"";


    }
    public String k3post (){

        String u="http://www.zhihuiup.com/webgoods/goods/findGoodsById";
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        RequestParams parms = new RequestParams();
        parms.add("from","WEB");
        parms.add("goodsId","1000960");
        parms.add("goodsText1","1");
        parms.add("goodsText2","4");
        asyncHttpClient.post(u, parms,new AsyncHttpResponseHandler() {

            private JSONObject jsonObject1;

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
               // Log.e("ss",s);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    String goodsItemDto1 = jsonObject.getString("data");
                    jsonObject1 = new JSONObject(goodsItemDto1);
                    s1 = jsonObject1.getString("storage");
                  //  Log.e("aaa", s1);
                } catch (JSONException e) {
                }
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                s1="-1";
            }
        });

        return s1;
    }

}
