import androidx.compose.runtime.Composable
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import presentation.ui.screen.NewsScreen
import presentation.ui.theme.AppTheme

@Composable
@Preview
fun app() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        AppTheme {
            NewsScreen()
        }
    }
}