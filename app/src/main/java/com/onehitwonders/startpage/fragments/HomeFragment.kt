package com.onehitwonders.startpage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.onehitwonders.startpage.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.shoppinginfo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HomeFragment() : Fragment() {

    private val listLoja = ArrayList<LojaItem>()

    private val scanCodeViewModel by lazy {
        activity?.let { ViewModelProviders.of(it).get(ScanCodeViewModel::class.java) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //listLoja.removeAll()
        val search = view.findViewById<SearchView>(R.id.barraPesquisa)

        val dao by lazy { ShoppingDatabase.getInstance(requireContext()).shoppingDao }

        lifecycleScope.launch {
            val listLojas = scanCodeViewModel?.scanCode?.let { dao.searchLojas(it.toInt()) }

            if (listLojas != null) {
                for (loja in listLojas) {
                    val exemploLoja = LojaItem(R.drawable.ic_baseline_shopping_bag_24, loja.nomeLoja, loja.pisoLoja.toString())
                    listLoja.add(exemploLoja)
                }
            }

        }

        shopRecyclerView.adapter = LojaAdapter(listLoja)
        shopRecyclerView.layoutManager = GridLayoutManager(activity, 1)
        shopRecyclerView.setHasFixedSize(false)

        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {


                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //adapter?.filter?.filter(p0)
                return false
            }

        })
    }

    }
