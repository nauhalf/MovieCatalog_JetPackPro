package com.dicoding.naufal.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.jetpack.nauhalf.academy.utils.vo.Resource
import com.dicoding.naufal.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.naufal.moviecatalogue.data.source.remote.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Suppress("UNCHECKED_CAST")
abstract class NetworkBoundResource<ResultType, RequestType> {
    private val result = MutableLiveData<Resource<ResultType>>()
    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {

        withContext(Dispatchers.Main) {
            result.value = Resource.loading(null)
        }

        val dbSource = loadFromDB()

        if (shouldFetch(dbSource)) {
            try {
                fetchFromNetwork(dbSource as ResultType)
            } catch (e: Exception) {
                setValue(Resource.error(e.message as String, null))
            }
        } else {
            setValue(Resource.success(dbSource))
        }

        return this

    }


    protected fun onFetchFailed() {}

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) result.postValue(newValue)
    }

    protected abstract suspend fun loadFromDB(): ResultType?

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): ApiResponse<RequestType>?

    protected abstract suspend fun saveCallResult(data: RequestType)

    private suspend fun fetchFromNetwork(dbSource: ResultType) {

        result.postValue(Resource.loading(null))

        val response = createCall()

        when (response?.status) {
            StatusResponse.SUCCESS -> {
                saveCallResult(response.body as RequestType)

                setValue(Resource.success(loadFromDB()))
            }


            StatusResponse.EMPTY -> {
                setValue(Resource.success(loadFromDB()))
            }

            StatusResponse.ERROR -> {
                onFetchFailed()
                setValue(Resource.error(response.message as String, dbSource))
            }
        }
    }


    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}