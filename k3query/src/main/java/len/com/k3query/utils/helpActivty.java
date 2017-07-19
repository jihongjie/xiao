package len.com.k3query.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;

import len.com.k3query.R;

/**
 * Created by An on 2017/4/25.
 */

public class helpActivty extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpctivity_layout);


        WebView vv = (WebView) findViewById(R.id.ww_help);
        Toast.makeText(this, "正在打开网页,请稍候", Toast.LENGTH_SHORT).show();

        vv.getSettings().setJavaScriptEnabled(true);
        vv.loadUrl("http://221.214.179.216:8000/bbs/index.php/help/");
        vv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);



                if (url.startsWith("http://")||url.startsWith("https://")){

                }else {
                    Intent intent;
                    try {
                        intent = Intent.parseUri(url,
                                Intent.URI_INTENT_SCHEME);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }
}
