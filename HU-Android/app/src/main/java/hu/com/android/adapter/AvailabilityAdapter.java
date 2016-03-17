package hu.com.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hu.com.android.R;
import hu.core.api.AvailabilityDays;
import hu.core.api.Hotel;

/**
 * Created by JGabrielFreitas on 17/03/16.
 */
public class AvailabilityAdapter extends BaseAdapter {

    Context context;
    List<AvailabilityDays> availabilityList;

    public AvailabilityAdapter(Context context, List<AvailabilityDays> availabilityList) {
        this.context = context;
        this.availabilityList = availabilityList;
    }

    public int getCount() {
        return availabilityList.size();
    }

    public AvailabilityDays getItem(int position) {
        return availabilityList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        AvailabilityDays currentAvailability = availabilityList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.availability_row, parent, false);

        TextView dateTextView        = (TextView) view.findViewById(R.id.dateTextView);
        TextView isAvailableTextView = (TextView) view.findViewById(R.id.isAvailableTextView);

        dateTextView.setText(currentAvailability.getDate());

        if (currentAvailability.isAvailable() == true) {
            isAvailableTextView.setText(context.getString(R.string.available));
            isAvailableTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else {
            isAvailableTextView.setText(R.string.not_available);
            isAvailableTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
        }

        return view;
    }
}
