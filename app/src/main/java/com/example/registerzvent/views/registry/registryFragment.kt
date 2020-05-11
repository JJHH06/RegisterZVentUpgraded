package com.example.registerzvent.views.registry

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.registerzvent.R
import com.example.registerzvent.databinding.FragmentRegistryBinding
import com.example.registerzvent.models.Guest
import com.example.registerzvent.views.activity.MainActivity

import com.example.registerzvent.views.registry.registryFragmentDirections

/**
 * Clase de el fragmento de registro
 *
 * Jose Hurtarte 19707
 */
class registryFragment : Fragment() {   
    private lateinit var viewModel: RegistryViewModel
    private lateinit var viewModelFactory: RegistryViewModelFactory
    
    private var guest= Guest() //variable para databinding en el fragment
    private lateinit var binding:FragmentRegistryBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         binding = DataBindingUtil.inflate<FragmentRegistryBinding>(inflater,
             R.layout.fragment_registry,container,false)

        //Se asocia el modelo descrito en esta clase, con el de la vista
        binding.guest = guest



        Log.i("RegistryFragment", "Called ViewModelProviders.of")
        viewModelFactory = RegistryViewModelFactory((activity as MainActivity).guestList)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RegistryViewModel::class.java)

        binding.registryViewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner //Hace que se actualice el databinding cuando es utilizado



        if(viewModel.numberOfInvitedPeople()==0){  //Progra defensiva en caso de que este vacia la lista
            //Lanzo nuevo fragment
            Toast.makeText(context, "No hay invitados, ingreselos en el registro", Toast.LENGTH_LONG).show()
            moveToActivity()
        }
        else {
            //Le asigno el primer valor de la lista para que al iniciar, ya este alli
            //binding.guest = viewModel.actualRegistryGuest()
            //fijo los datos


            //Dato inicial del action bar para saber como va la progrsion de los invitados
            (activity as AppCompatActivity).supportActionBar?.title = viewModel.titleOfActionBar()
        }
        //Es para que se pueda hacer botones en el action bar
        setHasOptionsMenu(true)



        //Este es el observador del LiveData de el contador para cuando se cambie de casilla
        viewModel.contador.observe(viewLifecycleOwner, Observer {/*newContador->*/

            if(viewModel.registryIsFinish()){
                //Lanzo nuevo fragment
                moveToActivity()
            }
            else {
                //cambia de guest para mostrarlo por medio de LiveData
                viewModel.progressInList()



                //modifica el action bar con respecto a cuantas personas hayan pasado el registro
                //Modifica el texto de la action bar para mostrar estado de invitados
                (activity as AppCompatActivity).supportActionBar?.title = viewModel.titleOfActionBar()
            }

        })



        return binding.root
    }


    /**
     * Esta funcion hace los calculos para mandar como
     * parametros al otro fragment, y al finalizarlos
     * inicia el nuevo fragment y manda los parametros
     */
    private fun moveToActivity(){

        //La funcion que prepara los datos que se mandan al fragment de resultados
        viewModel.finalToResults()

        val action = registryFragmentDirections.actionRegistryFragmentToResultsFragment()
        action.invitedSummary = viewModel.finalResultMessage()
        action.registeredPeople = viewModel.finalNumberOfRegisteredGuests()
        action.invitedPeople = viewModel.numberOfInvitedPeople()

        //llamado al otro fragment
        view?.findNavController()?.navigate(action)
    }


    /**
     * Crea el registry menu, causando a[i que ya se puedan ver los botones
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.registry_menu, menu)

    }


    /**
     * Funciona como tipo click listener, solo que dependiendo del boton seleccionado
     * hace una accion distinta, la cual se decide con el when
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.guest_accepted -> {
                viewModel.confirmedRegisterGuest()
                //Aqui la funcion coloca Si al invitado actual y tambien avanza
            }

            R.id.guest_denied -> {
                viewModel.nextGuest()
                //Aqui no coloque un set al No, ya que esta definido por defecto asi que solo es necesario avanzar

            }
        }
        return super.onOptionsItemSelected(item)
    }


}
