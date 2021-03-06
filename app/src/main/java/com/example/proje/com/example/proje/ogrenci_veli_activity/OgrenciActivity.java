package com.example.proje.com.example.proje.ogrenci_veli_activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.proje.com.example.proje.kayit_ve_login_activitiyleri.MainActivity;
import com.example.proje.R;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class OgrenciActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    static Ogrenci ogrenci;
    TextView headerAd, headerMail ;
    CircularImageView headerResim;

    File localFile;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogrenci);
        mAuth=FirebaseAuth.getInstance();
        context=this;
        Intent intent=getIntent();
        ogrenci=(Ogrenci)intent.getSerializableExtra("ogrenci");

        try {
            localFile = File.createTempFile("resim","jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        fragmentKimlikBilgisi kimlikBilgisi = new fragmentKimlikBilgisi(context);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        kimlikBilgisi.setOgrenci(ogrenci); // ögrenci kimlik bilgisi taşınır
        fragmentTransaction.replace(R.id.content_frame,kimlikBilgisi);
        fragmentTransaction.commit();
        System.out.println("------------------------------------------"+ogrenci.getAdSoyad());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View head = navigationView.getHeaderView(0);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        headerAd = head.findViewById(R.id.OgrenciAdiSoyadi);
        headerMail = head.findViewById(R.id.OgrenciEmail);
        headerResim = head.findViewById(R.id.ogrenciFotograf);
        headerAd.setText(ogrenci.getAdSoyad());
        headerMail.setText(ogrenci.getEmailAdres());
        Picasso.with(context).load(Uri.parse(ogrenci.getResim())).into(headerResim);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ogrenci, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_kimlikBilgisi) {
            fragmentKimlikBilgisi kimlikBilgisi = new fragmentKimlikBilgisi(context);
            kimlikBilgisi.setOgrenci(ogrenci);
            System.out.println(ogrenci.getAdSoyad());
            fragmentTransaction.replace(R.id.content_frame, kimlikBilgisi);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notlar) {
            fragmentNotGoruntuleme fragmentNotGoruntuleme = new fragmentNotGoruntuleme(ogrenci);
            fragmentTransaction.replace(R.id.content_frame, fragmentNotGoruntuleme);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_devamsizlik) {
            fragmentDevamsizlikGoruntuleme fragmentDevamsizlikGoruntuleme = new fragmentDevamsizlikGoruntuleme(ogrenci,context);
            fragmentTransaction.replace(R.id.content_frame, fragmentDevamsizlikGoruntuleme);
            fragmentTransaction.commit();
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);}
        else if (id == R.id.nav_cikis){

            mAuth.signOut();
            startActivity(new Intent(context, MainActivity.class));
            finish();



        }

        return true;
        }




    }
