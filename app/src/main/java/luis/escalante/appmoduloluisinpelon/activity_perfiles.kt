package luis.escalante.appmoduloluisinpelon

import Modelo.ClaseConexion
import Modelo.Pacientes
import RecyclerViewHelpers.AdaptadorPacientes
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_perfiles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfiles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAgregar = findViewById<ImageView>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            val pantallaInicio = Intent(this, activity_Inicio::class.java)
            startActivity(pantallaInicio)
            overridePendingTransition(0, 0)
        }

        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)

        rcvPacientes.layoutManager = LinearLayoutManager(this)

        fun obtenerPacientes(): List<Pacientes> {
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from pacientes")!!
            val listaP = mutableListOf<Pacientes>()
            while (resulSet.next()) {
                val id_paciente = resulSet.getInt("id_paciente")
                val nombres = resulSet.getString("nombres")
                val apellidos = resulSet.getString("apellidos")
                val edad = resulSet.getInt("edad")
                val enfermedad = resulSet.getString("enfermedad")
                val num_habitacion = resulSet.getInt("num_habitacion")
                val num_cama = resulSet.getInt("num_cama")
                val medicamento = resulSet.getString("medicamentos")
                val fecha_ingreso = resulSet.getString("fecha_ingreso")
                val hora_aplicacion = resulSet.getString("hora_aplicacion")
                val valoresJuntos = Pacientes(
                    id_paciente,
                    nombres,
                    apellidos,
                    edad,
                    enfermedad,
                    num_habitacion,
                    num_cama,
                    medicamento,
                    hora_aplicacion,
                    fecha_ingreso
                )
                listaP.add(valoresJuntos)
            }
            return listaP
        }
        CoroutineScope(Dispatchers.IO).launch {
            val pacientesBD = obtenerPacientes()
            withContext(Dispatchers.Main) {
                val adapter = AdaptadorPacientes(pacientesBD)
                rcvPacientes.adapter = adapter
            }
        }
    }
}

