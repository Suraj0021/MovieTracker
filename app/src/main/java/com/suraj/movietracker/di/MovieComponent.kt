package com.suraj.movietracker.di

import com.suraj.movietracker.ui.FavouriteFragment
import com.suraj.movietracker.ui.MoviesDetailsActivity
import com.suraj.movietracker.ui.NowPlayingFragment
import com.suraj.movietracker.ui.PopulerFragment
import com.suraj.movietracker.ui.TopRatedFragment
import com.suraj.movietracker.ui.UpcomingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface MovieComponent {

    fun inject(fragment: NowPlayingFragment)
    fun inject(populerFragment: PopulerFragment)
    fun inject(topRatedFragment: TopRatedFragment)
    fun inject(upcomingFragment: UpcomingFragment)
    fun inject(favouriteFragment: FavouriteFragment)
    fun inject(moviesDetailsActivity: MoviesDetailsActivity)

}