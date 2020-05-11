package com.example.registerzvent.views.registry

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registerzvent.models.Guest
import com.example.registerzvent.views.activity.MainActivity

/**
 * Clase de ViewModel para el registro
 * Jose Hurtarte 19707
 */
class RegistryViewModel(var listadoInvitados: MutableList<Guest>): ViewModel() {


    override fun onCleared() {
        super.onCleared()
        Log.i("RegistryViewModel", "RegistryViewModel destroyed!")
    }



    //La lista que va a contener a todos los invitados exitentes, al menos en este caso
     var guests_register: MutableList<Guest> =
    mutableListOf()


    //Contador de las posiciones con implementacion de LiveData
    private val _contador = MutableLiveData<Int>()
    val contador: LiveData<Int>
        get() = _contador

    /**
     * Livedata para la variable que se utiliza con el Livedata en el xml
     */
    private val _invitadoActual = MutableLiveData<Guest>()
    val invitadoActual: LiveData<Guest> get() = _invitadoActual


    private var registrados = 0

    private var mensaje = ""


    /**
     * Devuelve el invitado actual del registro
     */
    private fun actualRegistryGuest() : Guest{

        return guests_register[_contador.value!!]
    }

    fun titleOfActionBar() : String{

        return "Registrando" + " ("+(_contador.value!!+1)+"/"+guests_register.size+")"
    }



    fun progressInList(){
        _invitadoActual.value = actualRegistryGuest()
    }
    /**
     * Esta es la primera que se debe de llamar para poder mandar la info
     * si no se llama esa, el contador de registrados va a estar en 0 y el mensaje vacio
     *
     */
    fun finalToResults() {
        if (numberOfInvitedPeople() > 0) {
            //Se inicializa mensaje asi ya que sino va a tener una , en el inicio
            mensaje = guests_register[0].name + ": " + guests_register[0].registered
            var posicion = 1 //posicion de la lista en la que esta posicionado


            registrados = 0

            if (guests_register[0].registered == "Si") {
                registrados++;
            }

            //Aqui hago el loop para que recorra toda la lista
            while (posicion < guests_register.size) {
                mensaje += ", " + guests_register[posicion].name + ": " + guests_register[posicion].registered //Agrega a el string de resumen

                //Si esta registrado va a sumar 1 al contador de registrados
                if (guests_register[posicion].registered == "Si") {
                    registrados++;
                }
                //se suma una posicion
                posicion++
            }
        }
        mensaje += "." //Se agrega punto final al mensaje


    }

    init {
        guests_register = listadoInvitados
        Log.i("RegistryViewModel", "RegistryViewModel created!")
        _contador.value = 0
        if (guests_register.size==0){
            _invitadoActual.value = Guest()
        }
        else {
            _invitadoActual.value = actualRegistryGuest()
        }
    }

    /**
     * mensaje final que ira en el toast de resultados
     *
     * pre: debe de haber sido llamada la funcion finalToResult() para que de el mensaje bien
     * post: devuelve el texto concatenado que se realiza en la funcion de finalToResult()
     */
    fun finalResultMessage(): String{
        return mensaje
    }



    /**
     * Numero final de registrados
     *
     * pre: debe de haber sido llamada la funcion finalToResult() para que de el dato verdadero
     * post: dara la cantidad de personas que tengan 'Si' en el campo de registrados
     */
    fun finalNumberOfRegisteredGuests(): Int{
        return registrados
    }

    /**
     * dara el numero total de personas que han sido invitadas
     *
     * post: da la cantidad de Guests que estan en el mutableList de guest_register
     */
    fun numberOfInvitedPeople(): Int{
        return guests_register.size
    }

    /**
     * agrega 1 a la posicion en la que se esta en la lista de guests
     */
    fun nextGuest(){
        _contador.value = (_contador.value)?.plus(1)
    }

    /**
     * boolean que verifica si ya se registraron todas las personas en la Lista
     */
    fun registryIsFinish(): Boolean {
        return _contador.value!! >= guests_register.size
    }


    /**
     * Hace que se confirme al invitado actual
     */
    fun confirmedRegisterGuest(){
        guests_register[_contador.value!!].registered = "Si"
        nextGuest()
    }




}