package com.news.it.net

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceBuilder {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun getRSSService(): RssService {
        val dataClient = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()

        val xmlModule = JacksonXmlModule()
        xmlModule.setDefaultUseWrapper(false)

        val xmlMapper = XmlMapper(xmlModule)
        xmlMapper.apply {
            configure(MapperFeature.AUTO_DETECT_GETTERS, true)
            configure(MapperFeature.AUTO_DETECT_SETTERS, true)
            configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
            configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
            enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
        }


        return Retrofit.Builder().apply {
            baseUrl("https://vc.ru/rss/")
            client(dataClient)
            addConverterFactory(JacksonConverterFactory.create(xmlMapper))
        }.build().create(RssService::class.java)
    }
}