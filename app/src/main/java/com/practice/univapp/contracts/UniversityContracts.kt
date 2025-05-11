package com.practice.univapp.contracts

import com.practice.univapp.network.data.UniversityItem

interface UniversityContracts {

   interface View {
     fun onLoading()
     fun onSuccess(list: List<UniversityItem>)
     fun onError(message:String)
   }

   interface Presenter {
     fun getUniversity(country:String)
     fun onDestroy()
   }

   interface Model {
     interface OnFinishListener {
       fun onLoading()
       fun onError(message: String)
       fun onSuccess(list: List<UniversityItem>)
     }
     suspend fun fetchUniversity(onFinishListener: OnFinishListener, country: String)
   }
}