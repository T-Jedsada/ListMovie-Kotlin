package com.ponthaitay.listmovie.kotlin.movie

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.ponthaitay.listmovie.kotlin.JsonMockUtility
import com.ponthaitay.listmovie.kotlin.RxSchedulersOverrideRule
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieModelTest {

    @Rule @JvmField var rxSchedulerRule = RxSchedulersOverrideRule()

    private var mainViewModel = MainViewModel()
    private var movieModel = MovieModel()
    private var jsonUtil = JsonMockUtility()

    var mockApi: MovieApi = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        movieModel.setMockApi(mockApi)
        mainViewModel.setMovieModel(movieModel)
    }

    @Test
    fun requestMovieSuccess() {
        val mockResult = jsonUtil.getJsonToMock("movie_success.json", MovieDao::class.java)
        assertEquals(mockResult.result.size, 20)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        whenever(movieModel.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertResult(mockResponse)
    }

    @Test
    fun requestMovieError() {
        val throwable = Throwable("error")
        val mockObservable = Observable.error<Response<MovieDao>>(throwable)
        whenever(movieModel.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNotComplete()
        testObserver.assertError(throwable)
    }

    @Test
    fun requestMovieEmpty() {
        val mockResult = jsonUtil.getJsonToMock("movie_empty.json", MovieDao::class.java)
        assertEquals(mockResult.result.size, 0)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        whenever(movieModel.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertResult(mockResponse)
    }

    @Test
    fun requestMovieInvalidAPI() {
        val mockResult = jsonUtil.getJsonToMock("movie_invalid_api.json", MovieDao::class.java)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        whenever(movieModel.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertResult(mockResponse)
    }
}