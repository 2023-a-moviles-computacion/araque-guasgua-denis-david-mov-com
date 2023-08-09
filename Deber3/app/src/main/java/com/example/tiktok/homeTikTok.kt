package com.example.tiktok

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.PagerSnapHelper
import android.view.View


class homeTikTok : AppCompatActivity() {

    private lateinit var recyclerViewParaTi: RecyclerView
    private lateinit var recyclerViewSiguiendo: RecyclerView
    private lateinit var adapterParaTi: TikTokAdapter
    private lateinit var adapterSiguiendo: TikTokAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_tik_tok)


        recyclerViewParaTi = findViewById(R.id.recyclerviewParaTi)
        recyclerViewSiguiendo = findViewById(R.id.recyclerviewSiguiendo)

        // Configura los LayoutManagers y SnapHelpers
        val layoutManagerParaTi = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewParaTi.layoutManager = layoutManagerParaTi
        val snapHelperParaTi = PagerSnapHelper()
        snapHelperParaTi.attachToRecyclerView(recyclerViewParaTi)

        val layoutManagerSiguiendo = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewSiguiendo.layoutManager = layoutManagerSiguiendo
        val snapHelperSiguiendo = PagerSnapHelper()
        snapHelperSiguiendo.attachToRecyclerView(recyclerViewSiguiendo)

        // Define los conjuntos de datos para "Para ti" y "Siguiendo"
        val dataListParaTi: List<TikTokItemModel> = listOf(
        /* Datos para "Para ti" */
            TikTokItemModel(
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 1",
                "Descripción de la canción 1",
                "@Usuario1"
            ),
            TikTokItemModel(
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 2",
                "Descripción de la canción 2",
                "@Usuario2"
            ),

            TikTokItemModel(
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 3",
                "Descripción de la canción 3",
                "@Usuario3"
            )
        )
        val dataListSiguiendo: List<TikTokItemModel> = listOf(
            TikTokItemModel(
                /* Datos para "Siguiendo" */
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 4",
                "Descripción de la canción 4",
                "@Usuario4"
            ),
            TikTokItemModel(
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 5",
                "Descripción de la canción 5",
                "@Usuario5"
            ),

            TikTokItemModel(
                //R.drawable.disco,
                R.drawable.comentario,
                R.drawable.like,
                R.drawable.compartir,
                R.drawable.favorito,
                R.drawable.user,
                "Canción 6",
                "Descripción de la canción 6",
                "@Usuario6"
            )
        )

        // Crea los adaptadores correspondientes
        adapterParaTi = TikTokAdapter(dataListParaTi)
        adapterSiguiendo = TikTokAdapter2(dataListSiguiendo)

        // Configura el RecyclerView inicialmente con el adaptador "Para ti"
        recyclerViewParaTi.adapter = adapterParaTi
        recyclerViewSiguiendo.adapter = adapterSiguiendo

        // Asigna los listeners a los botones
        val buttonParaTi: Button = findViewById(R.id.button2)
        val buttonSiguiendo: Button = findViewById(R.id.button)

        buttonParaTi.setOnClickListener {
            recyclerViewParaTi.visibility = View.VISIBLE
            recyclerViewSiguiendo.visibility = View.GONE
            adapterParaTi.notifyDataSetChanged()
        }

        buttonSiguiendo.setOnClickListener {
            recyclerViewParaTi.visibility = View.GONE
            recyclerViewSiguiendo.visibility = View.VISIBLE
            adapterSiguiendo.notifyDataSetChanged()
        }
    }
}
