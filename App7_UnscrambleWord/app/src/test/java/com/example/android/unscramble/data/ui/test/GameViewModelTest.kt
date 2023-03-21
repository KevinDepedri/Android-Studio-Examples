package com.example.android.unscramble.data.ui.test

import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.allWords
import com.example.android.unscramble.data.getUnscrambledWord
import com.example.android.unscramble.ui.GameUiState
import com.example.android.unscramble.ui.GameViewModel
import junit.framework.TestCase.*
import org.jetbrains.annotations.TestOnly
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel: GameViewModel = GameViewModel()

    // SUCCESS PATHS
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val scrambledWord = currentGameUiState.currentScrambledWord
        val originalWord = getUnscrambledWord(scrambledWord)
        viewModel.updateUserGuess(originalWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }


    @Test
    fun gameViewModel_AllCorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState: GameUiState
        var scrambledWord = ""
        var originalWord = ""

        for (wordIndex in allWords.indices){
            currentGameUiState = viewModel.uiState.value
            scrambledWord = currentGameUiState.currentScrambledWord
            originalWord = getUnscrambledWord(scrambledWord)
            viewModel.updateUserGuess(originalWord)
            viewModel.checkUserGuess()

            currentGameUiState = viewModel.uiState.value
            assertFalse(currentGameUiState.isGuessedWordWrong)
            val expectedScore = SCORE_AFTER_FIRST_CORRECT_ANSWER * (wordIndex + 1)
            assertEquals(expectedScore, currentGameUiState.score)
            println("Scrambled word N${wordIndex+1}: $scrambledWord - Solution: $originalWord - Score: $expectedScore ")
        }
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    // ERROR PATHS
    @Test
    fun gameViewModel_WrongWordGuessed_ScoreNotUpdatedAndErrorFlagSet() {
        var currentGameUiState = viewModel.uiState.value
        val incorrectWord = "askdl"
        viewModel.updateUserGuess(incorrectWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertEquals(0, currentGameUiState.score)
    }

    // BOUNDARY PATHS
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        // Assert that current word is scrambled.
        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        // Assert that current word count is set to 1.
        assertTrue(gameUiState.currentWordCount == 1)
        // Assert that initially the score is 0.
        assertTrue(gameUiState.score == 0)
        // Assert that the wrong word guessed is false.
        assertFalse(gameUiState.isGuessedWordWrong)
        // Assert that game is not over.
        assertFalse(gameUiState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        repeat(MAX_NO_OF_WORDS) {
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
            // Assert that after each correct answer, score is updated correctly.
            assertEquals(expectedScore, currentGameUiState.score)
        }
        // Assert that after all questions are answered, the current word count is up-to-date.
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        // Assert that after 10 questions are answered, the game is over.
        assertTrue(currentGameUiState.isGameOver)
    }
}