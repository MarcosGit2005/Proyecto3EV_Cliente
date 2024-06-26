package com.example.proyecto3ev_cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto3ev_cliente.API.Connector;
import com.example.proyecto3ev_cliente.R;
import com.example.proyecto3ev_cliente.activities.model.Factura;
import com.example.proyecto3ev_cliente.base.BaseActivity;
import com.example.proyecto3ev_cliente.base.CallInterface;
import com.example.proyecto3ev_cliente.base.Parameters;

import java.util.List;

public class FacturasActivity extends BaseActivity implements CallInterface, View.OnClickListener {
    private RecyclerView recyclerViewFacturas;
    private AdaptadorRecycleViewFactura adaptadorRecycleViewFactura;
    private List<Factura> facturas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);

        recyclerViewFacturas = findViewById(R.id.recyclerViewFacturas);

        showProgress();
        executeCall(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_facturas_carrito_alquiladas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void doInBackground() {
        facturas = Connector.getConector().getAsList(Factura.class,"/clientesFacturas/"+ Parameters.idClienteSesión);
    }


    @Override
    public void doInUI() {
        hideProgress();

        adaptadorRecycleViewFactura = new AdaptadorRecycleViewFactura(this, facturas);

        adaptadorRecycleViewFactura.setOnClickListener(this);
        recyclerViewFacturas.setAdapter(adaptadorRecycleViewFactura);

        recyclerViewFacturas.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFacturas.getContext(),DividerItemDecoration.VERTICAL);
        recyclerViewFacturas.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onClick(View view) {
        Factura factura = facturas.get(recyclerViewFacturas.getChildAdapterPosition(view));
        Intent intent = new Intent(this, LineasActivity.class);
        intent.putExtra("factura",factura);
        intent.putExtra("con_boton",false);
        startActivity(intent);
    }
}
