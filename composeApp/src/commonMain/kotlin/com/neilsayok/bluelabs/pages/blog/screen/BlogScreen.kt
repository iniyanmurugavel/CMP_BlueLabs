package com.neilsayok.bluelabs.pages.blog.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.pages.blog.component.BlogComponent
import com.neilsayok.bluelabs.pages.blog.widgets.BlogCard
import com.neilsayok.bluelabs.util.layoutType

@Composable
fun BlogScreen(component: BlogComponent) {


    val horizontalPadding = when (layoutType) {
        NavigationSuiteType.NavigationBar -> 0.dp
        NavigationSuiteType.NavigationRail, NavigationSuiteType.NavigationDrawer -> 128.dp

        else -> 0.dp
    }

    LoaderScaffold {

        LazyColumn(modifier = Modifier.padding(horizontal = horizontalPadding)) {
            item {
                BlogCard(component.blog, component)
            }
        }


    }


}