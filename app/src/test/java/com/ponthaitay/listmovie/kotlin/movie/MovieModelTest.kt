package com.ponthaitay.listmovie.kotlin.movie

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import com.ponthaitay.listmovie.kotlin.JsonMockUtility
import com.ponthaitay.listmovie.kotlin.RxSchedulersOverrideRule
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MovieModelTest {

    @Rule @JvmField
    val schedulers = RxSchedulersOverrideRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieModel: MovieModel
    private lateinit var jsonUtil: JsonMockUtility

    var apiMock:MovieApi = mock()

    @Before
    fun setUp() {
        jsonUtil = JsonMockUtility()
        MockitoAnnotations.initMocks(this)

        movieModel = MovieModel()
        movieModel.setApi(apiMock)

        mainViewModel = MainViewModel()
        mainViewModel.setMovieModel(movieModel)
        val spyMainModel = spy(mainViewModel)
        spyMainModel.setMovieModel(movieModel)
    }

    @Test
    fun requestMovie() {
        val mockResult = jsonUtil.getJsonToMock("movie_success.json", MovieDao::class.java)
        Assert.assertEquals(mockResult.result.size, 20)
        val mockResponse = Response.success(mockResult)
        val mockObservable: Observable<Response<MovieDao>> = Observable.just(mockResponse!!)
//        movieModel.setApi(apiMock)
        whenever(movieModel.requestMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")

        val testSub = TestObserver<Response<MovieDao>>()
        mockObservable.subscribeWith(testSub)
        testSub.assertNoErrors()
////        testObserver.assertNoErrors()
//        testObserver.onSuccess(mockResponse)
//        testObserver.awaitTerminalEvent()
    }

    @Test
    fun requestMovie1() {
    }

    @Test
    fun nextPageAvailable() {
    }
}