package com.izelhatipoglu.videoappfirebase.base

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.izelhatipoglu.videoappfirebase.home.viewModel.*
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.viewModel.FirstIntroViewModel
import com.izelhatipoglu.videoappfirebase.landing.intro.screens.viewModel.SecondIntroViewModel
import com.izelhatipoglu.videoappfirebase.landing.intro.viewModel.IntroViewModel
import com.izelhatipoglu.videoappfirebase.landing.login.viewModel.LoginViewModel
import com.izelhatipoglu.videoappfirebase.landing.register.viewModel.RegisterViewModel
import com.izelhatipoglu.videoappfirebase.splash.viewModel.SplashViewModel
import com.izelhatipoglu.videoappfirebase.videoPlayer.viewModel.VideoPlayerViewModel

class ViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)-> VideoPlayerViewModel(application = Application()) as T
            modelClass.isAssignableFrom(SettingViewModel::class.java)-> SettingViewModel(application = Application()) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->HomeViewModel(application = Application()) as T
            modelClass.isAssignableFrom(SecondIntroViewModel::class.java) ->SecondIntroViewModel(application = Application()) as T
            modelClass.isAssignableFrom(FirstIntroViewModel::class.java) -> FirstIntroViewModel(application = Application()) as T
            modelClass.isAssignableFrom(IntroViewModel::class.java) -> IntroViewModel(application = Application()) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(application = Application()) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(application = Application()) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(application = Application()) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}