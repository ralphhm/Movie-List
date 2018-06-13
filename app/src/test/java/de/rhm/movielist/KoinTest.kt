import de.rhm.movielist.RepositoryModule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.BeforeClass
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.dryRun

class MyTest : AutoCloseKoinTest() {

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
        }
    }

    @Test
    fun dryRunTest() {
        // start Koin
        startKoin(listOf(RepositoryModule))
        // dry run of given module list
        dryRun()
    }
}