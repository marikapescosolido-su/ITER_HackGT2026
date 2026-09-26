package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LanternTest {
    // Saturday; the week started Monday 2026-09-21.
    private val saturday = LocalDate.of(2026, 9, 26)
    private val monday = LocalDate.of(2026, 9, 21)

    private fun checkIns(vararg daysAfterMonday: Int) = daysAfterMonday.map {
        CheckIn(monday.plusDays(it.toLong()), emptyMap(), 7f, true)
    }

    @Test fun `new week with no check-ins is an ember, not out`() {
        assertEquals(0, Lantern.compute(emptyList(), monday).level)
    }

    @Test fun `each check-in brightens one step`() {
        val state = Lantern.compute(checkIns(0, 1, 2, 3, 4), saturday)
        assertEquals(5, state.level) // Mon-Fri done, Saturday not yet
        assertFalse(state.checkedInToday)
    }

    @Test fun `a missed day dims one step`() {
        // Mon, Tue done; Wed missed; Thu, Fri done -> 1, 2, 1, 2, 3
        assertEquals(3, Lantern.compute(checkIns(0, 1, 3, 4), saturday).level)
    }

    @Test fun `never goes below the ember`() {
        // Only Friday done; Mon-Thu missed -> stays 0, then 1
        assertEquals(1, Lantern.compute(checkIns(4), saturday).level)
    }

    @Test fun `full week reaches the brightest level`() {
        val sunday = monday.plusDays(6)
        val state = Lantern.compute(checkIns(0, 1, 2, 3, 4, 5, 6), sunday)
        assertEquals(Lantern.MAX, state.level)
        assertTrue(state.checkedInToday)
    }

    @Test fun `previous week does not count`() {
        val lastWeek = listOf(CheckIn(monday.minusDays(1), emptyMap(), 7f, true))
        assertEquals(0, Lantern.compute(lastWeek, monday).level)
    }
}
