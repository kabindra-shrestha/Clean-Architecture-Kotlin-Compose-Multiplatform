import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import presentation.ui.screen.NewsScreen
import presentation.ui.theme.AppTheme

@Composable
@Preview
fun app() {
    KoinContext {
        AppTheme {
            NewsScreen()
        }
    }
}