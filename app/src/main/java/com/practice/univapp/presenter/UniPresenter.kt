package com.practice.univapp.presenter

import com.practice.univapp.contracts.UniversityContracts
import com.practice.univapp.network.data.UniversityItem
import com.practice.univapp.utils.launchOnMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class UniPresenter (private val view : UniversityContracts.View,
    private val model: UniversityContracts.Model) :
    UniversityContracts.Presenter, UniversityContracts.Model.OnFinishListener {

    private val scope = CoroutineScope(Dispatchers.IO)
    override fun getUniversity(country: String) {
       scope.launch {
         model.fetchUniversity(onFinishListener = this@UniPresenter, country = country)
       }
    }

    override fun onDestroy() {
      scope.cancel()
    }

    override fun onLoading() {
      scope.launchOnMain {
        view.onLoading()
      }
    }

    override fun onError(message: String) {
      scope.launchOnMain {
        view.onError(message = message)
      }
    }

    override fun onSuccess(list: List<UniversityItem>) {
      scope.launchOnMain {
        view.onSuccess(list)
      }
    }
}