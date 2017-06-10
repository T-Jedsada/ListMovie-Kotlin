package com.ponthaitay.listmovie.kotlin.movie

import com.ponthaitay.listmovie.kotlin.JsonMockUtility
import com.ponthaitay.listmovie.kotlin.RxSchedulersOverrideRule
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.powermock.api.mockito.PowerMockito.`when`
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MovieModelTest {

    @Rule @JvmField var rxSchedulerRule = RxSchedulersOverrideRule()

    @Mock var mockApi: MovieApi? = null

    private var mainViewModel: MainViewModel? = null
    private var movieModel: MovieModel? = null
    private var jsonUtil: JsonMockUtility? = null

    @Before
    fun setUp() {
        jsonUtil = JsonMockUtility()
        MockitoAnnotations.initMocks(this)

        movieModel = MovieModel()
        movieModel?.setApi(mockApi!!)

        mainViewModel = MainViewModel()
        mainViewModel?.setMovieModel(movieModel!!)
    }

    @Test
    fun requestMovieSuccess() {
        val mockResult = jsonUtil!!.getJsonToMock("movie_success.json", MovieDao::class.java)
        assertEquals(mockResult.result.size, 20)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        `when`(movieModel!!.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel!!.getListMovie("api_key", "sort_by")
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
        `when`(movieModel!!.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel?.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNotComplete()
        testObserver.assertError(throwable)
    }

    @Test
    fun requestMovieEmpty() {
        val mockResult = jsonUtil!!.getJsonToMock("movie_empty.json", MovieDao::class.java)
        assertEquals(mockResult.result.size, 0)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        `when`(movieModel!!.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel?.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertResult(mockResponse)
    }

    @Test
    fun requestMovieInvalidAPI() {
        val mockResult = jsonUtil!!.getJsonToMock("movie_invalid_api.json", MovieDao::class.java)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Observable.just(mockResponse)
        `when`(movieModel!!.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel?.getListMovie("api_key", "sort_by")
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertResult(mockResponse)
    }
}