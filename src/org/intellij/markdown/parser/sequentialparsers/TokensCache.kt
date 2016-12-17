package org.intellij.markdown.parser.sequentialparsers

import org.intellij.markdown.IElementType

abstract class TokensCache {
    abstract val cachedTokens: List<TokenInfo>
    abstract val filteredTokens: List<TokenInfo>
    abstract val originalText: CharSequence
    abstract val originalTextRange: IntRange

    fun getRawCharAt(index: Int): Char {
        if (index < originalTextRange.start) return 0.toChar()
        if (index > originalTextRange.endInclusive) return 0.toChar()
        return originalText[index]
    }

    protected fun verify() {
        for (i in cachedTokens.indices) {
            assert(cachedTokens[i].rawIndex == i)
        }
        for (i in filteredTokens.indices) {
            assert(filteredTokens[i].normIndex == i)
        }
    }

    inner class MutableRangeListIterator private constructor(private val ranges: List<IntRange>,
                                                             private var listIndex: Int,
                                                             value: Int) : MutableIterator(value) {
        constructor(ranges: List<IntRange>) : this(ranges, 0, ranges.firstOrNull()?.start ?: -1)

        override fun mutableCopy() = MutableRangeListIterator(ranges, listIndex, index)

        override fun advance(): MutableRangeListIterator {
            if (listIndex >= ranges.size) {
                return this
            }

            if (index == ranges[listIndex].endInclusive) {
                listIndex++
                index = ranges.getOrNull(listIndex)?.start ?: filteredTokens.size
            }
            else {
                index++
            }
            return this
        }

        override fun rollback(): MutableRangeListIterator {
            if (listIndex < 0) {
                return this
            }
            if (index == ranges[listIndex].start) {
                listIndex--
                index = ranges.getOrNull(listIndex)?.endInclusive ?: -1
            }
            else {
                index--
            }
            return this
        }

        override fun rawLookup(steps: Int): IElementType? {
            if (index + steps in ranges.getOrNull(listIndex) ?: return null) {
                return super.rawLookup(steps)
            }
            return null
        }
    }

    inner open class MutableIterator(override var index: Int) : Iterator() {
        override fun mutableCopy() : MutableIterator = MutableIterator(index)

        open fun advance(): MutableIterator {
            index++
            return this
        }

        open fun rollback(): MutableIterator {
            index--
            return this
        }
    }

    inner abstract class Iterator {
        abstract val index: Int

        abstract fun mutableCopy() : MutableIterator

//        abstract fun advance(): Iterator
//
//        abstract fun rollback(): Iterator

        val type : IElementType?
            get() {
                return info(0).type
            }

        val firstChar: Char
            get() {
                return getRawCharAt(info(0).tokenStart)
            }

        val length: Int
            get() {
                return info(0).tokenEnd - info(0).tokenStart
            }

        val start: Int
            get() {
                return info(0).tokenStart
            }

        val end: Int
            get() {
                return info(0).tokenEnd
            }

        private fun info(rawSteps: Int): TokenInfo {
            if (index < 0) {
                return TokenInfo(null, originalTextRange.start, originalTextRange.start, 0, 0)
            } else if (index > filteredTokens.size) {
                return TokenInfo(null, originalTextRange.endInclusive + 1, originalTextRange.endInclusive + 1, 0, 0)
            }

            val rawIndex = if (index < filteredTokens.size)
                filteredTokens[index].rawIndex + rawSteps
            else
                cachedTokens.size + rawSteps

            if (rawIndex < 0) {
                return TokenInfo(null, originalTextRange.start, originalTextRange.start, 0, 0)
            } else if (rawIndex >= cachedTokens.size) {
                return TokenInfo(null, originalTextRange.endInclusive + 1, originalTextRange.endInclusive + 1, 0, 0)
            }

            return cachedTokens[rawIndex]
        }


        open fun rawLookup(steps: Int): IElementType? {
            return info(steps).`type`
        }

        fun rawStart(steps: Int): Int {
            return info(steps).tokenStart
        }

        open fun charLookup(steps: Int): Char {
            if (steps == 0) {
                return getRawCharAt(start)
            }
            if (steps == 1) {
                return getRawCharAt(end)
            } else if (steps == -1) {
                return getRawCharAt(start - 1)
            } else {
                val pos = if (steps > 0) rawStart(steps) else rawStart(steps + 1) - 1
                return getRawCharAt(pos)
            }
        }

        override fun toString(): String {
            return "Iterator: $index: $type"
        }
    }

    class TokenInfo(val `type`: IElementType?,
                           val tokenStart: Int,
                           val tokenEnd: Int,
                           val rawIndex: Int,
                           var normIndex: Int) {

        override fun toString(): String {
            return "TokenInfo: " + `type`.toString() + " [" + tokenStart + ", " + tokenEnd + ")"
        }
    }

}
