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
import com.abhilekh.assignmentapplication.model.Hit
import com.abhilekh.assignmentapplication.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragmant_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())
    private val animalListDataObserver = Observer<List<Hit>> { list ->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }
    private val errorDataObserver = Observer<Boolean> { isError ->
        loadError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            animalList.visibility = View.GONE
            loadingView.visibility = View.GONE
        }
    }
    private val loadingDataObserver = Observer<Boolean> { isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            animalList.visibility = View.GONE
            loadError.visibility = View.GONE
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmant_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loadError.observe(this, errorDataObserver)
        viewModel.loading.observe(this, loadingDataObserver)
        Log.d("mvvm", "fragment-> fragment Created")
        // viewModel.refresh()
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