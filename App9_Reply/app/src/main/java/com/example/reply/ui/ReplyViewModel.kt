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

import androidx.lifecycle.ViewModel
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.data.local.LocalEmailsDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * [ViewModel] for Reply app.
 * It manages the data and logic of the app.
 * For example, it can be accountable to retrieve data from the network,
 * store user data and perform computations over the available data.
 *
 * It needs to define a UiState that will be read by the app composable to
 * understand what needs to be visualized.
 */
class ReplyViewModel : ViewModel() {

     /**
     * A [MutableStateFlow] is kept as private variable to describe the current state of the model.
     * To allow such state to be fetched by the composable, a non-mutable [StateFlow] is defined.
     * This takes the value of the private MutableStateFlow, and encapsulate it into a public
     * immutable one.
      *
     * The main difference between [MutableStateFlow] and [StateFlow] is that the first one has a 'value'
     * variable that can be updated, while the second one has the same variable but it can be only
     * read.
      *
     * When writing a new value on a [MutableStateFlow], it is advisable to always do it using its
     * [MutableStateFlow.update] method. In this way the change of value happens atomically, and RACE CONDITIONS are
     * avoided
     * */
    private val _uiState = MutableStateFlow(ReplyUiState())
    val uiState: StateFlow<ReplyUiState> = _uiState

    // Set up the initial UiState when this viewModel is instantiated.
    init {
        initializeUIState()
    }

    /**
     * Initializing mailbox emails by getting them from [LocalEmailsDataProvider]
     */
    private fun initializeUIState() {
        /**
         * Define a mapping (python dictionary) between the different possible MailboxType (that are
         * just a set of values defined inside an enum class) and a list of emails.
         *
         * Initialize that mapping right now by getting all the emails from [LocalEmailsDataProvider]
         * (which just emulates a static DB) and by ordering them according to their mailbox.
         * */
        var mailboxes: Map<MailboxType, List<Email>> =
            LocalEmailsDataProvider.allEmails.groupBy { it.mailbox }

        // After that, initialize the internal uiState value by providing the [Map] built above and
        // an initially selected email. We can safely assign a value to ReplyUiState (without using
        // its .update method) because this assignment is done at start-up, when no other co-routines
        // are trying to act on the same StateFlow. Later it is fundamental to always use .update()
        _uiState.value =
            ReplyUiState(
                mailboxes = mailboxes,
                currentSelectedEmail = mailboxes[MailboxType.Inbox]?.get(0)
                    ?: LocalEmailsDataProvider.defaultEmail
            )
    }


    /**
     * Update [currentMailbox]. This method is triggered directly by the user pressing on any
     * of the TABS of the main composable
     */
    fun updateCurrentMailbox(mailboxType: MailboxType) {
        _uiState.update {
            it.copy(
                currentMailbox = mailboxType
            )
        }
    }

    /**
     * Reset [currentSelectedEmail] state to first email and [isShowingHomepage] to true.
     * Used right after the method above, when the user asks to show a new TAB.
     * With [isShowingHomepage] we do NOT refer to a specific TAB, but just to showing the general
     * interface and not a specific email interface (with all the email details)
     */
    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                currentSelectedEmail = it.mailboxes[it.currentMailbox]?.get(0)
                    ?: LocalEmailsDataProvider.defaultEmail,
                isShowingHomepage = true
            )
        }
    }

    /**
     * Update [currentSelectedEmail] state and [isShowingHomepage] to false. Used when the user
     * taps on an email to see all the email info
     */
    fun updateDetailsScreenStates(email: Email) {
        // Update the StateFlow atomically, this is necessary since this method could be called
        // from multiple parts of the app, leading to possible race conditions. The same is true
        // for all the ViewModel's methods below
        _uiState.update {
            it.copy(
                currentSelectedEmail = email,
                isShowingHomepage = false
            )
        }
    }
}
