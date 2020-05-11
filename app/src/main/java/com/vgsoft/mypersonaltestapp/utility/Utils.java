package com.vgsoft.mypersonaltestapp.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.vgsoft.mypersonaltestapp.TestApp;

public final class Utils {
    private Utils() {
    }

    public static void makeToast(String text) {
        Toast.makeText(TestApp.getAppContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getPosterUri(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }
    public static String getPreviewUri(String imagePath) {
        return "http://img.youtube.com/vi/"+imagePath+"/0.jpg";
    }

    public static String getRatingString(double voteAverage){
        return String.valueOf(voteAverage) + "/10";
    }

    public static void playPosterInYoutube(String videoId){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        TestApp.getAppContext().startActivity(intent);
    }

}
