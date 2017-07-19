package len.com.k3query;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;

import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import len.com.k3query.service.k3queryservice;


public class LodActivity extends AppCompatActivity {
    private static final String insertJavaScript5 = "javascript:document.getElementsByClassName(\"PositionRelative\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript6 = "javascript:document.getElementsByClassName(\"img2\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript7 = "javascript:document.getElementById(\"forget\").style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript8 = "javascript:var d='[{\"goodsId\":\"1000960\",\"goodsNum\":\"1\",\"goodsItem\":\"11711\"}]';sessionStorage.setItem('goodsInfo',d);";
    private static final String in1 = "javascript:document.getElementById(\"hasAddress\").style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String in2 = "javascript:document.getElementsByClassName(\"mui-table-view mui-media MB20 BkgWhite\")[0].style.display='none'; document.getElementsByClassName('trans')[0].style.display='none';";
    private NavigationView navigation;
    private DrawerLayout viewById;
    private Toolbar viewById1;
    private WebView viewById2;
    String addressId;
    String yhid;
    Boolean isgetaddr = true;
    Boolean fengkuang=false;

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                textView.setText(cache);
                if (fengkuang){
                    commitdingdan("", "25299",false);
                }
              
                try {
                    if (cache.contains("有货")) {
                        commitdingdan("", "25299",false);
                    }
                } catch (Exception e) {
                }
                if (cache.length() > 300) {
                    cache = "";
                }
            }


            super.handleMessage(msg);
        }
    };

    String cache;
    String b;
    private TextView textView;
    private FloatingActionButton viewById3;
    private Intent iiintent;


    @Override
    protected void onStart() {
        super.onStart();
        navigation.setCheckedItem(R.id.zhixinahui);
        viewById1.setTitle("小智爱k3");
        viewById1.setTitleTextColor(Color.parseColor("#FFFFFF"));

    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 500);

            String string = getSharedPreferences("k3", MODE_PRIVATE).getString("p", "异常");
            cache = cache + string + "\n";

            Message obtain = Message.obtain();
            obtain.what = 0;
            h.sendMessage(obtain);

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdmain);

        iiintent = new Intent(this,k3queryservice.class);
        startService(iiintent);
        handler.postDelayed(runnable, 500);


        textView = (TextView) findViewById(R.id.md_tv);
        viewById3 = (FloatingActionButton) findViewById(R.id.fab_init);
        viewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getyouhui();
                getaddr();
            }
        });






        viewById1 = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton viewById = (FloatingActionButton) findViewById(R.id.fab_f5);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitdingdan("", "25299",true);
//                if (isgetaddr) {
//                    getaddr();
//                    isgetaddr=false;
//                }
                viewById2.loadUrl(insertJavaScript8);
                viewById2.loadUrl("http://www.zhihuiup.com/toOrderPage/confirmOrder");


            }
        });

        navigation = (NavigationView) findViewById(R.id.navigation_view_md);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.setChecked(true);
                int itemId = item.getItemId();
                if (itemId == R.id.shangpinshop) {
                    startActivity(new Intent(LodActivity.this, MainActivity.class));
                }
                LodActivity.this.viewById.closeDrawer(GravityCompat.START);

                return false;
            }
        });
        setSupportActionBar(viewById1);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#CDD7E6\">" + "Home" + "</font>"));
        this.viewById = (DrawerLayout) findViewById(R.id.dlout);
        viewById1.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        viewById1.setNavigationOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Snackbar.make(v, "xx", Snackbar.LENGTH_LONG).show();
                LodActivity.this.viewById = (DrawerLayout) findViewById(R.id.dlout);
                LodActivity.this.viewById.openDrawer(GravityCompat.START, true);
            }
        });
        viewById2 = (WebView) findViewById(R.id.myweb);
        viewById2.getSettings().setJavaScriptEnabled(true);
        viewById2.loadUrl("http://www.zhihuiup.com/toUserPage/login");
        viewById2.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                viewById2.loadUrl(insertJavaScript5);
                viewById2.loadUrl(insertJavaScript6);
                viewById2.loadUrl(insertJavaScript7);
                viewById2.loadUrl(insertJavaScript8);
                viewById2.loadUrl(in1);
                //viewById2.loadUrl(in2);
                viewById2.setBackgroundColor(0x00000000);

                CookieManager instance = CookieManager.getInstance();
                String cookie = instance.getCookie("www.zhihuiup.com");
                if (cookie.contains("SHOP_USER_SESSION")) {
                    try {
                        int sess = cookie.indexOf("SHOP_USER_SESSION");
                        String JSESSIONID2 = cookie.substring(12, sess);
                        String substring = cookie.substring(sess);
                        String substring1 = substring.substring(18);
                        getSharedPreferences("cookie", MODE_PRIVATE).edit().putString("SHOP_USER_SESSION", substring1).putString("JSESSIONID2", JSESSIONID2).apply();
                        //viewById2.setVisibility(View.INVISIBLE);


                    } catch (Exception r) {
                    }
                }

                super.onPageFinished(view, url);
            }
        });


    }


    public void commitdingdan(String p, String address,boolean ok) {
        if (ok){
            viewById2.loadUrl(insertJavaScript8);
            viewById2.loadUrl("http://www.zhihuiup.com/toOrderPage/confirmOrder");
        }
        String px = "";
        String web = "http://www.zhihuiup.com/weborder/order/addShoppingOrder";
        String wx = "http://www.zhihuiup.com/wxorder/order/addShoppingOrder";

        String user = getSharedPreferences("cookie", MODE_PRIVATE).getString("SHOP_USER_SESSION", "-1");
        String sess = getSharedPreferences("cookie", MODE_PRIVATE).getString("JSESSIONID2", "-1");
        String addr = getSharedPreferences("cookie", MODE_PRIVATE).getString("addr", "-1");
        px = getSharedPreferences("cookie", MODE_PRIVATE).getString("youhui", "-1");
        if (user.equals("-1") || sess.equals("-1")) {
            Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
        }
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        BasicClientCookie newCookie = new BasicClientCookie("SHOP_USER_SESSION", user);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie);
        BasicClientCookie newCookie1 = new BasicClientCookie("JSESSIONID2", sess);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie1);


        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient asyncHttpClient2 = new AsyncHttpClient();

        asyncHttpClient.setCookieStore(myCookieStore);
        asyncHttpClient2.setCookieStore(myCookieStore);
        RequestParams parms = new RequestParams();
        parms.add("from", "WEB");
        parms.add("dataType", "NOW");
        parms.add("goodsItemId", "11711");
        parms.add("goodsItemNum", "1");
        parms.add("shoppingCartId", "undefined");
        parms.add("addressId", addr);
        parms.add("invoiceType", "1");
        parms.add("invoiceHeader", "");
        parms.add("couponId", px);
        parms.add("channelNo", "100");
        parms.add("storeNo", "null");
        parms.add("buyType", "2");
        parms.add("ver", "1");
        parms.add("coupItemIds", "");

        RequestParams parms2 = new RequestParams();
        parms2.add("from", "WEB");
        parms2.add("dataType", "NOW");
        parms2.add("goodsItemId", "11711");
        parms2.add("goodsItemNum", "1");
        parms2.add("shoppingCartId", "undefined");
        parms2.add("addressId", addr);
        parms2.add("invoiceType", "1");
        parms2.add("invoiceHeader", "");
        parms2.add("couponId", "");
        parms2.add("channelNo", "101");
        parms2.add("storeNo", "null");
        parms2.add("buyType", "2");
        parms2.add("ver", "1");
        parms2.add("coupItemIds", px+":11711");


        asyncHttpClient.post(wx, parms, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                if (!fengkuang){
                    Toast.makeText(LodActivity.this, s, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

        asyncHttpClient2.post(web, parms2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                if (!fengkuang){
                    Toast.makeText(LodActivity.this, s, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


    public void getyouhui() {
        String geta = "http://www.zhihuiup.com/wxuser/coupons/getCoupontypesListToOrder";
        String user = getSharedPreferences("cookie", MODE_PRIVATE).getString("SHOP_USER_SESSION", "-1");
        String sess = getSharedPreferences("cookie", MODE_PRIVATE).getString("JSESSIONID2", "-1");
        if (user.equals("-1") || sess.equals("-1")) {
            Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("from", "WX");
        params.add("goodsItemId", "11711:1");
       // params.add("goodsItemId", "11729:1");
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        BasicClientCookie newCookie = new BasicClientCookie("SHOP_USER_SESSION", user);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie);
        BasicClientCookie newCookie1 = new BasicClientCookie("JSESSIONID2", sess);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie1);
        asyncHttpClient.setCookieStore(myCookieStore);
        asyncHttpClient.post(geta, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                JSONObject jsonObject = null;
                //Toast.makeText(LodActivity.this, s, Toast.LENGTH_SHORT).show();
                Log.e("k3",s);
                try {
                    jsonObject = new JSONObject(s);
                  String  yh1 = jsonObject.getString("resCoupList");
                    JSONArray jsonArray = new JSONArray(yh1);
                    for (int ia=0;ia<jsonArray.length();ia++){
                        JSONObject o = (JSONObject) jsonArray.get(ia);
                        String name = o.getString("name");
                       yhid = o.getString("couponId");
                        String money= o.getString("money");
                        if (money.equals("300")){
                            getSharedPreferences("cookie", MODE_PRIVATE).edit().putString("youhui", yhid).apply();
                            Toast.makeText(LodActivity.this, "优惠卷获取成功"+yhid, Toast.LENGTH_SHORT).show();
                        }
                        Log.e("name",name+yhid+"  "+money);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    public void getaddr() {
        String geta = "http://www.zhihuiup.com/wxuser/users/getDefaultAddress";
        String user = getSharedPreferences("cookie", MODE_PRIVATE).getString("SHOP_USER_SESSION", "-1");
        String sess = getSharedPreferences("cookie", MODE_PRIVATE).getString("JSESSIONID2", "-1");
        if (user.equals("-1") || sess.equals("-1")) {
            Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("from", "WX");
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        BasicClientCookie newCookie = new BasicClientCookie("SHOP_USER_SESSION", user);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie);
        BasicClientCookie newCookie1 = new BasicClientCookie("JSESSIONID2", sess);
        newCookie.setDomain("www.zhihuiup.com");
        newCookie.setPath("/");
        myCookieStore.addCookie(newCookie1);
        asyncHttpClient.setCookieStore(myCookieStore);
        asyncHttpClient.post(geta, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s = new String(bytes);
                JSONObject jsonObject = null;
                Toast.makeText(LodActivity.this, s, Toast.LENGTH_SHORT).show();
                try {
                    jsonObject = new JSONObject(s);
                    addressId = jsonObject.getString("addressId");
                    getSharedPreferences("cookie", MODE_PRIVATE).edit().putString("addr", addressId).apply();
                    //Toast.makeText(LodActivity.this, "获取地址成功" + addressId, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }








    private void clearcache() {
        //清空所有Cookie,删除所有缓存
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now0
        viewById2.clearCache(true);
        getApplicationContext().deleteDatabase("WebView.db");
        getApplicationContext().deleteDatabase("WebViewCache.db");
        getSharedPreferences("tmp", MODE_PRIVATE).edit().putString("s", "null").apply();
    }

    public void stooop(View view) {


        stopService(iiintent);
        handler.removeCallbacks(runnable);
    }

    public void feng(View view) {
        
        fengkuang=true;
        Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
    }
}
