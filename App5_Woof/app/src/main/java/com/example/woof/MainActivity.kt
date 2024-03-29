/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.woof

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofAppWithBar(modifier = Modifier.fillMaxHeight())
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WoofAppWithBar(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { WoofTopAppBar() }
    ) { WoofApp(modifier) }
}

@Composable
fun WoofApp(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier
        .background(color = MaterialTheme.colors.background)
    ) {
        items(dogs) {
            DogItem(dog = it)
        }
    }
}

@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_woof_logo),
            contentDescription = null
        )

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier
        .clip(RoundedCornerShape(25)) // Not strictly necessary, cards apply rounding by default since we defined in Theme.kt the shapes to use
        .padding(5.dp) ,// To avoid cards to touch each other
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
                )
            )
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = MaterialTheme.colors.surface) // Also color is no more necessary since we are inside a Card, which is a surface, and we defined in Theme.kt the colors for surfaces
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age, MaterialTheme.colors.onSurface)
                Spacer(modifier = Modifier.weight(1f)) // It is the only element with weight so it fills all the available space
                DogItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if (expanded) {
                DogHobby(dogHobby = dog.hobbies)
            }
        }
    }
}

@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        painter = painterResource(dogIcon),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, color: Color, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.h2,
            color = color, // Color redundant since DogInformation is used on a Cards which has colors defined in Themes.kt
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            color = color, // Color redundant since DogInformation is used on a Cards which has colors defined in Themes.kt
            style = MaterialTheme.typography.body1,
            modifier = modifier
        )
    }
}


@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = when(expanded){
            false -> Icons.Filled.ExpandMore
            true -> Icons.Filled.ExpandLess
            },
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.expand_button_content_description)
        )
    }
}

@Composable
private fun DogHobby(
    @StringRes dogHobby: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(dogHobby),
            style = MaterialTheme.typography.body1
        )

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WoofLightPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun WoofDarkPreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}