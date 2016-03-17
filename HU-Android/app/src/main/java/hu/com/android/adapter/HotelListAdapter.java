package hu.com.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hu.com.android.R;
import hu.core.api.Hotel;

/**
 * Created by JGabrielFreitas on 17/03/16.
 */
public class HotelListAdapter extends BaseAdapter {

    Context context;
    List<Hotel> hotels;

    public HotelListAdapter(Context context, List<Hotel> hotels) {
        this.context = context;
        this.hotels = hotels;
    }

    public int getCount() {
        return hotels.size();
    }

    public Hotel getItem(int position) {
        return hotels.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Hotel hotel = hotels.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.hotel_row, parent, false);

        TextView hotelNameTextView = (TextView) view.findViewById(R.id.hotelNameTextView);
        hotelNameTextView.setText(hotel.getName());

        return view;
    }
}
