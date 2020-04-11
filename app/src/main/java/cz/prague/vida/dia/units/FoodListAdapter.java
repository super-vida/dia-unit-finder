package cz.prague.vida.dia.units;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cz.prague.vida.dia.units.UnitFinder.R;

public class FoodListAdapter extends ArrayAdapter<FoodPresenter> {

    private Context mContext;
    private int mResource;


    public FoodListAdapter(Context context, int resource, List<FoodPresenter> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Foodstuff foodstuff = getItem(position).getFoodstuff();
        String conversion = getItem(position).getConversion();
        String serving = getItem(position).getServing();

        FoodPresenter foodPresenter = new FoodPresenter(foodstuff,serving,conversion);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView textView2 = convertView.findViewById(R.id.textView2);
        TextView textView3 = convertView.findViewById(R.id.textView3);
        TextView textView4 = convertView.findViewById(R.id.textView4);

        textView2.setText(foodstuff.getName());
        textView3.setText(serving);
        textView4.setText(conversion);

        return convertView;

    }


}
