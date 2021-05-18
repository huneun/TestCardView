package com.example.testcardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    boolean isLargeLayout;
    Button btn_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLargeLayout = getResources().getBoolean(R.bool.large_layout);
        btn_dialog = (Button)findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
       /*         Intent intent = shareTwitter(MainActivity.this, "제목", "https://gooners0304.tistory.com/entry/Dialog-Full-Size-%EB%B0%8F-%ED%99%94%EB%A9%B4-%ED%95%98%EB%8B%A8%EC%97%90-%EC%9C%84%EC%B9%98-%EC%8B%9C%ED%82%A4%EA%B8%B0");
                startActivity(intent); */
                shareBand(MainActivity.this, "https://developer88.tistory.com/132");
            }
        });
    }

    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDialogFragment newFragment = new CustomDialogFragment();
        newFragment.show(fragmentManager, "dialog");
    }

    public static void shareBand(Context context, String url){ // 매개 String url
        String decodeURL = null;
        try {
            decodeURL = URLDecoder.decode(url, "UTF-8"); // 매개1 url
            PackageManager manager = context.getPackageManager();
            Intent i = manager.getLaunchIntentForPackage("com.nhn.android.band");
            context.startActivity(i);
        } catch (UnsupportedEncodingException e) {
            // 밴드앱 설치되지 않은 경우 구글 플레이 설치페이지로 이동
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.band"));
            context.startActivity(intent);
            return;
        }
        String serviceDomain = decodeURL; //  연동 서비스 도메인 매개1 url
        Uri uri = Uri.parse("bandapp://create/post?text=" + url + "&route=" + serviceDomain); // 가운데 연산자도 url
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static Intent shareTwitter(Context context,  String title, String url) {
        String strLink = null;
        try {
            strLink = String.format("http://twitter.com/intent/tweet?text=%s&url=%s", title+"\n",
                    URLEncoder.encode(url, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e("test1234", "UnsupportedEncodingException in");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.twitter.android"));
            context.startActivity(intent);
            return intent;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strLink));
        return intent;
    }
}