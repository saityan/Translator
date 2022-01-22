package geekbrains.ru.translator.view.base

import androidx.appcompat.app.AppCompatActivity
import geekbrains.ru.translator.model.data.AppState
import geekbrains.ru.translator.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)
}
