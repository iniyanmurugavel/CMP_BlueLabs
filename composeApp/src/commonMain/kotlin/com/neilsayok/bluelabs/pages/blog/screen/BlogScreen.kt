package com.neilsayok.bluelabs.pages.blog.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.neilsayok.bluelabs.common.ui.components.LoaderScaffold
import com.neilsayok.bluelabs.pages.blog.component.BlogComponent
import com.neilsayok.bluelabs.pages.blog.widgets.BlogCard

@Composable
fun BlogScreen(component: BlogComponent) {

    LoaderScaffold {

        LazyColumn {
            item {
                BlogCard(component.blog)
            }
        }



    }


}