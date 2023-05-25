package app.k9mail.ui.catalog.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.k9mail.core.ui.compose.common.DevicePreviews
import app.k9mail.core.ui.compose.common.mvi.observe
import app.k9mail.ui.catalog.ui.CatalogContract.Event.OnThemeChanged
import app.k9mail.ui.catalog.ui.CatalogContract.Event.OnThemeVariantChanged
import app.k9mail.ui.catalog.ui.CatalogContract.ViewModel
import app.k9mail.ui.catalog.ui.common.theme.ThemeSwitch
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = koinViewModel<CatalogViewModel>(),
) {
    val (state, dispatch) = viewModel.observe(handleEffect = {})

    ThemeSwitch(
        theme = state.value.theme,
        themeVariant = state.value.themeVariant,
    ) {
        val contentPadding = WindowInsets.systemBars.asPaddingValues()

        val pages = persistentListOf(
            "Typography",
            "Colors",
            "Buttons",
            "Selection controls",
            "Text fields",
            "Icons",
            "Images",
            "Molecules",
        )

        CatalogContent(
            state = state.value,
            pages = pages,
            onThemeChanged = { dispatch(OnThemeChanged) },
            onThemeVariantChanged = { dispatch(OnThemeVariantChanged) },
            contentPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize()
                .then(modifier),
        )
    }
}

@DevicePreviews
@Composable
internal fun CatalogScreenPreview() {
    CatalogScreen()
}
