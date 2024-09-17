import com.kabindra.architecture.domain.entity.Article
import kotlinx.serialization.Serializable

/**
 * enum values that represent the screens in the app
 */
enum class Screens(val title: String) {
    News(title = "News"),
    NewsDetails(title = "News Details")
}

sealed class Route {
    @Serializable
    data object NewsListRoute : Route()

    @Serializable
    data class NewsDetailRoute(
        val article: Article
    ) : Route()
}