package com.listpopulate;

import java.util.List;

import map.helper.RouteModel;
import map.helper.StringHelper;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlemaphelper.R;

//tell the directions whether left or right
public class CustomRoadListViewAdapter extends ArrayAdapter<RouteModel> {

	Context context;

	public CustomRoadListViewAdapter(Context context, int resourceId,
			List<RouteModel> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
		TextView viewonmap;
		ImageView evacuationicon;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		final RouteModel rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.location_row, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView
					.findViewById(R.id.disasterDetails);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.disasterDetailsDesc);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		String toBeSearched = "<div style=\"font-size:0.9em\">";
		String toBeSearched1 = "</div>";
		String s = rowItem.getHtml_instructions();
		s = s.replace(toBeSearched, "&nbsp;");
		s = s.replace(toBeSearched1, "&nbsp;");
		holder.txtDesc.setText(Html.fromHtml(s.trim()));
		String d = StringHelper.n2s(rowItem.getDirections());
		if (d.equalsIgnoreCase("null"))
			d = "";
		System.out.println(s.trim());
		Log.v("Map", rowItem.getHtml_instructions().trim());
		if (d.equalsIgnoreCase("turn-left")) {
			holder.imageView.setImageResource(R.drawable.take_left);
			holder.txtTitle.setText(rowItem.getDistancekm() + " "
					+ rowItem.getDuration());
		} else if (d.equalsIgnoreCase("turn-right")) {
			holder.imageView.setImageResource(R.drawable.take_right);
			holder.txtTitle.setText(rowItem.getDistancekm() + " "
					+ rowItem.getDuration());
		} else if (d.equalsIgnoreCase("start")) {
			holder.imageView.setImageResource(R.drawable.start);
			holder.txtTitle.setText(rowItem.getDistancekm() + " "
					+ rowItem.getDuration());
		} else if (d.equalsIgnoreCase("end")) {
			holder.imageView.setImageResource(R.drawable.end);
			holder.txtTitle.setText(rowItem.getDistancekm() + " "
					+ rowItem.getDuration());
		}

		else {
			holder.imageView.setImageResource(R.drawable.direction);
			holder.txtTitle.setText(d + " " + rowItem.getDistancekm() + " "
					+ rowItem.getDuration());
		}

		return convertView;
	}
}