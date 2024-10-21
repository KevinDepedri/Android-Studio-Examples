/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.reply.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.ui.utils.ReplyContentType
import com.example.reply.ui.utils.ReplyNavigationType

/**
 * Main composable that serves as container
 * which displays content according to [replyUiState]
 */
@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    /**
     * Define a [viewModel] used to store UI-data for the current app/composable.
     * [viewModel] SURVIVE CONFIGURATION changes (like screen rotation, etc) and is generally used
     * to expose data and functions that composables can use.
     *
     * NOTICE how we use '= [viewModel()]' to
     * effectively create the object of type [ReplyViewModel] (which inherits from ViewModel).
     * */
    val viewModel: ReplyViewModel = viewModel()
    /**
     * The UiState is a snapshot of all the data needed by the composable.
     * It is defined inside the viewModel, and here it is referenced inside the main app code.
     * The [viewModel] is responsible for updating the UiState based on the events or data changes
     * that it observes.
     *
     * This composable relies on that UiState to display the correct content. If a changes in its
     * value happens, it TRIGGERS the RECOMPOSITION of the COMPOSABLE.
     * */
    val replyUiState = viewModel.uiState.collectAsState().value

    val navigationType: ReplyNavigationType
    val contentType: ReplyContentType

    // Define, according to screen size, which navigation and content will be used in the HomeScreen
    // composable below
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
            contentType = ReplyContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ReplyContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType =ReplyNavigationType.BOTTOM_NAVIGATION
            contentType = ReplyContentType.LIST_ONLY
        }
    }


    /**
     * Main app composable. It puts together all the smaller composable to build the Home screen.
     * Such composable requires:
     * -- navigation and content type define above (used to define the appearance of the app).
     * -- replayUiState, to observe it and trigger composable recomposition when something
     *    changes inside the viewModel
     * -- lambda functions that link viewModel methods to the composable items, allowing to call
     *    such functions according to the interaction with the app items.
     * */
    ReplyHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}
