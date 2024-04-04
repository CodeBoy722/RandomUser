package com.codeboy.randomuserandroid.di
import com.codeboy.randomuserandroid.data.RandomUserApi
import com.codeboy.randomuserandroid.domain.repository.RandomUserRepoDataStateSource
import com.codeboy.randomuserandroid.domain.useCases.UseCaseRandomUsers
import com.codeboy.randomuserandroid.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private var jsonSerializer: JsonSerializer<Date?> =
        JsonSerializer<Date?> { src, typeOfSrc, context ->
            if (src == null) {
                null
            } else {
                JsonPrimitive(
                    dateFormat.format(src)
                )
            }
        }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    private var jsonDeserializer: JsonDeserializer<Date?> =
        JsonDeserializer<Date?> { json, typeOfT, context ->
            if (json == null) {
                null
            } else {
                try {
                    json.asString?.let { dateFormat.parse(it) }
                } catch (e: ParseException) {
                    throw JsonParseException(e)
                }
            }
        }

    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                Date::class.java,
                jsonSerializer
            )
            .registerTypeAdapter(
                Date::class.java,
                jsonDeserializer
            )
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonBuilder: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.RANDOM_USER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(buildOkHttpClient())
            .build()
    }


    private val timeoutSeconds = 120L
    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomUserApi(retrofit: Retrofit): RandomUserApi {
        return retrofit.create(RandomUserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRandomUserRepo( api: RandomUserApi):  RandomUserRepoDataStateSource {
        return RandomUserRepoDataStateSource(api)
    }

    @Provides
    @Singleton
    fun provideRandomUsersUseCase(repo: RandomUserRepoDataStateSource): UseCaseRandomUsers {
        return UseCaseRandomUsers(repo)
    }

}