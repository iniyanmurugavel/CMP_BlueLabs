package com.neilsayok.bluelabs.common.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoaderBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    isLoading: Boolean? = false,
    isError: Boolean? = false,
    content: @Composable BoxScope.() -> Unit
) {
    LaunchedEffect(isError) {
        if (isError == true) {
            //TODO Toast
            //showToast(context, SOMETHING_WENT_WRONG)
        }
    }




    Box(
        modifier = modifier,
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints
    ){

        if (isLoading == true) {
            CommonLoader(modifier = Modifier.fillMaxSize())
        }
        else{
            content()
        }
    }

}