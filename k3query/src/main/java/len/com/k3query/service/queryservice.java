package len.com.k3query.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class queryservice extends Service {

    mod m=new mod();

    boolean vol=true;
    private String string;
    private String sheng;
    String  list[]={"1_72_4211_0","2_2823_51974_0","3_51040_2986_0","4_113_52484_0","5_142_42540_0","6_303_36780_0","7_412_3547_0","8_560_567_0","9_639_3172_0","10_727_3334_0","11_799_3240_0","12_904_3373_0","13_2900_2908_0","14_1151_19227_0","15_1158_3412_0","16_1303_3483_0","17_1432_1435_0","18_1482_3606_0","19_1601_3633_0","20_3168_3169_0","21_1827_3505_0","22_2103_2105_0","23_3690_4182_0","24_2144_3906_0","25_4108_6823_0","26_3970_3972_0","27_2428_31523_0","28_2525_4001_0","29_2580_2581_0","30_2628_2629_0","31_4110_4122_0","32_2768_2769_0"};
    private String ssg;
    private String cit;

    private int shangpin;

    String skuid;
    String vendid;
    String cat;


    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    public  int TIME=500;
    public void onCreate() {
        super.onCreate();
        string = getSharedPreferences("tmp", MODE_PRIVATE).getString("jduid", "1470020485");
        sheng = getSharedPreferences("sheng", MODE_PRIVATE).getString("sheng", "1");
        String spname = getSharedPreferences("new", MODE_PRIVATE).getString("name", "斐讯K3");
        cit = getSharedPreferences("sheng", MODE_PRIVATE).getString("city", "北京");
        int ps=Integer.parseInt(sheng);
        ssg = list[ps];
        shangpin();

        Toast.makeText(this,"当前省份:"+cit +"当前商品:"+spname, Toast.LENGTH_SHORT).show();

        handler.postDelayed(runnable, TIME); //每隔1s执行
    }
    private void shangpin(){
      //  getSharedPreferences("new",MODE_PRIVATE).edit().putString("name",name).putString("itemid",itemid).putString("shopid",shopid).putString("catid",catid).apply();

        String ownid = getSharedPreferences("new", MODE_PRIVATE).getString("itemid", 3959251 + "");
        String shopid = getSharedPreferences("new", MODE_PRIVATE).getString("shopid", 1000000837 + "");
        String catid = getSharedPreferences("new", MODE_PRIVATE).getString("catid", "670,699,700");
        skuid=ownid;
        vendid=shopid;
        cat=catid;
    }


    Handler handler=new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, TIME);
            String s = m.get(string,ssg,skuid,vendid,cat);

            try {
                if (s.contains("有货")){

                    if (vol){
                        AudioManager localAudioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
                       // localAudioManager.setStreamVolume(3, localAudioManager.getStreamMaxVolume(3) , 1);
                        vol=false;
                    }
                    boolean aBoolean = getSharedPreferences("root", MODE_PRIVATE).getBoolean("music", false);
                    if (aBoolean){
                        startService(new Intent(queryservice.this,httpget.class));
                    }


                }
            }catch (Exception e){
            }
            SharedPreferences sharedPreferences = getSharedPreferences("tmp",MODE_PRIVATE);
            sharedPreferences.edit().putString("s",s).apply();
            //
        }
    };
}
