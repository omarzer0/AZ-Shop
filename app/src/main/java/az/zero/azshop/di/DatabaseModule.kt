package az.zero.azshop.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import az.zero.azshop.R
import az.zero.azshop.dp.ProductDatabase
import az.zero.azshop.utils.DATABASE_NAME
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(
        app: Application
    ) = Room.databaseBuilder(app, ProductDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideTaskDao(db: ProductDatabase) = db.productDao()


}