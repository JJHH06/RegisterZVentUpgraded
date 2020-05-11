package com.example.registerzvent.views.addguests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registerzvent.models.Guest
import java.lang.IllegalArgumentException

class AddguestsViewModelFactory(private var listadoInvitados: MutableList<Guest>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddguestsViewModel::class.java)){
            return AddguestsViewModel(listadoInvitados) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}