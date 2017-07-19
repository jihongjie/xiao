package len.com.k3query.utils;



import java.io.DataOutputStream;
import java.io.IOException;

public class getsupersu {

    private Process process;

    public getsupersu() throws IOException {

        process = null;
        process = Runtime.getRuntime().exec("su");
    }

    public boolean getRootPermission(String pkgCodePath) {

        DataOutputStream os = null;
        try {
            String cmd = pkgCodePath;
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }


}
