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
import com.neilsayok.bluelabs.util.Platform
import com.neilsayok.bluelabs.util.getPlatform
import com.neilsayok.bluelabs.util.layoutType
import com.neilsayok.bluelabs.util.setPageTitle

@Composable
fun BlogScreen(component: BlogComponent) {



    setPageTitle(component.blog.title?.stringValue)

    LoaderScaffold {
        LazyColumn() {
            item {
                BlogCard(component.blog, component)
            }
        }


    }


}