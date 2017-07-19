package len.com.k3query;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import len.com.k3query.Bean.ShangPinBean;
import len.com.k3query.databaseDao.Badao;
import len.com.k3query.service.queryservice;
import len.com.k3query.utils.Sbar;
import len.com.k3query.utils.ShellUtils;
import len.com.k3query.utils.ThreadManager;
import len.com.k3query.utils.helpActivty;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    public int TIME = 500;
    String cache;
    private TextView viewById;
    private static final String insertJavaScript = "javascript:document.getElementsByClassName(\"search-tb\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript1 = "javascript:document.getElementsByClassName(\"swiper-wrapper J_ping\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript2 = "javascript:document.getElementsByClassName(\"hotsearch_head\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript10 = "javascript:document.getElementsByClassName(\"tags\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript11 = "javascript:document.getElementsByClassName(\"price\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript12 = "javascript:document.getElementsByClassName(\"similar_goods\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript13 = "javascript:document.getElementsByClassName(\"search-box\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript14 = "javascript:document.getElementsByClassName(\"filter\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript15 = "javascript:document.getElementsByClassName(\"preview\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript16 = "javascript:document.getElementsByClassName(\"pro-name\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript17 = "javascript:document.getElementsByClassName(\"cxy\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript18 = "javascript:document.getElementsByClassName(\"cxs\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript19 = "javascript:document.getElementsByClassName(\"pro-price\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript20 = "javascript:document.getElementsByClassName(\"pro-zi\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript21 = "javascript:document.getElementsByClassName(\"xssp\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private static final String insertJavaScript22 = "javascript:document.getElementsByClassName(\"list-con\")[0].style.display=\"none\"; document.getElementsByClassName('trans')[0].style.display='none';";
    private  static  final  String ijs="javascript: document.getElementById(\"content_29_trynow\").click(); document.getElementsByClassName('trans')[0].style.display='none'; ";
    private static final String insertJavaScript23 = "javascript:aa();function aa(){document.querySelector(\".product-intro\").style=\"margin-top:0px;padding:0px;\";\n" +
            "document.querySelector(\".m-item\").style=\"float:none;width:5000px;\";\n" +
            "document.querySelector(\".m-item-inner\").style=\"margin:0px;\";\n" +
            "document.querySelector(\".btn-buy\").style=\"height:5000px;border-radius: 0px;\";}";


    boolean startapp = true;
    long first;
    int count;
    boolean lock = true;


    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {

                viewById.setText(cache);
                if (cache.length() >= 500) {
                    cache = "";
                }
            }
        }
    };
    private WebView html;
    boolean click = false;
    boolean htmload = true;
    private String s;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    boolean accessenable = false;
    private TextView vid;
    private String deviceId;
    String intenturl = "0";
    private String s1;
    private String a;
    private EditText skuid;
    private EditText vend;
    private EditText cat;
    private String url;
    private NavigationView navigation;
    private Toolbar viewById1;



    @Override
    protected void onStart() {
        super.onStart();
        navigation.setCheckedItem(R.id.shangpinshop);
        viewById1.setTitle("商品添加选择页");
        viewById1.setTitleTextColor(Color.parseColor("#FFFFFF"));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        powerManager = (PowerManager) this.getSystemService(Service.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Lock");
        wakeLock.setReferenceCounted(false);
        setContentView(R.layout.main);







        viewById1 = (Toolbar) findViewById(R.id.toolbar);
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavigationIteonNaonNavigationItemSelectedvigationItemSelectedmSelected(item);
                return false;
            }
        });
        setSupportActionBar(viewById1);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#CDD7E6\">" + "Home" + "</font>"));
        seticon();
        viewById1.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        viewById1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout viewById = (DrawerLayout) findViewById(R.id.dlout);
                viewById.openDrawer(GravityCompat.START, true);
            }
        });

        loadjd();
        intent = new Intent(MainActivity.this, queryservice.class);
        this.viewById = (TextView) findViewById(R.id.tv1);

        if (intenturl.equals("0")) {
            Sbar.LongSnackbar(this.viewById, "Welcome,请按照右侧问号获取启动参数").show();
        } else {

            startService(intent);
            handler.postDelayed(runnable, TIME); //每隔1s执行
        }
        vid = (TextView) findViewById(R.id.tvvv);
        vid.setText("请先阅读帮助说明!!!");
        vid.setTextColor(Color.parseColor("#FD0011"));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearcache();
    }

    @SuppressLint("NewApi")
    private void clearcache() {
        //清空所有Cookie,删除所有缓存
        CookieSyncManager.createInstance(this);  //Create a singleton CookieSyncManager within a context
        CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
        cookieManager.removeAllCookie();// Removes all cookies.
        CookieSyncManager.getInstance().sync(); // forces sync manager to sync now0
        cookieManager.setAcceptThirdPartyCookies(html, true);

        html.setWebChromeClient(null);
        html.setWebViewClient(null);
        html.getSettings().setJavaScriptEnabled(false);
        html.clearCache(true);
        getApplicationContext().deleteDatabase("WebView.db");
        getApplicationContext().deleteDatabase("WebViewCache.db");
        getSharedPreferences("tmp", MODE_PRIVATE).edit().putString("s", "null").apply();
    }

    @SuppressLint("JavascriptInterface")
    public void loadjd() {
        html = (WebView) findViewById(R.id.wwview);
        html.setVisibility(View.INVISIBLE);
        html.setFocusableInTouchMode(true);
        html.addJavascriptInterface(this, "ww");
        html.setScrollContainer(false);
        html.setHorizontalScrollBarEnabled(false);
        html.setVerticalScrollBarEnabled(true);

        WebSettings settings = html.getSettings();
        settings.setUseWideViewPort(true);//关键点
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settings.setLoadWithOverviewMode(true);

        final String fanli = getSharedPreferences("f", MODE_PRIVATE).getString("f", "0");
        if (fanli.contains("https://union-click.jd.com/jdc?d=")) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get("http://221.214.179.216:8000/jd.txt", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String s = new String(bytes);
                    if (s.contains("ok")) {
                        html.loadUrl(fanli);
                    } else if (s.contains("no")) {
                        html.loadUrl("https://union-click.jd.com/jdc?d=j8DTtl");



                    }
                }


                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "网络出错,请退出重启软件", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            html.loadUrl("https://union-click.jd.com/jdc?d=j8DTtl");
        }
        html.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://") || url.startsWith("http://")) {
                    view.loadUrl(url);
                } else
                {
                    intenturl = url;
                    Sbar.LongSnackbar(view, "获取启动参数成功,欢迎使用").show();
                    TextView ceshu = (TextView) findViewById(R.id.tvvv_canshu);
                    ceshu.setText("  成功获取参数");
                    ceshu.setTextColor(Color.parseColor("#FF05FD75"));
                    CookieManager cookieManager = CookieManager.getInstance();
                    String CookieStr = cookieManager.getCookie("jd.com");
                    //int jdu = CookieStr.indexOf("_jdu=");
                    SharedPreferences.Editor cook = getSharedPreferences("cook", MODE_PRIVATE).edit();
                    cook.putString("jdtg",CookieStr).apply();
                    cook.apply();
                   // String substring0 = CookieStr.substring(jdu, jdu + 15);
                    //String substring1 = substring0.substring(5);
                    SharedPreferences tmp = getSharedPreferences("tmp", MODE_PRIVATE);
                    //tmp.edit().putString("jduid", substring1).apply();

                    if (getSharedPreferences("jd",MODE_PRIVATE).getBoolean("jd",true)){
                        jddnotime();
                        Toast.makeText(MainActivity.this, "首次获取参数成功,启动测试,请按返回键", Toast.LENGTH_LONG).show();
                        getSharedPreferences("jd",MODE_PRIVATE).edit().putBoolean("jd",false).apply();
                    }
                    try {
                        Intent intent;
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    } catch (Exception e) {
                    }
                }
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("URL", "==" + url);
                if (url.contains("item.m.jd.com/product")){
                    //   html.loadUrl(ijs);
                }

                if (url.startsWith("https://re.m.jd.com/cps")) {


                    //setMouseClick(1200, 1500);
                    html.loadUrl(insertJavaScript);
                    html.loadUrl(insertJavaScript1);
                    html.loadUrl(insertJavaScript2);
                    html.loadUrl(insertJavaScript10);
                    html.loadUrl(insertJavaScript11);
                    html.loadUrl(insertJavaScript12);
                    html.loadUrl(insertJavaScript13);
                    html.loadUrl(insertJavaScript14);
                    html.loadUrl(insertJavaScript15);
                    html.loadUrl(insertJavaScript16);
                    html.loadUrl(insertJavaScript17);
                    html.loadUrl(insertJavaScript18);
                    html.loadUrl(insertJavaScript19);
                    html.loadUrl(insertJavaScript20);
                    html.loadUrl(insertJavaScript21);
                    html.loadUrl(insertJavaScript22);
                    html.loadUrl(insertJavaScript23);



                    html.setBackgroundColor(0x00000000);

                    if (click) {
                        html.setVisibility(View.VISIBLE);
                        html.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                int action = motionEvent.getAction();
                                if (action == 0) {
                                    html.setVisibility(View.INVISIBLE);
                                }
                                return false;
                            }
                        });


                    }
                    click = true;


                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //  html.loadUrl(insertJavaScript);


            }
        });


    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME);
            SharedPreferences sharedPreferences = getSharedPreferences("tmp", MODE_PRIVATE);
            s = sharedPreferences.getString("s", "? ");
            try {
                if (s.contains("有货") && htmload) {
                    jdd();
                }
            } catch (Exception e) {
            }
            cache = cache + s + "\n";
            Message m = Message.obtain();
            m.what = 1;
            h.sendMessage(m);

        }
    };

    public void setMouseClick(int x, int y) {
        //模拟点击事件
        MotionEvent evenDownt = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_DOWN, x, y, 0);
        dispatchTouchEvent(evenDownt);
        MotionEvent eventUp = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis() + 100, MotionEvent.ACTION_UP, x, y, 0);
        dispatchTouchEvent(eventUp);
        evenDownt.recycle();
        eventUp.recycle();
    }public void jdd() {
        // String string = getSharedPreferences("url", MODE_PRIVATE).getString("url", "1");
        if (intenturl.equals("0")) {
            Sbar.LongSnackbar(viewById, "没有获取到启动参数,请按照右侧说明获取").show();
        }
        String ownid = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");
        url = intenturl.replace("3959251", ownid);

        try {
            Intent intent;
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(null);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //如果两次时间超过10s,时间重置
            if (count != 0 && System.currentTimeMillis() - first > 30000) {
                count = 0;
            }
            count++;
            if (count == 1) {
                first = System.currentTimeMillis();
                startActivity(intent);
            } else if (count == 2) {
            }
        } catch (Exception e) {
        }
    }

    public void jddnotime() {
        //String string = getSharedPreferences("url", MODE_PRIVATE).getString("url", "1");
        if (intenturl.equals("0")) {
            Sbar.LongSnackbar(viewById, "没有获取到启动参数,请按照右侧说明获取").show();

        }

        String ownid = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");
        url = intenturl.replace("3959251", ownid);
        try {

            Intent intent;
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setComponent(null);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
        }
    }


    //-----------------判断辅助功能是否开启
    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" ;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            //Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            //  Log.e(TAG, "Error finding setting, default accessibility to not found: "
            //+ e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            //Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    //  Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        //   Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            // Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }


    private void seticon() {
        Badao instance = Badao.getInstance(getApplicationContext());
        List<ShangPinBean> findall = instance.findall();
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.fab_add);
        FloatingActionButton shop = (FloatingActionButton) findViewById(R.id.fab_sping);
        FloatingActionButton loc = (FloatingActionButton) findViewById(R.id.fab_loc);
        FloatingActionButton hel = (FloatingActionButton) findViewById(R.id.fab_help);
        hel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,helpActivty.class));
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                wakeLock.acquire();
                startActivity(new Intent(MainActivity.this, ChooseItem.class));

            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jddnotime();
                //ShellUtils.execCommand("am force-stop com.jingdong.app.mall",true);
                ///ShellUtils.execCommand("am start -n com.jingdong.app.mall/com.jingdong.app.mall.shopping.ShoppingCartNewActivity",true);
                //ShellUtils.execCommand("am start -n com.jingdong.app.mall/com.jingdong.app.mall.settlement.view.activity.NewFillOrderActivity   ",true);

            }
        });
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                wakeLock.release();

                //viewById.setKeepScreenOn(true);

                final String[] single_list = {"北京", "上海", "天津", "重庆", "河北", "山西", "河南", "辽宁", "吉林 ", "黑龙江", "内蒙古", "江苏", "山东", "安徽", "浙江", "福建", "湖北", "湖南", "广东", "广西", "江西", "四川", "海南", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾"};


                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请选择省份");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setSingleChoiceItems(single_list, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str = single_list[which];
                        Snackbar.make(view, str + "  设置成功", Snackbar.LENGTH_LONG).show();

                        getSharedPreferences("sheng", MODE_PRIVATE).edit().putString("sheng", which + "").apply();
                        getSharedPreferences("sheng", MODE_PRIVATE).edit().putString("city", str).apply();
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    public boolean onNavigationIteonNaonNavigationItemSelectedvigationItemSelectedmSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.account) {
            if (intenturl.equals("0")) {
                Sbar.LongSnackbar(viewById, "没有获取到启动参数,请按照说明获取").show();
                AlertDialog.Builder la=new AlertDialog.Builder(this);
                la.setTitle("说明");
                la.setMessage("尊敬的用户您好,新增账号模式的使用方法为,先添加您的商品,重启后,点击红点获取到启动参数后,进入,添加您的账号,--长按--账号弹出菜单,建议选择--登陆加车生单选项--,点击开始抢购后,软件每6秒提交一次订单,所以,您可以添加6个以上的账号,先登陆成功,然后,每1s开始一个账号的抢购,这样,每秒都可以提交一次订单了,祝您好运");
                la.show();
            }else {
                startActivity(new Intent(MainActivity.this, FanLi.class));
            }

        }

        if (id == R.id.set) {
            startActivity(new Intent(MainActivity.this, settingactivity.class));
        }

        if (id==R.id.update){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://pan.baidu.com/s/1qXSrZtU");
            intent.setData(content_url);
            startActivity(intent);

        }
        if (id == R.id.juanz) {
            AlertDialog.Builder a = new AlertDialog.Builder(this);
            a.setTitle("捐赠我");
            a.setMessage("如果您觉得软件不错,您可以通过捐赠的方式来赞助我,我会把软件做的更好,谢谢");
            a.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    android.text.ClipboardManager cm = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText("#吱口令#长按复制此条消息，打开支付宝即可添加我为好友eVDu6f64j7");

                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                    try {
                        startActivity(LaunchIntent);
                    } catch (Throwable localThrowable2) {
                        Toast.makeText(MainActivity.this, "咱好像并没有安装支付宝宝啊", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(LaunchIntent);
                    Toast.makeText(MainActivity.this, "正在启动支付宝.....", Toast.LENGTH_SHORT).show();
                }
            });

            a.setNegativeButton("下次再说吧", null);
            a.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dlout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
