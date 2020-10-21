package com.example.socializer.models

class Group(val name: String, val contacts: List<Contact>, val messages: List<Message>) {

    constructor(name: String) : this(name, emptyList(), emptyList())
}