package app.pimatic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Pimatic extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pimatic);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout)
                this.findViewById(R.id.swipe_container);
        final WebView pimaticView = (WebView)
                findViewById(R.id.pimaticView);
        WebSettings webSettings = pimaticView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        pimaticView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Intent settingsIntent = new Intent(Pimatic.this, Settings.class);
                startActivity(settingsIntent);
                Toast.makeText(Pimatic.this, "Pimatic can't be reached",
                        Toast.LENGTH_LONG).show();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String ipAdress = sharedPreferences.getString("IP", null);
        if (ipAdress != null && !ipAdress.isEmpty()) {
            pimaticView.loadUrl("http://" + ipAdress + "/");
        } else {
            Intent settingsIntent = new Intent(Pimatic.this, Settings.class);
            startActivity(settingsIntent);
        }


        swipeLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Intent settingsIntent = new Intent(Pimatic.this, Pimatic.class);
                        startActivity(settingsIntent);
                        swipeLayout.setRefreshing(false);
                    }
                }
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pimatic, menu);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Intent settingsIntent = new Intent(Pimatic.this, Settings.class);
            startActivity(settingsIntent);
        } else if (id == R.id.nav_pimatic) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pimatic.org"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
