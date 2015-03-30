package cronus.com.randomideas;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, IdeaHolder.OnFragmentInteractionListener, CustomDialog.CustomDialogListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public String[] contents;
    public FeedReaderContract.FeedReaderDbHelper feedReaderDbHelper;
    public FeedReaderContract contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        // initiate db
        contract = new FeedReaderContract();
        feedReaderDbHelper = contract.creatFeedReader(getApplicationContext());
        contents = contract.getDB(feedReaderDbHelper.getReadableDatabase());

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        FragmentManager fragmentManager = getSupportFragmentManager();

        // what item in the side nave menu was clicked
        // create the appropriate fragment
        if(position == 0){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, IdeaHolder.newInstance(""))
                    .commit();
        }else{
            ListView listViewFragment = new ListView();
            if(contents != null) {
                listViewFragment.setArray(contents);
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Put some ideas into the database", Toast.LENGTH_LONG).show();
                    }
                });
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.container, listViewFragment)
                    .commit();
        }


    }

    public void reloadListView(){
        contents = contract.getDB(feedReaderDbHelper.getReadableDatabase());

        FragmentManager fragmentManager = getSupportFragmentManager();

        ListView listViewFragment = new ListView();
        if(contents != null) {
            listViewFragment.setArray(contents);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Put some ideas into the database", Toast.LENGTH_LONG).show();
                }
            });
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, listViewFragment)
                .commit();
    }



    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
    public void onFragmentInteraction(int position) {

    }



    public void onRandomClick(View view){
        TextView textView = (TextView) this.findViewById(R.id.ideaText);
        Random r = new Random();
        if(contents != null && contents.length > 0) {
            int max = contents.length;
            int x = r.nextInt(max);
            int y = r.nextInt(max);
            textView.setText(contents[x] + ", " + contents[y]);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Put Some Ideas In The Database",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void onIdeaClick(View view){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(fragmentManager,"dialog");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // save whatever is in the dialog text box to the idea db
        EditText editText = (EditText) dialog.getDialog().findViewById(R.id.ideaTextInput);
        if(editText != null){

            final String value = editText.getText().toString();
            if(value.length()>0) {
                contract.insert(value, feedReaderDbHelper);
                reloadListView();
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Enter Something!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }





}
