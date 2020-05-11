package com.example.registerzvent.views.registry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registerzvent.models.Guest
import java.lang.IllegalArgumentException

/**
 * Factory para el registro
 *
 * Jose Hurtarte 19707
 */
class RegistryViewModelFactory(private var listadoInvitados: MutableList<Guest>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistryViewModel::class.java)){
            return RegistryViewModel(listadoInvitados) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}