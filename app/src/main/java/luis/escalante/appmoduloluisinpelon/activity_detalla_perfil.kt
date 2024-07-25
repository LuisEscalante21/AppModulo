package luis.escalante.appmoduloluisinpelon

import RecyclerViewHelpers.AdaptadorPacientes
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class activity_detalla_perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalla_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombresR = intent.getStringExtra("nombres")
        val apellidosR = intent.getStringExtra("apellidos")
        val edadR = intent.getIntExtra("edad",0)
        val enfermedadR = intent.getStringExtra("enfermedad")
        val num_habitacionR = intent.getIntExtra("num_habitacion",0)
        val num_camaR = intent.getIntExtra("num_cama",0)
        val medicamentoR = intent.getStringExtra("medicamentos")
        val fecha_ingresoR = intent.getStringExtra("fecha_ingreso")
        val hora_aplicacionR = intent.getStringExtra("hora_aplicacion")

        //Mando a llamar a todos los elementos de la pantalla
        val txtNombresDetalle = findViewById<TextView>(R.id.txtPacienteNombreI)
        val txtApellidosDetalle = findViewById<TextView>(R.id.txtPacienteApellidoI)
        val txtEdadDetalle = findViewById<TextView>(R.id.txtEdadPacienteI)
        val txtEnfermedadPaciente = findViewById<TextView>(R.id.txtEnfermedadPacienteI)
        val txtNumeroHabitacion = findViewById<TextView>(R.id.txtNumeroHabitacionI)
        val txtNumeroCama = findViewById<TextView>(R.id.txtNumeroCamaI)
        val txtMedicamento = findViewById<TextView>(R.id.txtMedicamentoI)
        val txtFechaIngreso = findViewById<TextView>(R.id.txtFechaIngresoI)
        val txtHoraAplicacion = findViewById<TextView>(R.id.txtHoraAplicacionI)

        //Asigarle los datos recibidos a mis TextView
        txtNombresDetalle.text = nombresR
        txtApellidosDetalle.text = apellidosR
        txtEdadDetalle.text = edadR.toString()
        txtEnfermedadPaciente.text = enfermedadR
        txtNumeroHabitacion.text = num_habitacionR.toString()
        txtNumeroCama.text = num_camaR.toString()
        txtMedicamento.text = medicamentoR
        txtFechaIngreso.text = fecha_ingresoR
        txtHoraAplicacion.text = hora_aplicacionR




    }
}