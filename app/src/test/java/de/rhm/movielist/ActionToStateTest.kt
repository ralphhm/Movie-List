package de.rhm.movielist

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.BeforeClass
import org.junit.Test

class ActionToStateTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    val timetableRepository = mock<MovieListRepository>()
    val retryAction = mock<() -> Unit>()
    val observer = TestObserver.create<MovieListUiState>()
    val action: Subject<FetchMovieListAction> = PublishSubject.create()

    init {
        ActionToState(timetableRepository, retryAction).apply(action).subscribe(observer)
    }

    @Test
    fun emitsLoadingState() {
        whenever(timetableRepository.getMovies()).thenReturn(Single.never())
        action.onNext(FetchMovieListAction)
        observer.assertValue(Loading)
    }

    @Test
    fun emitsFailureState_afterError() {
        val exception = Exception()
        whenever(timetableRepository.getMovies()).thenReturn(Single.error(exception))
        action.onNext(FetchMovieListAction)
        observer.values().run {
            assert(first() === Loading)
            assert(get(1) is Failure)
        }
    }

    @Test
    fun emitsResultState_afterSuccess() {
        whenever(timetableRepository.getMovies()).thenReturn(Single.just(emptyList()))
        action.onNext(FetchMovieListAction)
        observer.values().run {
            assert(first() === Loading)
            assert(get(1) is Result)
        }
    }
}