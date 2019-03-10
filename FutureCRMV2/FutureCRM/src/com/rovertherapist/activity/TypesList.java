package com.rovertherapist.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.entities.TypeEntity;
import com.helper.CategoryConstants;

public class TypesList extends CommonActivity {
	String address = "";
	MyCustomAdapter dataAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_types_list);
		address = this.getIntent().getStringExtra("addr");
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(TypesList.this);
		type = s.getString("types", CategoryConstants.ATTRACTIONS);
		toast(type);
		displayListView();
	}

	String type = "";

	private void displayListView() {

		// Array list of countries
		String[] typesArr = CategoryConstants.typesArr;
		ArrayList<TypeEntity> typesList = new ArrayList<TypeEntity>();

		for (int i = 0; i < typesArr.length; i++) {
			TypeEntity typeEntity = new TypeEntity(typesArr[i], false);
			typesList.add(typeEntity);
		}

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.typelistlayout,
				typesList);
		ListView listView = (ListView) findViewById(R.id.listView1);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				TypeEntity country = (TypeEntity) parent
						.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),
						"Clicked on Row: " + country.getName(),
						Toast.LENGTH_LONG).show();
			}
		});

	}

	private class MyCustomAdapter extends ArrayAdapter<TypeEntity> {

		private ArrayList<TypeEntity> countryList;

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<TypeEntity> countryList) {
			super(context, textViewResourceId, countryList);
			this.countryList = new ArrayList<TypeEntity>();
			this.countryList.addAll(countryList);
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.typelistlayout, null);

				holder = new ViewHolder();
				holder.name = (CheckBox) convertView
						.findViewById(R.id.checkBox1);
				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						TypeEntity country = (TypeEntity) cb.getTag();
						country.setSelected(cb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TypeEntity typeEntity = countryList.get(position);

			holder.name.setText(typeEntity.getName());
			holder.name.setChecked(typeEntity.isSelected());
			holder.name.setTag(typeEntity);
			if (type.length() > 0 && type.indexOf(typeEntity.getName()) != -1) {
				holder.name.setSelected(true);
				holder.name.setChecked(true);
				typeEntity.setSelected(true);
			} else {
				holder.name.setSelected(false);
				holder.name.setChecked(false);
				typeEntity.setSelected(false);
			}
			return convertView;

		}

	}

	public void checkButtonClick(View view) {

		StringBuffer responseText = new StringBuffer();

		ArrayList<TypeEntity> typeEntites = dataAdapter.countryList;
		int i = 0;
		for (TypeEntity typeEntity : typeEntites) {

			if (typeEntity.isSelected()) {
				i++;
				if (i == 1) {
					responseText.append(typeEntity.getName());
				} else {
					responseText.append("|" + typeEntity.getName());
				}
			}
		}

		Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_LONG)
				.show();
		SharedPreferences s = PreferenceManager
				.getDefaultSharedPreferences(TypesList.this);

		SharedPreferences.Editor editor = s.edit();
		editor.putString("types", responseText.toString());

		editor.commit();

		Intent intent = new Intent(TypesList.this, HomeActivity.class);
		intent.putExtra("types", responseText.toString());
		intent.putExtra("addr", address);
		TypesList.this.startActivity(intent);

	}
}
