package com.example.socializer.models

data class ExternalContact(
    val contactId: Long,
    val lookupKey: String,
    val displayName: String,
)