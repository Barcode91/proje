package com.example.proje.com.example.proje.ogretmenactivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proje.com.example.proje.kayit_ve_login_activitiyleri.MainActivity;
import com.example.proje.R;
import com.example.proje.com.example.proje.tanımliclasslar.Ogrenci;
import com.example.proje.com.example.proje.tanımliclasslar.Ogretmen;
import com.example.proje.com.example.proje.ogrenci_veli_activity.fragmentKimlikBilgisi;
import com.google.firebase.auth.FirebaseAuth;

public class OgretmenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    public static Ogretmen ogretmen;
    TextView headerAd, headerMail ;
    ImageView headerResim;
    static Ogrenci ogrenci1;
    static String ogretmenBolum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ogretmen);
        context=this;

        Intent intent=getIntent();
        ogretmen=(Ogretmen) intent.getSerializableExtra("ogretmen");
        ogretmenBolum=ogretmen.getBolum();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentKimlikBilgisi kimlikBilgisi = new fragmentKimlikBilgisi(context);
        kimlikBilgisi.setOgretmen(ogretmen);  // öğretmen profil bilgileri için nesne taşınır
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentSiniflar siniflar = new fragmentSiniflar(context);
        fragmentTransaction.replace(R.id.content_frame,siniflar);
        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View head = navigationView.getHeaderView(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        headerAd = head.findViewById(R.id.OgretmenAdiSoyadi);
        headerMail = head.findViewById(R.id.OgretmenEposta);
        headerResim = head.findViewById(R.id.OgretmenFotograf);
        headerAd.setText(ogretmen.getAdSoyad());
        headerMail.setText(ogretmen.getEmailAdres());
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
        getMenuInflater().inflate(R.menu.ogretmen, menu);
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
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_notlar) {
            fragmentNotlar notlar =new fragmentNotlar(context);
            fragmentTransaction.replace(R.id.content_frame,notlar);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_degerlendirme) {
            fragmentDegerlendirme degerlendirme =new fragmentDegerlendirme(context);
            fragmentTransaction.replace(R.id.content_frame,degerlendirme);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_devamsizlik) {
           fragmentDevamsizlik devamsizlik =new fragmentDevamsizlik(context);
            fragmentTransaction.replace(R.id.content_frame,devamsizlik);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_sinfilar) {
            fragmentSiniflar siniflar = new fragmentSiniflar(context);
            fragmentTransaction.replace(R.id.content_frame,siniflar);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_cikis){
            FirebaseAuth auth=FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(context, MainActivity.class));
            finish();




        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
