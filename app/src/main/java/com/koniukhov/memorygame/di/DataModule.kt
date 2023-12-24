package com.koniukhov.memorygame.di

import android.content.Context
import com.koniukhov.memorygame.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideIconsIds(@ApplicationContext context: Context): List<Int>{
        val res = ArrayList<Int>()
        val icons = context.resources.obtainTypedArray(R.array.icons)
        for (i in 0 until icons.length()){
            res.add(icons.getResourceId(i, -1))
        }
        icons.recycle()
        return res
    }
}