package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(activity?.application)
        val dataSource  = AsteroidsDatabase.getInstance(application)

        binding.lifecycleOwner = this

        val factory = MainViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        binding.viewModel = viewModel

        val adapter = AsteroidsAdapter(AsteroidsAdapter.AsteroidClickListener{
            asteroid ->  viewModel.onItemClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroidsList.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }
        viewModel.navigateToAsteroidDetail.observe(viewLifecycleOwner){asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.doneNavigating()
            }
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.show_week_menu ->
            {
                viewModel.getWeekAsteroids()
            }
            R.id.show_today_menu ->
            {
                viewModel.getTodayAsteroids()
            }
            R.id.show_saved_menu ->
            {
                viewModel.getSavedAsteroids()
            }
        }
        return true
    }
}
