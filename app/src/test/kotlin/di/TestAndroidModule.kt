package di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import me.androidbox.busbymovies.utils.MovieSchedulers
import javax.inject.Singleton

/**
 * Created by steve on 10/23/17.
 */
@Module
class TestAndroidModule {
    @Singleton
    @Provides
    fun providesAndroidSchedulers(): MovieSchedulers {
        return object: MovieSchedulers {
            override fun getBackgroundScheduler(): Scheduler {
                return Schedulers.trampoline()
            }

            override fun getUIScheduler(): Scheduler {
                return Schedulers.trampoline()
            }
        }
    }
}