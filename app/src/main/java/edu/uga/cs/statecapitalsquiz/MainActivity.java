package edu.uga.cs.statecapitalsquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/**
 * The main activity class.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // must use androidx version.
        toolbar = findViewById(R.id.toolbar);

        // Use toolbar in place of action bar that would be placed as default.
        setSupportActionBar(toolbar);

        // collection of styles that will help style our entire application (similar to a css file).
        // can inherit from an already existing theme.
        // Replace a default theme for-example (NoActionBar in the themes.xml parent attribute).
        // res -> values -> themes -> themes.xml

        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // getDrawerLayout
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Connect DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle); // add the drawer listener (toggle)

        // Find the drawer view
        navigationView = findViewById(R.id.nvView);

        // when an menu item is selected the NavigatationItemSelected listener will
        // notify when a menu item is selected.
        navigationView.setNavigationItemSelectedListener(
        // create a menu folder in res -> menu via new menu resource file
        menuItem -> {
            selectDrawerItem(menuItem); //
            return true;
        }); // setNavigationItemSelectedListener
    } // onCreate

    // this method controls what happens after the user selection of the navigation drawer
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        // user selects an item in the drawer (selects which fragment to navigate to)
        // Create a new fragment based on the used selection in the nav drawer
        switch( menuItem.getItemId() ) {
            case R.id.menu_start_quiz:
                fragment = new ViewPagerFragment(); // displays the quiz for the user to take.
                break;
            case R.id.menu_past_quizzes:
                fragment = new PastQuizzesFragment(); // displays the recyclerView of all the past quizzes
                break;
            case R.id.menu_help:
                fragment = new HelpFragment(); // displays the help fragment
                break;
            case R.id.menu_close_app:
                finish(); // shuts down the navigation and the program
                break;
            default:
                return;
        } // switch

        // Set up the fragment by replacing any existing fragment in the main activity
        FragmentManager fragmentManager = getSupportFragmentManager();

        // fragmentContainerView has SplashScreen fragment as default
        // replaces the original fragment with new fragment using the replace method
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack("main screen").commit();

        // Close the navigation drawer after selection is made
        drawerLayout.closeDrawers();
    } // selectDrawerItem

    // provides the definition of the menu
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference. ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.

        // This drawable shows a Hamburger icon when drawer is closed and an arrow when drawer is open.
        // It animates between these two states as the drawer opens.
        // String resources must be provided to describe the open/close drawer actions for accessibility services.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    } // setupDrawerToggle

    // onPostCreate is called when activity start-up is complete after onStart()
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    } // onPostCreated

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    } // onConfigurationChanged

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } // if
        return super.onOptionsItemSelected(item);
    } // onOptionsItemSelected

    // use this method in FinalScoreFragment when the button is pressed to display "Past Quizzes Fragment"
    public void replaceFragment(String dateAndTime, double score) {
        Fragment fragment = null;
        try {
            fragment = PastQuizzesFragment.newInstance(dateAndTime, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack("Start New Quiz Fragment").commit();
    }
} // MainActivity
