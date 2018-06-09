package com.example.metermanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class RoomInvoicingStatus extends Activity {
    ListView listView;
    RoomInvoiceStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoicesstatus);
        listView=(ListView)findViewById(R.id.invoices_Status);
        adapter=new RoomInvoiceStatusAdapter(this);
        listView.setAdapter(adapter);
    }
}
