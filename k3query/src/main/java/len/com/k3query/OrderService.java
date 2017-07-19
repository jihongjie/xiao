package len.com.k3query;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import len.com.k3query.Bean.Jdaccount;
import len.com.k3query.databaseDao.Badao;
import len.com.k3query.service.httpget;
import len.com.k3query.service.queryservice;


public class OrderService extends Service {

    private List<Jdaccount> accfindcookie;
    private int data1;

    JSONObject   jsonObject = null;
    private Runnable run;
    private SharedPreferences.Editor q;
    private int anInt;
    private int isqq;
    private String ownid;
    private String jdtg;
    private String aidi;
    boolean isokkk=true;

    private static   final  String miaoxiangqing="http://wq.jd.com/mitem/view?sku=";
    private static   final  String miaojiesuanye="https://wqs.jd.com/order/s_confirm_miao.shtml?bid=&scene=jd&isCanEdit=1&EncryptInfo=&Token=&commlist=,,1,";
    private String substring00;
    private String substringseq;
    private int cishu=0;
    private FanLi fanLi;


    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        fanLi = new FanLi();
        q = getSharedPreferences("q", MODE_PRIVATE).edit();
        anInt = getSharedPreferences("root", MODE_PRIVATE).getInt("time", 6000);
        ownid = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");
        aidi = getSharedPreferences("tmp",MODE_PRIVATE).getString("adid","0");
        SharedPreferences cook = getSharedPreferences("cook", MODE_PRIVATE);
        jdtg = cook.getString("jdtg", "");
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
       //String data=intent.getStringExtra("data");//这里的intent是参数里的，不是自定义的
        accfindcookie = Badao.getInstance(getApplicationContext()).accfindall();
        data1 = intent.getIntExtra("data", -1);

        isqq = intent.getIntExtra("isqq", -1);
        for ( int i=0;i<500;i++){
            if (data1==i){
                final int finalI = i;
                run = new Runnable() {
                    public void run() {
                        han.postDelayed(this, anInt);

                        if (isqq==0){
                            subqqorder(finalI);
                           // Badao.getInstance(getApplicationContext()).accfindall().get(finalI).getGtk();
                        }else {
                            suborder(finalI);
                        }

                    }
                };

            }
        }
        han.postDelayed(run, anInt); //每隔1s执行
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy() {
        han.removeCallbacks(run);
        Log.e("服务停止","ok");
        super.onDestroy();
    }
    public void addShopcard(final int pos0) {
        String skuid0 = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");
        String u = "http://cart.jd.com/selectAllItem.action";
        final String ddda="http://cart.jd.com/batchRemoveSkusFromCart.action";
        final String kkh="http://gate.jd.com/InitCart.aspx?pid="+skuid0+"&pcount=1&ptype=1";
        List<Jdaccount> accfindcookie2 = Badao.getInstance(getApplicationContext()).accfindall();
        final AsyncHttpClient asyncHttpClient0 = new AsyncHttpClient();
        asyncHttpClient0.addHeader("Cookie", accfindcookie2.get(pos0).getCookie()+jdtg);
        asyncHttpClient0.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        asyncHttpClient0.get(u, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                asyncHttpClient0.get(ddda, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        asyncHttpClient0.get(kkh, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }



    public void suborder(final int pos) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        asyncHttpClient.addHeader("X-Requested-With", "XMLHttpRequest");
        asyncHttpClient.addHeader("DNT", "1");
        asyncHttpClient.addHeader("Content-Type", "application/x-www-form-urlencoded");
        asyncHttpClient.addHeader("Cookie", accfindcookie.get(pos).getCookie()+jdtg);
        asyncHttpClient.addHeader("Accept-Encoding", "gzip, deflate, br");
        asyncHttpClient.addHeader("Origin", "https://trade.jd.com");
        asyncHttpClient.addHeader("Referer", "https://trade.jd.com/shopping/order/getOrderInfo.action?rid=1492678359738");
        asyncHttpClient.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        asyncHttpClient.get("http://trade.jd.com/shopping/order/submitOrder.action", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                SimpleDateFormat formatter    =   new    SimpleDateFormat    ("HH:mm:ss:  SSS ");
                Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
                String    str    =    formatter.format(curDate);
                String s = new String(bytes);
                if (s.contains("{\"error\":\"NotLogin\"}")){
                    //Toast.makeText(OrderService.this, "账号未登录或登陆过期", Toast.LENGTH_SHORT).show();
                    Badao.getInstance(getApplicationContext()).moifydenglu(pos);
                    int ai=pos+1;
                    q.putString("z",str+"  第"+ai+"个账号登陆态失效,请重新登陆");

                    //accfindcookie.get(pos).setIsaddshoped("登陆态失效");




                }
                try {
                    jsonObject = new JSONObject(s);
                    String  yh1 = jsonObject.getString("message");
                  //  Toast.makeText(OrderService.this, yh1, Toast.LENGTH_SHORT).show();
                    if (yh1.contains("null")){
                        int ai=pos+1;
                        q.putString("success",str+"  第"+ai+"个账号抢购成功,请付款");
                        boolean aBoolean = getSharedPreferences("root", MODE_PRIVATE).getBoolean("music", false);
                        if (aBoolean){
                            startService(new Intent(OrderService.this,httpget.class));
                        }
                    }else if (yh1.contains("获取用户订单信息失败")){
                        addShopcard(pos);
                    }else {
                        q.putString("z",str+"  "+yh1);
                    }
                    q.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });



    }



    public  void  subqqorder(final int pos ){





        String isa = String.valueOf((Math.random() * 9 + 1) * 1000000000);
        final String dda = isa + "021541";
        String  ismiao="http://wq.jd.com/deal/miao/IsMiao?callback=aa&skuid="+ownid+"&t="+dda;
        final String uur="http://wq.jd.com/deal/morder/orderview?r=0."+dda+"&cid=2&sid=&action=1&type=0&unpl=&useaddr=1&dpid=&addrType=1&paytype=0&infotype=30&reg=1&commlist="+ownid+"%2C%2C1%2C"+ownid+"%2C1%2C0%2C0&cmdyop=0&clearbeancard=1&_="+System.currentTimeMillis()+"&g_tk="+accfindcookie.get(pos).getGtk()+"&g_ty=ajax";
        final AsyncHttpClient miao = new AsyncHttpClient(getSchemeRegistry());
        miao.get(ismiao, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String miaos = new String(bytes);
                if (miaos.contains(ownid)){
                    //秒杀商品
                    cishu = cishu+1;
                    Log.e("cishu",cishu+"");
                    if (cishu>5){
                        isokkk=true;
                        cishu=0;
                    }


                    final String oederurl="http://wqmiao1.jd.com/miaov/miaov/orderview?callback=_cbOrderviewB&bid=&addrType=1&paytype=0&addressid="+aidi+"&commlist=,,1,"+ownid+"&r="+dda+"&scene=jd&&g_tk="+accfindcookie.get(pos).getGtk()+"&g_ty=ls&jttl=1";
                    miao.addHeader("cookie",accfindcookie.get(pos).getQqcookie()+jdtg);
                    miao.get(miaoxiangqing + ownid, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            miao.get(miaojiesuanye + ownid, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    if (isokkk){
                                        miao.get(oederurl, new AsyncHttpResponseHandler() {
                                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                                String s = new String(bytes);
                                                int i1 = s.indexOf("price:{\"1\":");
                                                int i2 = s.indexOf("},priceDesc:{");
                                                int i3 = s.indexOf("\",seq:\"");
                                                int i4 = s.indexOf("\",BuyerInfo:");
                                                substringseq = s.substring(i3 + 7, i4);
                                                substring00 = s.substring(i1 + 11, i2);
                                                isokkk=false;

                                            }
                                            @Override
                                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                            }
                                        });
                                    }
                                        String sub="http://wq.jd.com/miao/confirm?req="+substringseq+"&addressid="+aidi+"&addrType=1&paytype=0&paychannel=4&bid=&sg=&code=&cid=&totalprice="+substring00+"&delivdate=-1&instdate=-1&PPRD_P=LOGID.1496240156990.285233996-UUID.886394248&price="+substring00+"&skuid="+ownid+"&callback=submitCbA&r="+dda+"&g_tk="+accfindcookie.get(pos).getGtk()+"&g_ty=ls";
                                        for (int pp=0;pp<6;pp++){
                                            miao.get(sub, new AsyncHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                                  FanLi.setlogi(new String(bytes));



                                                    //  Toast.makeText(OrderService.this, new String(bytes), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                                }
                                            });
                                        }

                                    }






                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });









                }else {
                    //不是秒杀商品

                    final AsyncHttpClient hhttpClient = new AsyncHttpClient();
                    hhttpClient.addHeader("Accept","text/plain");
                    hhttpClient.addHeader("cookie",accfindcookie.get(pos).getQqcookie()+jdtg);
                    hhttpClient.addHeader("Content-Type", "application/x-www-form-urlencoded");
                    hhttpClient.addHeader("Origin", "http://wqs.jd.com");
                    hhttpClient.get(uur, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            String s = new String(bytes);
                            try {
                                JSONObject j=new JSONObject(s);
                                String token2 = j.getString("token2");

                                String  uu="http://wqdeal2.jd.com/deal/morder2/confirm?paytype=0&paychannel=3&reg=1&seq=&type=0&token2="+token2+"&dpid=&skulist="+ownid+"&scan_orig=&gpolicy=&platprice=0&callback=cbConfirmA&r=0."+dda+"&action=1&commlist="+ownid+",,1,"+ownid+",1,0,0&savepayship=0&ship=0|65|4|||0||1||2016-10-16|15:00-19:00|{%221%22:1,%2235%22:2,%2230%22:1}|2||||&remark=&g_tk="+accfindcookie.get(pos).getGtk()+"&g_ty=ls";
                                hhttpClient.get(uu, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                        String s1 = new String(bytes);
                                        String[] tok0 = s1.split(",");
                                        String a=tok0[1].replaceAll("\"errMsg\":\"","").replaceAll("\"","");
                                        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("HH:mm:ss:  SSS ");
                                        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
                                        String    str    =    formatter.format(curDate);
                                        if (a.equals("")){
                                            int tmp=pos+1;
                                            q.putString("success",str+"  第"+tmp+"个QQ账号抢购成功,请付款");
                                            q.remove("z");
                                        }else {
                                            q.putString("z",str+"  "+a);
                                        }
                                        q.apply();


                                    }

                                    @Override
                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });









    }


    public static SchemeRegistry getSchemeRegistry() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            HttpConnectionParams.setSoTimeout(params, 10000);
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            return registry;
        } catch (Exception e) {
            return null;
        }
    }





    Handler han=new Handler();

}
