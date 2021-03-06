package sun.tianyu.ijob;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.Reducer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import sun.tianyu.ijob.common.CommonActivity;
import sun.tianyu.ijob.controllers.newest.NewestFragment;
import sun.tianyu.ijob.controllers.search.OfferSearchFragment;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String TAG = "STYLOG";
    private IjobApplication application;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (IjobApplication)getApplication();
        if (application.defautValues.PRIVATE) {
            finish();
        }

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        helloCBL();
    }

    boolean doubleBackToExitPressedOnce;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "[戻る]をもう一度押すと終了", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void helloCBL() {
        createAllDocument(((IjobApplication) getApplication()).database);
        // Test Code
        outputAllDocs(((IjobApplication) getApplication()).database);

        // MapReduce Test
        testMapReduce();
    }

    // Test Code
    private String createDocument(Database database) {
        // Create a new document and add data

        /*　求人一覧 */
        Document document = database.createDocument();
        String documentId = document.getId();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = GregorianCalendar.getInstance();
        String currentTimeString = dateFormatter.format(calendar.getTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offer_id", "4");
        map.put("doc_type", "1");
        map.put("offer_name", ".NET開発");
        map.put("offer_type", "4");
        map.put("created_at", currentTimeString);
        map.put("offer_info", ".NET経験3年以上。人数2名");
        map.put("offer_term", "2015年8月 ~ 2016年4月");
        try {
            // Save the properties to the document
            document.putProperties(map);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        return documentId;
    }

    // Test Code
    // DB 初期化
    private String createAllDocument(Database database) {
        // Create a new document and add data

        /*　求人一覧 */
        Document document = database.createDocument();
        String documentId = document.getId();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = GregorianCalendar.getInstance();
        String currentTimeString = dateFormatter.format(calendar.getTime());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offer_id", "4");
        map.put("doc_type", "1");
        map.put("offer_name", ".NET開発");
        map.put("offer_type", "4");
        map.put("created_at", currentTimeString);
        map.put("offer_info", ".NET経験3年以上。人数2名");
        map.put("offer_term", "2015年8月 ~ 2016年4月");
        try {
            // Save the properties to the document
            document.putProperties(map);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        Document document2 = database.createDocument();
        String documentId2 = document2.getId();
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("offer_id", "1");
        map2.put("doc_type", "1");
        map2.put("offer_name", "Android 開発者");
        map2.put("offer_type", "2");
        map2.put("created_at", currentTimeString);
        map2.put("offer_info", "Androidアプリ設計、開発経験1年以上、HTTP通信プロトコル知識が持っている方");
        map2.put("offer_term", "2015年8月 ~ 2015年12月");
        try {
            // Save the properties to the document
            document2.putProperties(map2);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        Document document3 = database.createDocument();
        String documentId3 = document3.getId();
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("offer_id", "2");
        map3.put("doc_type", "1");
        map3.put("offer_name", "Java 開発者");
        map3.put("offer_type", "1");
        map3.put("created_at", currentTimeString);
        map3.put("offer_info", " 開発経験あり、日本語堪能の方、50万から");
        map3.put("offer_term", "2015年8月 ~ 2015年12月");
        try {
            // Save the properties to the document
            document3.putProperties(map3);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        Document document4 = database.createDocument();
        String documentId4 = document4.getId();


        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("offer_id", "3");
        map4.put("doc_type", "1");
        map4.put("offer_name", "PHP 開発者");
        map4.put("offer_type", "3");
        map4.put("created_at", currentTimeString);
        map4.put("offer_info", "PHP、MySQL、Apache、LDAPの知識、経験必須。人数３名");
        map4.put("offer_term", "2015年9月 ~ 中長期");
        try {
            // Save the properties to the document
            document4.putProperties(map4);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        Document document5 = database.createDocument();
        String documentId5 = document5.getId();
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("offer_id", "5");
        map5.put("doc_type", "1");
        map5.put("offer_name", "iOS 開発者");
        map5.put("offer_type", "2");
        map5.put("created_at", currentTimeString);
        map5.put("offer_info", "iOSアプリ設計、開発経験1年以上");
        map5.put("offer_term", "2015年8月 ~ 2015年12月");
        try {
            // Save the properties to the document
            document5.putProperties(map5);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }

        Document document6 = database.createDocument();
        String documentId6 = document6.getId();
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("offer_id", "6");
        map6.put("doc_type", "1");
        map6.put("offer_name", "とりあえず、Nullに設定がある");
        map6.put("offer_type", "2");
        map6.put("created_at", currentTimeString);
//        map5.put("offer_info", );
        map6.put("offer_term", "2015年8月 ~ 2015年12月");
        try {
            // Save the properties to the document
            document6.putProperties(map6);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }


        return documentId;
    }

    // Test Code
    private void testMapReduce() {
        Query q = getQuery(((IjobApplication) getApplication()).database,  0);


    }

    private void outputAllDocs (Database database) {
        // Query all document
        Query query = database.createAllDocumentsQuery();
//        query.setAllDocsMode(Query.AllDocsMode.ONLY_CONFLICTS);
        QueryEnumerator rows = null;
        try {
            rows = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Log.e("STYLOG", " Exception: "+e);
        }

        if (rows.getCount() == 0) {
            return;
        }
        Log.e("STYLOG", " Current DB Docs Count: "+ rows.getCount());

        int rowsCount = rows.getCount();
        for (int i=0; i<rowsCount; i++) {
            QueryRow row = rows.getRow(i);
            Document doc = row.getDocument();
            Log.e("STYLOG", " Current DB Docs:"+ " i: " + i + "  Doc:" + String.valueOf(doc.getProperties()));
        }

        com.couchbase.lite.View phoneView = database.getView("rmb");
        if (phoneView.getMap() == null) {
            Mapper map = new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    java.util.List<String> offerTypes = new ArrayList<>();
                    offerTypes.add(String.valueOf(document.get("offer_type")));
                    emitter.emit(offerTypes, document);
                }
            };
            Reducer reduce = new Reducer() {
                @Override
                public Object reduce(List<Object> keys, List<Object> values, boolean rereduce) {
                    for (int i = 0; i < keys.size(); i++) {
                        if (keys.lastIndexOf(keys.get(i)) == i) {
                            return keys.lastIndexOf(i);
                        }

                    }
                    return null;
                }
            };
            phoneView.setMapReduce(map, reduce, "2");

        }



//        phoneView.setMapReduce(new Mapper() {
//            @Override
//            public void map(Map<String, Object> document, Emitter emitter) {
//                java.util.List<String> offerTypes = new ArrayList<>();
//                offerTypes.add(String.valueOf(document.get("offer_type")));
//                emitter.emit(offerTypes, document);
//            }
//        }, new Reducer() {
//            @Override
//            public Object reduce(List<Object> keys, List<Object> values, boolean rereduce) {
//                for(int i = 0; i < keys.size(); i++) {
//                    if (keys.lastIndexOf(keys.get(i)) == i) {
//                        return keys.lastIndexOf(i);
//                    }
//
//                }
//
//                return null;
//            }
//
//        }, "2");

    }

    private void outputContents (Database database, String docID) {
        Document retrievedDocument = database.getDocument(docID);
        Log.e(TAG, "retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));
    }

    // Test Code
    private void updateDoc(Database database, String documentId) {
        Document document = database.getDocument(documentId);
        try {
            // Update the document with more data
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.putAll(document.getProperties());
            updatedProperties.put("eventDescription", "Everyone is invited!");
            updatedProperties.put("address", "123 Elm St.");
            document.putProperties(updatedProperties);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }
    }

    // Test Code
    private Query getQuery(Database database, final int category_num) {
        com.couchbase.lite.View view = database.getView("distinct_test");
        if (view.getMap() == null) {
            view.setMapReduce(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    java.util.List<Object> values = new ArrayList<Object>();
                    values.add(document.get("offer_type"));
                    emitter.emit("type", values);
                }
            }, new Reducer() {
                @Override
                public Object reduce(List<Object> keys, List<Object> values, boolean rereduce) {
                    List list = new ArrayList<String>();
                    for (int i=0; i<keys.size(); i++) {
                    }
                    for (int j=0; j<values.size(); j++) {
                        if (!list.contains(String.valueOf(values.get(j)))) {
                            list.add(String.valueOf(values.get(j)));
                        }
                    }
                    int count;
                    if (list != null) {
                        count = list.size();
                    } else {
                        count = 0;
                    }
                    Log.e("STYLOG", " list size: " + list.size());
                    return list;
                }
            }, "2");
        }

        Query query = view.createQuery();
        query.setDescending(true);

        QueryEnumerator rows = null;
        try {
            rows = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Log.e("STYLOG", " Exception: "+e);
        }

        if (rows.getCount() == 0) {
            Log.e("STYLOG", "Data null");
        }

        int rowsCount = rows.getCount();
        for (int i=0; i<rowsCount; i++) {
            QueryRow row = rows.getRow(i);
            Document doc = row.getDocument();
            List<String> type = (List<String>) row.getValue();
            Log.e("STYLOG", " NewestPagerFragment getQuery:"+ " i: " + i + "  Doc:" + String.valueOf(type));
        }


        return query;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                FragmentManager newestFragment = getSupportFragmentManager();
                newestFragment.beginTransaction()
                        .replace(R.id.container, NewestFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1:
                FragmentManager offerSearchFragment = getSupportFragmentManager();
                offerSearchFragment.beginTransaction().replace(R.id.container, OfferSearchFragment.newInstance(position + 1)).commit();
                break;
            default:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_newest);
                break;
            case 2:
                mTitle = getString(R.string.title_search);
                break;
            case 3:
                mTitle = getString(R.string.title_bookmark);
                break;
            case 4:
                mTitle = getString(R.string.title_look_history);
                break;
            case 5:
                mTitle = getString(R.string.title_apply_history);
                break;
            case 6:
                mTitle = getString(R.string.title_log_in);
                break;
            default:
                mTitle = "";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public void setTabs() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.removeAllTabs();
        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 15; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText("Tab " + (i + 1))
                            .setTabListener(tabListener));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            // Section
            getMenuInflater().inflate(R.menu.home, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER, 0));
        }
    }

}
