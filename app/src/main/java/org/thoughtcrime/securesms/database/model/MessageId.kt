package org.thoughtcrime.securesms.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents a pair of values that can be used to find a message. Because we have two tables,
 * that means this has both the primary key and a boolean indicating which table it's in.
 */
@Parcelize
data class MessageId(
  val id: Long,
  @get:JvmName("isMms") val mms: Boolean
) : Parcelable {
  fun serialize(): String {
    return "$id|$mms"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MessageId

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }

  companion object {
    /**
     * Returns null for invalid IDs. Useful when pulling a possibly-unset ID from a database, or something like that.
     */
    @JvmStatic
    fun fromNullable(id: Long, mms: Boolean): MessageId? {
      return if (id > 0) {
        MessageId(id, mms)
      } else {
        null
      }
    }

    @JvmStatic
    fun deserialize(serialized: String): MessageId {
      val parts: List<String> = serialized.split("|")
      return MessageId(parts[0].toLong(), parts[1].toBoolean())
    }
  }
}
