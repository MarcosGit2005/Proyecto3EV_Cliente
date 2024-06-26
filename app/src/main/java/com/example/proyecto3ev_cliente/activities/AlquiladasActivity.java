package com.example.proyecto3ev_cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto3ev_cliente.API.Connector;
import com.example.proyecto3ev_cliente.R;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedCapítulo;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedCapítuloSinBotones;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedCorto;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedCortoSinBotones;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedPelicula;
import com.example.proyecto3ev_cliente.activities.actividades_detalladas.ActivityDetailedPeliculaSinBotones;
import com.example.proyecto3ev_cliente.activities.model.Contenido;
import com.example.proyecto3ev_cliente.base.BaseActivity;
import com.example.proyecto3ev_cliente.base.CallInterface;
import com.example.proyecto3ev_cliente.base.Parameters;

import java.util.List;

public class AlquiladasActivity extends BaseActivity implements CallInterface,View.OnClickListener {
    private RecyclerView recyclerViewAlquiladas;
    private AdaptadorRecycleViewContenidoCarritoAlquiladas adaptadorRecycleViewContenido;
    private List<Contenido> contenidos;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peliculas_alquiladas);

        recyclerViewAlquiladas = findViewById(R.id.recyclerViewAlquiladas);

        showProgress();
        executeCall(this);
    }

    @Override
    public void doInBackground() {
        contenidos = Connector.getConector().getAsList(Contenido.class,"/contenidoCliente/"+Parameters.idClienteSesión);
    }


    @Override
    public void doInUI() {
        hideProgress();

        adaptadorRecycleViewContenido = new AdaptadorRecycleViewContenidoCarritoAlquiladas(this, contenidos);

        adaptadorRecycleViewContenido.setOnClickListener(this);
        recyclerViewAlquiladas.setAdapter(adaptadorRecycleViewContenido);

        recyclerViewAlquiladas.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAlquiladas.getContext(),DividerItemDecoration.VERTICAL);
        recyclerViewAlquiladas.addItemDecoration(dividerItemDecoration);
    }
    @Override
    public void onClick(View view) {
        Contenido contenido = contenidos.get(recyclerViewAlquiladas.getChildAdapterPosition(view));
        Intent intent = null;
        if (contenido.getTipoContenido().equals("película")){
            intent = new Intent(this, ActivityDetailedPeliculaSinBotones.class);
        } else if (contenido.getTipoContenido().equals("capítulo")){
            intent = new Intent(this, ActivityDetailedCapítuloSinBotones.class);
        } else {
            intent = new Intent(this, ActivityDetailedCortoSinBotones.class);
        }
        intent.putExtra("contenido",contenido);
        startActivity(intent);
    }
}
