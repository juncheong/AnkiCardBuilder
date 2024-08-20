import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.singleWindowApplication
import module.appModule
import org.koin.compose.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import ui.MainView


fun main() = singleWindowApplication(
    title = "AnkiCardBuilder"
) {
//    startKoin {
//        modules(appModule)
//    }


    KoinApplication(application = {
        modules(appModule)
    } ) {
        MainView()
    }
}
