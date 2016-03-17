package hu.com.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hu.com.android.BuildConfig;
import hu.com.android.R;
import hu.com.android.util.Utils;
import hu.com.android.adapter.AvailabilityAdapter;
import hu.core.api.Hotel;
import hu.core.retrofit.RetrofitCallback;
import hu.core.retrofit.RetrofitConsumer;
import hu.core.retrofit.ServiceApi;
import retrofit.Response;

public class HotelDetailsActivity extends AppCompatActivity {

    int selectedId;

    @Bind(R.id.hotelDetailsNameTextView)
    TextView hotelDetailsNameTextView;

    @Bind(R.id.hotelDetailsLocationTextView)
    TextView hotelDetailsLocationTextView;

    @Bind(R.id.datesListView)
    ListView datesListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        ButterKnife.bind(this);

        selectedId = getIntent().getExtras().getInt(Utils.SELECTED_ID);
    }

    protected void onResume() {
        super.onResume();

        RetrofitConsumer<Hotel> listRetrofitConsumer = new RetrofitConsumer<>(this, BuildConfig.URL_API);
        ServiceApi serviceApi = listRetrofitConsumer.getRetrofit().create(ServiceApi.class);
        listRetrofitConsumer.setWorkInBackground(false);
        listRetrofitConsumer.setDialogMessage("Carregando hotéis...");
        listRetrofitConsumer.executable(serviceApi.getHotel(selectedId));
        listRetrofitConsumer.setRetrofitCallback(new RetrofitCallback<Hotel>() {

            public void onSuccess(Hotel response) {

                hotelDetailsNameTextView.setText(response.getName());
                hotelDetailsLocationTextView.setText(response.getLocation());

                datesListView.setAdapter(new AvailabilityAdapter(HotelDetailsActivity.this, response.getAvailabilityDays()));
            }

            public void clientError(Hotel object, Response<Hotel> response) {
                Utils.feedback(HotelDetailsActivity.this, "Ocorreu um erro, tente novamente");
            }

            public void internalServerError() {
                Utils.feedback(HotelDetailsActivity.this, "Erro na comunicação com o servidor, tente novamente");
            }

            public void onError(Throwable t) {
                Log.e("Hotel", "onError");
                t.printStackTrace();
                Utils.feedback(HotelDetailsActivity.this, "Oooops....Desculpe, ocorreu um erro, tente novamente");
            }
        });
        listRetrofitConsumer.run();
    }
}
