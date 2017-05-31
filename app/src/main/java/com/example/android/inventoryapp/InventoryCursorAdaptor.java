package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import static com.example.android.inventoryapp.R.id.price;

/**
 * Created by Nicholas on 5/24/2017.
 */

public class InventoryCursorAdaptor extends CursorAdapter {


    /**
     * Constructs a new {@link InventoryCursorAdaptor}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdaptor(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the item data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current item can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView nameText = (TextView) view.findViewById(R.id.name);
        TextView priceText = (TextView) view.findViewById(price);
        TextView stockText = (TextView) view.findViewById(R.id.in_stock);


        // Extract properties from cursor
        int name = cursor.getColumnIndex(InventoryEntry.Column_Item_Name);
        int price = cursor.getColumnIndex(InventoryEntry.Column_Item_Price);
        final int quantity = cursor.getColumnIndex(InventoryEntry.Column_Item_Quantity);



        String invName = cursor.getString(name);
        String invPrice = "Price: $" + cursor.getString(price);
        if (TextUtils.isEmpty(invPrice) || price == 0){
            invPrice = context.getString(R.string.free);
        }
        String invStock = "We currently have " + cursor.getString(quantity) + " in Stock";
        if(TextUtils.isEmpty(invStock) || quantity == 0){
            invStock = context.getString(R.string.out);
        }

//Handle buttons and add onClickListeners
        Button oderFive = (Button)view.findViewById(R.id.order);
        Button sellOne = (Button)view.findViewById(R.id.sell);

        oderFive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                quantity = quantity - 1;
                if (quantity <= 0){
                    quantity = 0;
                }

                notifyDataSetChanged();
            }
        });
        sellOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 quantity = quantity - 1;
                if (quantity <= 0){
                    quantity = 0;
                }

                notifyDataSetChanged();
            }
        });



        // Populate fields with extracted properties
        nameText.setText(invName);
        priceText.setText(invPrice);
        stockText.setText(invStock);
    }

}
