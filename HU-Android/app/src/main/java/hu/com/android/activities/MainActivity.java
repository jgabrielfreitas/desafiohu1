package hu.com.android.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hu.com.android.BuildConfig;
import hu.com.android.R;
import hu.com.android.util.Utils;
import hu.com.android.adapter.HotelListAdapter;
import hu.core.api.Hotel;
import hu.core.retrofit.RetrofitCallback;
import hu.core.retrofit.RetrofitConsumer;
import hu.core.retrofit.ServiceApi;
import retrofit.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.searchToolbar)
    Toolbar searchToolbar;

    @Bind(R.id.hotelListView)
    ListView hotelListView;

    EditText edtSearch;

    List<Hotel> hotelsList = new ArrayList<>();
    MenuItem mSearchAction;
    boolean isSearchOpened = false;

    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        handleMenuSearch();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(searchToolbar);
        hotelListView.setOnItemClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        loadHotels();
    }

    private void loadHotels() {

        RetrofitConsumer<List<Hotel>> listRetrofitConsumer = new RetrofitConsumer<List<Hotel>>(this, BuildConfig.URL_API);
        ServiceApi serviceApi = listRetrofitConsumer.getRetrofit().create(ServiceApi.class);
        listRetrofitConsumer.setWorkInBackground(false);
        listRetrofitConsumer.setDialogMessage("Carregando hotéis...");
        listRetrofitConsumer.executable(serviceApi.getHotelsList());
        listRetrofitConsumer.setRetrofitCallback(new RetrofitCallback<List<Hotel>>() {

            public void onSuccess(List<Hotel> response) {
                hotelsList = response;
                refreshListView(response);
            }

            public void clientError(List<Hotel> object, Response<List<Hotel>> response) {
                Utils.feedback(MainActivity.this, "Ocorreu um erro, tente novamente");
            }

            public void internalServerError() {
                Utils.feedback(MainActivity.this, "Erro na comunicação com o servidor, tente novamente");
            }

            public void onError(Throwable t) {
                Utils.feedback(MainActivity.this, "Oooops....Desculpe, ocorreu um erro, tente novamente");
                t.printStackTrace();
            }
        });
        listRetrofitConsumer.run();
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSearch = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSearch.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    doSearch(s.toString());
                }

                public void afterTextChanged(Editable s) {}
            });

            edtSearch.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = true;
        }
    }

    private void doSearch(String toSearch) {
        if (hotelsList.size() > 0) {
            List<Hotel> hotelsWithFilterList = new ArrayList<>();
            for (Hotel hotel : hotelsList)
                if (hotel.getName().startsWith(toSearch) == true)
                    hotelsWithFilterList.add(hotel);

            refreshListView(hotelsWithFilterList);
        }
    }

    public void onBackPressed() {
        if (isSearchOpened == true) {
            handleMenuSearch();
            refreshListView(hotelsList);
            return;
        }
        super.onBackPressed();
    }

    public void refreshListView(List<Hotel> items) {
        HotelListAdapter hotelListAdapter = new HotelListAdapter(this, items);
        hotelListView.setAdapter(hotelListAdapter);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Hotel selectedHotel = ((HotelListAdapter) hotelListView.getAdapter()).getItem(position);
        Intent detailsIntent = new Intent(this, HotelDetailsActivity.class);
        detailsIntent.putExtra(Utils.SELECTED_ID, Integer.parseInt(selectedHotel.getId()));
        startActivity(detailsIntent);
    }
}
