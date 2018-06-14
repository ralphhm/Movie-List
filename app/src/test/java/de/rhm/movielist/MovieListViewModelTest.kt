package de.rhm.movielist

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import de.rhm.movielist.api.model.MovieListResult
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.util.*

class MovieListViewModelTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    val observer = TestObserver<MovieListUiState>()

    @Test
    fun secondSubscription_emitsCachedState() {
        val repository = mock<MovieListRepository> {
            on { getMovies(any(), any()) } doReturn (Single.error(Exception()))
        }
        val observer = TestObserver<MovieListUiState>()
        val viewModel = MovieListViewModel(repository)
        viewModel.uiState.subscribe()
        viewModel.uiState.subscribe(observer)
        observer.assertValue { it is Failure }
    }

    @Test
    fun secondPageLoad_emitsResultWithAggregatedList() {
        val repository = mock<MovieListRepository> {
            on { getMovies(any(), any()) } doReturn (Single.just(listOf(MovieListResult("id", "title", Date(), "", null))))
        }
        val viewModel = MovieListViewModel(repository)
        viewModel.uiState.subscribe(observer)
        observer.values().last()
                .assertIs<Result>()
                .run {
                    assertEquals(1, movies.size)
                    //trigger load more
                    loadMoreAction.invoke()
                }
        observer.values().last()
                .assertIs<Result>()
                .run {
                    assertEquals(2, movies.size)
                }
    }

    @Test
    fun changeFilterDate_emitsLoadingState() {
        val secondDate = Date()
        val repository = mock<MovieListRepository> {
            //simulate never returning network call
            on { getMovies(any(), any()) } doReturn (Single.just(listOf(MovieListResult("id", "title", Date(), "", null))))
        }
        MovieListViewModel(repository).uiState.subscribe(observer)
        whenever(repository.getMovies(any(), any())).thenReturn(Single.never())
        observer.values().last()
                .assertIs<Result>()
                .apply {
                    assertEquals(1, movies.size)
                }
                .run {
                    //trigger change filter date
                    filterAction.invoke(secondDate)
                }
        observer.values().last()
                .assertIs<Loading>()
                .check {
                    assertEquals(secondDate, filterDate)
                }
    }

}

inline fun <reified T> Any.assertIs(): T {
    assert(this is T)
    return this as T
}

inline fun <reified T> T.check(block: T.()-> Unit): T {
    block.invoke(this)
    return this
}