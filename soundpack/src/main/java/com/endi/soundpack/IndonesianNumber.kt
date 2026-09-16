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

    fun toAudioNames(amount: Long): List<String> {
        require(amount >= 0) {
            "Amount must not be negative"
        }

        if (amount == 0L) {
            return listOf(
                "nol",
                "rupiah"
            )
        }

        return convertToNames(amount) + "rupiah"
    }

    private fun convertToNames(number: Long): List<String> {
        return when {
            number < 10 -> {
                listOf(digitName(number))
            }

            number == 10L -> {
                listOf("sepuluh")
            }

            number == 11L -> {
                listOf("sebelas")
            }

            number < 20 -> {
                listOf(
                    digitName(number % 10),
                    "belas"
                )
            }

            number < 100 -> {
                val tens = number / 10
                val ones = number % 10

                buildList {
                    add(digitName(tens))
                    add("puluh")

                    if (ones > 0) {
                        add(digitName(ones))
                    }
                }
            }

            number == 100L -> {
                listOf("seratus")
            }

            number < 200 -> {
                buildList {
                    add("seratus")

                    val remainder = number - 100

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number < 1000 -> {
                val hundreds = number / 100
                val remainder = number % 100

                buildList {
                    add(digitName(hundreds))
                    add("ratus")

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number == 1000L -> {
                listOf("seribu")
            }

            number < 2000 -> {
                buildList {
                    add("seribu")

                    val remainder = number - 1000

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number < 1_000_000 -> {
                val thousands = number / 1000
                val remainder = number % 1000

                buildList {
                    addAll(convertToNames(thousands))
                    add("ribu")

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number < 1_000_000_000 -> {
                val millions = number / 1_000_000
                val remainder = number % 1_000_000

                buildList {
                    addAll(convertToNames(millions))
                    add("juta")

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number < 1_000_000_000_000L -> {
                val billions = number / 1_000_000_000
                val remainder = number % 1_000_000_000

                buildList {
                    addAll(convertToNames(billions))
                    add("miliar")

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
                    }
                }
            }

            number < 1_000_000_000_000_000L -> {
                val trillions = number / 1_000_000_000_000L
                val remainder = number % 1_000_000_000_000L

                buildList {
                    addAll(convertToNames(trillions))
                    add("triliun")

                    if (remainder > 0) {
                        addAll(convertToNames(remainder))
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

    private fun digitName(digit: Long): String {
        return when (digit) {
            0L -> "nol"
            1L -> "satu"
            2L -> "dua"
            3L -> "tiga"
            4L -> "empat"
            5L -> "lima"
            6L -> "enam"
            7L -> "tujuh"
            8L -> "delapan"
            9L -> "sembilan"
            else -> throw IllegalArgumentException(
                "Invalid digit: $digit"
            )
        }
    }
}