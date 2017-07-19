package len.com.k3query.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import len.com.k3query.R;


/**
 * Created by An on 2017/3/29.
 */

public class Sbar {

    public static Snackbar LongSnackbar(View view, String message){
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_LONG);
        setSnackbarColor(snackbar, Color.parseColor("#FFFFFF"),Color.parseColor("#E0327DFF"));
        return snackbar;
    }


    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if(view!=null){
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextSize(17);
        }
    }



}
