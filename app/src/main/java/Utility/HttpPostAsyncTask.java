package Utility;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

public class HttpPostAsyncTask extends AsyncTask<String, String, String>{

    String TAG = "HttpPostAsyncTask";

    Map<String, String> httpHeader = new HashMap<>();

    private final String lineEnd = "\r\n";
    private final String twoHyphens = "--";
    private final String boundary = UUID.randomUUID().toString();
    private String charset;
    HttpURLConnection conn = null;
    private OutputStream outputStream;
    private PrintWriter writer;
    //private BufferedWriter writer;
    FileInputStream fileInputStream = null;

    /*
    * This method starts a connection with the server containing the ML models and receives
    * in response the keyword and the language of the audio file given to it
    *
    * @param strings[0] -> Service URL
    * @param strings[1] -> Server charset
    * @param strings[2] -> User agent
    * @param strings[3] -> Field name
    * @param strings[4] -> File path
    */
    @Override
    protected String doInBackground(String... strings) {

        String response = "";
        try
        {
            /* CLIENT REQUEST*/
            URL url = new URL(strings[0]);
            this.charset = strings[1];
            httpHeader.put("User-Agent", strings[2]);
            String filePath = strings[4];

            File fp = new File(filePath);

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a cached copy.
            conn.setRequestMethod("POST"); // Use a post method.

            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            if (httpHeader != null && httpHeader.size() > 0) {
                Iterator<String> it = httpHeader.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = httpHeader.get(key);
                    conn.setRequestProperty(key, value);
                }
            }

            outputStream = conn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
            //writer = new BufferedWriter(new OutputStreamWriter(outputStream, charset));

            // File
            writer.append(twoHyphens + boundary).append(lineEnd);
            writer.append("Content-Disposition: form-data; name=\"" + strings[3] + "\"; filename=\"" + fp.getName() + "\"").append(lineEnd);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fp.getName())).append(lineEnd);
            writer.append("Content-Transfer-Encoding: binary").append(lineEnd);
            writer.append(lineEnd);
            writer.flush();

            fileInputStream = new FileInputStream(fp);
            byte[] buffer = new byte[8 * 1024 * 1024]; // 8MB
            int bytesRead = -1;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            fileInputStream.close();
            writer.append(lineEnd);
            writer.flush();

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* SERVER RESPONSE*/
        writer.flush();
        writer.append(twoHyphens + boundary + twoHyphens).append(lineEnd);
        writer.close();


        int responseCode= 0;
        try {
            // Check status connection code
            responseCode = conn.getResponseCode();
            Log.v(TAG, String.valueOf(responseCode));

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[8 * 1024 * 1024];
                int length;
                while ((length = conn.getInputStream().read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                response = result.toString(this.charset);
            }
            else {
                response= "Server response non-OK status: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close connection
        conn.disconnect();

        return response;
    }
}
