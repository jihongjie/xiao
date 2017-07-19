package len.com.k3query;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.List;

import len.com.k3query.Bean.Jdaccount;
import len.com.k3query.databaseDao.Badao;

public class FanLi extends AppCompatActivity {

    private WebView login;
    private apt ad;
    private AsyncHttpClient asyncHttpClient;
    //public static String js1 = "javascript:fillAccount();function fillAccount(){ document.getElementById(\"loginname\").value = '13255528447'; document.getElementById(\"nloginpwd\").value = 'android521'; document.getElementById(\"paipaiLoginSubmit\").click(); }";
    private String ownid;
    private String cookieStr;
    private ListView listView;
    private List<Jdaccount> accfindall;
    private FloatingActionButton b1;
    private TextView tvcookie;
    boolean ok = true;
    boolean isstart = true;
    private PersistentCookieStore myCookieStore;
    private String jdtg = "";
    private BroadcastReceiver mReceiver;
    private static TextView log;
    private String s;
    private String succ;
    private String s11;
    private String succ11;
    private  static String setlogg="";
    public TextView succlog;
    String ppp = "";
    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                ImageView viewById = (ImageView) findViewById(R.id.verycode);
                try {
                    FileInputStream fis = new FileInputStream("/data/data/len.com.k3query/shared_prefs/img.png");
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);///把流转化为Bitmap图片
                    viewById.setImageBitmap(bitmap);
                } catch (Exception e) {
                }
            }

            if (msg.what == 9) {

            }
            if (msg.what == 99) {
                log.setText(s11);
                if (s11.length() > 1000) {
                    s11 = "";
                }
                if (succ11.contains("成功")) {
                    if (succ11.length() > 800) {
                        succ11 = "";
                    }
                    succlog.setText(succ11);
                }

            }
            if (msg.what == 33) {
                final String uui = "https://ssl.ptlogin2.qq.com/pt_open_login?verifycode=";
                uui2 = uui + v + "&u=" + tempusername + "&p=" + ppp + "&pt_randsalt=2&ptlang=2052&low_login_enable=0&u1=http%3A%2F%2Fconnect.qq.com&from_ui=1&fp=loginerroralert&device=2&aid=716027609&pt_3rd_aid=100273020&ptredirect=1&h=1&g=1&pt_uistyle=35&pt_vcode_v1=0&pt_verifysession_v1=" + e;

                Toast.makeText(FanLi.this, ppp, Toast.LENGTH_SHORT).show();
                asy.get(uui2, f, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String s1 = new String(bytes);
                        if (s1.contains("登录成功")) {
                            String[] t0ok = s1.split(",");
                            String s2 = t0ok[2].replaceAll("'", "");
                            String s30 = getqqCookieText();
                            asy.addHeader("cookie", s30);
                            asy.removeHeader("Accept-Encoding");
                            asy.get(s2, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    String s3 = getqqCookieText();
                                    String s4 = getqqgtkCookieText();
                                    Log.e("s4", "   " + s4);
                                    Log.e("s3", "   " + s3);
                                    accfindall.get(temppos).setIsaddshoped("登陆成功");
                                    listView.setAdapter(ad);
                                    Badao.getInstance(getApplicationContext()).moifyqqcookie(tempusername, s3 + jdtg, s4);
                                    Toast.makeText(FanLi.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    asy.removeHeader("cookie");
                                    asy.addHeader("cookie",s3);
                                    asy.get("http://wq.jd.com/deal/recvaddr/GetRecvAddrListV3?callback=cbList&reg=1&r=0.470959998956307&sid=?&g_tk="+s4+"&g_ty=ls", new AsyncHttpResponseHandler() {
                                        @Override
                                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                            String s = new String(bytes);
                                            try{
                                                int i1 = s.indexOf("\"adid\":\"");
                                                int i2 = s.indexOf("\",\"addrdetail");
                                                String substring = s.substring(i1 + 8, i2);
                                                getSharedPreferences("tmp",MODE_PRIVATE).edit().putString("adid",substring).apply();
                                            }catch(Exception r){
                                                Toast.makeText(getApplicationContext(), "您还没有设置默认地址!", Toast.LENGTH_SHORT).show();
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
                        } else {


                            //需要进一步解码unicode码
                            Toast.makeText(FanLi.this, s1, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            }
        }
    };
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, lotime);
            SharedPreferences sharedPreferences = getSharedPreferences("q", MODE_PRIVATE);
            s = sharedPreferences.getString("z", "未知 ");
            succ = sharedPreferences.getString("success", "");
            s11 = s11 + s + "\n";
            succ11 = succ11 + succ + "\n";
            h.sendEmptyMessage(99);
        }
    };
    private String username;
    private FloatingActionButton set;
    private PersistentCookieStore ieStore;
    private FloatingActionButton stop;
    private int lotime;
    private boolean ct = true;
    private String v;
    private String e;
    private String uui2;
    private AsyncHttpClient asy;
    private RequestParams f;
    private String tempusername;
    private int temppos;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fanli_layout);
        init();
        login = (WebView) findViewById(R.id.wv_login);
        login.getSettings().setJavaScriptEnabled(true);
        login.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        login.addJavascriptInterface(new html(), "as");
        login.loadUrl("file:///android_asset/index.html");
        //  login.setWebChromeClient(new MyWebChromeClient());
        listView = (ListView) findViewById(R.id.lv_zhanghuao);
        b1 = (FloatingActionButton) findViewById(R.id.fab_acc_add);
        set = (FloatingActionButton) findViewById(R.id.fab_acc_setting);
        stop = (FloatingActionButton) findViewById(R.id.fab_acc_stop);
        log = (TextView) findViewById(R.id.wv_tv);
        succlog = (TextView) findViewById(R.id.succ_ess);

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.removeCallbacks(r);
                stopService(new Intent(FanLi.this, OrderService.class));
                isstart = true;
                Toast.makeText(FanLi.this, "所有任务已经停止", Toast.LENGTH_SHORT).show();


            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FanLi.this, settingactivity.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            private RadioGroup radioGroup;
            private Button b1;
            private EditText pwd;
            private EditText use;

            @Override
            public void onClick(View v) {

                ok = true;
                final AlertDialog.Builder al = new AlertDialog.Builder(FanLi.this);
                final AlertDialog alertDialog = al.create();
                alertDialog.setTitle("添加京东账号");
                alertDialog.show();
                alertDialog.setContentView(R.layout.add);
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


                use = (EditText) alertDialog.findViewById(R.id.jd_user);
                pwd = (EditText) alertDialog.findViewById(R.id.jd_pwd);
                b1 = (Button) alertDialog.findViewById(R.id.jd_ok);

                radioGroup = (RadioGroup) alertDialog.findViewById(R.id.rg_group);

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(use.getText()) && TextUtils.isEmpty(pwd.getText())) {
                            Toast.makeText(FanLi.this, "空值!", Toast.LENGTH_SHORT).show();
                        } else {

                            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                            if (checkedRadioButtonId == R.id.qqlogin) {
                                Badao.getInstance(getApplicationContext()).accinsert(use.getText().toString(), pwd.getText().toString(), "", "", "", false);

                            } else if (checkedRadioButtonId == R.id.jdlogin) {
                                Badao.getInstance(getApplicationContext()).accinsert(use.getText().toString(), pwd.getText().toString(), "", "", "", true);
                            }
                            Toast.makeText(FanLi.this, "添加成功", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            accfindall = Badao.getInstance(getApplicationContext()).accfindall();
                            listView.setAdapter(ad);

                        }

                    }
                });


            }
        });
        ad = new apt();
        listView.setAdapter(ad);
        registerForContextMenu(listView);

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                //menu.add(0, 0, 0, "登陆该账号");
                menu.add(0, 1, 0, "登陆该账号");
                menu.add(0, 2, 0, "开始抢购");
            }
        });
    }

    public class html {
        @JavascriptInterface
        public void jsMethod(String name) {
            Log.e("jsmod", name);
            ppp = name;

            h.sendEmptyMessage(33);
        }
    }











    protected void onDestroy() {
        handler.removeCallbacks(r);
        stopService(new Intent(FanLi.this, OrderService.class));
        isstart = true;
        Toast.makeText(FanLi.this, "所有任务已经停止", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    protected void onResume() {

        lotime = getSharedPreferences("root", MODE_PRIVATE).getInt("logtime", 500);
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int i = (int) menuInfo.id;
        switch (item.getItemId()) {
            case 0:
                httpser(accfindall.get(i).getUsername(), accfindall.get(i).getPassword(), i, false);
                //  setwebtview("",accfindall.get(i).getUsername(),accfindall.get(i).getPassword(),i,false,true);
                break;
            case 1:

                int isqq = Badao.getInstance(getApplicationContext()).accfindall().get(i).getIsqq();

                //0是true  1是 false

                if (isqq == 1) {
                    httpser(accfindall.get(i).getUsername(), accfindall.get(i).getPassword(), i, true);
                } else if (isqq == 0) {
                    qqlogin(accfindall.get(i).getUsername(), accfindall.get(i).getPassword(), i);
                }

                // setwebtview("",accfindall.get(i).getUsername(),accfindall.get(i).getPassword(),i,true,false);

                break;
            case 2:
                if (isstart) {
                    //日志刷新
                    handler.postDelayed(r, lotime);
                    isstart = false;
                }
                Intent intent;
                intent = new Intent(FanLi.this, OrderService.class);
                intent.putExtra("data", i);
                intent.putExtra("isqq", accfindall.get(i).getIsqq());
                startService(intent);
                //suborder(i);
                break;
            case 3:

                break;
            case 4:

            case 5:
                break;
        }
        return super.onContextItemSelected(item);
    }


    class apt extends BaseAdapter {
        @Override
        public int getCount() {
            return accfindall.size();
        }

        @Override
        public Object getItem(int position) {
            return accfindall.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.accountlist, null);
                TextView user = (TextView) convertView.findViewById(R.id.k_usern);
                tvcookie = (TextView) convertView.findViewById(R.id.k_logincookie);
                String info = accfindall.get(position).getIsaddshoped();
                if (info.equals("登陆成功")) {
                    tvcookie.setText(info);
                    tvcookie.setTextColor(Color.parseColor("#33FF66"));
                } else {
                    tvcookie.setText("未登录");
                    tvcookie.setTextColor(Color.parseColor("#FF0033"));
                }

                user.setText(accfindall.get(position).getUsername());

            }
            return convertView;
        }
    }


    public void addShopcard(String skuid, final int pos) {
        String u = "http://cart.jd.com/selectAllItem.action";
        final String ddda = "http://cart.jd.com/batchRemoveSkusFromCart.action";
        final String kkh = "http://gate.jd.com/InitCart.aspx?pid=" + skuid + "&pcount=1&ptype=1";
        List<Jdaccount> accfindcookie2 = Badao.getInstance(getApplicationContext()).accfindall();
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("Cookie", accfindcookie2.get(pos).getCookie());
        asyncHttpClient.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        asyncHttpClient.get(u, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                asyncHttpClient.get(ddda, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        asyncHttpClient.get(kkh, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {


                                RequestParams g = new RequestParams();
                                g.add("paymentId", "4");
                                g.add("shipParam.reset311", "0");
                                g.add("resetFlag", "0000000000");
                                asyncHttpClient.post("http://trade.jd.com/shopping/dynamic/payAndShip/getAdditShipment.action", g, new AsyncHttpResponseHandler() {
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

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


    public void httpser(final String username, final String pwd, final int pos, final boolean isadd) {


        String u = "https://passport.jd.com/new/login.aspx?ReturnUrl=http%3A%2F%2Fwww.jd.com%2F";
        final AsyncHttpClient async = new AsyncHttpClient(getSchemeRegistry());
        myCookieStore = new PersistentCookieStore(FanLi.this);
        myCookieStore.clear();
        async.setCookieStore(myCookieStore);
        //async.addHeader("cookie",jdtg);
        async.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        async.addHeader("Accept", "*/*");
        async.addHeader("application/x-www-form-urlencoded", "application/x-www-form-urlencoded");
        async.addHeader("Referer", "https://passport.jd.com/new/login.aspx?ReturnUrl=http%3A%2F%2Fwww.jd.com%2F");
        async.get(u, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                final String s = new String(bytes);
                int i1 = s.indexOf("<input type=\"hidden\" id=\"uuid\" name=\"uuid\" value=\"");
                final String uuid = s.substring(i1 + 50, i1 + 86);
                int i2 = s.indexOf("id=\"pubKey\"");
                final String substring1 = s.substring(i2 + 19, i2 + 216 + 19);
                final RequestParams r = new RequestParams();
                r.add("loginName", username);
                String is = String.valueOf((Math.random() * 9 + 1) * 1000000000);
                final String dd = is + "021541";
                final String uu = "https://passport.jd.com/uc/showAuthCode?r=0." + dd + "&version=2015";
                async.post(uu, r, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String s1 = new String(bytes);
                        if (s1.contains("true")) {
                            Toast.makeText(FanLi.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                            String uuu = "https://authcode.jd.com/verify/image?a=1&acid=" + uuid + "&uid=+" + uuid;
                            async.get(uuu, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    File file = new File("/data/data/len.com.k3query/shared_prefs/img.png");
                                    if (file.exists()) {
                                        try {
                                            file.createNewFile();
                                        } catch (Exception e) {
                                        }
                                    }
                                    try {
                                        FileOutputStream os = new FileOutputStream(file);
                                        os.write(bytes);
                                        os.close();
                                        h.sendEmptyMessage(1);
                                        final EditText viewById = (EditText) findViewById(R.id.et_code);
                                        final Button viewById1 = (Button) findViewById(R.id.conmmit_code);
                                        viewById1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String s2 = viewById.getText().toString();
                                                String cookieText = getCookieText2();
                                                RequestParams ff = new RequestParams();
                                                ff.add("uuid", uuid);
                                                ff.add("pubKey", substring1);
                                                ff.add("loginname", username);
                                                ff.add("nloginpwd", pwd);
                                                ff.add("loginpwd", pwd);
                                                ff.add("chkRememberMe", "on");
                                                ff.add("authcode", s2);
                                                ff.add("_t", cookieText);

                                                String pkurl = "https://passport.jd.com/uc/loginService?uuid=" + uuid + "&ReturnUrl=http%3A%2F%2Fwww.jd.com%2F&r=0." + dd + "&version=2015";
                                                async.post(pkurl, ff, new AsyncHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                                                        String s3h = new String(bytes);
                                                        if (s3h.contains("success")) {
                                                            accfindall.get(pos).setIsaddshoped("登陆成功");
                                                            Toast.makeText(FanLi.this, "登录成功", Toast.LENGTH_SHORT).show();
                                                            listView.setAdapter(ad);
                                                            String coo = getCookieText();
                                                            String p = coo + jdtg;
                                                            Badao.getInstance(getApplicationContext()).moifycookie(username, p);
                                                            if (isadd) {
                                                                addShopcard(ownid, pos);
                                                            }

                                                        } else {
                                                            Toast.makeText(FanLi.this, s3h, Toast.LENGTH_SHORT).show();
                                                        }

                                                    }

                                                    @Override
                                                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                                    }
                                                });

                                            }
                                        });
                                    } catch (Exception e) {
                                    }
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                                }
                            });
                        } else if (s1.contains("false")) {
                            String cookieText = getCookieText2();
                            RequestParams ff = new RequestParams();
                            ff.add("uuid", uuid);
                            ff.add("pubKey", substring1);
                            ff.add("loginname", username);
                            ff.add("nloginpwd", pwd);
                            ff.add("loginpwd", pwd);
                            ff.add("chkRememberMe", "on");
                            ff.add("authcode", "");
                            ff.add("_t", cookieText);

                            String pkurl = "https://passport.jd.com/uc/loginService?uuid=" + uuid + "&ReturnUrl=http%3A%2F%2Fwww.jd.com%2F&r=0." + dd + "&version=2015";
                            async.post(pkurl, ff, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    String s3h = new String(bytes);
                                    //Toast.makeText(FanLi.this, s3h, Toast.LENGTH_SHORT).show();
                                    //Log.e("登陆信息",s3h);

                                    if (s3h.contains("success")) {
                                        accfindall.get(pos).setIsaddshoped("登陆成功");
                                        Toast.makeText(FanLi.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        listView.setAdapter(ad);
                                        String coo = getCookieText();
                                        String p = coo + jdtg;
                                        Badao.getInstance(getApplicationContext()).moifycookie(username, p);
                                        if (isadd) {
                                            addShopcard(ownid, pos);
                                        }

                                    } else {
                                        Toast.makeText(FanLi.this, s3h, Toast.LENGTH_SHORT).show();
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

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });

    }


    public void qqlogin(final String usernamem, final String pawd, final int pos) {

        tempusername = usernamem;
        temppos = pos;


        ct = true;
        String isa = String.valueOf((Math.random() * 9 + 1) * 1000000000);
        final String dda = isa + "021541";
        // long time=System.currentimeMillis();
        long l = System.currentTimeMillis() / 1000;
        String m = "which=&refer_cgi=authorize&response_type=code&client_id=100273020&state=&display=&openapi=1010_1011&switch=0&src=1&sdkv=&sdkp=a&tid=1451563149&pf=&need_pay=0&browser=0&browser_error=&serial=&token_key=&redirect_uri=http%3A%2F%2Fwqlogin2.jd.com%2Fmlogin%2Fh5v1%2FGetCode%3Fftype%3D2%26check%3D5d4c600cdc3a400f18b0efbd0fc14c17%26state%3D0-" + l + "-49045-1601&sign=&time=&status_version=&status_os=&status_machine=&page_type=1&has_auth=1&update_auth=1&auth_time=1451563169352";
        //String jm="which%3D%26refer_cgi%3Dauthorize%26response_type%3Dcode%26client_id%3D100273020%26state%3D%26display%3D%26openapi%3D1010_1011%26switch%3D0%26src%3D1%26sdkv%3D%26sdkp%3Da%26tid%3D1451563149%26pf%3D%26need_pay%3D0%26browser%3D0%26browser_error%3D%26serial%3D%26token_key%3D%26redirect_uri%3Dhttp%253A%252F%252Fwqlogin1.jd.com%252Fmlogin%252Fh5v1%252FGetCode%253Fftype%253D2%2526check%253D5d4c600cdc3a400f18b0efbd0fc14c17%2526state%253D0-1493018918-205604-1635%26sign%3D%26time%3D%26status_version%3D%26status_os%3D%26status_machine%3D%26page_type%3D1%26has_auth%3D1%26update_auth%3D1%26auth_time%3D1451563169352";
        f = new RequestParams();
        f.add("openlogin_data", m);

        final String qqurl = "https://ssl.ptlogin2.qq.com/check?pt_tea=1&uin=" + usernamem + "&appid=716027609&ptlang=2052&r=0." + dda;
        asy = new AsyncHttpClient(getSchemeRegistry());
        ieStore = new PersistentCookieStore(FanLi.this);
        ieStore.clear();
        asy.setCookieStore(ieStore);

        asy.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; H60-L02 Build/HDH60-L02) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.4 TBS/025413 Mobile Safari/533.1 V1_AND_SQ_5.5.1_236_YYB_D QQ/5.5.1.2435 NetType/WIFI WebP/0.3.0");
        asy.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        asy.get("http://wq.jd.com/mlogin/h5v1/cpLogin_BJ?rurl=http%3A%2F%2Fwqs.jd.com%2Fmy%2Findexv2.shtml", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                asy.get(qqurl, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String s = new String(bytes);
                        String[] tok = s.split(",");
                        v = tok[1].replaceAll("'", "");
                        e = tok[3].replaceAll("'", "");
                        login.loadUrl("file:///android_asset/index.html");
                        try {
                            login.loadUrl("javascript:getEncryption2('" + pawd + "','" + usernamem + "','" + v + "')");
                            Thread.sleep(2000);
                        } catch (InterruptedException ev) {
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


    }

    public void init() {
        asyncHttpClient = new AsyncHttpClient();
        ownid = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");

        Toast.makeText(this, "当前商品id  " + ownid, Toast.LENGTH_LONG).show();
        accfindall = Badao.getInstance(getApplicationContext()).accfindall();

        showdialog();
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

    public  static  void   setlogi(String text){
        setlogg=setlogg+text+"\n";

        log.setText(setlogg);
        if (setlogg.length()>2000){
            setlogg="";
        }
    }




    public String GetG_TK(String str) {
        int hash = 5381;
        for (int i = 0, len = str.length(); i < len; ++i) {
            hash += (hash << 5) + (int) (char) str.charAt(i);
        }
        return (hash & 0x7fffffff) + "";
    }

    private String getCookieText() {
        List<Cookie> cookies = myCookieStore.getCookies();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        // Log.e("cookie", sb.toString());
        return sb.toString();
    }

    private String getqqCookieText() {
        List<Cookie> cookies = ieStore.getCookies();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }
        //  Log.e("cookie", sb.toString());
        return sb.toString();
    }

    private String getqqgtkCookieText() {
        List<Cookie> cookies = ieStore.getCookies();

        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            if (cookieName.equals("wq_skey")) {
                String stf = GetG_TK(cookie.getValue());
                return stf;
            }

        }
        return "0";


    }


    private String getCookieText2() {

        List<Cookie> cookies = myCookieStore.getCookies();
        Log.d("cookies.size() = ", cookies.size() + "");
        for (Cookie cookie : cookies) {
            Log.d(cookie.getName() + " = ", cookie.getValue());
        }
        String im = "0";
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            if (cookieName.contains("_nt")) {
                im = cookieName;
            }

        }
        return im;

    }


    public void showdialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("注意! 更换商品之后,一定要把账号重新登陆一遍,切记,不然还是之前的商品,秒杀商品(如手机),请到设置里把提交订单间隔改成4000-5000,日志刷新间隔改成99999,请提前实名认证qq绑定的京东账号,提前一分钟点击开始抢购后,挂着就行,我也不知道能不能抢购成功,请去京东网站查看是否成功,想退出本软件请强行停止,自带的停止没用");
       // Toast.makeText(this, "注意! 更换商品之后,一定要把账号重新登陆一遍,切记,不然还是之前的商品", Toast.LENGTH_LONG).show();
builder.show();

    }
}
