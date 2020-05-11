package com.example.registerzvent.views.guests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registerzvent.models.Guest
import java.lang.IllegalArgumentException


/**
 * ViewModelFactory de Guests
 *
 * Jose Hurtarte 19707
 */
class GuestsViewModelFactory(private var listadoInvitados: MutableList<Guest>): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GuestsViewModel::class.java)){
            return GuestsViewModel(listadoInvitados) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}