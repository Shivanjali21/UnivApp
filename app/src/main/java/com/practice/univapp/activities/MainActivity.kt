package com.practice.univapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.practice.univapp.R
import com.practice.univapp.adapter.UniAdapter
import com.practice.univapp.connectivity.NetworkManager
import com.practice.univapp.contracts.UniversityContracts
import com.practice.univapp.databinding.ActivityMainBinding
import com.practice.univapp.model.UniMainModel
import com.practice.univapp.network.api.UniversityService
import com.practice.univapp.network.data.UniversityItem
import com.practice.univapp.presenter.UniPresenter
import com.practice.univapp.utils.showCustomSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UniversityContracts.View {

    private lateinit var uniPresenter: UniPresenter

    @Inject
    lateinit var uniService: UniversityService
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
    private val uniAdapter = UniAdapter()
    private var wasConnected: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uniPresenter = UniPresenter(this, UniMainModel(uniService))
        initView()
        checkInternet()
    }

    private fun initView() {
        binding.rvUniversity.adapter = uniAdapter
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                  uniAdapter.asyncDiffList.submitList(emptyList())
                } else {
                  uniPresenter.getUniversity(newText)
                }
                return true
            }
        })
    }

    private fun checkInternet() {
        val networkManager = NetworkManager(this)
        networkManager.observe(this) { isConnected ->
            // If wasConnected is null, set it for the first time
            if (wasConnected == null) {
                wasConnected = isConnected
            }

            if (isConnected) {
                if (wasConnected == false) {
                    // Show snackbar only if internet was previously disconnected
                    this.showCustomSnackBar(getString(R.string.internet_back), findViewById(android.R.id.content))
                }

                binding.apply {
                    searchView.visibility = View.VISIBLE
                    rvUniversity.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    ivNoInternet.visibility = View.GONE
                    mtvNoInternet.visibility = View.GONE
                }

                val query = binding.searchView.query.toString()
                if (query.isNotBlank()) {
                    uniPresenter.getUniversity(query)
                }
            } else {
                binding.apply {
                    searchView.visibility = View.GONE
                    rvUniversity.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    ivNoInternet.visibility = View.VISIBLE
                    mtvNoInternet.visibility = View.VISIBLE
                }
            }
            wasConnected = isConnected
        }
    }

    override fun onLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(list: List<UniversityItem>) {
        binding.progressBar.visibility = View.GONE
        uniAdapter.asyncDiffList.submitList(list)
    }

    override fun onError(message: String) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        uniPresenter.onDestroy()
        super.onDestroy()
    }
}