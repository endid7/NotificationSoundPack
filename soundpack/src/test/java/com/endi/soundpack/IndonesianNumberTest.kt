package com.endi.soundpack

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

class IndonesianNumberTest {

    @Test
    fun zero() {
        assertEquals(
            listOf(
                R.raw.nol,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(0)
        )
    }

    @Test
    fun singleDigit() {
        assertEquals(
            listOf(
                R.raw.lima,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(5)
        )
    }

    @Test
    fun ten() {
        assertEquals(
            listOf(
                R.raw.sepuluh,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(10)
        )
    }

    @Test
    fun eleven() {
        assertEquals(
            listOf(
                R.raw.sebelas,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(11)
        )
    }

    @Test
    fun twelve() {
        assertEquals(
            listOf(
                R.raw.dua,
                R.raw.belas,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(12)
        )
    }

    @Test
    fun twentyFive() {
        assertEquals(
            listOf(
                R.raw.dua,
                R.raw.puluh,
                R.raw.lima,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(25)
        )
    }

    @Test
    fun oneHundred() {
        assertEquals(
            listOf(
                R.raw.seratus,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(100)
        )
    }

    @Test
    fun oneHundredTwentyFive() {
        assertEquals(
            listOf(
                R.raw.seratus,
                R.raw.dua,
                R.raw.puluh,
                R.raw.lima,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(125)
        )
    }

    @Test
    fun oneThousand() {
        assertEquals(
            listOf(
                R.raw.seribu,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(1_000)
        )
    }

    @Test
    fun twelveThousandFiveHundred() {
        assertEquals(
            listOf(
                R.raw.dua,
                R.raw.belas,
                R.raw.ribu,
                R.raw.lima,
                R.raw.ratus,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(12_500)
        )
    }

    @Test
    fun oneMillion() {
        assertEquals(
            listOf(
                R.raw.satu,
                R.raw.juta,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(1_000_000)
        )
    }

    @Test
    fun oneBillion() {
        assertEquals(
            listOf(
                R.raw.satu,
                R.raw.miliar,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(1_000_000_000)
        )
    }

    @Test
    fun oneTrillion() {
        assertEquals(
            listOf(
                R.raw.satu,
                R.raw.triliun,
                R.raw.rupiah
            ),
            IndonesianNumber.toAudioResources(1_000_000_000_000)
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun negativeAmountIsRejected() {
        IndonesianNumber.toAudioResources(-1)
    }
}