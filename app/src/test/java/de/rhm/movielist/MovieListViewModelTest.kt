package de.rhm.movielist

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.BeforeClass
import org.junit.Test

class MovieListViewModelTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    @Test
    fun secondSubscription_emitsCachedState() {
        val repository = mock<MovieListRepository> {
            on { getMovies(any()) } doReturn (Single.error(Exception()))
        }
        val observer = TestObserver<MovieListUiState>()
        val viewModel = MovieListViewModel(repository)
        viewModel.uiState.subscribe()
        viewModel.uiState.subscribe(observer)
        observer.assertValue { it is Failure }
    }

}