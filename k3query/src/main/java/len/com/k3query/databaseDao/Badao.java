package len.com.k3query.databaseDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import len.com.k3query.Bean.Jdaccount;
import len.com.k3query.Bean.ShangPinBean;
public class Badao {
    private len.com.k3query.databaseDao.dataBa dataBa;
    private static Badao badao;
    private Badao(Context ctx) {
        dataBa = new dataBa(ctx);
    }
    public static Badao getInstance(Context context) {
        if (badao == null) {
            badao = new Badao(context);
        }
        return badao;
    }


    public void insert(String name, String itemid, String shopid, String catid) {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("itemid", itemid);
        cv.put("shopid", shopid);
        cv.put("catid", catid);
        writableDatabase.insert("jingdonglist", null, cv);
        writableDatabase.close();
    }
    public void accinsert(String username, String password, String cookie, String pname,String isadd,boolean isqq) {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("cookie", cookie);
        cv.put("pname", pname);
        cv.put("isaddshoped", isadd);
        cv.put("isqq", isqq);
        writableDatabase.insert("account", null, cv);
        writableDatabase.close();
    }








    public void delete(String itemid) {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        writableDatabase.delete("jingdonglist", "itemid=?", new String[]{itemid});
        writableDatabase.close();
    }
    public void accdelete(String username) {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        writableDatabase.delete("account", "username=?", new String[]{username});
        writableDatabase.close();
    }


    public  void  moifycookie(String username,String cookie){
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cookie", cookie);
        values.put("isaddshoped","登陆成功");
        writableDatabase.update("account", values, "username=?", new String[]{username});
        writableDatabase.close();
    }
    public  void  moifyqqcookie(String username,String cookie,String gtk){
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("qqcookie", cookie);
        values.put("isaddshoped","登陆成功");
        values.put("gtk",gtk);
        writableDatabase.update("account", values, "username=?", new String[]{username});
        writableDatabase.close();
    }


    public  void  moifydenglu(int pos){
        int i = pos + 1;
        String p=i+"";
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isaddshoped","登陆态失效");
        writableDatabase.update("account",values,"_id=?",new String[] {p});
        writableDatabase.close();
    }
    public  void  moifystate(int pos,String info){
        int i = pos + 1;
        String p=i+"";
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pname",info);
        writableDatabase.update("account",values,"_id=?",new String[] {p});
        writableDatabase.close();
    }


    public List<ShangPinBean> findall() {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        Cursor jingdonglist = writableDatabase.query("jingdonglist", new String[]{"name", "itemid", "shopid", "catid"}, null, null, null, null, null);
        List<ShangPinBean> gList = new ArrayList<ShangPinBean>();
        while (jingdonglist.moveToNext()) {

            ShangPinBean shangPinBean = new ShangPinBean();
            String Sname = jingdonglist.getString(0);
            String Sitemid = jingdonglist.getString(1);
            String Sshopid = jingdonglist.getString(2);
            String Scatid = jingdonglist.getString(3);
            shangPinBean.setName(Sname);
            shangPinBean.setItemid(Sitemid);
            shangPinBean.setShopid(Sshopid);
            shangPinBean.setCatid(Scatid);
            gList.add(shangPinBean);
        }
        jingdonglist.close();
        writableDatabase.close();
        return  gList;
    }


//    public  List<Jdaccount> accfindisqq(){
//        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
//        Cursor account = writableDatabase.query("account", new String[]{"isqq"},null,null,null,null,null);
//        ArrayList<Jdaccount> objects = new ArrayList<>();
//
//        while ()
//    }


    public List<Jdaccount> accfindall() {
        SQLiteDatabase writableDatabase = dataBa.getWritableDatabase();
        Cursor jingdonglist = writableDatabase.query("account", new String[]{"username", "password", "isaddshoped", "cookie","pname","qqcookie","isqq","gtk"}, null, null, null, null, null);

        List<Jdaccount> gList = new ArrayList<Jdaccount>();
        while (jingdonglist.moveToNext()) {
            Jdaccount jd=new Jdaccount();
            String username = jingdonglist.getString(0);
            String password = jingdonglist.getString(1);
            String isaddshop = jingdonglist.getString(2);
            String cookie = jingdonglist.getString(3);
            String pname = jingdonglist.getString(4);
            String qqcookie = jingdonglist.getString(5);
            int anInt = jingdonglist.getInt(6);
            String gtk=jingdonglist.getString(7);

            //0是true  1是 false

            jd.setUsername(username);
            jd.setPassword(password);
            jd.setIsaddshoped(isaddshop);
            jd.setCookie(cookie);
            jd.setPname(pname);
            jd.setQqcookie(qqcookie);
            jd.setIsqq(anInt);
            jd.setGtk(gtk);
            gList.add(jd);
        }
        jingdonglist.close();
        writableDatabase.close();
        return  gList;
    }
}
