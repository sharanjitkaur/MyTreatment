package com.sharanjit.mytreatment;

/*
<!--TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rowTextView"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:padding="10dp"
    android:textSize="16sp" >
</TextView-->
 */

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TreatmentListAdapter implements ListAdapter {

    private Context ctx;
    private List<TreatmentData> tData;
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

    public TreatmentListAdapter(Context pctx, List<TreatmentData> ptData) {
        this.ctx = pctx;
        this.tData = ptData;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return tData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int getCount() {
        return tData.size();
    }

    @Override
    public Object getItem(int position) {
        return tData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pposition = position;
        if ( convertView == null ) {
            LayoutInflater infalInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = infalInflater.inflate(R.layout.sr_childr, null);
            convertView = infalInflater.inflate(R.layout.treatmentlistrow, parent, false);
        }

        TreatmentData td1 = (TreatmentData)this.getItem(position);

        final TreatmentData td2 = td1;

        TextView chcol = null;
        if (position == 0) {
            chcol = (TextView) convertView.findViewById(R.id.chcolh1);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setTypeface(null, Typeface.BOLD);
            chcol.setBackgroundColor(Color.parseColor("#FF0066"));
            chcol.setTextColor(Color.BLACK);
            chcol.setText("Name");
            chcol = (TextView) convertView.findViewById(R.id.chcolh2);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setTypeface(null, Typeface.BOLD);
            chcol.setBackgroundColor(Color.parseColor("#FF0066"));
            chcol.setTextColor(Color.BLACK);
            chcol.setText("Description");
            chcol = (TextView) convertView.findViewById(R.id.chcolh3);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setTypeface(null, Typeface.BOLD);
            chcol.setBackgroundColor(Color.parseColor("#FF0066"));
            chcol.setTextColor(Color.BLACK);
            chcol.setText("Dates");
        } else {
            chcol = (TextView) convertView.findViewById(R.id.chcolh1);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setBackgroundColor(Color.TRANSPARENT);
            chcol.setTextColor(Color.TRANSPARENT);
            chcol.setText("Name");
            chcol = (TextView) convertView.findViewById(R.id.chcolh2);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setBackgroundColor(Color.TRANSPARENT);
            chcol.setTextColor(Color.TRANSPARENT);
            chcol.setText("Description");
            chcol = (TextView) convertView.findViewById(R.id.chcolh3);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setBackgroundColor(Color.TRANSPARENT);
            chcol.setTextColor(Color.TRANSPARENT);
            chcol.setText("Dates");
        }

        chcol = (TextView) convertView.findViewById(R.id.chcol1);
        /*if ( position == 0 ) {
            chcol.setPadding(5, 50, 0, 0);
        } else {
            chcol.setPadding(5, 0, 0, 0);
        }*/
        chcol.setPadding(5, 0, 0, 0);
        chcol.setText(td1.getName());
        chcol = (TextView) convertView.findViewById(R.id.chcol2);
        chcol.setPadding(5, 0, 0, 0);
        chcol.setText(td1.getDesc());
        chcol = (TextView) convertView.findViewById(R.id.chcol3);
        chcol.setPadding(5, 0, 0, 0);
        //chcol.setText(((td1.getSdate() != null)?df.format(td1.getSdate()):"") + "\n" + ((td1.getEdate() != null)?df.format(td1.getEdate()):""));
        chcol.setText( ((td1.getSdate() != null && td1.getSdate().trim().length() > 0)?td1.getSdate():"") + ((td1.getEdate() != null && td1.getEdate().trim().length() > 0)?("\n" + td1.getEdate()):""));

        chcol = null;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.setBackgroundColor(Color.GRAY);
                Log.d("TREATMENTTAG", "Yo: " + pposition + ", td: " + td2.getName() + ", id: " + td2.getTid());

                Intent intent = new Intent(ctx, TreatmentForm.class);
                intent.putExtra("TreatmentFormFlag", "modify");
                intent.putExtra("TreatmentFormID", td2.getTid());
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                ctx.startActivity(intent);

            }
        });


        if ( position%2 == 0 ) {
            convertView.setBackgroundColor(Color.parseColor("#FFFAFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFF5FA"));
        }

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
