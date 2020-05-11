package com.example.registerzvent.views.guests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registerzvent.models.Guest

/**
 * ViewModel de GuestFragment
 *
 * Jose Hurtarte 19707
 */
class GuestsViewModel(var listadoInvitados: MutableList<Guest>) : ViewModel() {
    //La lista que va a contener a todos los invitados exitentes, al menos en este caso
    private var guests_register: MutableList<Guest> =
        mutableListOf()




    //Lista que contendra a los invitados
    private val _infoGuests = MutableLiveData<String>()

    //livedata para el despliegue de todos los invitados en un scroll view
    val infoGuests: LiveData<String>
        get() = _infoGuests



    init {
        Log.i("GuestsViewModel", "GuestsViewModel created!")
        guests_register = listadoInvitados

        _infoGuests.value = infoOfAllGuests() //Se inicializa con la funcion de resumen de invitados
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GuestsViewModel", "GuestsViewModel destroyed!")
    }

    /**
     * Crea un texto concatenado para cada invitado
     *
     * pre: Debe de tener al menos 1 invitado en la lista
     * post: Mediante un ciclo concatena todas los parametros de cada Invitado
     */
    private fun infoOfAllGuests() :String{
        if(guests_register.size>0) { //progra defensiva
            var mensaje = ""
            var posicion = 0 //posicion de la lista en la que esta posicionado


            //Aqui hago el loop para que recorra toda la lista
            while (posicion < guests_register.size) {
                mensaje += "\n\n" + "Invitado "+(posicion+1) +"\n" +
                        "Nombre: " +guests_register[posicion].name + "\n" +
                        "Teléfono: "+ guests_register[posicion].phone +"\n" +
                        "Correo Electrónico: "+guests_register[posicion].email

                //se suma una posicion
                posicion++
            }
            return mensaje
        }
        else{
            return ""
        }
    }

}