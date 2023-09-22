package project.robby.userappnative.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import project.robby.userappnative.BuildConfig
import project.robby.userappnative.repository.AuthRepository
import project.robby.userappnative.repository.AuthRepositoryImpl
import project.robby.userappnative.repository.UserRepository
import project.robby.userappnative.repository.UserRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase =
        Firebase.database(BuildConfig.FIREBASE_URL)
}