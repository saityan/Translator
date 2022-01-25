package geekbrains.ru.translator.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SavedStateViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val data: LiveData<String> = savedStateHandle.getLiveData("searchWord")

    fun setSearchWord (searchWord: String) {
        savedStateHandle["searchWord"] = searchWord
    }
}
