package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/**
 * The main activity class.  It just sets listeners for the two buttons.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        //
        setContentView( R.layout.activity_main );

        // must use androidx version.
        // assigning ID of the toolbar to a variable
        toolbar = findViewById( R.id.toolbar );

        // Use this in place of action bar that would be placed as default
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);

        // collection of styles that will help style our entire application (similar to a css file).
        // can inherit from an already existing theme. Replace a default theme for-example (NoActionBar in the themes.xml parent attribute).
        // res -> values -> themes -> themes.xml


        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout ); // getDrawerLayout
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled( true );
        drawerToggle.syncState();

        // Connect DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener( drawerToggle ); // add the drawer listener (toggle)

        // Find the drawer view
        navigationView = findViewById( R.id.nvView );
        navigationView.setNavigationItemSelectedListener(

        // create a menu folder in res -> menu via new menu resource file
        menuItem -> {
            selectDrawerItem( menuItem );
            return true;
        });
    }

    // user selection of the navigation drawer
    public void selectDrawerItem( MenuItem menuItem ) {
        Fragment fragment = null;

        // user selects an item in the drawer (selects which fragment to navigate to)
        // Create a new fragment based on the used selection in the nav drawer
        switch( menuItem.getItemId() ) {
            case R.id.menu_start_quiz:
                fragment = new StartNewQuizFragment();
                break;
            case R.id.menu_past_quizzes:
                fragment = new PastQuizzesFragment();
                break;
            case R.id.menu_help:
                fragment = new HelpFragment();
                break;
            case R.id.menu_close_app:
                finish(); // shuts down the navigation
                break;
            default:
                return;
        }

        // Set up the fragment by replacing any existing fragment in the main activity
        FragmentManager fragmentManager = getSupportFragmentManager();

        // fragmentContainerView has MainScreen fragment as default
        // replaces the original fragment with new fragment using the replace method
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    // provides the definition of the menu
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.

        // This drawable shows a Hamburger icon when drawer is closed and an arrow when drawer is open.
        // It animates between these two states as the drawer opens.
        // String resources must be provided to describe the open/close drawer actions for accessibility services.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close );
    }

    // onPostCreate is called when activity start-up is complete after onStart()
    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged( @NonNull Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged( newConfig );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        if( drawerToggle.onOptionsItemSelected( item ) ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

}
