package example.tukcu.tukcu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tukcu.tukcu.R;

public class ContactsFragment extends Fragment {
    TextView website,twitter,facebook,youtube,mail,instagramTv, phoneTv, flickrTv, phone2Tv;
    ImageView phoneLogo, emailLogo,facebookLogo, twitterLogo,instagramLogo, youtubeLogo, flickrLogo, websiteLogo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView =  inflater.inflate(R.layout.fragment_contacts,container,false);


        website = RootView.findViewById(R.id.websiteTv);
        twitter = RootView.findViewById(R.id.twitterTv);
        facebook = RootView.findViewById(R.id.facebookTv);
        youtube = RootView.findViewById(R.id.youtubeTv);
        mail = RootView.findViewById(R.id.mailTv);
        instagramTv = RootView.findViewById(R.id.instagramTv);
        phoneTv = RootView.findViewById(R.id.phoneTv);
        phone2Tv = RootView.findViewById(R.id.phone2Tv);
        flickrTv = RootView.findViewById(R.id.flickrTv);
        phoneLogo = RootView.findViewById(R.id.contactPhoneLogo);
        emailLogo = RootView.findViewById(R.id.contactEmailLogo);
        facebookLogo = RootView.findViewById(R.id.contactfacebookLogo);
        twitterLogo = RootView.findViewById(R.id.contactTwitterLogo);
        instagramLogo = RootView.findViewById(R.id.contactInstagramLogo);
        youtubeLogo = RootView.findViewById(R.id.contactYouTubeLogo);
        flickrLogo = RootView.findViewById(R.id.contactFlickrLogo);
        websiteLogo = RootView.findViewById(R.id.contactWebsiteLogo);

        phoneLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0717868648";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);
            }
        });

        phoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0717868648";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);
            }
        });

        phone2Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0714804291";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);
            }
        });

        websiteLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.tukcu.org";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://www.tukcu.org";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitterLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://twitter.com/TUK_CU";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://twitter.com/TUK_CU";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        facebookLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.facebook.com/TUK-Christian-Union-214681508701296/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.facebook.com/TUK-Christian-Union-214681508701296/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        youtubeLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.youtube.com/channel/UCs3h5-fD-_P3FDBNB3470Jg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.youtube.com/channel/UCs3h5-fD-_P3FDBNB3470Jg";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        instagramLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.instagram.com/tuk_cu/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        instagramTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.instagram.com/tuk_cu/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        emailLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "technicaluniversitycu@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "TUKCU, ");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "technicaluniversitycu@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "TUKCU, ");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        flickrLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.flickr.com/photos/185666365@N04/albums";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        flickrTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.flickr.com/photos/185666365@N04/albums";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return RootView;
    }

}
