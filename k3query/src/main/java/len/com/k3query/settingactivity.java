package len.com.k3query;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import len.com.k3query.utils.Sbar;
import len.com.k3query.utils.ShellUtils;
import len.com.k3query.utils.getsupersu;


public class settingactivity extends AppCompatActivity {
    private EditText tim;
    private EditText logtime0;
    private TextView fanli;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting);




        CheckBox music = (CheckBox) findViewById(R.id.checkbo2);

        tim = (EditText) findViewById(R.id.et_time);
        logtime0 = (EditText) findViewById(R.id.et_logtime);
        fanli = (TextView) findViewById(R.id.set_fanli);
        boolean musici = getSharedPreferences("root", MODE_PRIVATE).getBoolean("music", false);
        int time = getSharedPreferences("root", MODE_PRIVATE).getInt("time", 6000);
        int logtime = getSharedPreferences("root", MODE_PRIVATE).getInt("logtime", 500);
        String t=time+"";
        String  logt=logtime+"";
        logtime0.setText(logt);


        tim.setText(t);
        music.setChecked(musici);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("root",MODE_PRIVATE).edit().putBoolean("music",isChecked).apply();
            }
        });

        fanli.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(settingactivity.this);
            final EditText editText = new EditText(settingactivity.this);
            editText.setSingleLine(true);
            editText.setHint("https://union-click.jd.com/jdc?d=CVs1EF");
            builder.setTitle("设置返利");
            builder.setMessage("仅支持京东联盟的斐讯k3链接,但是选择购买其他商品依然会有返利,清空代表取消返利模式.如下格式(必须为https):https://union-click.jd.com/jdc?d=CVs1EF,,瞎填后果自负");
            builder.setView(editText);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (editText.getText().equals("")) {
                        getSharedPreferences("f", MODE_PRIVATE).edit().putString("f", "0").apply();
                    }
                    getSharedPreferences("f", MODE_PRIVATE).edit().putString("f", editText.getText().toString()).apply();
                    Sbar.LongSnackbar(fanli, "设置成功,请按返回键重启软件").show();
                }
            });
            builder.show();
        }
    });
    }

    private void showpopwindow() {

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.setting_popwindow);
        TextView textView = new TextView(this);
        textView.setText("woshi--popwindow");
        textView.setTextColor(Color.BLUE);
        PopupWindow ppp=new PopupWindow(textView,500,600,true);
        ppp.setBackgroundDrawable(new ColorDrawable(Color.GREEN));//一定要设置背景,否则返回键失效
        ppp.showAtLocation(relativeLayout, Gravity.CENTER,0,0);//0,0代表偏移量




    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            String s = tim.getText().toString();
            String s0 =logtime0.getText().toString();
            int ss=Integer.parseInt(s);
            int ss0=Integer.parseInt(s0);
            getSharedPreferences("root",MODE_PRIVATE).edit().putInt("time",ss).putInt("logtime",ss0).apply();
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void aaaaa(View view) {
        showpopwindow();
    }
}
