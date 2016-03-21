package com.alhikmah.ciprb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Shihab on 3/11/2016.
 */
public class FormAdapter extends BaseAdapter {

        private final Context mContext;

        public FormAdapter(Context context) {

            this.mContext = context;
        }

        @Override
        public int getCount() {
            // return 100;
            return ApplicationData.formList.size();

        }

        @Override
        public Object getItem(int position) {
            return ApplicationData.formList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.listview_item_a_food, parent, false);

                holder = new ViewHolder();

                holder.res_name = (TextView) convertView
                        .findViewById(R.id.form_title);



                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Form resObj = ApplicationData.formList
                    .get(position);
            holder.res_name.setText(resObj.getTitle());

            return convertView;
        }

        class ViewHolder {

            private int catatory_id;
            private TextView res_name;
            private TextView res_area;
            private ImageView catagory_image;
            RatingBar ratingBar_restaurent;

        }


}
