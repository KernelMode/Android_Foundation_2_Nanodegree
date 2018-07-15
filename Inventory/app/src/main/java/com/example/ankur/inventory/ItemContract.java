package com.example.ankur.inventory;

import android.provider.BaseColumns;

/**
 * Created by Ankur on 23-03-2018.
 */

public final class ItemContract {
    private ItemContract(){}

    public static class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "Inventory";
        public static final String _ID = "_id";
        public static final String COLUMN_INAME = "Itemname";
        public static final String COLUMN_QUANTITY = "Quantity";
        public static final String COLUMN_PRICE = "Price";
        public static final String COLUMN_IMAGE = "Image";
        public static final String COLUMN_SUPPLIER = "Supplier";
    }
}
