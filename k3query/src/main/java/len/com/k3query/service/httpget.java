package len.com.k3query.service;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.io.IOException;
import len.com.k3query.R;

/**
 * Created by An on 2017/3/25.
 */

    public class httpget   extends Service{

    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }
    @Override
    public void onStart(Intent intent, int startId) {
        mediaPlayer =MediaPlayer.create(this, R.raw.nof);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });

    }
}
