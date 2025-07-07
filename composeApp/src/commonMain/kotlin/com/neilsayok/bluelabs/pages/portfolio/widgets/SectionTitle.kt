package com.neilsayok.bluelabs.pages.portfolio.widgets

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SectionTitle(title : String){

    Text(title, style = MaterialTheme.typography.headlineSmall)
}