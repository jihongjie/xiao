package len.com.k3query.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class k3queryservice extends Service {

    mod m=new mod();
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(runnable, 500); //每隔1s执行

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    Handler handler=new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 500);
            String s = m.k3post();
            SimpleDateFormat formatter    =   new    SimpleDateFormat    ("HH:mm:ss:  SSS ");
            Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
            String    str    =    formatter.format(curDate);
            String p=null;
            try{
                if (s.equals("0")){
                    p=str+"  库存"+s;
                }else if (s.equals("-1")){
                    p=str+"  异常";
                }else {
                    p=str+"  有货"+s;
                }
            }catch (Exception e){

            }


            getSharedPreferences("k3",MODE_PRIVATE).edit().putString("p",p).apply();



        }
    };
}
