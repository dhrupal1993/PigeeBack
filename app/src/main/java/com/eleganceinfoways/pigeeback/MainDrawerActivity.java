package com.eleganceinfoways.pigeeback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.eleganceinfoways.pigeeback.data.SharedData;
import com.eleganceinfoways.pigeeback.utils.CustomTypefaceSpan;
import com.eleganceinfoways.pigeeback.utils.GetFontstyle;
import com.eleganceinfoways.pigeeback.utils.MyAlertDialog;
import com.eleganceinfoways.pigeeback.utils.RuntimePermission;

import static com.eleganceinfoways.pigeeback.R.id.nav_home;
import static com.eleganceinfoways.pigeeback.utils.RuntimePermission.OnRequestPermissionsResultListener;

public class MainDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public RuntimePermission permission;
    SharedData sharedData;
    TextView tvemail, tvname;
    GetFontstyle getFontstyle;
    boolean isProvider = false;
    NavigationView navigationView;
    Menu nav_Menu;
    public Toolbar toolbar;
    public  static  int selectedPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        sharedData = new SharedData(this);
        sharedData.putInt("selectedPosition",0);
        getFontstyle = new GetFontstyle(MainDrawerActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvemail = (TextView) findViewById(R.id.tvemail);
        setTitle();
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        isProvider = sharedData.getBoolean("isProvider", false);
        permission = new RuntimePermission();



      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);
        tvemail = (TextView) header.findViewById(R.id.tvemail);
        tvname = (TextView) header.findViewById(R.id.tvname);
        if (selectedPosition == 0) displaySelectedScreen(nav_home);

        else displaySelectedScreen(selectedPosition);

        /*        if(isProvider)
            displaySelectedScreen(R.id.nav_customers);
        else
            displaySelectedScreen(R.id.nav_service_providers);*/
        navigationView.setItemIconTintList(null);

        showHideItems();

        setFontstyle();

        //Typeface for navigation view
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


        //Permission
     /*   asked(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, new OnRequestPermissionsResultListener() {
            @Override
            public void onGranted(String permission[], int requestCode) {
                //  gps.getLocation();
                //  objSharedData.putBoolean(IsBeaconCanScan, true);
                Log.e("onGranted", "" + permission.toString() + "-true");

            }

            @Override
            public void onDenied(String permission[], int requestCode) {
            }

        }, PERMISSION_REQUEST, getResources().getString(R.string.ok), getResources().getString(R.string.cancel), getResources().getString(R.string.location_permission));*/

    }


    public void setTitle() {
        if (toolbar != null)
            toolbar.setTitle("PigeeBack");
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
        getMenuInflater().inflate(R.menu.main_drawer, menu);
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
       /* int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        displaySelectedScreen(item.getItemId());
        //displaySelectedScreen(selectedPosition);
        return true;
    }

    public void asked(String permission[], OnRequestPermissionsResultListener listener, int requestCode, String ok, String cancel, String permission_msg) {
        this.permission = new RuntimePermission();
        this.permission.ask(this, listener, permission, requestCode, permission_msg, ok, cancel);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        selectedPosition = itemId;
        //sharedData.putInt("selectedPosition",itemId);
        //initializing the fragment object which is selected
        switch (itemId) {

            case nav_home:
                fragment = new Home();
                break;

            case R.id.nav_service_providers:
                fragment = new ServiceProvider();
                break;

            case R.id.nav_customers:
                fragment = new Customers();
                break;

            case R.id.nav_post_route:
                fragment = new PostRoute();
                break;

            case R.id.nav_post_pickup:
                fragment = new PostPickup();
                break;

            case R.id.nav_edit_ac:
                fragment = new EditAccount();
                break;

            case R.id.nav_my_list:
                fragment = new CustomerRoutesList();
                break;

            case R.id.nav_orders:
                fragment = new Orders();
                break;

            case R.id.nav_services:
                fragment = new Services();
                break;

            case R.id.nav_logout:
                Logout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    //Logout
    public void Logout() {
        showAlert("", getResources().getString(R.string.logoutmsg), new MyAlertDialog.OnClickListener() {
            @Override
            public void onClick() {
                sharedData.clearallData();
                Intent intent = new Intent(MainDrawerActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, null);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/CoreSansGRounded-Bold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void showAlert(String title, String message, MyAlertDialog.OnClickListener positive, MyAlertDialog.OnClickListener negative) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this, positive, negative);
        myAlertDialog.showCancel(getResources().getString(R.string.cancel), getResources().getString(R.string.ok), message);
    }

    public void setFontstyle() {
        if (getFontstyle != null) {
            tvemail.setTypeface(getFontstyle.CoreSansMedium());
            tvname.setTypeface(getFontstyle.CoreSansMedium());
        }
    }

    public void showHideItems() {
        if (isProvider) {
            nav_Menu.findItem(R.id.nav_post_pickup).setVisible(false);
            nav_Menu.findItem(R.id.nav_my_list).setVisible(false);
            nav_Menu.findItem(R.id.nav_service_providers).setVisible(false);
            nav_Menu.findItem(R.id.nav_customers).setVisible(true);
            nav_Menu.findItem(R.id.nav_post_route).setVisible(true);
            nav_Menu.findItem(R.id.nav_services).setVisible(true);

        } else {
            nav_Menu.findItem(R.id.nav_post_route).setVisible(false);
            nav_Menu.findItem(R.id.nav_services).setVisible(false);
            nav_Menu.findItem(R.id.nav_customers).setVisible(false);
            nav_Menu.findItem(R.id.nav_post_pickup).setVisible(true);
            nav_Menu.findItem(R.id.nav_my_list).setVisible(true);
            nav_Menu.findItem(R.id.nav_service_providers).setVisible(true);
        }
    }
}
