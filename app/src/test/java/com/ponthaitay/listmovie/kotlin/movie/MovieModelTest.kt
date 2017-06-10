package com.ponthaitay.listmovie.kotlin.movie

import com.ponthaitay.listmovie.kotlin.JsonMockUtility
import com.ponthaitay.listmovie.kotlin.RxSchedulersOverrideRule
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
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

    private var mainViewModel: MainViewModel? = null
    private var movieModel: MovieModel? = null
    private var jsonUtil: JsonMockUtility? = null

    @Mock var mockApi: MovieApi? = null

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        jsonUtil = JsonMockUtility()
        MockitoAnnotations.initMocks(this)

        movieModel = MovieModel()
        movieModel?.setApi(mockApi!!)

        mainViewModel = MainViewModel()
        mainViewModel?.setMovieModel(movieModel!!)
    }

    @After
    fun tearDownClass() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun requestMovie() {
        val mockResult = jsonUtil?.getJsonToMock("movie_success.json", MovieDao::class.java)
        Assert.assertEquals(mockResult?.result?.size, 20)
        val mockResponse = Response.success(mockResult)
        val mockObservable: Observable<Response<MovieDao>> = Observable.just(mockResponse)
        `when`(movieModel?.observableMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel?.getListMovie("api_key", "sort_by")
    }

    @Test
    fun requestMovie1() {

    }

    @Test
    fun nextPageAvailable() {

    }
}