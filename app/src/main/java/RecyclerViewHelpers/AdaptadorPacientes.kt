package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.Pacientes
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luis.escalante.appmoduloluisinpelon.R
import java.sql.SQLException

class AdaptadorPacientes(var Datos: List<Pacientes>): RecyclerView.Adapter<ViewHolderPacientes>() {

    fun updateLista(newLista: List<Pacientes>) {
        Datos = newLista
        notifyDataSetChanged()
    }

    fun updateScreen(
        id_paciente: Int,
        new_Nombres: String,
        new_Apellidos: String,
        new_edad: Int,
        new_enfermadad: String,
        new_num_habitacion: Int,
        new_num_cama: Int,
        new_medicamentos: String,
        new_fecha_ingreso: String,
        new_hora_aplicacion: String
    ) {
        val index = Datos.indexOfFirst { it.id_paciente == id_paciente }
        if (index != -1){
            Datos[index].nombres = new_Nombres
            Datos[index].apellidos = new_Apellidos
            Datos[index].edad = new_edad
        Datos[index].enfermedad = new_enfermadad
        Datos[index].num_habitacion = new_num_habitacion
        Datos[index].num_cama = new_num_cama
        Datos[index].medicamento = new_medicamentos
        Datos[index].fecha_ingreso = new_fecha_ingreso
        Datos[index].hora_aplicacion = new_hora_aplicacion
        notifyItemChanged(index)
    }
}

    fun deleteData(context: Context, id_paciente: Int, position: Int) {
        val dataList = Datos.toMutableList()
        dataList.removeAt(position)

        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            if(objConexion != null) {
                try {
                    objConexion.autoCommit = false

                    val borrarPaciente =
                        objConexion.prepareStatement("delete from Pacientes where id_paciente = ?")!!
                    borrarPaciente.setInt(1, id_paciente)
                    val pacienteEliminado = borrarPaciente.executeUpdate()
                    Log.d("deleteData", "Paciente eliminado: $pacienteEliminado")

                    objConexion.commit()
                    Log.d("deleteData", "Commit exitoso")

                    withContext(Dispatchers.Main) {
                        Datos = dataList.toList()
                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                        Toast.makeText(
                            context,
                            "Paciente borrado correctamente",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } catch (e: SQLException) {
                    Log.e("deleteData", "error sql", e)
                    try {
                        objConexion.rollback()
                        Log.d("deleteData", "Todo bien con el rollback")
                    }catch (rollbackEx: SQLException){
                        Log.e("deleteData", "No esta bien el rollback", rollbackEx)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al borrar paciente: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    Log.e("deleteData", "Error que no se esperaba", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error que no se esperaba: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } finally {
                    try {
                        objConexion.close()
                        Log.d("deleteData", "se cerro la conexion")
                    }catch (closeEx: SQLException){
                        Log.e("deleteData", "no cierra la conexion", closeEx)
                    }
                }
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "no furula la base", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun updateData(context: Context, newNombres: String, newApellidos: String, newEdad: Int, newEnfermedad: String, newnum_habitacion: Int, newnum_cama: Int, newmedicamentos: String,  newfecha_ingreso: String,newhora_aplicacion: String, id_paciente: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val objConexion = ClaseConexion().cadenaConexion()
            if(objConexion != null) {
                try {
                    val actualizarPaciente =
                        objConexion.prepareStatement("update Pacientes set nombres =?, apellidos = ?, edad = ?, enfermedad = ?, num_habitacion = ?, num_cama = ?, medicamentos = ?, fecha_ingreso = ?, hora_aplicacion = ? where id_paciente = ?")!!
                    actualizarPaciente.setString(1, newNombres)
                    actualizarPaciente.setString(2, newApellidos)
                    actualizarPaciente.setInt(3, newEdad)
                    actualizarPaciente.setString(4, newEnfermedad)
                    actualizarPaciente.setInt(5, newnum_habitacion)
                    actualizarPaciente.setInt(6, newnum_cama)
                    actualizarPaciente.setString(7, newmedicamentos)
                    actualizarPaciente.setString(8, newfecha_ingreso)
                    actualizarPaciente.setString(9, newhora_aplicacion)
                    actualizarPaciente.setInt(10, id_paciente)
                    actualizarPaciente.executeUpdate()

                    withContext(Dispatchers.Main) {
                        updateScreen(
                            id_paciente,
                            newNombres,
                            newApellidos,
                            newEdad,
                            newEnfermedad,
                            newnum_habitacion,
                            newnum_cama,
                            newmedicamentos,
                            newfecha_ingreso,
                            newhora_aplicacion
                        )
                        Toast.makeText(
                            context,
                            "Todo se actualizo joya",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Revisa bien eso", Toast.LENGTH_SHORT)
                            .show()
                    }
                    e.printStackTrace()
                }finally {
                    objConexion.close()
                }
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "La conexion esta mal (ip)", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPacientes {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_cardpaciente, parent, false)
        return ViewHolderPacientes(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        val item = Datos[position]
        holder.txtPacienteNombre.text = item.nombres
        holder.txtPacienteApellido.text = item.apellidos

        holder.btnBorrar.setOnClickListener {
            val context = holder.itemView.context

            val builder = androidx.appcompat.app.AlertDialog.Builder(context)
            builder.setTitle("Borrar")
            builder.setMessage("Estás segur@ de borrar este paciente?")

            builder.setPositiveButton("Si") { dialog, which ->
                deleteData(context, item.id_paciente, position)
            }

            builder.setNeutralButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

    }

}