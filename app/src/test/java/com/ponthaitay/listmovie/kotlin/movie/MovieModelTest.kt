package com.ponthaitay.listmovie.kotlin.movie

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.ponthaitay.listmovie.kotlin.JsonMockUtility
import com.ponthaitay.listmovie.kotlin.RxSchedulersOverrideRule
import com.ponthaitay.listmovie.kotlin.api.MovieApi
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyInt
import org.mockito.Matchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.only
import org.mockito.MockitoAnnotations
import retrofit2.Response


//@RunWith(PowerMockRunner::class)
//@PrepareForTest(Log::class)
//@PowerMockIgnore("javax.net.ssl.*")
class MovieModelTest {

    @Rule @JvmField
    public val schedulers = RxSchedulersOverrideRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieModel: MovieModel
    private lateinit var jsonUtil: JsonMockUtility

    @Before
    fun setUp() {
        jsonUtil = JsonMockUtility()
        MockitoAnnotations.initMocks(this)

        var mockApi = mock<MovieApi> {
            only()
        }

        movieModel = MovieModel()
        movieModel.setApi(mockApi)

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
        val mockObservable: Observable<Response<MovieDao>> = Observable.just(mockResponse)
        movieModel.setApi(mock<MovieApi> {
        })
        `when`(movieModel.requestMovie(anyString(), anyString(), anyInt())).thenReturn(mockObservable)
        mainViewModel.getListMovie("api_key", "sort_by")


        mockObservable.subscribe(Consumer { t -> }, Consumer { t -> })

//        val testSub = TestSubscriber<Response<MovieDao>>()
//        mockObservable.subscribeWith(testSub)
//        testSub.assertNoErrors()
//        testSub.assertValueCount(1)
//        testSub.assertCompleted(
//
//        val testObserver = mockObservable.test()
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