
import de.rhm.movielist.RepositoryModule
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.dryRun

class MyTest : AutoCloseKoinTest() {
    @Test
    fun dryRunTest(){
        // start Koin
        startKoin(listOf(RepositoryModule))
        // dry run of given module list
        dryRun()
    }
}