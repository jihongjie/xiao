package len.com.k3query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import len.com.k3query.Bean.ShangPinBean;
import len.com.k3query.databaseDao.Badao;
import len.com.k3query.utils.Sbar;

public class ChooseItem  extends AppCompatActivity{

    private List<ShangPinBean> findall;
    private EditText skuid;
    private EditText vend;
    private EditText cat;
    private EditText name;
    private ListView listView;
    private myadp mya;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        setTitle("选择您的商品");
        initdata();

    }

    public  void  addcat(){

        AlertDialog.Builder aaa = new AlertDialog.Builder(ChooseItem.this);
        final AlertDialog alertDialog = aaa.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.setContentView(R.layout.dialog);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Button ok = (Button) alertDialog.findViewById(R.id.ok);
        Button no = (Button) alertDialog.findViewById(R.id.no);
        skuid = (EditText) alertDialog.findViewById(R.id.sku);
        vend = (EditText) alertDialog.findViewById(R.id.vendid);
        cat = (EditText) alertDialog.findViewById(R.id.catid);
        name = (EditText) alertDialog.findViewById(R.id.namei);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name0 = name.getText().toString();
                String  s1 = skuid.getText().toString().trim();
                String a = vend.getText().toString().trim();
                String  ccat = cat.getText().toString().trim();
                if (s1.equals("") || a.equals("") || cat.equals("")) {
                    Sbar.LongSnackbar(view, "不能为空,店铺id如果为0就写0").show();

                } else {
                    Badao instance = Badao.getInstance(getApplicationContext());
                    instance.insert(name0,s1,a,ccat);
                  //  findall = instance.findall();


                    //getSharedPreferences("sheng", MODE_PRIVATE).edit().putString("ownskuid", s1).putString("ownvend", a).putString("shangpin", "-1").putString("shangpinname", "自定义商品").putString("catid", ccat).apply();
                    alertDialog.dismiss();
                    finish();
                    startActivity(new Intent(ChooseItem.this,ChooseItem.class));


                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }


    public  void initdata(){

        Badao instance = Badao.getInstance(getApplicationContext());
        findall = instance.findall();
        listView = (ListView) findViewById(R.id.lv_1);

        Button b1 = (Button) findViewById(R.id.add_shang);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcat();
            }
        });
        mya = new myadp();
        listView.setAdapter(mya);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name=findall.get(i).getName();
                String itemid = findall.get(i).getItemid();
                String shopid = findall.get(i).getShopid();
                String catid = findall.get(i).getCatid();

                getSharedPreferences("new",MODE_PRIVATE).edit().putString("name",name).putString("itemid",itemid).putString("shopid",shopid).putString("catid",catid).apply();
                Sbar.LongSnackbar(view, "new 设置成功,一定要按返回键退出,重新进入").show();
                getSharedPreferences("jd",MODE_PRIVATE).edit().putBoolean("jd",true).apply();

            }
        });

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }
    class  myadp extends BaseAdapter{

        @Override
        public int getCount() {
            return findall.size();
        }

        @Override
        public Object getItem(int i) {
            return findall.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1;
            if (view!=null){
              view1=view;
            }else {
                view1=View.inflate(ChooseItem.this,R.layout.chooselist,null);
                TextView tv1 = (TextView) view1.findViewById(R.id.tv_add);
                tv1.setText(findall.get(i).getName());
            }

            return view1;
        }
    }


}
