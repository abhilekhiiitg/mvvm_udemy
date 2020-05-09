package com.abhilekh.assignmentapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.abhilekh.assignmentapplication.R
import com.abhilekh.assignmentapplication.viewmodel.ListViewModel
import com.mindorks.framework.mvvm.utils.Status
import kotlinx.android.synthetic.main.fragmant_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmant_list, container, false)
    }


    private fun setupObserver() {
        viewModel.getAnimals().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    animalList.visibility = View.VISIBLE
                    loadingView.visibility = View.GONE
                    loadError.visibility = View.GONE
                    var data = it.data
                    if (data != null) {
                        listAdapter.updateAnimalList(data)
                    }
                }
                Status.LOADING -> {
                    loadingView.visibility = View.VISIBLE
                    animalList.visibility = View.GONE
                    loadError.visibility = View.GONE
                }
                Status.ERROR -> {
                    loadError.visibility = View.VISIBLE
                    animalList.visibility = View.GONE
                    loadingView.visibility = View.GONE
                }
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        setupObserver()
        Log.d("mvvm", "fragment-> fragment Created")
        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            Log.d("mvvm", "fragment-> swiped down for refresh")
            animalList.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            loadError.visibility = View.GONE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }
}