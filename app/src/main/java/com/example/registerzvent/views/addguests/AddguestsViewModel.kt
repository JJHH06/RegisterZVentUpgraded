package com.example.registerzvent.views.addguests

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.registerzvent.models.Guest

class AddguestsViewModel(var listadoInvitados: MutableList<Guest>): ViewModel() {
    init {
        Log.i("AddguestsViewModel", "AdguestsViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("AddguestsViewModel", "AddguestsViewModel destroyed!")
    }
}