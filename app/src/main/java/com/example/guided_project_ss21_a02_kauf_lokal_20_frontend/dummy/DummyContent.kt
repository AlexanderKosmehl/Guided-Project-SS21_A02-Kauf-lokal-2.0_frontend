package com.example.guided_project_ss21_a02_kauf_lokal_20_frontend.dummy

import java.util.ArrayList
import java.util.HashMap
import kotlin.random.Random

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): DummyItem {
        return DummyItem(position.toString(), "Händler $position", Random.nextInt(5), "Shop", listOf(true, false).random(), Random.nextInt(6)*50)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val title: String, val rating: Int, val vendorCategory: String, val isOpen: Boolean, val distance: Int)
}