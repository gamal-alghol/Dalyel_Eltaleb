package com.dalyel.dalyelaltaleb.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dalyel.dalyelaltaleb.R;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class ContactUs extends Fragment {


TextView tXtWhatUp,txtTelegram;
ImageButton telgram,facebook,whatsapp;
    String shareBody;

    View context;
    public ContactUs() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tXtWhatUp=view.findViewById(R.id.waht_up);
txtTelegram=view.findViewById(R.id.telegram);
facebook=view.findViewById(R.id.imageButton_faceBook);
        whatsapp=view.findViewById(R.id.imageButton_whatsApp);
        telgram=view.findViewById(R.id.imageButton_telegram);



         shareBody="https://play.google.com/store/apps/details?id="+view.getContext().getPackageName();
context=view;

        tXtWhatUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String smsNumber = "972594132519";
                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
                    startActivity(sendIntent);
                }catch (Exception e){
                    copy("972594132519");
                }


            }
        });

        txtTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                    telegramIntent.setData(Uri.parse("http://telegram.me/MostfaZwayed"));
                    final String appName = "org.telegram.messenger";
                    telegramIntent.setPackage(appName);
                    startActivity(telegramIntent);

                } catch (Exception e) {
                    // show error message
                }
            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
       if(whatsappIntent!=null){
                whatsappIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(whatsappIntent);

            }else{
           Toast.makeText(view.getContext(), "whatsup is not installed", Toast.LENGTH_SHORT).show();

       }
            }
        });

        telgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                waIntent.setPackage("org.telegram.messenger");
                if (waIntent != null) {
                    waIntent.putExtra(Intent.EXTRA_TEXT, shareBody);//
                   view.getContext().startActivity(Intent.createChooser(waIntent, "Share with"));
                }
                else
                {
                    Toast.makeText(view.getContext(), "Telegram is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                waIntent.setPackage("com.facebook.orca");
                if (waIntent != null) {
                    waIntent.putExtra(Intent.EXTRA_TEXT, shareBody);//
                    view.getContext().startActivity(Intent.createChooser(waIntent, "Share with"));
                }
                else
                {
                    Toast.makeText(view.getContext(), "facebook is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_us, container, false);

    }
    private void copy(String text){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText( getContext(), "تم نسخ"+text+"للتواصل", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(text,text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText( getContext(), "تم نسخ"+text+"للتواصل", Toast.LENGTH_SHORT).show();

        }
    }

}