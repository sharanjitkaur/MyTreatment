package com.sharanjit.mytreatment.medicine;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.sharanjit.mytreatment.R;

import java.util.List;

public class MedicineListAdapter implements ListAdapter {

    private Context ctx;
    private List<MedicineData> tData;

    public MedicineListAdapter(Context pctx, List<MedicineData> ptData) {
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
//Log.d("TREATMENTTAG1", "Inflating Doctor List for Position: " + position);
        }

        MedicineData td1 = (MedicineData)this.getItem(position);

        final MedicineData td2 = td1;

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
            chcol.setText("Notes");
            chcol = (TextView) convertView.findViewById(R.id.chcolh3);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setTypeface(null, Typeface.BOLD);
            chcol.setBackgroundColor(Color.parseColor("#FF0066"));
            chcol.setTextColor(Color.BLACK);
            chcol.setText("Details");
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
            chcol.setText("Notes");
            chcol = (TextView) convertView.findViewById(R.id.chcolh3);
            chcol.setPadding(5, 0, 0, 0);
            chcol.setBackgroundColor(Color.TRANSPARENT);
            chcol.setTextColor(Color.TRANSPARENT);
            chcol.setText("Details");
        }

        chcol = (TextView) convertView.findViewById(R.id.chcol1);
        /*if ( position == 0 ) {
            chcol.setPadding(5, 50, 0, 0);
        } else {
            chcol.setPadding(5, 0, 0, 0);
        }*/
        chcol.setPadding(5, 0, 0, 0);
        chcol.setText(((td1.mname != null && td1.mname.trim().length() > 0) ? td1.mname : ""));
        chcol = (TextView) convertView.findViewById(R.id.chcol2);
        chcol.setPadding(5, 0, 0, 0);
        chcol.setText(((td1.getNotes() != null && td1.getNotes().trim().length() > 0) ? td1.getNotes() : ""));
        chcol = (TextView) convertView.findViewById(R.id.chcol3);
        chcol.setPadding(5, 0, 0, 0);
        chcol.setText(((td1.getSdate() != null && td1.getSdate().trim().length() > 0)?td1.getSdate():"") + ((td1.getEdate() != null && td1.getEdate().trim().length() > 0) ? (" to " + td1.getEdate()) : "") + ((td1.getFreq() != null && td1.getFreq().trim().length() > 0)?((td1.getFreq().equalsIgnoreCase("W"))?"\nWeekly":"\nDaily"):""));

        chcol = null;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MedicineForm.class);
                intent.putExtra("MedicineFormFlag", "modify");
                intent.putExtra("MedicineFormMID", td2.getMid());
                intent.putExtra("MedicineFormTID", td2.getTid());
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
