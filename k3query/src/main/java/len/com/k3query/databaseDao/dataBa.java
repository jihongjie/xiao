package len.com.k3query.databaseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by An on 2017/4/2.
 */

public class dataBa  extends SQLiteOpenHelper {
    public dataBa(Context context) {
        super(context, "shangpin", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table jingdonglist (_id integer primary key autoincrement,name varchar(20), itemid varchar(20), shopid varchar(20),catid varchar(20) )");
        sqLiteDatabase.execSQL("create table account (_id integer primary key autoincrement,username varchar(40), password varchar(40), isaddshoped varchar(10),cookie varchar(1000) ,pname varchar(20))");
        ContentValues value =new ContentValues();
        value.put("name","斐讯k3");
        value.put("itemid","3959251");
        value.put("shopid","1000000837");
        value.put("catid","670,699,700");
        sqLiteDatabase.insert("jingdonglist",null,value);
        value.put("name","腾达AC9");
        value.put("itemid","3968219");
        value.put("shopid","1000000867");
        value.put("catid","670,699,700");
        sqLiteDatabase.insert("jingdonglist",null,value);
        sqLiteDatabase.execSQL("alter table account add qqcookie varchar(1000) ");
        sqLiteDatabase.execSQL("alter table account add isqq boolean");
        sqLiteDatabase.execSQL("alter table account add gtk varchar(30)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("alter table account add qqcookie varchar(1000) ");
            sqLiteDatabase.execSQL("alter table account add isqq boolean");
            sqLiteDatabase.execSQL("alter table account add gtk varchar(30)");
    }
}
