//Developer: Rohini Naidu
package com.katherine.bloomuii.ObjectClasses;
import android.os.AsyncTask;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
//Source: https://www.youtube.com/watch?v=-Ld1IoOF_uk
public class RetrievePDFStream extends AsyncTask<String, Void, InputStream> {
    PDFView pdfView;
    public RetrievePDFStream(PDFView pdfView){
        this.pdfView = pdfView;
    }
    @Override
    protected InputStream doInBackground(String... strings) {
        InputStream inputStream = null;
        try{
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            if(urlConnection.getResponseCode() == 200){
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }
        }
        catch (IOException e){
            return null;
        }
        return inputStream;
    }
    @Override
    protected void onPostExecute(InputStream inputStream){
        pdfView.fromStream(inputStream).load();
    }
}
