package com.endi.soundpack

/**
 * @author Endi
 * @version 1.0
 * @since 9/10/2026
 */

object IndonesianNumber {

    fun toAudioResources(amount: Long): List<Int> {
        require(amount >= 0) {
            "Amount must not be negative"
        }

        if (amount == 0L) {
            return listOf(
                R.raw.nol,
                R.raw.rupiah
            )
        }

        return convert(amount) + R.raw.rupiah
    }

    private fun convert(number: Long): List<Int> {
        return when {
            number < 10 -> {
                listOf(digitResource(number))
            }

            number == 10L -> {
                listOf(R.raw.sepuluh)
            }

            number == 11L -> {
                listOf(R.raw.sebelas)
            }

            number < 20 -> {
                listOf(
                    digitResource(number % 10),
                    R.raw.belas
                )
            }

            number < 100 -> {
                val tens = number / 10
                val ones = number % 10

                buildList {
                    add(digitResource(tens))
                    add(R.raw.puluh)

                    if (ones > 0) {
                        add(digitResource(ones))
                    }
                }
            }

            number == 100L -> {
                listOf(R.raw.seratus)
            }

            number < 200 -> {
                buildList {
                    add(R.raw.seratus)

                    val remainder = number - 100

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number < 1000 -> {
                val hundreds = number / 100
                val remainder = number % 100

                buildList {
                    add(digitResource(hundreds))
                    add(R.raw.ratus)

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number == 1000L -> {
                listOf(R.raw.seribu)
            }

            number < 2000 -> {
                buildList {
                    add(R.raw.seribu)

                    val remainder = number - 1000

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number < 1_000_000 -> {
                val thousands = number / 1000
                val remainder = number % 1000

                buildList {
                    addAll(convert(thousands))
                    add(R.raw.ribu)

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number < 1_000_000_000 -> {
                val millions = number / 1_000_000
                val remainder = number % 1_000_000

                buildList {
                    addAll(convert(millions))
                    add(R.raw.juta)

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number < 1_000_000_000_000L -> {
                val billions = number / 1_000_000_000
                val remainder = number % 1_000_000_000

                buildList {
                    addAll(convert(billions))
                    add(R.raw.miliar)

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            number < 1_000_000_000_000_000L -> {
                val trillions = number / 1_000_000_000_000L
                val remainder = number % 1_000_000_000_000L

                buildList {
                    addAll(convert(trillions))
                    add(R.raw.triliun)

                    if (remainder > 0) {
                        addAll(convert(remainder))
                    }
                }
            }

            else -> {
                throw IllegalArgumentException(
                    "Amount is too large: $number"
                )
            }
        }
    }

    private fun digitResource(digit: Long): Int {
        return when (digit) {
            0L -> R.raw.nol
            1L -> R.raw.satu
            2L -> R.raw.dua
            3L -> R.raw.tiga
            4L -> R.raw.empat
            5L -> R.raw.lima
            6L -> R.raw.enam
            7L -> R.raw.tujuh
            8L -> R.raw.delapan
            9L -> R.raw.sembilan
            else -> throw IllegalArgumentException(
                "Invalid digit: $digit"
            )
        }
    }
}