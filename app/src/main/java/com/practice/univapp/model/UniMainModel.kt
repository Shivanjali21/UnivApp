package com.practice.univapp.model

import com.practice.univapp.contracts.UniversityContracts
import com.practice.univapp.network.api.UniversityService

class UniMainModel(private val universityService: UniversityService) : UniversityContracts.Model {

    override suspend fun fetchUniversity(
        onFinishListener: UniversityContracts.Model.OnFinishListener,
        country: String
    ) {

        onFinishListener.onLoading()
        try {
            val uniResponse = universityService.getUniversity(country = country)
            if (uniResponse.isSuccessful) {
                uniResponse.body().let {
                    onFinishListener.onSuccess(it!!)
                }
            } else {
                onFinishListener.onError(uniResponse.errorBody().toString())
            }
        } catch (e: Exception) {
            onFinishListener.onError(message = e.message.toString())
        }
    }

}