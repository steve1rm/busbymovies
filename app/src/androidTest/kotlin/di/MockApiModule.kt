package di

import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.network.MovieAPIService
import javax.inject.Singleton
import org.mockito.Mockito

/**
 * Created by steve on 12/2/17.
 */
@Module
class MockApiModule {
    @Singleton
    @Provides
    fun provideMovieService(): MovieAPIService {
        return Mockito.mock(MovieAPIService::class.java)
    }
}